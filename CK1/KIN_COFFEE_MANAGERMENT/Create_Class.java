package KIN_COFFEE_MANAGERMENT;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Create_Class extends JFrame {
    JLabel lbcre = new JLabel("Create Account");
    JLabel lbID = new JLabel("ID_Staff");
    JLabel lbNameLg = new JLabel("Name login");
    JLabel lbcrPass = new JLabel("Enter password");
    JLabel lbrePass = new JLabel("Confirm password");
    JTextField tfID = new JTextField();
    JTextField tfName = new JTextField();
    JPasswordField tfCRPass = new JPasswordField();
    JPasswordField tfRePass = new JPasswordField();
    JLabel lbcmt = new JLabel("<html>*You must have ID and Username from the manager to login");
    JButton btnCreate = new JButton("Create");
    JCheckBox show = new JCheckBox("Show password");

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;

    Database db;

    public Create_Class() {
        setLayout(null);
        setSize(400, 480);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);

        db = new Database();
        db.connect();

        lbcre.setBounds(45, 30, 300, 50);
        lbcre.setFont(font.fontAr40);
        lbcre.setForeground(color.darkbr);
        add(lbcre);
        lbID.setBounds(30, 120, 100, 30);
        lbID.setFont(font.fontAR16);
        lbID.setForeground(color.darkbr);
        add(lbID);
        lbNameLg.setBounds(30, 160, 150, 30);
        lbNameLg.setFont(font.fontAR16);
        lbNameLg.setForeground(color.darkbr);
        add(lbNameLg);
        lbcrPass.setBounds(30, 200, 150, 30);
        lbcrPass.setFont(font.fontAR16);
        lbcrPass.setForeground(color.darkbr);
        add(lbcrPass);
        lbrePass.setBounds(30, 240, 150, 30);
        lbrePass.setFont(font.fontAR16);
        lbrePass.setForeground(color.darkbr);
        add(lbrePass);
        tfID.setBounds(190, 120, 180, 30);
        buttonCustum = new Button_Custum(tfID);
        add(tfID);
        tfName.setBounds(190, 160, 180, 30);
        buttonCustum = new Button_Custum(tfName);
        add(tfName);
        tfCRPass.setBounds(190, 200, 180, 30);
        buttonCustum = new Button_Custum(tfCRPass);
        add(tfCRPass);
        tfRePass.setBounds(190, 240, 180, 30);
        buttonCustum = new Button_Custum(tfRePass);
        add(tfRePass);
        show.setBounds(190, 270, 180, 30);
        show.setForeground(color.darkbr);
        add(show);
        lbcmt.setBounds(50, 300, 300, 60);
        lbcmt.setFont(font.fontAR14);
        lbcmt.setForeground(color.redBt);
        add(lbcmt);
        btnCreate.setBounds(100, 380, 200, 50);
        buttonCustum = new Button_Custum(btnCreate);
        add(btnCreate);
        char x = tfCRPass.getEchoChar();

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (show.isSelected()) {
                    tfCRPass.setEchoChar((char) 0);
                    tfRePass.setEchoChar((char) 0);
                } else {
                    tfCRPass.setEchoChar(x);
                    tfRePass.setEchoChar(x);
                }
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (check() && checkPassword()){
                    create();
//                    setVisible(false);
                }
            }
        });

        setVisible(true);
    }

    public boolean check() {
        ResultSet rs = db.getDB("SELECT ID_Staff, Name_Login FROM staff WHERE ID_Staff = '" + tfID.getText() + "'");
        boolean check = false;
        try {
            if (rs.next()) {
                String id = rs.getString("ID_Staff");
                String name = rs.getString("Name_Login");

                if (id.equals(tfID.getText()) && name.equals(tfName.getText())) {
                    check = true;
                } else JOptionPane.showMessageDialog(null, "ID and Name do not exist!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    public boolean checkPassword() {
        boolean check = false;
        if (Arrays.equals(tfCRPass.getPassword(), tfRePass.getPassword())) {
            check = true;
        } else JOptionPane.showMessageDialog(null, "Password wrong!");
        return check;
    }

    public String hashPass(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public void create() {
        try {
            int rs = db.executeDB("INSERT INTO login(Name_Login, Password, ID_Staff_FK) "
                    +("VALUE( '")
                    +tfName.getText()
                    +"', '"
                    +hashPass(tfCRPass.getText())
                    +"', '"
                    +tfID.getText()
                    +"')"
            );
            if (rs > 0) JOptionPane.showMessageDialog(null, "Login created!");
            else JOptionPane.showMessageDialog(null, "Create failed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
