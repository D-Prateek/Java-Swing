import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class Signup extends JFrame {

    JLabel lblUser, lblEmail, lblPass, lblConfirm;
    JTextField txtUser, txtEmail;
    JPasswordField txtPass, txtConfirm;
    JButton signupBtn,signinBtn;

    public Signup() {
        setTitle("Signup");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(245, 245, 245));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(50, 30, 500, 370);
        panel.setBackground(java.awt.Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Create Account"));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        lblUser = new JLabel("Username:"); lblUser.setFont(labelFont);
        lblEmail = new JLabel("Email:"); lblEmail.setFont(labelFont);
        lblPass = new JLabel("Password:"); lblPass.setFont(labelFont);
        lblConfirm = new JLabel("Confirm Password:"); lblConfirm.setFont(labelFont);

        txtUser = new JTextField(); txtUser.setFont(labelFont);
        txtEmail = new JTextField(); txtEmail.setFont(labelFont);
        txtPass = new JPasswordField(); txtPass.setFont(labelFont);
        txtConfirm = new JPasswordField(); txtConfirm.setFont(labelFont);

        signupBtn = new JButton("Signup");
        signinBtn = new JButton("Sign In");
        signupBtn.setBackground(new java.awt.Color(33, 150, 243));
        signupBtn.setForeground(Color.WHITE);
        signinBtn.setBackground(new java.awt.Color(76, 175, 80));
        signinBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signinBtn.setFocusPainted(false);


        signupBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { signupBtn.setBackground(new java.awt.Color(30, 136, 229)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { signupBtn.setBackground(new java.awt.Color(33, 150, 243)); }
        });
        signinBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { signinBtn.setBackground(new java.awt.Color(67, 160, 71)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { signinBtn.setBackground(new java.awt.Color(76, 175, 80)); }
        });

        lblUser.setBounds(50, 30, 130, 30); txtUser.setBounds(200, 30, 220, 30);
        lblEmail.setBounds(50, 80, 130, 30); txtEmail.setBounds(200, 80, 220, 30);
        lblPass.setBounds(50, 130, 130, 30); txtPass.setBounds(200, 130, 220, 30);
        lblConfirm.setBounds(50, 180, 130, 30); txtConfirm.setBounds(200, 180, 220, 30);
        signupBtn.setBounds(200, 250, 100, 35); signinBtn.setBounds(320, 250, 100, 35);

        signupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupUser();
            }
        });

        signinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });

        panel.add(lblUser);
        panel.add(txtUser);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(lblConfirm);
        panel.add(txtConfirm);
        panel.add(signupBtn);
        panel.add(signinBtn);

        add(panel);
        setVisible(true);
    }

    private void signupUser() {
        String username = txtUser.getText();
        String email = txtEmail.getText();
        String password = String.valueOf(txtPass.getPassword());
        String confirm = String.valueOf(txtConfirm.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }

        try {
            Connection con = DBconnection.getConnection();
            String sql = "INSERT INTO users(username, email, password) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password); // (plain for now, can hash later)

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Signup Successful!");


            new Login();
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Email already exists!");
        }
    }
}
