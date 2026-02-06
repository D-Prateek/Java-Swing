import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    JLabel username, password;
    JTextField txtfirst;
    JPasswordField txtsecond;

    public Login() {
        setTitle("Workspace");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(245, 245, 245));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(50, 50, 500, 300);
        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setBorder(BorderFactory.createTitledBorder("Login"));

        username = new JLabel("Username");
        password = new JLabel("Password");
        username.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        password.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));


        txtfirst = new JTextField();
        txtsecond = new JPasswordField();
        txtfirst.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtsecond.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));


        JButton loginbtn = new JButton("Login");
        JButton Signup = new JButton("Signup");
        loginbtn.setBackground(new java.awt.Color(33, 150, 243));
        loginbtn.setForeground(java.awt.Color.WHITE);
        Signup.setBackground(new java.awt.Color(76, 175, 80));
        Signup.setForeground(java.awt.Color.WHITE);

        loginbtn.setFocusPainted(false);
        Signup.setFocusPainted(false);

        loginbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { loginbtn.setBackground(new java.awt.Color(30, 136, 229)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { loginbtn.setBackground(new java.awt.Color(33, 150, 243)); }
        });
        Signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { Signup.setBackground(new java.awt.Color(67, 160, 71)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { Signup.setBackground(new java.awt.Color(76, 175, 80)); }
        });

        JToggleButton eyeButton = new JToggleButton("👁️");
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
                    txtsecond.setEchoChar((char) 0);
                } else {
                    txtsecond.setEchoChar(defaultEchoChar);
                }
            }
        });

        // LOGIN button action
        loginbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        // SIGNUP button action
        Signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Signup(); // opens signup page
                dispose();
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

    private void loginUser() {
        String uname = txtfirst.getText();
        String pass = String.valueOf(txtsecond.getPassword());

        if (uname.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }

        try {
            Connection con = DBconnection.getConnection();
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!");
                return;
            }

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, uname);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                new Dashboard(uname);
                dispose();
            } else {

                JOptionPane.showMessageDialog(this, "User not found! Please signup.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
