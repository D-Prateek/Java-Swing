import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    JLabel username, password;
    JTextField txtfirst;
    JPasswordField txtsecond;

    public Login() {
        setTitle("Workspace");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBounds(350,200,500,300);

        username = new JLabel("Username");
        password = new JLabel("Password");

        txtfirst = new JTextField();
        txtsecond = new JPasswordField();

        JButton loginbtn= new JButton("Login");
        JButton Signup= new JButton("Signup");

        JToggleButton eyeButton =new JToggleButton ("👁️");
        eyeButton.setFocusPainted(false);

        username.setBounds(50, 50, 100, 30);
        txtfirst.setBounds(150, 50, 220, 30);

        password.setBounds(50, 100, 100, 30);
        txtsecond.setBounds(150, 100, 220, 30);
        eyeButton.setBounds(380, 100, 50, 30);

        loginbtn.setBounds(120, 170, 100, 35);
        Signup.setBounds(240, 170, 100, 35);

        char defaultEchoChar = txtsecond.getEchoChar();

        eyeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eyeButton.isSelected()) {
                    txtsecond.setEchoChar((char)0);
                }
                else{
                    txtsecond.setEchoChar(defaultEchoChar);
                }
            }
        });

        panel.add(username);
        panel.add(txtfirst);
        panel.add(password);
        panel.add(txtsecond);
        panel.add(eyeButton);
        panel.add(loginbtn);
        panel.add(Signup);
        add(panel);
        setVisible(true);
    }
}
