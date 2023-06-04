

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class View extends JFrame{
    private JTable table1;
    private JButton BACKButton;
    private JPanel panel1;

    public View() {

        setContentPane(panel1);
        setTitle("veiw");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    table1.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"PID", "Product Name","Description", "Price","Quantity"}
    ));
    BACKButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupplierMenu x =new SupplierMenu();
            setVisible(false);
        }
    });
        displayProducts();
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


    public static void main(String[] args) {
        View x =new View();

    }
}
