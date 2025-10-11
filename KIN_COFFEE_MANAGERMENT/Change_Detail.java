package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Change_Detail extends JFrame {
    JPanel panel = new JPanel(){
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            g.setColor(color.brownmilk);
            g.fillRect(0, 0, getWidth(), 60);
            g.setColor(Color.WHITE);
            g.fillRect(0, 60, getWidth(), getHeight() - 40);
            g.setColor(color.darkbr);
//            g.fillRect(0, 60, getWidth(), 5);
        }
    };
    JLabel lbChange = new JLabel("Edit Item");
    JLabel lbName = new JLabel();
    JLabel lbQuantity = new JLabel("Quantity");
    JSpinner spinner;
    JButton btdel = new JButton("Delete");
    JButton btedit = new JButton("Edit");

    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    static Color_CF color = new Color_CF();

    Database db = new Database();

    private String ID_Item;
    private String Name;
    private int quantity;
    private String TB_Name;
    private int ID_Bill;

    public Change_Detail(String ID, String name, int ID_Bill) {
        this.ID_Item = ID;
        this.Name = name;
        this.ID_Bill = ID_Bill;

        setTitle("Edit Detail");
        setLayout(null);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setBackground(color.brownmilk);

        db.connect();

        lbChange.setBounds(40, 20, 200, 30);
        lbChange.setFont(font.fontAR32);
        add(lbChange);
        lbName.setBounds(70, 70, 200, 30);
        lbName.setText(name);
        lbName.setFont(font.fontAR16);
        add(lbName);
        lbQuantity.setBounds(70, 110, 200, 30);
        lbQuantity.setFont(font.fontAR16);
        add(lbQuantity);
        SpinnerModel spinnerModel = new SpinnerNumberModel(getQuantityDetail(), 1, getQuantity(), 1);
        spinner = new JSpinner(spinnerModel);
        spinner.setBounds(220, 110, 150, 30);
        spinner.setFont(font.fontAR16);
        buttonCustum = new Button_Custum(spinner);
        add(spinner);
        btdel.setBounds(70, 160, 120, 30);
        btcus(btdel);
        add(btdel);
        btedit.setBounds(250, 160, 120, 30);
        buttonCustum = new Button_Custum(btedit);
        add(btedit);

        panel.setBounds(0, 0, getWidth(), getHeight());
        add(panel);

        setVisible(true);
    }

    public int getQuantity() {
        ResultSet rs = db.getDB("SELECT Quantity FROM item WHERE ID_Item = '" + ID_Item + "'");

        int quantity = 0;
        try {
            if (rs.next()) {
                quantity = rs.getInt("Quantity");
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
        return quantity;
    }

    public int getQuantityDetail() {
        ResultSet rs = db.getDB("SELECT Quantity FROM detail WHERE ID_Item = '" + ID_Item + "' AND ID_Bill = '" + ID_Bill + "'");

        int quantity = 0;
        try {
            if (rs.next()) {
                quantity = rs.getInt("Quantity");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quantity;
    }

    public void deleteDetail() {
        try {
            int rs = db.executeDB("DELETE FROM detail WHERE ID_Item = '" + ID_Item + "' AND ID_Bill = '" + ID_Bill + "'");
            if (rs > 0) JOptionPane.showMessageDialog(null, "Item Deleted");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDetail() {
        try {
            int rs = db.executeDB("UPDATE detail SET Quantity = '" + spinner.getValue() + "' WHERE ID_Item = '" + ID_Item + "' AND ID_Bill = '" + ID_Bill + "'");
            if (rs > 0) JOptionPane.showMessageDialog(null, "Item Updated");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btcus(JButton bt) {
        bt.setForeground(Color.WHITE);
        bt.setBackground(color.brownmilk);
        bt.setBorderPainted(false);
        bt.setFont(font.fontAR16);
        bt.setBackground(color.redBt);

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(new Color(255, 0, 0));
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(color.redBt);
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(new Color(147, 1, 1));
            }
        });
    }
}
