package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Add_Table extends JPanel {
    JLabel label = new JLabel("ADD TABLE");
    JLabel lbID = new JLabel("ID Table");
    JLabel lbName = new JLabel("Name Table");
    JTextField tfID = new JTextField(30);
    JTextField tfName = new JTextField(30);
    JButton btnAdd = new JButton("ADD");

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    Image img = imgF.getImage();
    Image img2 = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon imgL = new ImageIcon(img2);
    JButton btexit = new JButton("", imgL);

    Database db;

    static Color_CF cf = new Color_CF();
    static Button_Custum buttonCustum;

    static Font font1 = new Font("Arial", Font.BOLD, 40);
    static Font font3 = new Font("Arial", Font.BOLD, 20);

    public Add_Table() {
        setSize(360, 300);
        setLayout(null);

        label.setBounds(80, 30, 300, 50 );
        label.setFont(font1);
        add(label);
        lbID.setBounds(20, 100, 100, 50 );
        lbID.setFont(font3);
        add(lbID);
        lbName.setBounds(20, 160, 180, 50 );
        lbName.setFont(font3);
        add(lbName);
        tfID.setBounds(170, 100, 170, 50 );
        tfID.setFont(font3);
        buttonCustum = new Button_Custum(tfID);
        add(tfID);
        tfName.setBounds(170, 160, 170, 50 );
        tfName.setFont(font3);
        buttonCustum = new Button_Custum(tfName);
        add(tfName);

        btnAdd.setBounds(100, 220, 170, 50);
        btnAdd.setFont(font3);
        btnAdd.setForeground(cf.brownmilk);
        setButtton(btnAdd);
        add(btnAdd);

        btexit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btexit);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = tfID.getText();
                if (id.length() >= 4) {
                    addTable();
                } else if (id == null || id.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please enter ID");
                } else JOptionPane.showMessageDialog(null, "ID must be at least 4 characters");

            }
        });

        btexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        db = new Database();
        db.connect();
    }

    public void addTable() {
        try {
            int rs = db.executeDB("INSERT INTO tablecf(ID_Table, Name) VALUES('"
                    + tfID.getText()
                    + "', '"
                    + tfName.getText()
                    + "')"
            );
            if (rs > 0) JOptionPane.showMessageDialog(null, "Table Added Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setButtton(JButton bt) {
        bt.setForeground(Color.WHITE);
        bt.setFont(font3);
        bt.setBackground(cf.brownmilk);
        bt.setBorderPainted(false);

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(cf.brownmilk2);
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(cf.brownmilk);
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(cf.darkbr);
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(cf.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
