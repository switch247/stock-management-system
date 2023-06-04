
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MyOrders extends JFrame{
    private JTable table1;
    private JButton backButton;
    private JPanel panel1;
    private JSpinner orderidspinner;
    private JButton cancelOrderButton;
    private int CID;

    public MyOrders(int cid) {
        CID = cid;
        setContentPane(panel1);
        setTitle("MY Orders");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    table1.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{ "Order_id", "PID", "Product Name","Description", "Price for each","total price","Quantity","Order Date","Status"}
    ));
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Choose x =new Choose(CID);
            setVisible(false);

        }
    });
        displayProducts(CID);
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int oid = (int) orderidspinner.getValue();
                if (CANCELORDERS(oid)) {
                    // CANCEL successful
                    JOptionPane.showMessageDialog(panel1,"SUCCESSFUL");
                    MyOrders x =new MyOrders( CID);
                    setVisible(false);


//                try {
////                    successfull.setText("SUCCESSFUL");
//                    Thread.sleep(2000);
//                    ;
//                } catch (InterruptedException p) {
//                    // handle the exception
//                }



                } else {
                    // RESTOCK failed
                    JOptionPane.showMessageDialog(panel1,"wrong id");

                }


            }
        });
    }
    private void displayProducts(int id) {
        try {
            Connect connect = new Connect();
            Connection connection = connect.getConnection();
            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            ResultSet resultSet = statement.executeQuery(
                    "SELECT products.product_id, products.product_name,products.description ,products.price, order_items.product_id, order_items.quantity,order_items.Order_id,orders.customer_id,orders.status,orders.order_date " +
                            " FROM order_items " +" JOIN products ON  order_items.product_id = products.product_id"+
                            " JOIN orders ON order_items.Order_id = orders.Order_id AND orders.customer_id = "+id
//"SELECT *" +
//        "FROM products" +
////        " JOIN inventory ON products.product_id = inventory.product_id " +
//        " JOIN order_items ON  order_items.product_id = products.product_id"+
//        " JOIN orders ON    orders.order_id = order_items.product_id AND orders.customer_id = "+id

            );

            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            while (resultSet.next()) {

                int oid = resultSet.getInt("order_id");
                int pid = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String date =resultSet.getString("order_date");
                String status =resultSet.getString("status");
                double tot = quantity * price;


                if(quantity>0)
                    model.addRow(new Object[]{ oid, pid, productName,description ,price,tot,quantity,date,status});
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean CANCELORDERS (int id ){
        boolean success = false;
        int quantity1 =0;
        try {
            // Establish a connection to the database
            Connection conn = new Connect().getConnection();
            String query = "UPDATE orders SET status = 'canceled' WHERE order_id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            int rowsUpdated = preparedStmt.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
                System.out.println("Update successful");
            } else {
                System.out.println("Update failed");
            }
            preparedStmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    };


    public static void main(String[] args) {
        MyOrders x =new MyOrders(1);

//        x.displayProducts();
    }
}
