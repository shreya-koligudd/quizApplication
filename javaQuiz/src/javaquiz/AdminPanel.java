package javaQuiz;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel extends JFrame implements ActionListener {

    JButton viewResults, viewUsers, logout;
    JTable table;
    JScrollPane scrollPane;

    AdminPanel() {
        setTitle("Admin Panel");
        setSize(900, 600);
        setLocation(200, 100);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));

        // Heading
        JLabel heading = new JLabel("ADMIN PANEL");
        heading.setBounds(350, 20, 300, 40);
        heading.setFont(new Font("Algerian", Font.BOLD, 28));
        heading.setForeground(new Color(30, 144, 255));
        add(heading);

        // View Results button
        viewResults = new JButton("View All Results");
        viewResults.setBounds(50, 80, 200, 40);
        viewResults.setBackground(new Color(30, 144, 255));
        viewResults.setForeground(Color.WHITE);
        viewResults.setFont(new Font("Tahoma", Font.BOLD, 16));
        viewResults.addActionListener(this);
        add(viewResults);

        // View Users button
        viewUsers = new JButton("View All Users");
        viewUsers.setBounds(280, 80, 200, 40);
        viewUsers.setBackground(new Color(34, 139, 34));
        viewUsers.setForeground(Color.WHITE);
        viewUsers.setFont(new Font("Tahoma", Font.BOLD, 16));
        viewUsers.addActionListener(this);
        add(viewUsers);

        // Logout button
        logout = new JButton("Logout");
        logout.setBounds(700, 80, 120, 40);
        logout.setBackground(new Color(220, 50, 50));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Tahoma", Font.BOLD, 16));
        logout.addActionListener(this);
        add(logout);

        // Table to display data
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 800, 380);
        add(scrollPane);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewResults) {
            // NEW: loads all results from database and shows in table
            showResults();
        } else if (ae.getSource() == viewUsers) {
            // NEW: loads all users from database and shows in table
            showUsers();
        } else if (ae.getSource() == logout) {
            // NEW: logs out and goes back to login screen
            setVisible(false);
            new login();
        }
    }

    private void showResults() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM results ORDER BY attempted_on DESC");

            // NEW: converts database results into table format
            table.setModel(buildTableModel(rs));
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void showUsers() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, username FROM users");

            // NEW: converts database users into table format
            // Note: we don't show passwords for security!
            table.setModel(buildTableModel(rs));
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // NEW: this method converts database ResultSet into JTable format
    private TableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // Get column names
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        // Get row data
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            model.addRow(row);
        }
        return model;
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}