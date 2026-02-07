import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditProfile extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton saveBtn;

    String currentUsername;

    public EditProfile(String username) {
        currentUsername = username;

        setTitle("Edit Profile");
        setSize(500, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblUser = new JLabel("New Username:");
        JLabel lblPass = new JLabel("New Password:");

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        lblUser.setBounds(50, 60, 150, 30);
        txtUsername.setBounds(200, 60, 200, 30);

        lblPass.setBounds(50, 110, 150, 30);
        txtPassword.setBounds(200, 110, 200, 30);

        saveBtn = new JButton("Save Changes");
        saveBtn.setBounds(180, 180, 140, 35);

        saveBtn.addActionListener(e -> updateProfile());

        add(lblUser);
        add(txtUsername);
        add(lblPass);
        add(txtPassword);
        add(saveBtn);

        setVisible(true);
    }

    private void updateProfile() {
        String newUsername = txtUsername.getText().trim();
        String newPassword = String.valueOf(txtPassword.getPassword());

        try {
            Connection con = DBconnection.getConnection();

            // 1️⃣ Check last username change
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

            // 2️⃣ Check if username exists
            PreparedStatement checkUser = con.prepareStatement(
                    "SELECT * FROM users WHERE username = ?");
            checkUser.setString(1, newUsername);
            if (checkUser.executeQuery().next()) {
                JOptionPane.showMessageDialog(this,
                        "Username already exists!");
                return;
            }

            // 3️⃣ Update username & password
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
