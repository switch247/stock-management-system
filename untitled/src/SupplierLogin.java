

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierLogin extends JFrame{
    private JTextField emailTextField;
    private JTextField passwordTextField;
    private JButton BACKButton;
    private JButton LOGINButton;
    private JPanel panel1;

    public SupplierLogin() {
        setContentPane(panel1);
        setTitle("Login");
        setBounds(600,200,300,400);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    LOGINButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            String email =emailTextField.getText();
            String password =passwordTextField.getText();
            if (log(email, password)) {
                // Login successful
                SupplierMenu x = new SupplierMenu();
                setVisible(false);
            } else {
                // Login failed
                JOptionPane.showMessageDialog(panel1,"wrong pass or email");

            }
        }
    });
    BACKButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupplierLR x = new SupplierLR();

        }
    });
}
    public boolean log(String email, String password) {
        boolean success = false;
        try {
            // Establish a connection to the database
            Connection conn = new Connect().getConnection();

            // Prepare a SQL statement to check if the email and password match a customer user
            String sql = "SELECT * FROM suppliers WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            // Execute the SQL statement and check if a customer user was found
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                success = true;
            }

            // Close the database connection and resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }



    public static void main(String[] args) {
        SupplierLogin x = new SupplierLogin();

    }
}
