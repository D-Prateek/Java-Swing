import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard(String username) {
        setTitle("Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(245, 245, 245));

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setBounds(50, 100, 500, 50);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(250, 200, 100, 35);
        logoutBtn.setBackground(new java.awt.Color(244, 67, 54));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);

        // Hover effect
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new java.awt.Color(211, 47, 47)); }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(new java.awt.Color(244, 67, 54)); }
        });

        logoutBtn.addActionListener(e -> { new Login(); dispose(); });

        add(welcomeLabel);
        add(logoutBtn);
        setVisible(true);
    }

}

