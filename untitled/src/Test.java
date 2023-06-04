import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame{
    private JTextField textField1;
    private JButton button1;
    private JPanel MainPanel;
    private JButton BUYButton;

    public Test() {
    System.out.printf("test");
    button1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(button1,textField1.getText()+"  Hello");
        }
    });


        BUYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Buy z =new Buy();

            }
        });

}

    public static void main(String[] args) {
        Test x = new Test();
        x.setContentPane(x.MainPanel);
        x.setTitle("test");
        x.setBounds(600,200,300,400);
//        x.setSize(300,400);
        x.setVisible(true);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


}
