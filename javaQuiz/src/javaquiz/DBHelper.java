package javaQuiz;

import java.sql.*;

public class DBHelper {

    public static String[][] loadQuestions() {
        String[][] questions = new String[10][5];
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM questions");
            int i = 0;
            while (rs.next()) {
                questions[i][0] = rs.getString("question");
                questions[i][1] = rs.getString("option1");
                questions[i][2] = rs.getString("option2");
                questions[i][3] = rs.getString("option3");
                questions[i][4] = rs.getString("option4");
                i++;
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error loading questions: " + e.getMessage());
        }
        return questions;
    }

    public static String[] loadAnswers() {
        String[] answers = new String[10];
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT correct_answer FROM questions");
            int i = 0;
            while (rs.next()) {
                answers[i] = rs.getString("correct_answer");
                i++;
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error loading answers: " + e.getMessage());
        }
        return answers;
    }
    
    // Check if login is correct
public static boolean loginUser(String username, String password) {
    try {
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        boolean found = rs.next();
        con.close();
        return found;
    } catch (Exception e) {
        System.out.println("Login error: " + e.getMessage());
        return false;
    }
}

// Register new user
public static boolean registerUser(String username, String password) {
    try {
        Connection con = DBConnection.getConnection();
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeUpdate();
        con.close();
        return true;
    } catch (Exception e) {
        System.out.println("Register error: " + e.getMessage());
        return false;
    }
}

    public static void saveResult(String name, int score) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO results (player_name, score, total_questions) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, score);
            ps.setInt(3, 10);
            ps.executeUpdate();
            con.close();
            System.out.println("Result saved!");
        } catch (Exception e) {
            System.out.println("Error saving result: " + e.getMessage());
        }
    }
}