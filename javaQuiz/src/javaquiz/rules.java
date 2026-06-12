


package javaQuiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class rules extends JFrame implements ActionListener {

    String name;
    JButton start, back;

    rules(String name) {
        this.name = name;
        setLayout(null);

        // --- Background image setup ---
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/rules.jpeg")); 
        Image i2 = i1.getImage().getScaledInstance(800, 650, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 800, 650);
        add(background);

        // --- Heading ---
        JLabel heading = new JLabel("Follow the Rules!");
        heading.setBounds(120, 90, 600, 40);
        heading.setFont(new Font("Algerian", Font.BOLD, 24));
        heading.setForeground(new Color(0, 51, 102));
        background.add(heading);

        // --- Rules text ---
        JLabel rules = new JLabel();
        rules.setBounds(120, 160, 560, 350); // within the white box area
        rules.setFont(new Font("Tahoma", Font.PLAIN, 16));
        rules.setForeground(Color.BLACK);
        rules.setText(
            "<html>" +
                "1. Read each question carefully before answering.<br><br>" +
                "2. Each question has only one correct answer — choose wisely!<br><br>" +
                "3. You have 15 seconds to answer each question.<br><br>" +
                "4. Use the 50-50 lifeline only once during the quiz.<br><br>" +
                "5. Do not close the window or refresh the page during the quiz.<br><br>" +
                "6. Each correct answer gives you 10 points — no negative marking!<br><br>" +
                "7. Your final score will be displayed at the end of the quiz.<br><br>" +
                "8. Most importantly, have fun and learn something new!<br><br>" +
            "</html>"
        );
        background.add(rules);

        // --- Buttons (same style as login page) ---
        back = new JButton("Back");
        back.setBounds(250, 520, 120, 35);
        back.setBackground(Color.WHITE);
        back.setForeground(new Color(30, 144, 255));
        back.setFont(new Font("Tahoma", Font.BOLD, 16));
        back.addActionListener(this);
        background.add(back);

        start = new JButton("Start");
        start.setBounds(420, 520, 120, 35);
        start.setBackground(Color.WHITE);
        start.setForeground(new Color(30, 144, 255));
        start.setFont(new Font("Tahoma", Font.BOLD, 16));
        start.addActionListener(this);
        background.add(start);

        // --- Frame setup ---
        setSize(800, 650);
        setLocation(350, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == start) {
            setVisible(false);
            new quiz(name);
        } else {
            setVisible(false);
            new login();
        }
    }

    public static void main(String[] args) {
        new rules("User");
    }
}
