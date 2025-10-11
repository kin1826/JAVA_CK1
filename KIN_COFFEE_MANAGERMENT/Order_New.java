package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Order_New extends JPanel {
    JLabel lbN = new JLabel("ORDER");
    JLabel lbDataTime = new JLabel("Data time:");
    JButton btnNew = new JButton("New");
    JButton btnCancel = new JButton("Cancel");
    JButton btBill = new JButton("Bill");
    JButton btDone = new JButton("Done");
    JLabel lbSum = new JLabel("Sum:");
    JLabel lbsell = new JLabel("Sell:");
    JLabel lbTotal = new JLabel("Total:");
    JLabel lbStaff = new JLabel("Staff:");
    JLabel lbPhone = new JLabel("Phone customer");
    JTextField tfPhone = new JTextField();

    JTable tborder;
    JScrollPane jscrollpane;
    DefaultTableModel model;

    Vector<Object> vec = new Vector<>();

    static Font_CF font = new Font_CF();
    static Color_CF color = new Color_CF();
    static Button_Custum buttonCustum;
    DecimalFormat formatMoney = new DecimalFormat("#,###");

    Database db;

    private String NameTable;
    private int sum = 0;
    private int lastSum = 0;
    private double sell;
    public boolean check = false;
    private int ID_Bill;

    public int getID_Bill() {
        return ID_Bill;
    }

    public void setID_Bill(int ID_Bill) {
        this.ID_Bill = ID_Bill;
        System.out.println(ID_Bill);
    }

    public Order_New(String ID_Tanle) {
        this.NameTable = ID_Tanle;
        display();
    }

    public void display() {
        setLayout(null);
        setSize(360, 700);
        setBackground(Color.WHITE);

        db = new Database();
        db.connect();

        String[] columnName = {"Name", "Quantity", "Price"};
        model = new DefaultTableModel(null, columnName);
        tborder = new JTable(model);
        tborder.getColumnModel().getColumn(0).setPreferredWidth(170);
        tborder.getColumnModel().getColumn(1).setPreferredWidth(60);
        tborder.getColumnModel().getColumn(2).setPreferredWidth(90);
        jscrollpane = new JScrollPane(tborder);
        jscrollpane.setBounds(20, 190, 320, 340);
        add(jscrollpane);

        lbN.setBounds(110, 40, 300, 40);
        lbN.setFont(font.fontAr40);
        lbN.setForeground(color.darkbr);
        add(lbN);
        lbDataTime.setBounds(20, 100, 200, 20);
        lbDataTime.setForeground(color.darkbr);
        add(lbDataTime);
        lbStaff.setBounds(20, 120, 200, 20);
        lbStaff.setForeground(color.darkbr);
        add(lbStaff);
        lbPhone.setBounds(20, 150, 130, 30);
        lbPhone.setForeground(color.darkbr);
        lbPhone.setFont(font.fontAR16);
        add(lbPhone);
        tfPhone.setBounds(180, 150, 160, 30);
        buttonCustum = new Button_Custum(tfPhone);
        tfPhone.setFont(font.fontAR14);
        add(tfPhone);
        btnNew.setBounds(240, 110, 100, 30);
        btnNew.setForeground(Color.WHITE);
        btnNew.setFont(font.fontAR20);
        btnNew.setBackground(color.greenBt);
        btnNew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNew.setBackground(new Color(0, 160, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNew.setBackground(color.greenBt);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btnNew.setBackground(color.darkbr);
            }
        });
        add(btnNew);
        lbTotal.setBounds(190, 540, 150, 30);
        lbTotal.setForeground(color.darkbr);
        lbTotal.setFont(font.fontAR20);
        lbTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lbTotal.setText("Total: ");
        add(lbTotal);
        lbsell.setBounds(190, 570, 150, 30);
        lbsell.setForeground(color.darkbr);
        lbsell.setFont(font.fontAR20);
        lbsell.setHorizontalAlignment(SwingConstants.RIGHT);
        lbsell.setText("Sell: ");
        add(lbsell);
        lbSum.setBounds(190, 600, 150, 30);
        lbSum.setForeground(color.darkbr);
        lbSum.setFont(font.fontAR20);
        lbSum.setHorizontalAlignment(SwingConstants.RIGHT);
        lbSum.setText("Sum: ");
        add(lbSum);
        btnCancel.setBounds(20, 640, 100, 40);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(font.fontAR20);
        btnCancel.setBackground(color.redBt);
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCancel.setBackground(new Color(160, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCancel.setBackground(color.redBt);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btnCancel.setBackground(color.darkbr);
            }
        });
        add(btnCancel);
        btBill.setBounds(130, 640, 100, 40);
        btBill.setForeground(Color.WHITE);
        btBill.setFont(font.fontAR20);
