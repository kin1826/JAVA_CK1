package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

public class View_Staff extends JPanel {
    JLabel lbID = new JLabel();
    JLabel lbName = new JLabel();
    JLabel lbPosi = new JLabel();
    JLabel lbPhone = new JLabel();
    JLabel lbSex = new JLabel();
    JLabel lbSalary = new JLabel();
    JLabel lbnamelg = new JLabel();
    JLabel lbimg = new JLabel();

    static Vector<Object> vec = new Vector<>();

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    Image img = imgF.getImage();
    Image img2 = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon imgL = new ImageIcon(img2);
    JButton btexit = new JButton("", imgL);
    ImageIcon imgbc = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\backStaff.png");
    ImageIcon imgback = new ImageIcon(imgbc.getImage().getScaledInstance(800, 220, Image.SCALE_SMOOTH));
    JLabel lbback = new JLabel(imgback);

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    Database db = new Database();

    private String ID_Staff;

    public View_Staff(String ID){
        ID_Staff = ID;
        display();
    }

    public void display() {
        setLayout(null);
        setSize(800, 220);

        db = new Database();
        db.connect();

        getStaff_Full();
        Object[] ob = (Object[]) vec.get(0);

        ImageIcon imgi = (ImageIcon) ob[6];
        Image img = imgi.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        lbimg.setBounds(30, 50, 150, 150);
        lbimg.setIcon(new ImageIcon(img));
        lbimg.setBorder(new LineBorder(color.darkbr, 5));
        add(lbimg);
        lbID.setBounds(200, 50, 200, 30);
        lbID.setFont(font.fontAR16);
        lbID.setForeground(color.darkbr);
        lbID.setText("ID: " + ob[0].toString());
        add(lbID);
        lbName.setBounds(200, 85, 200, 30);
        lbName.setFont(font.fontAR16);
        lbName.setForeground(color.darkbr);
        lbName.setText("Name: " + ob[1].toString());
        add(lbName);
        lbPosi.setBounds(200, 120, 200, 30);
        lbPosi.setFont(font.fontAR16);
        lbPosi.setForeground(color.darkbr);
        lbPosi.setText("Position: " + ob[2].toString());
        add(lbPosi);
        lbPhone.setBounds(200, 155, 200, 30);
        lbPhone.setFont(font.fontAR16);
        lbPhone.setForeground(color.darkbr);
        lbPhone.setText("Phone: " + ob[3].toString());
        add(lbPhone);
        lbSex.setBounds(380, 50, 200, 30);
        lbSex.setFont(font.fontAR16);
        lbSex.setForeground(color.darkbr);
        lbSex.setText("Gender: " + ob[4].toString());
        add(lbSex);
        lbSalary.setBounds(380, 85, 200, 30);
        lbSalary.setFont(font.fontAR16);
        lbSalary.setForeground(color.darkbr);
        int sala = (int) ob[5];
        lbSalary.setText("Salary: " +FMMoney.format(sala));
        add(lbSalary);
        lbnamelg.setBounds(380, 120, 200, 30);
        lbnamelg.setFont(font.fontAR16);
        lbnamelg.setForeground(color.darkbr);
        lbnamelg.setText("Name: " + ob[7].toString());
        add(lbnamelg);

        btexit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btexit);
        btexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        lbback.setBounds(0, 26, 800, 220);
        add(lbback);
    }

    public void getStaff_Full() {
        ResultSet rs = db.getDB("SELECT * FROM staff WHERE ID_Staff='" + ID_Staff + "'");

        try {
            vec.clear();

            while (rs.next()) {
                String id = rs.getString("ID_Staff");
                String name = rs.getString("Name_Staff");
                String position = rs.getString("Position");
                String phone = rs.getString("Phone");
                String sex = rs.getString("Sex");
                int salary = rs.getInt("Salary");
                Blob bl = rs.getBlob("Image");
                InputStream is = bl.getBinaryStream();
                ImageIcon img = new ImageIcon(is.readAllBytes());
                ImageIcon imageIcon = new ImageIcon(img.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
