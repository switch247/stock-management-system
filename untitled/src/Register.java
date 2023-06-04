
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends JFrame{
    private JTextField nameTextField;
    private JTextField contact_infoTextField;
    private JTextField emailTextField;
    private JPasswordField passwordPasswordField;
    private JButton registerButton;
    private JButton BACKButton;
    private JTextField addressTextField;
    private JPanel panel1;
    private int CID;

    public Register() {
        setContentPane(panel1);
        setTitle("REGISTER");
        setBounds(600,200,300,400);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    registerButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailTextField.getText();
            String password = passwordPasswordField.getText();
            String contactInfo = contact_infoTextField.getText();
            String customer_Name = nameTextField.getText();
            String address = addressTextField.getText();
            if (register(customer_Name,email, password, contactInfo,address)) {
                // Registration successful
                Login x = new Login();
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
            LR x = new LR();
            setVisible(false);

        }
    });
}
    public boolean register( String supplierName,String email, String password, String contactInfo,String address) {
        boolean success = false;
        try {
            // Establish a connection to the database
            Connection conn = new Connect().getConnection();

            // Prepare a SQL statement to insert a new supplier into the Customer table
            String sql = "INSERT INTO customers (customer_name,email, password, contact_info,address) VALUES (?, ?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, supplierName);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, contactInfo);
            stmt.setString(5, address);


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
        Register x = new Register();

    }
}
