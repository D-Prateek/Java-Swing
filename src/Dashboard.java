import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard(String username) {
        setTitle("Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Welcome label at TOP
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setBounds(0, 20, 600, 40);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Edit Profile button
        JButton editProfileBtn = new JButton("Edit Profile");
        editProfileBtn.setBounds(230, 120, 140, 35);
        editProfileBtn.setBackground(new Color(33, 150, 243));
        editProfileBtn.setForeground(Color.WHITE);
        editProfileBtn.setFocusPainted(false);

        editProfileBtn.addActionListener(e -> {
            new EditProfile(username);
            dispose();
        });

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(250, 180, 100, 35);
        logoutBtn.setBackground(new Color(244, 67, 54));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);

        logoutBtn.addActionListener(e -> {
            new Login();
            dispose();
        });

        add(welcomeLabel);
        add(editProfileBtn);
        add(logoutBtn);

        setVisible(true);
    }
}
