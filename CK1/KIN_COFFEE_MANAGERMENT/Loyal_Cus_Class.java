package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class Loyal_Cus_Class extends JPanel {
    JLabel lbL = new JLabel("LOYAL CUSTOMER");
    JButton btedit = new JButton("Edit");
    JTable table;
    JScrollPane scrollPane;
    DefaultTableModel tableModel;
    DefaultTableCellRenderer cen = new DefaultTableCellRenderer();

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\sell.png");
    ImageIcon imgSell = new ImageIcon(imgF.getImage().getScaledInstance(800, 220, Image.SCALE_SMOOTH));
    JLabel lbimgsell = new JLabel(imgSell);

    Update_Cus updateCus;

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    Database db;

    private String phone;

    public Loyal_Cus_Class(String phone) {
        this.phone = phone;
    }

    public Loyal_Cus_Class() {
        display();
    }

    public void display() {
        setLayout(null);
        setSize(1160, 770);
        setBackground(color.brownmilk);

        db = new Database();
        db.connect();

        checkloyal();
        lbL.setBounds(240, 10, 400, 50);
        lbL.setForeground(Color.WHITE);
        lbL.setFont(font.fontAr40);
        add(lbL);
        cen.setHorizontalAlignment(SwingConstants.CENTER);
        String[] columnNames = {"Phone", "Sell_Off", "Sum"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setCellRenderer(cen);
        table.getColumnModel().getColumn(1).setPreferredWidth(400);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.setRowHeight(25);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 70, 800, 470);
        add(scrollPane);
        lbimgsell.setBounds(10, 550, 800, 220);
        add(lbimgsell);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    String phone = (String) table.getValueAt(row, 0);
                    updateCus = new Update_Cus(phone);
                    updateCus.setBounds(820, 70, 360, 470);
                    add(updateCus);
                    updateCus.setVisible(true);
                    repaint();
                    revalidate();
                }
            }
        });

        Timer timer = new Timer(1000, e -> {
            getCus();
        });
        timer.start();
    }

    public void getCus() {
        ResultSet rs = db.getDB("SELECT * FROM customer");

        try {
            tableModel.setRowCount(0);

            while (rs.next()) {
                String phone = rs.getString("Phone");
                float sell_off = rs.getFloat("Lever");
                int sum = rs.getInt("Sum");

                Object[] row = {phone, sell_off, FMMoney.format(sum)};
                tableModel.addRow(row);
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

    public void checkloyal() {
        ResultSet rs = db.getDB("SELECT * FROM customer");

        try {
            while (rs.next()) {
                String phone = rs.getString("Phone");
                int sum = rs.getInt("Sum");

                if (sum >= 200000 && sum <= 600000) {
                    int re = db.executeDB("UPDATE customer SET lever = 0.05 WHERE Phone = '" + phone + "'");
                } else if (sum >= 600000 && sum <= 1000000) {
                    int re = db.executeDB("UPDATE customer SET lever = 0.1 WHERE Phone = '" + phone + "'");
                } else if (sum >= 1000000) {
                    int re = db.executeDB("UPDATE customer SET lever = 0.15 WHERE Phone = '" + phone + "'");
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
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color.brownmilk);
        g.fillRect(0, 100, 1000, 860);
        g.setColor(Color.WHITE);
        g.fillRoundRect(820, 70, 360, 700, 10, 10);
        g.fillRect(10, 550, 800, 220);
        g.fillRect(10, 70, 800, 470);
    }
}
