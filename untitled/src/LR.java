

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LR extends JFrame{
    private JButton LOGINButton;
    private JButton REGISTERButton;
    private JPanel panel;
    private JButton backButton;

    public LR(){

        setContentPane(panel);
        setTitle("Login or Register ");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    LOGINButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login l =  new Login();
            setVisible(false);

        }
    });
    REGISTERButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Register r =  new Register();
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
    LR x = new LR();

    }
}
