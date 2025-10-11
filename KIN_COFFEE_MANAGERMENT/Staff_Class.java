package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Vector;

public class Staff_Class extends JPanel {
    JLabel lbStaff = new JLabel("STAFF");
    JScrollPane sc;
    JTable table;
    DefaultTableModel model;
    JButton btadd = new JButton("ADD STAFF");

    static Vector<Object> vec = new Vector<>();

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    Add_Staff addStaff = new Add_Staff();

    Database db;

    public Staff_Class() {
        setLayout(null);
        setSize(1160, 770);
        setBackground(color.brownmilk);

        db = new Database();
        db.connect();

        String[] columnName = {"ID", "Name", "Position", "Phone", "Sex", "Salary", "Name_Login"};
        model = new DefaultTableModel(columnName, 0);
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.setRowHeight(25);
        sc = new JScrollPane(table);
        sc.setBounds(25, 85, 770, 440);
        add(sc);
        lbStaff.setBounds(350, 10, 400, 50);
        lbStaff.setForeground(Color.WHITE);
        lbStaff.setFont(font.fontAr40);
        add(lbStaff);
        btadd.setBounds(900, 730, 200, 30);
        addStaff.butcus(btadd);
        add(btadd);

        addStaff.setBounds(820, 70, 360, 470);
        add(addStaff);

        btadd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStaff.setVisible(true);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    Object value = table.getValueAt(row, 0);
                    String ID = value.toString();
                    View_Staff view = new View_Staff(ID);
                    view.setBounds(10, 550, 800, 220);
                    add(view);
                    view.setVisible(true);
                    UpdateDelete_Staff udstaff = new UpdateDelete_Staff(ID);
                    udstaff.setBounds(820, 70, 360, 470);
                    add(udstaff);
                    udstaff.setVisible(true);
                    addStaff.setVisible(false);

                    udstaff.btDelete.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            udstaff.setVisible(false);
                            view.setVisible(false);
                            addStaff.setVisible(true);
                        }
                    });

                    repaint();
                    revalidate();
                }
            }
        });

        Timer timer = new Timer(1000, e -> {
            getStaff();
        });
        timer.start();
    }

    public void getStaff() {
        ResultSet rs = db.getDB("SELECT * FROM staff");

        try {
            model.setRowCount(0);

            while (rs.next()) {
                String id = rs.getString("ID_Staff");
                String name = rs.getString("Name_Staff");
                String position = rs.getString("Position");
                String phone = rs.getString("Phone");
                String sex = rs.getString("Sex");
                int salary = rs.getInt("Salary");
                String namelg = rs.getString("Name_Login");

                model.addRow(new Object[]{id, name, position, phone, sex, FMMoney.format(salary), namelg});
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

    @Override
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
