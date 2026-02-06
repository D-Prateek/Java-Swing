import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Signup extends JFrame {

    JLabel lblUser, lblEmail, lblPass, lblConfirm;
    JTextField txtUser, txtEmail;
    JPasswordField txtPass, txtConfirm;
    JButton signupBtn;

    public Signup() {
        setTitle("Signup");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(350, 180, 500, 350);

        lblUser = new JLabel("Username");
        lblEmail = new JLabel("Email");
        lblPass = new JLabel("Password");
        lblConfirm = new JLabel("Confirm Password");

        txtUser = new JTextField();
        txtEmail = new JTextField();
        txtPass = new JPasswordField();
        txtConfirm = new JPasswordField();

        signupBtn = new JButton("Signup");

        lblUser.setBounds(50, 40, 120, 30);
        txtUser.setBounds(180, 40, 220, 30);

        lblEmail.setBounds(50, 90, 120, 30);
        txtEmail.setBounds(180, 90, 220, 30);

        lblPass.setBounds(50, 140, 120, 30);
        txtPass.setBounds(180, 140, 220, 30);

        lblConfirm.setBounds(50, 190, 120, 30);
        txtConfirm.setBounds(180, 190, 220, 30);

        signupBtn.setBounds(180, 250, 120, 35);

        signupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupUser();
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

            // Go back to Login page
            new Login();
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Email already exists!");
        }
    }
}