//        btBill.setHorizontalAlignment(SwingConstants.RIGHT);
        btBill.setBackground(color.BlueBt);
        btBill.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btBill.setBackground(new Color(0, 0, 160));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btBill.setBackground(color.BlueBt);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btBill.setBackground(color.darkbr);
            }
        });
        add(btBill);
        btDone.setBounds(240, 640, 100, 40);
        btDone.setForeground(Color.WHITE);
        btDone.setFont(font.fontAR20);
        btDone.setBackground(color.greenBt);
        btDone.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btDone.setBackground(new Color(0, 160, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btDone.setBackground(color.greenBt);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btDone.setBackground(color.darkbr);
            }
        });
        add(btDone);

        btBill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Bill bill = new Bill(vec, sum, sell);
            }
        });

        tborder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tborder.rowAtPoint(e.getPoint());
                if (row != -1) {
                    Object v_Name = tborder.getValueAt(row, 0);
                    String Name = v_Name.toString();
                    Change_Detail changeDetail = new Change_Detail(getIDItemInBill(Name), Name, ID_Bill);
                    changeDetail.btdel.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            changeDetail.deleteDetail();
                            getDetails();
                            changeDetail.setVisible(false);
                            repaint();
                            revalidate();
                        }
                    });
                    changeDetail.btedit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            changeDetail.updateDetail();
                            getDetails();
                            changeDetail.setVisible(false);
                            repaint();
                            revalidate();
                        }
                    });
                }
            }
        });
    }

    public int getMaxID() {
        ResultSet rs = db.getDB("SELECT MAX(ID_Bill) FROM bill");

        int id = 0;
        try {
            if (rs.next()) {
                id = rs.getInt("MAX(ID_Bill)");
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
        return id;
    }

    public void createNewCus() {
        try {
            int rs = db.executeDB("INSERT INTO customer(Phone) "
                    + "VALUES ('"
                    + tfPhone.getText()
                    + "')"
            );
            if (rs > 0) JOptionPane.showMessageDialog(null, "Cus created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getID_Cus() {
        ResultSet rs = db.getDB("SELECT * FROM customer" );

        String ID = "";
        int last = 0;
        try {
            while (rs.next()) {
                String phone = rs.getString("Phone");
                double sella = rs.getDouble("Lever");
                last = rs.getInt("Sum");

                if (phone.equals(tfPhone.getText())) {
                    check = true;
                    sell = sella;
                    lastSum = last;
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
        if (check) {
            ID = tfPhone.getText();
        } else {
            createNewCus();
            ID = tfPhone.getText();
        }
        return ID;
    }

    public void newBill(int ID_Bill, String ID_Staff, String ID_Table) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateString = date.format(formatter);

        try {
            int rs = db.executeDB("INSERT INTO bill "
                    + "VALUE("
                    + ID_Bill
                    + ", '"
                    + ID_Staff
                    +"', '"
                    + getID_Cus()
                    +"', '"
                    + ID_Table
                    +"', '"
                    + dateString
                    +"', 'Non', 0, 0, " +getSell() +", ' ')"
            );
            if (rs > 0) JOptionPane.showMessageDialog(null, "Bill Created Successfully");
            else JOptionPane.showMessageDialog(null, "Bill Created Failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        lbDataTime.setText("Date time: " +dateString);
    }

    public void getDetails() {
        ResultSet rs = db.getDB("SELECT i.Name_Item, d.Quantity, i.Price FROM detail d INNER JOIN item i ON d.ID_Item = i.ID_Item INNER JOIN bill b ON d.ID_Bill = b.ID_Bill INNER JOIN tablecf tb ON b.ID_Table = tb.ID_Table WHERE tb.Name = '"+NameTable+"'");

        try {
            model.setRowCount(0);
            sum = 0;
            vec.clear();
            while (rs.next()){
                String name = rs.getString("i.Name_Item");
                int quantity = rs.getInt("d.Quantity");
                int price = rs.getInt("i.Price");

                model.addRow(new Object[]{name, quantity, formatMoney.format(price)});
                vec.add(new Object[]{name, quantity, formatMoney.format(price)});

                sum += price * quantity;
                lbTotal.setText("Total : " + formatMoney.format(sum));
                lbsell.setText("Sell : " + sell*100 + "%");
                lbSum.setText("Sum: " + formatMoney.format(sum - (sum * sell)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public double getSell() {
        ResultSet rs = db.getDB("SELECT lever FROM customer WHERE Phone = '" + tfPhone.getText() +"'");

        double sell = 0;
        try {
            if (rs.next()) {
                sell = rs.getDouble("lever");
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
        return sell;
    }

    public int getIncome(int ID_Bill) {
        ResultSet rs = db.getDB("SELECT Ori_price, d.Quantity FROM detail d INNER JOIN item i ON d.ID_Item = i.ID_Item WHERE d.ID_Bill = '" + ID_Bill + "'");

        int income = 0;
        try {
            while (rs.next()) {
                int price = rs.getInt("Ori_price");
                int quantity = rs.getInt("Quantity");
                income += price * quantity;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return income;
    }

    public void done(int ID_Bill, String numTable) {
        HashMap<String, Integer> map = new HashMap<>();

        ResultSet res = db.getDB("SELECT ID_Item, Quantity FROM detail");

        try {
            while (res.next()) {
                String ID = res.getString("ID_Item");
                int quantity = res.getInt("Quantity");

                map.put(ID, quantity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String ID = entry.getKey();
            int quantity = entry.getValue();

            try {
                int rs = db.executeDB("UPDATE item SET Quantity = Quantity - " + quantity + " WHERE ID_Item = '" + ID + "'");
                if (rs > 0) JOptionPane.showMessageDialog(null, "Item Updated Successfully");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            int rs = db.executeDB("UPDATE bill SET Status = 'Done', Sum = " +(sum - (sum * sell)) +", Income = " +getIncome(ID_Bill) +", Sell_Off = " +getSell() +" WHERE ID_Bill = " + ID_Bill);
            if (rs > 0) JOptionPane.showMessageDialog(null, "Bill Done Successfully");
            else JOptionPane.showMessageDialog(null, "Bill Done Failed");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int result = db.executeDB("DELETE FROM detail WHERE ID_Bill = '"+ID_Bill+"'");
            if (result > 0) JOptionPane.showMessageDialog(null, "Detail Deleted");
            else JOptionPane.showMessageDialog(null, "Detail Not Deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int rs = db.executeDB("UPDATE customer SET Lever = 0, Sum = " +(sum + lastSum) +" WHERE Phone = '" + tfPhone.getText() +"'" );
            if (rs > 0) JOptionPane.showMessageDialog(null, "Customer Lever Updated");
            else JOptionPane.showMessageDialog(null, "Customer Lever Not Updated");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int re = db.executeDB("UPDATE tablecf SET Status = 'Y' WHERE Name = '" +numTable +"'");
            if (re > 0) JOptionPane.showMessageDialog(null, "Table Updated");
            else JOptionPane.showMessageDialog(null, "Table Not Updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIDItemInBill(String Name) {
        ResultSet rs = db.getDB("SELECT ID_Item FROM item WHERE Name_Item = '" + Name + "'");

        String ID = "";
        try {
            if (rs.next()) {
                ID = rs.getString("ID_Item");
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
        return ID;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
