package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Update_Catalog extends JPanel {
    JLabel label = new JLabel("UPDATE CATALOG");
    JLabel lbID = new JLabel("ID Catalog");
    JLabel lbName = new JLabel("Name Catalog");
    JComboBox<String> cbID;
    JTextField tfName = new JTextField(30);
    JButton btnUpdate = new JButton("UPDATE");

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    Image img = imgF.getImage();
    Image img2 = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon imgL = new ImageIcon(img2);
    JButton btexit = new JButton("", imgL);

    Database db;

    static Color_CF colorCf = new Color_CF();
    static Button_Custum buttonCustum;
    static Font_CF fontCf = new Font_CF();

    public Update_Catalog() {
        setSize(360, 300);
        setLayout(null);

        db = new Database();
        db.connect();

        String[] listID = getID();
        cbID = new JComboBox<>(listID);
        cbID.setSelectedIndex(0);
        buttonCustum = new Button_Custum(cbID);

        label.setBounds(30, 30, 340, 50 );
        label.setFont(fontCf.fontAR32);
        add(label);
        lbID.setBounds(20, 100, 100, 50 );
        lbID.setFont(fontCf.fontAR20);
        add(lbID);
        lbName.setBounds(20, 160, 180, 50 );
        lbName.setFont(fontCf.fontAR20);
        add(lbName);
        cbID.setBounds(170, 110, 170, 30 );
        cbID.setFont(fontCf.fontAR20);
        add(cbID);
        tfName.setBounds(170, 170, 170, 30 );
        tfName.setFont(fontCf.fontAR20);
        buttonCustum = new Button_Custum(tfName);
        add(tfName);

        btnUpdate.setBounds(100, 220, 170, 30);
        btnUpdate.setFont(fontCf.fontAR20);
        btnUpdate.setForeground(colorCf.brownmilk);
        buttonCustum = new Button_Custum(btnUpdate);
        add(btnUpdate);

        btexit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btexit);

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCatalog();
            }
        });

        btexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public String[] getID() {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = db.getDB("SELECT ID_Catalog FROM catalog");

        try {
            while (rs.next()){
                String id = rs.getString("ID_Catalog");
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

    public void updateCatalog() {
        try {
            int rs = db.executeDB("UPDATE catalog SET ID_Catalog = '"
                    + cbID.getSelectedItem().toString()
                    + "', Name_Cata = '"
                    + tfName.getText()
                    + "' WHERE ID_Catalog = '"
                    + cbID.getSelectedItem().toString()
                    +"'"
            );
            if (rs > 0){
                JOptionPane.showMessageDialog(null, "Catalog Updated Successfully\nPlease restart app!!");
                String[] list = getID();
                cbID.setModel(new DefaultComboBoxModel<>(list));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(colorCf.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
