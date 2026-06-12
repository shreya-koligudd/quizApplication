package javaQuiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class login extends JFrame implements ActionListener {
    JButton loginBtn, registerBtn; // CHANGED: was "rules, back" now "loginBtn, registerBtn"
    JTextField tfname;
    JPasswordField tfpassword; // NEW: password field (hides text with ***)

    login() {
        setSize(1200, 500);
        setLocation(200, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/quiz_frame.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(1200, 500, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 1200, 500);
        add(background);
        background.setLayout(null);

        JLabel heading = new JLabel("SMART QUIZ");
        heading.setBounds(100, 60, 400, 60);
        heading.setFont(new Font("Algerian", Font.BOLD, 48));
        heading.setForeground(Color.BLACK);
        background.add(heading);

        // Username -- SAME AS BEFORE just label changed to "Username:"
        JLabel name = new JLabel("Username:");
        name.setBounds(100, 150, 250, 25);
        name.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
        name.setForeground(Color.BLACK);
        background.add(name);

        tfname = new JTextField();
        tfname.setBounds(100, 185, 300, 30);
        tfname.setFont(new Font("Times New Roman", Font.BOLD, 20));
        background.add(tfname);

        // NEW: Password label and field added below username
        JLabel pass = new JLabel("Password:");
        pass.setBounds(100, 225, 250, 25);
        pass.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
        pass.setForeground(Color.BLACK);
        background.add(pass);

        // NEW: JPasswordField hides typed text with *** for security
        tfpassword = new JPasswordField();
        tfpassword.setBounds(100, 260, 300, 30);
        tfpassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
        background.add(tfpassword);

        // CHANGED: was "Rules" button, now "Login" button
        loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 310, 120, 35);
        loginBtn.setBackground(new Color(30, 144, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        loginBtn.addActionListener(this);
        background.add(loginBtn);

        // NEW: Register button for new users
        registerBtn = new JButton("Register");
        registerBtn.setBounds(240, 310, 120, 35);
        registerBtn.setBackground(new Color(34, 139, 34));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        registerBtn.addActionListener(this);
        background.add(registerBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String username = tfname.getText();
        String password = new String(tfpassword.getPassword()); // NEW: reads password field

        // NEW: checks if fields are empty before doing anything
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!");
            return;
        }

        if (ae.getSource() == loginBtn) {
    boolean success = DBHelper.loginUser(username, password);
    if (success) {
        setVisible(false);
        // NEW: if username is "admin" go to admin panel, else go to quiz
        if (username.equals("admin")) {
            new AdminPanel();
        } else {
            new rules(username);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password!");
    }
} else if (ae.getSource() == registerBtn) {
            // NEW: saves new user to MySQL database
            boolean success = DBHelper.registerUser(username, password);
            if (success) {
                // NEW: shows success popup
                JOptionPane.showMessageDialog(this, "Registered successfully! Please login.");
            } else {
                // NEW: shows error popup if registration fails
                JOptionPane.showMessageDialog(this, "Registration failed! Try again.");
            }
        }
    }

    public static void main(String[] args) {
        new login();
    }
}