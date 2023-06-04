

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame{

    private JTextField emailtextField1;
    private JButton loginButton;
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JButton backButton;
    private int CID;

    public Login() {
        setContentPane(panel1);
        setTitle("LOGIN");
        setBounds(600,200,300,400);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String email = emailtextField1.getText();
            String password = passwordField1.getText();
            if (log(email, password)) {
                // Login successful
                Choose x = new Choose(CID);
                setVisible(false);
            } else {
                // Login failed
                JOptionPane.showMessageDialog(panel1,"wrong pass or email");

            }

        }
    });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LR x =new LR();
                setVisible(false);
            }
        });
    }
    public boolean log(String email, String password) {
        boolean success = false;
        try {
            // Establish a connection to the database
            Connection conn = new Connect().getConnection();

            // Prepare a SQL statement to check if the email and password match a customer user
            String sql = "SELECT * FROM Customers WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            // Execute the SQL statement and check if a customer user was found
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                success = true;
                CID = rs.getInt("customer_id");
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
        Login x = new Login();

    }
}
