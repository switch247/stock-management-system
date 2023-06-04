


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Choose extends JFrame{
    private int CID;
    private JButton browseStoreButton;
    private JButton myOrdersButton;
    private JButton backButton;
    private JPanel panel1;
    public Choose(int cid) {
        CID =cid;

        setContentPane(panel1);
        setTitle("Choose");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    browseStoreButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Buy x =new Buy(CID);
            setVisible(false);

        }
    });
    myOrdersButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MyOrders x = new MyOrders(CID);
            setVisible(false);

        }
    });
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LR x = new LR();
            setVisible(false);

        }
    });
}


    public static void main(String[] args) {
        Choose x =new Choose(1);

    }
}
