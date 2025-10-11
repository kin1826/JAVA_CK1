package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Table_Class extends JPanel {
    JLabel lbTable = new JLabel("TABLE");
    JButton btadd = new JButton("Add");
    JButton btupdate = new JButton("Update");
    JButton btdel = new JButton("Delete");
    JTable table;
    JScrollPane scrollTB;
    DefaultTableModel model;

    Add_Table addTabl = new Add_Table();
    Update_Table updateTabl = new Update_Table();
    Delete_Table deleteTabl = new Delete_Table();
    Table_Show tableShow = new Table_Show();
    JScrollPane sc;

    static Color_CF colorCf = new Color_CF();
    static Font_CF fontCf = new Font_CF();
    static Button_Custum buttonCustum;

    Database db;

    public Table_Class() {
        setLayout(null);
        setSize(1160, 770);
        setBackground(colorCf.brownmilk);

        db = new Database();
        db.connect();

        String[] columnName = {"ID", "Name", "Status"};
        model = new DefaultTableModel(columnName, 0);
        table = new JTable(model);
        table.setBackground(colorCf.brownmilk);
        scrollTB = new JScrollPane(table);
        scrollTB.setBounds(830, 400, 340, 280);
        add(scrollTB);

        lbTable.setBounds(340, 10, 200, 50);
        lbTable.setForeground(Color.WHITE);
        lbTable.setFont(fontCf.fontAr40);
        add(lbTable);

        sc = new JScrollPane(tableShow);
        sc.setBounds(10, 70, 800, 700);
        add(sc);
        addTabl.setBounds(820, 70, 360, 300);
        add(addTabl);
        addTabl.setVisible(true);
        updateTabl.setBounds(820, 70, 360, 300);
        add(updateTabl);
        updateTabl.setVisible(false);
        deleteTabl.setBounds(820, 70, 360, 300);
        add(deleteTabl);
        deleteTabl.setVisible(false);

        btadd.setBounds(830, 700, 100, 40);
        btadd.setBackground(Color.GREEN);
        setButtton(btadd);
        add(btadd);
        btupdate.setBounds(950, 700, 100, 40);
        btupdate.setBackground(Color.BLUE);
        setButtton(btupdate);
        add(btupdate);
        btdel.setBounds(1070, 700, 100, 40);
        btdel.setBackground(Color.RED);
        setButtton(btdel);
        add(btdel);

        btadd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTabl.setVisible(true);
                updateTabl.setVisible(false);
                deleteTabl.setVisible(false);
                repaint();
                revalidate();
            }
        });

        btupdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTabl.setVisible(true);
                addTabl.setVisible(false);
                deleteTabl.setVisible(false);
                updateTabl.updatedata();
                repaint();
                revalidate();
            }
        });

        btdel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTabl.setVisible(true);
                addTabl.setVisible(false);
                updateTabl.setVisible(false);
                deleteTabl.updatedata();
                repaint();
                revalidate();
            }
        });

        Timer timer = new Timer(1000, e -> {
            getInformation();
        });
        timer.start();
    }

    public void getInformation() {
        ResultSet rs = db.getDB("SELECT * FROM Tablecf");

        try {
            model.setRowCount(0);

            while (rs.next()){
                String id = rs.getString("ID_Table");
                String name = rs.getString("Name");
                String status = rs.getString("Status");

                model.addRow(new Object[]{id, name, status});
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

    public static void setButtton(JButton bt) {
        bt.setForeground(colorCf.darkbr);
        bt.setFont(fontCf.fontAR16);
        bt.setBorderPainted(false);
        Color last = bt.getBackground();

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(colorCf.brownmilkdr);
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(last);
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(colorCf.darkbr);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(colorCf.brownmilk);
        g.fillRect(0, 100, 1000, 860);
        g.setColor(Color.WHITE);
        g.fillRoundRect(820, 70, 360, 700, 10, 10);
    }
}
