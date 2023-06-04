import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Restock extends JFrame{
    private JTable table1;
    private JSpinner PIDTextField;
    private JSpinner quantityTextField;
    private JButton restockButton;
    private JButton backButton;
    private JPanel panel1;
    private JLabel successfull;

    public Restock() {
        setContentPane(panel1);
        setTitle("RESTOCK");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    table1.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"PID", "Product Name","Description", "Price","Quantity"}
    ));
    restockButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            int id = (Integer) PIDTextField.getValue();
            int quantity =(Integer) quantityTextField.getValue();
            if (re(id, quantity)) {
                // RESTOCK successful
                JOptionPane.showMessageDialog(panel1,"SUCCESSFUL");
                Restock x = new Restock();
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
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupplierMenu x = new SupplierMenu();
            setVisible(false);

        }
    });
        displayProducts();
}
public boolean re (int id , int quantity){
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


    int x =quantity+quantity1;

        String query = "UPDATE inventory SET quantity = ? WHERE product_id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, x);
        preparedStmt.setInt(2, id);
        int rowsUpdated = preparedStmt.executeUpdate();

        if (rowsUpdated > 0) {
            success = true;

            System.out.println("Update successful");
        } else {
            System.out.println("Update failed");
        }

//        String sql = "UPDATE inventory SET quantity = ?  WHERE product_id = ? ";
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setInt(1, x);
//        stmt.setInt(2, id);
//
//        // Execute the SQL statement and UPDATE COLUMN
//        //doesnt prpoduce result set??
////        ResultSet rs = stmt.executeQuery();
//        if (!stmt.executeQuery().wasNull()) {
//            success = true;
//        }

        // Close the database connection and resources
        rss.close();
        stmt1.close();
//        rs.close();
//        stmt.close();
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


    public static void main(String[] args) {
        Restock x =new Restock();

    }

}
