package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add_Catalog extends JPanel {
    JLabel lbAdd = new JLabel("ADD CATALOG");
    JLabel lbID = new JLabel("ID Catalog");
    JLabel lbName = new JLabel("Name Catalog");
    JTextField tfID = new JTextField(30);
    JTextField tfName = new JTextField(30);
    JButton btnAdd = new JButton("ADD");

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    Image img = imgF.getImage();
    Image img2 = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon imgL = new ImageIcon(img2);
    JButton btexit = new JButton("", imgL);

    static Button_Custum buttonCustum;
    static Font_CF font_CF = new Font_CF();
    static Color_CF color_CF = new Color_CF();

    Database db;

    public Add_Catalog() {
        setSize(360, 300);
        setLayout(null);

        db = new Database();
        db.connect();

        lbAdd.setBounds(30, 30, 300, 50 );
        lbAdd.setFont(font_CF.fontAr40);
        add(lbAdd);
        lbID.setBounds(20, 100, 100, 50 );
        lbID.setFont(font_CF.fontAR20);
        add(lbID);
        lbName.setBounds(20, 160, 180, 50 );
        lbName.setFont(font_CF.fontAR20);
        add(lbName);
        tfID.setBounds(170, 110, 170, 30 );
        tfID.setFont(font_CF.fontAR20);
        buttonCustum = new Button_Custum(tfID);
        add(tfID);
        tfName.setBounds(170, 170, 170, 30 );
        tfName.setFont(font_CF.fontAR20);
        buttonCustum = new Button_Custum(tfName);
        add(tfName);

        btnAdd.setBounds(100, 220, 170, 30);
        btnAdd.setFont(font_CF.fontAR20);
        btnAdd.setForeground(color_CF.brownmilk);
        buttonCustum = new Button_Custum(btnAdd);
        add(btnAdd);

        btexit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btexit);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = tfID.getText();
                if (id.length() >= 4) {
                    addCatalog();
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
    }

    public void addCatalog() {
        try {
            int rs = db.executeDB("INSERT INTO catalog(ID_Catalog, Name_Cata) VALUES ('"
                    +tfID.getText()
                    +"','"
                    +tfName.getText()
                    +"')"
            );
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Add catalog successfully");
            } else JOptionPane.showMessageDialog(null, "Add catalog failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color_CF.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
