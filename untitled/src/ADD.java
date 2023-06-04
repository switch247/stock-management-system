
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ADD extends JFrame{
    private JTextField product_nameTextField;
    private JButton ADDButton;
    private JButton BACKButton;
    private JButton VIEWButton;
    private JTextArea descriptionTextField;
    private JComboBox addressTextField;
    private JComboBox location_nameTextField;
    private JSpinner spinner2;
    private JTextField spinner1;
    private JPanel panel1;

    public ADD() {
    ADDButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String product_name =  product_nameTextField.getText();
            String description =  descriptionTextField.getText();
//            int price = (int) spinner1.getValue();
            double price = Double.parseDouble(spinner1.getText());

            int quantity = (int) spinner2.getValue();

            String location_name =  (String) location_nameTextField.getSelectedItem();
            String address =   (String)addressTextField.getSelectedItem();

            try {
                if(
                insertProduct(new Connect().getConnection(),product_name,description,price,quantity,location_name,address))
                    JOptionPane.showMessageDialog(panel1,"success");
                    System.out.println("added");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        }
    });
    VIEWButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            View x =new View();
            setVisible(false);
        }
    });
    BACKButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupplierMenu x =new SupplierMenu();
            setVisible(false);
        }
    });
        setContentPane(panel1);

        setTitle("ADD ITEMS");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}

    public boolean insertProduct(Connection connection, String productName, String description, double price, int quantity, String locationName, String address) throws SQLException {
        boolean success = false;

        String query = "INSERT INTO products (product_name, description, price) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, productName);
        statement.setString(2, description);
        statement.setDouble(3, price);
//        statement.setInt(4, quantity);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated < 0)
            success = false;
        else{
            success = true;
            System.out.println("products table succesfully updated");
        }

//adds locations if they do not already exist
        query = "INSERT INTO locations (location_name, address) SELECT ?, ? WHERE NOT EXISTS (SELECT * FROM locations WHERE location_name = ? AND address = ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, locationName);
        statement.setString(2, address);
        statement.setString(3, locationName);
        statement.setString(4, address);
        int rowsUpdated2 = statement.executeUpdate();

        if (rowsUpdated2 < 0){
            success = false;
        return success;
    }
        else {success =true;
            System.out.println("locations table succesfully updated");

        }

        //select location id to add to inventory table
        query = "SELECT DISTINCT location_id FROM locations WHERE location_name = ? AND address = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, locationName);
        statement.setString(2, address);
        ResultSet result = statement.executeQuery();
        int loc_id=1;
        if (result.next()) {
            success = true;
            loc_id = result.getInt("location_id");
            System.out.println("succesfully got location id"+loc_id);


        }
        else{
            success = false;
        return success;}

//select product id to add to inventory the last row should contain what we just added
        query = "SELECT * FROM products ORDER BY product_id DESC LIMIT 1";
        statement = connection.prepareStatement(query);
        result = statement.executeQuery();
        int product_id =1;
        if (result.next()) {
            success = true;
            product_id = result.getInt("product_id");
            System.out.println("succesfully got product_id"+product_id);



        }
        else{
            success = false;
        return success;
        }


//insert new item into inventory
        query = "INSERT INTO `inventory` ( `product_id`, `location_id`, `quantity`) VALUES ( ?, ?, ?)";
        statement = connection.prepareStatement(query);
        statement.setInt(1, product_id);
        statement.setInt(2, loc_id);
        statement.setInt(3, quantity);

        int rowsUpdated3 = statement.executeUpdate();

        if(rowsUpdated3<0){
            success =false;
        return success;}

        else success =true;



        return success;


    }


    public static void main(String[] args) {
        ADD x =new ADD();


    }
}
