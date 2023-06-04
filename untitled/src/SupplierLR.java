
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupplierLR extends JFrame{
    private JButton LOGINButton;
    private JButton REGISTERButton;
    private JPanel panel1;
    private JButton backButton;

    public SupplierLR() {
    setContentPane(panel1);
    setTitle("LOGIN OR REGISTER");
    setBounds(600,200,1200,600);
//        x.setSize(300,400);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    LOGINButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupplierLogin x = new SupplierLogin();
            setVisible(false);

        }
    });
    REGISTERButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupplierRegister x = new SupplierRegister();
            setVisible(false);



        }
    });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Index x = new Index();
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        SupplierLR x = new SupplierLR();

    }
}
