import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Index extends JFrame{
    private JButton supplierButton;
    private JButton customerButton;
    private JPanel panel1;

    public Index() {
    setContentPane(panel1);

    setTitle("WELCOME");
    setBounds(600,200,1200,600);
//        x.setSize(300,400);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    customerButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LR x = new LR();
            setVisible(false);
        }
    });
    supplierButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupplierLR x = new SupplierLR();
            setVisible(false);
        }
    });
}

    public static void main(String[] args) {
        Index x = new Index();
    }
}
