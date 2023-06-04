import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Buy extends JFrame{
    private JButton clear;
    private JPanel panel1;
    private JTable table1;
    private JTextField enterPidTextField;
    private JButton ADDToCartButton;
    private JButton removeButton;
    private JButton BUYButton;
    private JTextField removePidTextField1;
    private JTable table2;
    private JButton BACKButton;
    private JTextField textField2;
    private int CID;

    public Buy(int cid) {
        CID =cid;
        setContentPane(panel1);
        setTitle("Buy");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"PID", "Product Name","Description", "Price","Quantity"}
        ));
        table2.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"PID", "Product Name","Description", "Price","Quantity"}
        ));

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table2.getModel();
                model.setRowCount(0);

            }
        });


        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pid = Integer.parseInt(removePidTextField1.getText());
                DefaultTableModel model = (DefaultTableModel) table2.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(pid)) {
                        model.removeRow(i);
                        break;
                    }
                }
            }
        });
        ADDToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProducts( Integer.parseInt( enterPidTextField.getText() ) );

            }
        });
        BUYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table2.getRowCount();
                int column = table2.getColumnCount();
                int id=0,quantity=0;
boolean flag =false;
                for (int j = 0; j < row; j++) {
                    for (int i = 0; i < column; i++) {
                        if (i == 0 )
                            id = (int) table2.getValueAt(j, i);
                        if (i == 4 )
                            quantity= (int) table2.getValueAt(j, i);
                        System.out.println(table2.getValueAt(j, i));
                    }
                    System.out.println("\n"+id+" "+quantity);

                    if (BuyProduct(id, quantity)) {
                        // BUY successful
                        flag =true;


                    } else {
                        // Buy failed
                        flag =false;
                        JOptionPane.showMessageDialog(panel1,"Failed To Complete");

                    }
                    if(flag){
                        JOptionPane.showMessageDialog(panel1,"SUCCESSFUL");
                        MyOrders x = new MyOrders(CID);
                        setVisible(false);}


                }


            }
        });
        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Choose x= new Choose(CID);
                setVisible(false);

            }
        });
        displayProducts();
    }
    private void Products(int id) {
        try {
            Connect connect = new Connect();
            Connection connection = connect.getConnection();
            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            ResultSet resultSet = statement.executeQuery(
                    "SELECT products.product_id, products.product_name,products.description ,products.price, inventory.quantity " +
                            "FROM products " +
                            "JOIN inventory ON products.product_id = inventory.product_id"

            );
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            while (resultSet.next()) {
                int pid = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                if(quantity>0)
                    model.addRow(new Object[]{pid, productName,description ,price,quantity});
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        }

    private void AddProducts(int id) {
        try {
            Connect connect = new Connect();
            Connection connection = connect.getConnection();
            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            ResultSet resultSet = statement.executeQuery(
                    "SELECT products.product_id, products.product_name,products.description ,products.price, inventory.quantity " +
                            "FROM products " +
                            "JOIN inventory ON products.product_id = inventory.product_id"+ " Where products.product_id = "+id

            );

            DefaultTableModel model = (DefaultTableModel) table2.getModel();
            int quantity;
    String quant;
            quant = textField2.getText();

    if (Integer.parseInt(quant)>0){
        quantity = Integer.parseInt(quant);}
    else quantity =1;
            while (resultSet.next()) {
                int pid = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");



                if(quantity>0)
                    model.addRow(new Object[]{pid, productName,description ,price,quantity});
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayProducts() {
        try {
            Connect connect = new Connect();
            Connection connection = connect.getConnection();
            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            ResultSet resultSet = statement.executeQuery(
                    "SELECT products.product_id, products.product_name,products.description ,products.price, inventory.quantity " +
                            "FROM products " +
                            "JOIN inventory ON products.product_id = inventory.product_id"

            );
            
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            while (resultSet.next()) {
                int pid = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                if(quantity>0)
                model.addRow(new Object[]{pid, productName,description ,price,quantity});
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean BuyProduct (int id , int quantity){
        boolean success = false;
        int quantity1 =0;
        try {
            // Establish a connection to the database
            Connection conn = new Connect().getConnection();
            String sq = "SELECT * FROM inventory WHERE   product_id = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sq);
            stmt1.setInt(1, id);
            ResultSet rss = stmt1.executeQuery();
            while (rss.next()) {
                quantity1 = rss.getInt("quantity");
            }
            System.out.println(quantity1);
            System.out.println("\n"+CID);


            if(quantity< quantity1){
            int x =-quantity+quantity1;

            String query = "UPDATE inventory SET quantity = ? WHERE product_id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, x);
            preparedStmt.setInt(2, id);
            int rowsUpdated = preparedStmt.executeUpdate();

            if (rowsUpdated > 0) {
                success = true;
                System.out.println("Update successful");
//                update orders and order items table
                query = " INSERT INTO `orders` (`order_id`, `customer_id`, `order_date`, `status`) VALUES (NULL, " + CID + ", current_timestamp(), 'pending')";
                preparedStmt = conn.prepareStatement(query);
//                preparedStmt.setInt(1, CID);
                int rowsUpdated2 = preparedStmt.executeUpdate();
                if (rowsUpdated2 > 0) {
                    success = true;
//get
                    query = "SELECT * FROM orders ORDER BY order_id DESC LIMIT 1";
                    preparedStmt = conn.prepareStatement(query);

                    ResultSet result = preparedStmt.executeQuery();
                    int order_id =0;
                    if (result.next()) {
                        success = true;
                        order_id = result.getInt("order_id");
                        System.out.println("succesfully got order_id"+order_id);
//                now update order items table
                        query = " INSERT INTO order_items ( order_id, product_id, quantity) VALUES ( ?,?,?) ";
                        preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setInt(1, order_id);
                        preparedStmt.setInt(2, id);
                        preparedStmt.setInt(3, quantity);
                        int rowsUpdated3 = preparedStmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            success = true;}
                        else
                            success = false;


                    }




                }
                else success = false;




            } else {
                System.out.println("Update failed");
            }

            // Close the database connection and resources
            rss.close();
            stmt1.close();
//        rs.close();
//        stmt.close();
            conn.close();}
            else{
                JOptionPane.showMessageDialog(panel1,"item unavailable");
//                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    };

        public static void main(String[] args) {
        Buy x =new Buy(1);

    }



}
