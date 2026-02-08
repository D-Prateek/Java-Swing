import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditProfile extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton saveBtn, backBtn;
    JLabel usernameInfoLabel;

    String currentUsername;

    public EditProfile(String username) {
        currentUsername = username;

        setTitle("Edit Profile");
        setSize(500, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("New Username:");
        JLabel lblPass = new JLabel("New Password:");

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        lblUser.setBounds(50, 60, 150, 30);
        txtUsername.setBounds(200, 60, 200, 30);

        lblPass.setBounds(50, 110, 150, 30);
        txtPassword.setBounds(200, 110, 200, 30);

        saveBtn = new JButton("Save Changes");
        saveBtn.setBounds(170, 170, 150, 35);
        saveBtn.addActionListener(e -> updateProfile());

        backBtn = new JButton("Back");
        backBtn.setBounds(170, 220, 150, 35);
        backBtn.setBackground(new java.awt.Color(158, 158, 158));
        backBtn.setForeground(java.awt.Color.WHITE);
        backBtn.setFocusPainted(false);

        usernameInfoLabel = new JLabel("");
        usernameInfoLabel.setBounds(200, 90, 260, 20);
        usernameInfoLabel.setForeground(Color.RED);
        usernameInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        add(usernameInfoLabel);

        checkUsernameChangeLimit();


        backBtn.addActionListener(e -> {
            new Dashboard(currentUsername);
            dispose();
        });

        add(lblUser);
        add(txtUsername);
        add(lblPass);
        add(txtPassword);
        add(saveBtn);
        add(backBtn);

        setVisible(true);
    }

    private void checkUsernameChangeLimit() {
        try {
            Connection con = DBconnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT last_username_change FROM users WHERE username = ?");
            ps.setString(1, currentUsername);

            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getDate("last_username_change") != null) {

                long lastChange = rs.getDate("last_username_change").getTime();
                long now = System.currentTimeMillis();

                long daysPassed = (now - lastChange) / (1000 * 60 * 60 * 24);

                if (daysPassed < 30) {
                    long daysLeft = 30 - daysPassed;

                    txtUsername.setEnabled(false);

                    usernameInfoLabel.setText(
                            "You can change username again in " + daysLeft + " day(s)");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void updateProfile() {
        String newUsername = txtUsername.getText().trim();
        String newPassword = String.valueOf(txtPassword.getPassword());

        try {
            Connection con = DBconnection.getConnection();

            PreparedStatement psDate = con.prepareStatement(
                    "SELECT last_username_change FROM users WHERE username = ?");
            psDate.setString(1, currentUsername);
            ResultSet rs = psDate.executeQuery();

            if (rs.next() && rs.getDate("last_username_change") != null) {
                long diff = System.currentTimeMillis() -
                        rs.getDate("last_username_change").getTime();
                long days = diff / (1000 * 60 * 60 * 24);

                if (days < 30) {
                    JOptionPane.showMessageDialog(this,
                            "Username can only be changed once per month!");
                    return;
                }
            }

            PreparedStatement checkUser = con.prepareStatement(
                    "SELECT * FROM users WHERE username = ?");
            checkUser.setString(1, newUsername);

            if (!txtUsername.isEnabled()) {
                JOptionPane.showMessageDialog(this,
                        "Username change is currently locked.");
                return;
            }


            if (checkUser.executeQuery().next()) {
                JOptionPane.showMessageDialog(this,
                        "Username already exists!");
                return;
            }

            PreparedStatement update = con.prepareStatement(
                    "UPDATE users SET username=?, password=?, last_username_change=CURDATE() WHERE username=?");

            update.setString(1, newUsername);
            update.setString(2, newPassword);
            update.setString(3, currentUsername);

            update.executeUpdate();

            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            new Dashboard(newUsername);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update failed!");
        }
    }

}
