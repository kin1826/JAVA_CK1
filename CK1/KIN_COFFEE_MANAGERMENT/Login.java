package KIN_COFFEE_MANAGERMENT;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Login extends JPanel {
    ImageIcon img1 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\INTRO\\img1.jpg");
    Image image1 = img1.getImage().getScaledInstance(1200, 870, Image.SCALE_SMOOTH);
    ImageIcon icon1 = new ImageIcon(image1);
    JLabel labelbr = new JLabel(icon1);
    ImageIcon img2 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\2.png");
    Image image2 = img2.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
    ImageIcon icon2 = new ImageIcon(image2);
    JLabel labellogo = new JLabel(icon2);
    JLabel lbcre = new JLabel("Create new account");

    Font fcer = lbcre.getFont();
    Map atr = fcer.getAttributes();

    JCheckBox show = new JCheckBox("Show password");

    JLabel lb = new JLabel("LOGIN");
    JLabel lbName = new JLabel("Name login");
    JLabel lbPassword = new JLabel("Password");
    JTextField tf = new JTextField(30);
    JPasswordField pf = new JPasswordField(30);
    JButton btnLogin = new JButton("Login");

    Color_CF color = new Color_CF();
    static Font_CF fontcf = new Font_CF();
    Button_Custum buttonCustum;

    Database db;

    Font font1 = new Font("Times New Roman", Font.BOLD, 32);
    Font font2 = new Font("Times New Roman", Font.BOLD, 20);

    public Login() throws FileNotFoundException {
        setLayout(null);
        db = new Database();
        db.connect();

        atr.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        Font font = new Font(atr);

        lb.setFont(font1);
        lb.setForeground(color.darkbr);
        lbName.setFont(font2);
        lbName.setForeground(color.darkbr);
        lbPassword.setFont(font2);
        lbPassword.setForeground(color.darkbr);
        btnLogin.setFont(font2);
        btnLogin.setBackground(color.brownmilk);
        btnLogin.setForeground(color.darkbr);
        show.setForeground(color.darkbr);
        show.setBackground(Color.WHITE);
        char x = pf.getEchoChar();

         labelbr.setBounds(0, 0, 1000, 870);
        add(labelbr);
        labellogo.setBounds(1180, 20, 150, 200);
        add(labellogo);
        lb.setBounds(1200, 330, 200, 50);
        add(lb);
        lbName.setBounds(1090, 420, 200, 30);
        add(lbName);
        lbPassword.setBounds(1090, 480, 200, 30);
        add(lbPassword);
        tf.setBounds(1200, 420, 250, 40);
        tf.setFont(fontcf.fontAR16);
        add(tf);
        pf.setBounds(1200, 480, 250, 40);
        pf.setFont(fontcf.fontAR16);
        add(pf);
        show.setBounds(1200, 525, 120, 30);
        add(show);
        btnLogin.setBounds(1170, 600, 200, 50);
        buttonCustum = new Button_Custum(btnLogin);
        add(btnLogin);
        lbcre.setBounds(1090, 550, 120, 30);
        lbcre.setForeground(color.BlueBt);
        lbcre.setBackground(Color.RED);
        add(lbcre);
//        create.setBounds(100, 100, 400, 450);
//        add(create);
//        create.setVisible(false);

        lbcre.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Create_Class();
            }
        });

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btnLogin.setBackground(color.brownmilkdr);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btnLogin.setBackground(color.brownmilk);
            }
        });

        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (show.isSelected()) {
                    pf.setEchoChar((char) 0);
                } else {
                    pf.setEchoChar(x);
                }
            }
        });

        lbcre.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                lbcre.setFont(font);
            }
            public void mouseExited(MouseEvent e) {
                lbcre.setFont(fcer);
            }
        });
        setVisible(true);
    }

    public boolean checkOwner() {
        boolean check = false;
        ResultSet rs = db.getDB("SELECT * FROM login");
        try {
            while (rs.next()){
                String name = rs.getString("Name_Login");
                String password = rs.getString("Password");

                if (name.equals(tf.getText()) && checkPass(pf.getText(), password)) {
                    check = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return check;
    }

    public String getID() {
        String id = "";
        ResultSet rs = db.getDB("SELECT ID_Staff_FK FROM login WHERE Name_Login = '" + tf.getText() + "'");

        try {
            while (rs.next()) {
                id = rs.getString("ID_Staff_FK");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return id;
    }

    public String hashPass(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt(12));
    }

    public boolean checkPass(String pass, String hash) {
        return BCrypt.checkpw(pass, hash);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color.brownmilk);
        g.fillRect(1000, 0, 650, 870);
        g.setColor(Color.WHITE);
        g.fillRoundRect(1060, 270, 400, 420, 10, 10);

        g.setColor(color.darkbr);
        g.fillRect(1060, 270, 400, 26);
    }
}
