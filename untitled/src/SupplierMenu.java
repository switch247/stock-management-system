//package Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupplierMenu extends JFrame{

    private JButton ADDINVENTORYButton;
    private JButton RESTOCKButton;
    private JButton VEIWINVENTORYButton;
    private JButton ORDERSButton;
    private JPanel panel1;

    public SupplierMenu() {
        setContentPane(panel1);
        setTitle("Menu");
        setBounds(600,200,1200,600);
//        x.setSize(300,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    RESTOCKButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Restock x =new Restock();
            setVisible(false);

        }
    });
    VEIWINVENTORYButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            View x =new View();
            setVisible(false);

        }
    });
    ADDINVENTORYButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ADD x =new ADD();
            setVisible(false);

        }
    });
    ORDERSButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Orders x =new Orders();
            setVisible(false);

        }
    });
}

    public static void main(String[] args) {
        SupplierMenu x =new SupplierMenu();

    }
}
