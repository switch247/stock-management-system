import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SupplierRegister extends JFrame{
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JPasswordField passwordPasswordField;
    private JTextField contact_informationTextField;
    private JButton REGISTERButton;
    private JButton BACKButton;
    private JPanel panel1;

    public SupplierRegister() {
        setContentPane(panel1);
        setTitle("Register");
        setBounds(600,200,300,400);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    REGISTERButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailTextField.getText();
            String password = passwordPasswordField.getText();
            String contactInfo = contact_informationTextField.getText();
            String supplierName = nameTextField.getText();
            if (register(supplierName,email, password, contactInfo)) {
                // Registration successful
                SupplierMenu x = new SupplierMenu();
                setVisible(false);
            } else {
                // Registration failed
                JOptionPane.showMessageDialog(panel1,"fill out every thing please");
            }

        }
    });
    BACKButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
}
    public boolean register( String supplierName,String email, String password, String contactInfo) {
        boolean success = false;
        try {
            // Establish a connection to the database
            Connection conn = new Connect().getConnection();

            // Prepare a SQL statement to insert a new supplier into the Supplier table
            String sql = "INSERT INTO suppliers (supplier_name,email, password, contact_info) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, supplierName);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, contactInfo);

            // Execute the SQL statement and check if the supplier was added successfully
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

            // Close the database connection and resources
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void main(String[] args) {

        SupplierRegister x = new SupplierRegister();

    }
}
