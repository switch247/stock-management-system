//package Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Orders extends JFrame{
    private JTable table1;
    private JButton backButton;
    private JPanel panel1;
    private JSpinner orderidspinner;
    private JButton cancelOrderButton;

    public Orders() {
        table1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"PID","Quantity","Total Price", "Order_id","customer_id", "order_date","Status"}
        ));
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            SupplierMenu x = new SupplierMenu();
            setVisible(false);
            int oid = (int) orderidspinner.getValue();
        }
    });

        setContentPane(panel1);
        setTitle("ORDERS");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayProducts();
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int oid = (int) orderidspinner.getValue();
                if (CANCELORDERS(oid)) {
                    // CANCEL successful
                    JOptionPane.showMessageDialog(panel1,"SUCCESSFUL");
                    Orders x =new Orders();
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


    private void displayProducts() {
        try {
            Connect connect = new Connect();
            Connection connection = connect.getConnection();
            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            ResultSet resultSet = statement.executeQuery(
                    "SELECT products.price, order_items.product_id, order_items.quantity,order_items.Order_id,orders.customer_id,orders.status,orders.order_date " +
                            "FROM order_items " + " JOIN products ON  order_items.product_id = products.product_id"+
                            " JOIN orders ON order_items.Order_id = orders.Order_id"
                       //  +   "select products.product_name join order_items on  products.product_id = order_items.product_id"

            );

            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            while (resultSet.next()) {
                int pid = resultSet.getInt("product_id");
                String Order_id = resultSet.getString("Order_id");
////              // String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                int customer_id = resultSet.getInt("customer_id");
                String order_date= resultSet.getString("order_date");
                String Status = resultSet.getString("Status");
                double x =(price *quantity);

                if(quantity>0)
                    model.addRow(new Object[]{pid, quantity,x,Order_id,customer_id,order_date ,Status});
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Orders x =new Orders();


    }
}
