package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

public class Update_Cus extends JPanel {
    JLabel lbUpdate = new JLabel("EDIT CUSTOMER");
    JLabel lbPhone = new JLabel();
    JLabel lbLevel = new JLabel();
    JLabel lbSum = new JLabel();
    JButton btnDelete = new JButton("DELETE");
    JLabel lbcmt = new JLabel("<html>* There are 4 levels for customers with each<br>discount rate.");
    JLabel lbrank = new JLabel("<html>1. 0%<br>2. >200,000đ -> 5%<br>3. >600,000đ -> 10%<br>4. >1,000,000đ -> 15%");

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    ImageIcon imgexit = new ImageIcon(imgF.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    JButton btnExit = new JButton(imgexit);

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    Vector<Object> vec = new Vector<>();

    Database db;

    private String rowChoice;

    public Update_Cus(String rowChoice) {
        this.rowChoice = rowChoice;
        displayUpdate();
    }

    public void displayUpdate() {
        setLayout(null);
        setSize(360, 470);

        db = new Database();
        db.connect();

        getInfor();
        Object[] ob = (Object[]) vec.get(0);

        btnExit.setBounds(getWidth()- 30, 3, 20, 20);
        add(btnExit);
        lbUpdate.setBounds(50, 30, 360, 50);
        lbUpdate.setFont(font.fontAR32);
        add(lbUpdate);
        lbPhone.setBounds(80, 130, 250, 30);
        lbPhone.setFont(font.fontAR16);
        lbPhone.setText("Phone: " +rowChoice);
        add(lbPhone);
        lbLevel.setBounds(80, 170, 200, 30);
        lbLevel.setFont(font.fontAR16);
        lbLevel.setText("Sell_Off : " +ob[1].toString());
        add(lbLevel);
        lbSum.setBounds(80, 210, 200, 30);
        lbSum.setFont(font.fontAR16);
        lbSum.setText("Sum: " +ob[2].toString());
        add(lbSum);
        lbcmt.setBounds(20, 250, 360, 40);
        lbcmt.setFont(font.fontAR14);
        add(lbcmt);
        lbrank.setBounds(40, 280, 300, 80);
        lbrank.setFont(font.fontAR14);
        add(lbrank);

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCus();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public void getInfor() {
        ResultSet rs = db.getDB("SELECT * FROM customer WHERE Phone = '" + rowChoice + "'");

        try {
            vec.clear();
            if (rs.next()) {
                String phone = rs.getString("Phone");
                float level = rs.getFloat("Lever");
                int sum = rs.getInt("Sum");

                Object[] ob = new Object[]{phone, level, FMMoney.format(sum)};
                vec.add(ob);
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
    }

    public void deleteCus() {
        try {
            int rs = db.executeDB("DELETE FROM customer WHERE Phone = '" + rowChoice + "'");
            if (rs > 0) JOptionPane.showMessageDialog(null, "Customer has been deleted");
            else JOptionPane.showMessageDialog(null, "Customer has not been deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void custumBut(JButton bt) {
        bt.setForeground(Color.WHITE);
        bt.setFont(font.fontAR20);
        bt.setBorderPainted(false);
        Color last = bt.getBackground();

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(color.brownmilk2);
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(last);
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(color.darkbr);
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
