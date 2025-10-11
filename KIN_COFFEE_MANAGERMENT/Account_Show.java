package KIN_COFFEE_MANAGERMENT;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

public class Account_Show extends JPanel {
    JLabel lnChange = new JLabel("Change password");
    JLabel lbID = new JLabel();
    JLabel lbName = new JLabel();
    JLabel lbPosi = new JLabel();
    JLabel lbPhone = new JLabel();
    JLabel lbSex = new JLabel();
    JLabel lbSalary = new JLabel();
    JLabel lbnamelg = new JLabel();
    JLabel lbimg = new JLabel();
    JLabel lboldPass = new JLabel("Previous password");
    JLabel lbnewPass = new JLabel("New password");
    JLabel lbnewPassCon = new JLabel("Confirm password");
    JPasswordField tfold = new JPasswordField();
    JPasswordField tfnew = new JPasswordField();
    JPasswordField tfnewCon = new JPasswordField();
    JCheckBox show = new JCheckBox("Show all password");
    JButton btsubmit = new JButton("Done");

    ImageIcon imgbc = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\backStaff.png");
    ImageIcon imgback = new ImageIcon(imgbc.getImage().getScaledInstance(800, 280, Image.SCALE_SMOOTH));
    JLabel lbback = new JLabel(imgback);

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    Vector<Object> vec = new Vector<>();

    Database db;

    private String id_Staff;

    public Account_Show(String id) {
        this.id_Staff = id;
        display();
    }

