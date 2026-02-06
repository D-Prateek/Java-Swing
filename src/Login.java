import javax.swing.*;

public class Login extends JFrame {
    JLabel username, password;
    JTextField txtfirst;
    JPasswordField txtsecond;

    public Login() {
        setTitle("Workspace");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        username = new JLabel("Username");
        password = new JLabel("Password");

        txtfirst = new JTextField();
        txtsecond = new JPasswordField();

        username.setBounds(400, 250, 100, 30);
        txtfirst.setBounds(500, 250, 200, 30);

        password.setBounds(400, 300, 100, 30);
        txtsecond.setBounds(500, 300, 200, 30);

        add(username);
        add(txtfirst);
        add(password);
        add(txtsecond);

        setVisible(true);
    }
}
