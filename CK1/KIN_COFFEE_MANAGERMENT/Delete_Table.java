package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Delete_Table extends JPanel {
    JLabel label = new JLabel("DELETE TABLE");
    JLabel lbID = new JLabel("ID Table");
    JComboBox<String> cbID;
    JButton btnDelete = new JButton("DELETE");

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    Image img = imgF.getImage();
    Image img2 = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon imgL = new ImageIcon(img2);
    JButton btexit = new JButton("", imgL);

    Database db;

    static Color_CF cf = new Color_CF();
    static Button_Custum custum;

    static Font font1 = new Font("Arial", Font.BOLD, 40);
    static Font font3 = new Font("Arial", Font.BOLD, 20);

    public Delete_Table() {
        setSize(360, 300);
        setLayout(null);

        db = new Database();
        db.connect();

        String[] listID = getID();
        cbID = new JComboBox<>(listID);
        cbID.setSelectedIndex(0);
        custum = new Button_Custum(cbID);

        label.setBounds(25, 30, 340, 50 );
        label.setFont(font1);
        add(label);
        lbID.setBounds(20, 100, 100, 50 );
        lbID.setFont(font3);
        add(lbID);
        cbID.setBounds(170, 100, 170, 50 );
        cbID.setFont(font3);
        add(cbID);

        btnDelete.setBounds(100, 220, 170, 50);
        btnDelete.setFont(font3);
        btnDelete.setForeground(cf.brownmilk);
        setButtton(btnDelete);
        add(btnDelete);

        btexit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btexit);

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTable();
            }
        });

        btexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public void updatedata() {
        String[] listID = getID();
        cbID.setModel(new DefaultComboBoxModel<>(listID));
    }


    public String[] getID() {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = db.getDB("SELECT ID_Table FROM tablecf");

        try {
            while (rs.next()){
                String id = rs.getString("ID_Table");
                list.add(id);
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
        return list.toArray(new String[list.size()]);
    }

    public void deleteTable() {
        try {
            int rs = db.executeDB("DELETE FROM tablecf WHERE ID_Table = '" + cbID.getSelectedItem().toString() + "'");
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Table Added Successfully");
                String[] list = getID();
                cbID.setModel(new DefaultComboBoxModel<>(list));
            }
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