    public void display() {
        setLayout(null);
        setSize(1160, 770);
        setBackground(color.brownmilk);

        db = new Database();
        db.connect();

        getStaff_Full();
        Object[] ob = (Object[]) vec.get(0);

        ImageIcon imgi = (ImageIcon) ob[6];
        Image img = imgi.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        lbimg.setBounds(50, 330, 200, 200);
        lbimg.setIcon(new ImageIcon(img));
        lbimg.setBorder(new LineBorder(color.darkbr, 5));
        add(lbimg);
        lbID.setBounds(270, 350, 200, 30);
        lbID.setFont(font.fontAR20);
        lbID.setForeground(color.darkbr);
        lbID.setText("ID: " + ob[0].toString());
        add(lbID);
        lbName.setBounds(270, 410, 200, 30);
        lbName.setFont(font.fontAR20);
        lbName.setForeground(color.darkbr);
        lbName.setText("Name: " + ob[1].toString());
        add(lbName);
        lbPosi.setBounds(270, 470, 200, 30);
        lbPosi.setFont(font.fontAR20);
        lbPosi.setForeground(color.darkbr);
        lbPosi.setText("Position: " + ob[2].toString());
        add(lbPosi);
        lbPhone.setBounds(450, 350, 200, 30);
        lbPhone.setFont(font.fontAR20);
        lbPhone.setForeground(color.darkbr);
        lbPhone.setText("Phone: " + ob[3].toString());
        add(lbPhone);
        lbSex.setBounds(450, 410, 200, 30);
        lbSex.setFont(font.fontAR20);
        lbSex.setForeground(color.darkbr);
        lbSex.setText("Gender: " + ob[4].toString());
        add(lbSex);
        lbSalary.setBounds(450, 470, 200, 30);
        lbSalary.setFont(font.fontAR20);
        lbSalary.setForeground(color.darkbr);
        int sa = (int) ob[5];
        lbSalary.setText("Salary: " +FMMoney.format(sa));
        add(lbSalary);
        lbnamelg.setBounds(450, 530, 200, 30);
        lbnamelg.setFont(font.fontAR20);
        lbnamelg.setForeground(color.darkbr);
        lbnamelg.setText("Name: " + ob[7].toString());
        add(lbnamelg);
        lbback.setBounds(10, 300, 800, 280);
        add(lbback);

        lnChange.setBounds(865, 240, 400, 50);
        lnChange.setFont(font.fontAR32);
        lnChange.setForeground(color.darkbr);
        add(lnChange);
        lboldPass.setBounds(900, 340, 200, 30);
        lboldPass.setFont(font.fontAR16);
        lboldPass.setForeground(color.darkbr);
        add(lboldPass);
        tfold.setBounds(900, 370, 200, 30);
        tfold.setForeground(color.darkbr);
        tfold.setFont(font.fontAR14);
        buttonCustum = new Button_Custum(tfold);
        add(tfold);
        lbnewPass.setBounds(900, 400, 200, 30);
        lbnewPass.setFont(font.fontAR16);
        lbnewPass.setForeground(color.darkbr);
        add(lbnewPass);
        tfnew.setBounds(900, 430, 200, 30);
        tfnew.setForeground(color.darkbr);
        tfnew.setFont(font.fontAR14);
        buttonCustum = new Button_Custum(tfnew);
        add(tfnew);
        lbnewPassCon.setBounds(900, 460, 200, 30);
        lbnewPassCon.setFont(font.fontAR16);
        lbnewPassCon.setForeground(color.darkbr);
        add(lbnewPassCon);
        tfnewCon.setBounds(900, 490, 200, 30);
        tfnewCon.setForeground(color.darkbr);
        tfnewCon.setFont(font.fontAR14);
        buttonCustum = new Button_Custum(tfnewCon);
        add(tfnewCon);
        show.setBounds(900, 520, 200, 30);
        show.setFont(font.fontAR14);
        show.setForeground(color.darkbr);
        show.setBackground(Color.WHITE);
        add(show);
        btsubmit.setBounds(880, 580, 240, 40);
        buttonCustum = new Button_Custum(btsubmit);
        add(btsubmit);

        btsubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkPass() && checkPassNew()) {
                    updatePass();
                }
            }
        });

        char x = tfnew.getEchoChar();
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (show.isSelected()) {
                    tfold.setEchoChar((char) 0);
                    tfnew.setEchoChar((char) 0);
                    tfnewCon.setEchoChar((char) 0);
                } else {
                    tfold.setEchoChar(x);
                    tfnew.setEchoChar(x);
                    tfnewCon.setEchoChar(x);
                }
            }
        });
    }

    public void getStaff_Full() {
        ResultSet rs = db.getDB("SELECT * FROM staff WHERE ID_Staff='" + id_Staff + "'");

        try {
            vec.clear();

            if (rs.next()) {
                String id = rs.getString("ID_Staff");
                String name = rs.getString("Name_Staff");
                String position = rs.getString("Position");
                String phone = rs.getString("Phone");
                String sex = rs.getString("Sex");
                int salary = rs.getInt("Salary");
                Blob bl = rs.getBlob("Image");
                InputStream is = bl.getBinaryStream();
                ImageIcon img = new ImageIcon(is.readAllBytes());
                ImageIcon imageIcon = new ImageIcon(img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                String namelg = rs.getString("Name_Login");

                vec.add(new Object[]{id, name, position, phone, sex, salary, imageIcon, namelg});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public boolean checkPass() {
        ResultSet rs = db.getDB("SELECT Password FROM login WHERE ID_Staff_FK = '" + id_Staff + "'");
        boolean flag = false;

        try {
            if (rs.next()) {
                String password = rs.getString("Password");

                if (checkPassword(tfold.getText(), password)) {
                    flag = true;
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
        return flag;
    }

    public boolean checkPassNew() {
        return tfnew.getText().equals(tfnewCon.getText());
    }

    public void updatePass() {
        try {
            int rs = db.executeDB("UPDATE login SET Password = '" + hashPassword(tfnewCon.getText()) + "' WHERE ID_Staff_FK = '" + id_Staff +"'");

            if (rs > 0) JOptionPane.showMessageDialog(null, "Password Update Successful");
            else JOptionPane.showMessageDialog(null, "Password Update Failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(820, 200, 360, 470);
        g.fillRect(10, 200, 800, 470);
        g.setColor(color.darkbr);
        g.fillRect(10, 200, 800, 100);
        g.fillRect(10, 570, 800, 100);
        g.fillRect(820, 200, 360, 30);
    }
}
