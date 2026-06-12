package javaQuiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class quiz extends JFrame implements ActionListener {
    
    String questions[][] = new String[10][5];
    String[] correctAnswers;
    String useranswers[][] = new String[10][1];
    JLabel qno, question;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup groupoptions;
    JButton next, submit, lifeline;
    
    public static int timer = 15;
    public static int ans_given = 0;
    public static int count = 0;
    public static int score = 0;
    
    String name;
    
    quiz(String name) {
        this.name = name;
        setBounds(50, 0, 1440, 850);
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/quiz_pg.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(1440, 850, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 1440, 850);
        add(background);
        
        qno = new JLabel();
        qno.setBounds(100, 100, 50, 40);
        qno.setFont(new Font("Tahoma", Font.BOLD, 22));
        qno.setForeground(Color.BLACK);
        background.add(qno);
        
        question = new JLabel();
        question.setBounds(150, 100, 1000, 40);
        question.setFont(new Font("Tahoma", Font.PLAIN, 22));
        question.setForeground(Color.BLACK);
        background.add(question);
        
        opt1 = new JRadioButton();
        opt1.setBounds(170, 180, 400, 35);
        opt1.setBackground(new Color(255, 255, 255, 180));
        opt1.setFont(new Font("Dialog", Font.PLAIN, 20));
        background.add(opt1);
        
        opt2 = new JRadioButton();
        opt2.setBounds(600, 180, 400, 35);
        opt2.setBackground(new Color(255, 255, 255, 180));
        opt2.setFont(new Font("Dialog", Font.PLAIN, 20));
        background.add(opt2);
        
        opt3 = new JRadioButton();
        opt3.setBounds(170, 240, 400, 35);
        opt3.setBackground(new Color(255, 255, 255, 180));
        opt3.setFont(new Font("Dialog", Font.PLAIN, 20));
        background.add(opt3);
        
        opt4 = new JRadioButton();
        opt4.setBounds(600, 240, 400, 35);
        opt4.setBackground(new Color(255, 255, 255, 180));
        opt4.setFont(new Font("Dialog", Font.PLAIN, 20));
        background.add(opt4);
        
        groupoptions = new ButtonGroup();
        groupoptions.add(opt1);
        groupoptions.add(opt2);
        groupoptions.add(opt3);
        groupoptions.add(opt4);
        
        next = new JButton("Next");
        next.setBounds(250, 330, 150, 40);
        next.setFont(new Font("Tahoma", Font.BOLD, 18));
        next.setBackground(new Color(30, 144, 255));
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        background.add(next);
        
        lifeline = new JButton("50-50 Lifeline");
        lifeline.setBounds(450, 330, 200, 40);
        lifeline.setFont(new Font("Tahoma", Font.BOLD, 18));
        lifeline.setBackground(new Color(255, 165, 0));
        lifeline.setForeground(Color.WHITE);
        lifeline.addActionListener(this);
        background.add(lifeline);
        
        submit = new JButton("Submit");
        submit.setBounds(690, 330, 150, 40);
        submit.setFont(new Font("Tahoma", Font.BOLD, 18));
        submit.setBackground(new Color(34, 139, 34));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setEnabled(false);
        background.add(submit);
        
        // Load from database
        questions = DBHelper.loadQuestions();
        correctAnswers = DBHelper.loadAnswers();
        start(count);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            repaint();
            opt1.setEnabled(true);
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);
            
            ans_given = 1;
            if (groupoptions.getSelection() == null) {
                useranswers[count][0] = "";
            } else {
                useranswers[count][0] = groupoptions.getSelection().getActionCommand();
            }
            
            if (count == 8) {
                next.setEnabled(false);
                submit.setEnabled(true);
            }
            
            count++;
            start(count);
        } else if (ae.getSource() == lifeline) {
            if (count == 2 || count == 4 || count == 6 || count == 8 || count == 9) {
                opt2.setEnabled(false);
                opt3.setEnabled(false);
            } else {
                opt1.setEnabled(false);
                opt4.setEnabled(false);
            }
            lifeline.setEnabled(false);
        } else if (ae.getSource() == submit) {
            ans_given = 1;
            if (groupoptions.getSelection() == null) {
                useranswers[count][0] = "";
            } else {
                useranswers[count][0] = groupoptions.getSelection().getActionCommand();
            }

            for (int i = 0; i < useranswers.length; i++) {
                if (useranswers[i][0].equals(correctAnswers[i])) {
                    score += 10;
                }
            }
            setVisible(false);
            new score(name, score);
        }
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        String time = "Time left - " + timer + " seconds";
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 25));
        g.drawString(time, 1100, 120);
        
        timer--;
        
        try {
            Thread.sleep(1000);
            repaint();
        } catch (Exception e) {}
        
        if (ans_given == 1) {
            ans_given = 0;
            timer = 15;
        } else if (timer < 0) {
            timer = 15;
            opt1.setEnabled(true);
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);
            
            if (count == 8) {
                next.setEnabled(false);
                submit.setEnabled(true);
            }
            if (count == 9) {
                if (groupoptions.getSelection() == null) {
                    useranswers[count][0] = "";
                } else {
                    useranswers[count][0] = groupoptions.getSelection().getActionCommand();
                }
                for (int i = 0; i < useranswers.length; i++) {
                    if (useranswers[i][0].equals(correctAnswers[i])) {
                        score += 10;
                    }
                }
                setVisible(false);
                new score(name, score);
            } else {
                if (groupoptions.getSelection() == null) {
                    useranswers[count][0] = "";
                } else {
                    useranswers[count][0] = groupoptions.getSelection().getActionCommand();
                }
                count++;
                start(count);
            }
        }
    }
    
    public void start(int count) {
        qno.setText("" + (count + 1) + ". ");
        question.setText(questions[count][0]);
        opt1.setText(questions[count][1]);
        opt1.setActionCommand(questions[count][1]);
        opt2.setText(questions[count][2]);
        opt2.setActionCommand(questions[count][2]);
        opt3.setText(questions[count][3]);
        opt3.setActionCommand(questions[count][3]);
        opt4.setText(questions[count][4]);
        opt4.setActionCommand(questions[count][4]);
        groupoptions.clearSelection();
    }
    
    public static void main(String[] args) {
        new quiz("User");
    }
}