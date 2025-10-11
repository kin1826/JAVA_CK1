package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Order_Class extends JPanel {
    JLabel lbOrder = new JLabel("ORDER");
    JLabel lbStaff = new JLabel();
    JTextField tffind = new JTextField();
    JComboBox cbCata;
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> suggestionList = new JList<>(listModel);
    JScrollPane scrollPane = new JScrollPane(suggestionList);

    ImageIcon imsearch = new ImageIcon("E:\\IT3\\ImageCode\\search.jpg");
    ImageIcon imgsearch = new ImageIcon(imsearch.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    JButton btnSearch = new JButton(imgsearch);

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;

    Database db;
    Table_Show table_show;
    Menu menu;
    Order_New tborder;

    Vector<Object> vec = new Vector<>();

    private String ID_Staff;
    private String numTable;
    private String cata_Name;
    private int ID_Bill;
    private boolean check_table = false;
    private String phone;

    public Order_Class(String ID_Staff) {
        this.ID_Staff = ID_Staff;
        order();
    }

    public void order() {
        setLayout(null);
        setSize(1160, 770);
        setBackground(color.brownmilk);

        db = new Database();
        db.connect();

        tffind.setBounds(10, 20, 200, 30);
        buttonCustum = new Button_Custum(tffind);
        tffind.setBackground(Color.WHITE);
        tffind.setFont(font.fontAR14);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        scrollPane.setBounds(10, 50, 200, 100);
        btnSearch.setBounds(210, 20, 30, 30);

        String[] listCata = getNameCatalog();
        cbCata = new JComboBox(listCata);
        cbCata.setSelectedIndex(0);
        cbCata.setBounds(630, 20, 200, 30);
        cbCata.setForeground(color.darkbr);
        cbCata.setBackground(Color.WHITE);
        cbCata.setFont(font.fontAR20);
        add(cbCata);

        lbOrder.setBounds(340, 10, 200, 50);
        lbOrder.setForeground(Color.WHITE);
        lbOrder.setFont(font.fontAr40);
        add(lbOrder);

        lbStaff.setBounds(20, 780, 200, 50);
        lbStaff.setForeground(Color.WHITE);
        lbStaff.setFont(font.fontAR20);
        lbStaff.setText("Staff: " + getNameStaff());
        add(lbStaff);

        table_show = new Table_Show(new Table_Show.ButtonClickListener() {
            @Override
            public void onButtonClick(String buttonText) {
                numTable = buttonText;
                table_show.setVisible(false);
                tborder = new Order_New(numTable);
                tborder.setBounds(820, 70, 360, 700);
                add(tborder);
                if (checkTable()) {
                    check_table = true;
                }
                if (!check_table) {
                    tborder.btnNew.setVisible(false);
                    tborder.getDetails();
                    ID_Bill = getIDBillLast();
                    tborder.tfPhone.setText(phone);
                }

                tborder.btnNew.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int ID_Last = tborder.getMaxID();
                        ID_Bill = ID_Last + 1;
                        if (!tborder.tfPhone.getText().isEmpty()){
                            tborder.newBill(ID_Bill, ID_Staff, getID_Table());
                            changeStatus();
                        } else JOptionPane.showMessageDialog(null, "Please enter phone number");
                        tborder.lbStaff.setText("Staff: " + getNameStaff());
                        tborder.setID_Bill(ID_Bill);
                    }
                });

                tborder.btnCancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        deleteDetail();
                    }
                });

                tborder.btDone.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tborder.done(ID_Bill, numTable);
                        repaint();
                        revalidate();
                    }
                });

                repaint();
                revalidate();
            }
        });
        table_show.setBounds(10, 70, 800, 470);
        add(table_show);

        cbCata.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cata_Name = (String) cbCata.getSelectedItem();
                menu = new Menu(getID_Catalog());
                menu.setBounds(10, 70, 800, 470);
                add(menu);

                add(tffind);
                add(btnSearch);
                vec = menu.getVector();
                tffind.getDocument().addDocumentListener(new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateFind();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateFind();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateFind();
                    }
                });

                btnSearch.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        updateFind();
                    }
                });

                menu.setButtonClickListener(new Menu.ButtonClickListener() {
                    @Override
                    public void onButtonClick(String buttonText) {
                        String id_Item = buttonText;
                        Order_Show orderShow = new Order_Show(id_Item);
                        orderShow.setBounds(10, 550, 800, 220);
                        add(orderShow);

                        orderShow.btadd.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                orderShow.addToDetail(ID_Bill);
                                tborder.getDetails();
                                orderShow.setVisible(false);
                            }
                        });
                        repaint();
                        revalidate();
                    }
                });
                repaint();
                revalidate();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                scrollPane.setVisible(false);
                repaint();
                revalidate();
            }
        });

        Timer timer = new Timer(1000, e -> {
            table_show.getTable();
            table_show.createTable();
        });
        timer.start();
        setVisible(false);
    }

    public String getID_Catalog() {
        String catalog = "";
        ResultSet rs = db.getDB("SELECT ID_Catalog FROM catalog WHERE Name_Cata = '"+cata_Name+"'");

        try {
            if (rs.next()) {
                catalog = rs.getString("ID_Catalog");
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
        return catalog;
    }

    public String getNameStaff() {
        String name = "";
        ResultSet rs = db.getDB("SELECT Name_Staff FROM staff WHERE `ID_Staff` = '" + ID_Staff + "'");

        try {
            if (rs.next()) {
                name = rs.getString("Name_Staff");
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
        return name;
    }

    public String[] getNameCatalog() {
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = db.getDB("SELECT Name_Cata FROM catalog");

        try {
            while (rs.next()){
                String name = rs.getString("Name_Cata");
                list.add(name);
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

    public void deleteDetail() {
        try {
            int result = db.executeDB("DELETE FROM detail WHERE ID_Bill = '"+ID_Bill+"'");
            if (result > 0) JOptionPane.showMessageDialog(null, "Detail Deleted");
            else JOptionPane.showMessageDialog(null, "Detail Not Deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int result = db.executeDB("DELETE FROM bill WHERE ID_Bill = " + ID_Bill);
            if (result > 0) JOptionPane.showMessageDialog(null, "Bill cancelled");
            else JOptionPane.showMessageDialog(null, "Bill Not Cancelled");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!tborder.check) {
            try {
                int result = db.executeDB("DELETE FROM customer WHERE Phone = '" + tborder.tfPhone.getText() + "'");
                if (result > 0) JOptionPane.showMessageDialog(null, "Customer Cancelled");
                else JOptionPane.showMessageDialog(null, "Customer Not Cancelled");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            int re = db.executeDB("UPDATE tablecf SET Status = 'Y' WHERE Name = '" +numTable +"'");
            if (re > 0) JOptionPane.showMessageDialog(null, "Table Updated");
            else JOptionPane.showMessageDialog(null, "Table Not Updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkTable() {
        ResultSet rs = db.getDB("SELECT Status FROM tablecf WHERE Name = '" +numTable +"'");

        boolean check = false;
        try {
            if (rs.next()) {
                String status = rs.getString("Status");

                if (status.equals("Y")) {
                    check = true;
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
        return check;
    }

    public void changeStatus() {
        try {
            int rs = db.executeDB("UPDATE tablecf SET Status = 'N' WHERE Name = '" + numTable +"'");
            if (rs > 0) JOptionPane.showMessageDialog(null, "Status Changed");
            else JOptionPane.showMessageDialog(null, "Status Not Changed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getID_Table() {
        ResultSet rs = db.getDB("SELECT ID_Table FROM tablecf WHERE Name = '" +numTable +"'");

        String id = "";
        try {
            if (rs.next()) {
                id = rs.getString("ID_Table");
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

    public int getIDBillLast() {
        ResultSet rs = db.getDB("SELECT ID_Bill, ID_Cus FROM bill WHERE ID_Table = '" + getID_Table() + "' AND Status = 'Non'");

        int id = 0;
        try {
            if (rs.next()) {
                id = rs.getInt("ID_Bill");
                phone = rs.getString("ID_Cus");
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

    public void updateFind() {
        String input = tffind.getText().toLowerCase();
        listModel.clear();

        if (!input.isEmpty()) {
            for (int i = 0; i < vec.size(); i++) {
                Object[] object = (Object[]) vec.get(i);
                String s = (String) object[1];

                String[] words = s.toLowerCase().split(" ");

                for (String word : words) {
                    if (word.startsWith(input)) {
                        listModel.addElement(s);
                        break;
                    }
                }
            }
        }
        scrollPane.setVisible(true);
        add(scrollPane);
        setComponentZOrder(scrollPane, 0);

        suggestionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String select = suggestionList.getSelectedValue();

                for (int i = 0; i < vec.size(); i++) {
                    Object[] object = (Object[]) vec.get(i);
                    String s = (String) object[1];

                    if (s.equals(select)) {
                        String id = (String) object[0];
                        Order_Show orderShow = new Order_Show(id);
                        orderShow.setBounds(10, 550, 800, 220);
                        add(orderShow);
                        scrollPane.setVisible(false);
                        repaint();
                        revalidate();
                    }
                }
            }
        });
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

