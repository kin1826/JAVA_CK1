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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemCata_Class extends JPanel {
    JLabel lbItem = new JLabel("ITEM & CATALOG");
    JLabel lbCatalog = new JLabel("Catalog");
    JLabel lbIt = new JLabel("Item");
    JButton btadd = new JButton("Add");
    JButton btupdate = new JButton("Update");
    JButton btdel = new JButton("Delete");
    JButton btaddItem = new JButton("+");
    JLabel lbcmt = new JLabel("Click on the item to edit or delete.");
    JComboBox cbChoiceCata;

    JScrollPane scItem;
    JTable tblItem;
    DefaultTableModel modelItems;
    JScrollPane scCatalog;
    JTable tblCatalog;
    DefaultTableModel modelCatalog;

    Add_Catalog addCatalog = new Add_Catalog();
    Update_Catalog updateCatalog = new Update_Catalog();
    Delete_Catalog deleteCatalog = new Delete_Catalog();
    Add_Item addItem;
    UpdateDelete_Item updateItem;
    Menu menu;

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    Database db;

    private String nameCata;
    private String typeDis;
    private String ID_Item;

    public ItemCata_Class() {
        setLayout(null);
        setSize(1160, 770);
        setBackground(color.brownmilk);

        db = new Database();
        db.connect();

        String[] list = getNameCatalog();
        cbChoiceCata = new JComboBox(list);
        cbChoiceCata.setSelectedIndex(0);
        cbChoiceCata.setBounds(630, 20, 200, 30);
        cbChoiceCata.setForeground(color.darkbr);
        cbChoiceCata.setBackground(Color.WHITE);
        cbChoiceCata.setFont(font.fontAR20);
        add(cbChoiceCata);

        String[] columnItemName = {"ID_Item", "Name_Item", "Quantity", "Price"};
        modelItems = new DefaultTableModel(columnItemName, 0);
        tblItem = new JTable(modelItems);
        tblItem.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblItem.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblItem.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblItem.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblItem.setBackground(color.brownmilk);
        scItem = new JScrollPane(tblItem);
        scItem.setBounds(830, 485, 340, 130);
        add(scItem);
        String[] columnCataName = {"ID_Catalog", "Name"};
        modelCatalog = new DefaultTableModel(columnCataName, 0);
        tblCatalog = new JTable(modelCatalog);
        tblCatalog.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblCatalog.getColumnModel().getColumn(1).setPreferredWidth(240);
        tblCatalog.setBackground(color.brownmilk);
        scCatalog = new JScrollPane(tblCatalog);
        scCatalog.setBounds(830, 380, 340, 100);
        add(scCatalog);

        lbItem.setBounds(260, 10, 400, 50);
        lbItem.setForeground(Color.WHITE);
        lbItem.setFont(font.fontAr40);
        add(lbItem);

        addCatalog.setBounds(820, 70, 360, 300);
        add(addCatalog);
        addCatalog.setVisible(true);
        updateCatalog.setBounds(820, 70, 360, 300);
        add(updateCatalog);
        updateCatalog.setVisible(false);
        deleteCatalog.setBounds(820, 70, 360, 300);
        add(deleteCatalog);
        deleteCatalog.setVisible(false);

        lbCatalog.setForeground(color.darkbr);
        lbCatalog.setFont(font.fontAR20);
        lbCatalog.setBounds(830, 690, 200, 30);
        add(lbCatalog);
        btadd.setBounds(830, 720, 100, 40);
        btadd.setBackground(color.greenBt);
        setButtton(btadd);
        add(btadd);
        btupdate.setBounds(950, 720, 100, 40);
        btupdate.setBackground(color.BlueBt);
        setButtton(btupdate);
        add(btupdate);
        btdel.setBounds(1070, 720, 100, 40);
        btdel.setBackground(color.redBt);
        setButtton(btdel);
        add(btdel);

        lbIt.setForeground(color.darkbr);
        lbIt.setFont(font.fontAR20);
        lbIt.setBounds(830, 620, 200, 30);
        add(lbIt);
        btaddItem.setBounds(830, 650, 60, 40);
        btaddItem.setBackground(color.greenBt);
        setButtton(btaddItem);
        add(btaddItem);
        lbcmt.setForeground(color.darkbr);
        lbcmt.setFont(font.fontAR16);
        lbcmt.setBounds(900, 655, 500, 30);
        add(lbcmt);

        typeDis = "All";

//        menu = new Menu(typeDis);
//        menu.setBounds(10, 70, 800, 470);
//        add(menu);


        nameCata = (String) cbChoiceCata.getSelectedItem();
        menu = new Menu(getID_Catalog());
        menu.setBounds(10, 70, 800, 470);
        add(menu);
        menu.setButtonClickListener(new Menu.ButtonClickListener() {
            @Override
            public void onButtonClick(String id) {
                ID_Item = id;
                System.out.println(ID_Item);
                updateItem = new UpdateDelete_Item(nameCata, ID_Item);
                updateItem.setBounds(10, 550, 800, 220);
                add(updateItem);
                updateItem.setVisible(true);
                repaint();
                revalidate();
            }
        });

        cbChoiceCata.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameCata = (String) cbChoiceCata.getSelectedItem();
                typeDis = nameCata;
                menu = new Menu(getID_Catalog());
                menu.setBounds(10, 70, 800, 470);
                add(menu);

                menu.setButtonClickListener(new Menu.ButtonClickListener() {
                    @Override
                    public void onButtonClick(String id) {
                        ID_Item = id;
                        System.out.println(ID_Item);
                        updateItem = new UpdateDelete_Item(nameCata, ID_Item);
                        updateItem.setBounds(10, 550, 800, 220);
                        add(updateItem);
                        updateItem.setVisible(true);
                        repaint();
                        revalidate();
                    }
                });
            }
        });

        btadd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCatalog.setVisible(true);
                updateCatalog.setVisible(false);
                deleteCatalog.setVisible(false);
                String[] list1 = getNameCatalog();
                cbChoiceCata.setModel(new DefaultComboBoxModel<>(list1));
            }
        });

        btupdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCatalog.setVisible(true);
                addCatalog.setVisible(false);
                deleteCatalog.setVisible(false);
                String[] list1 = getNameCatalog();
                cbChoiceCata.setModel(new DefaultComboBoxModel<>(list1));
            }
        });

        btdel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCatalog.setVisible(true);
                updateCatalog.setVisible(false);
                addCatalog.setVisible(false);
                String[] list1 = getNameCatalog();
                cbChoiceCata.setModel(new DefaultComboBoxModel<>(list1));
            }
        });

        btaddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItem = new Add_Item(nameCata);
                addItem.setBounds(10, 550, 800, 220);
                add(addItem);
                addItem.setVisible(true);

                repaint();
                revalidate();
            }
        });



        Timer timer = new Timer(1000, e -> {
            getListCatalog();
            getListItem();
            repaint();
            revalidate();
        });
        timer.start();
    }

    public String[] getNameCatalog() {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = db.getDB("SELECT Name_Cata FROM catalog");

        try {
            while (rs.next()){
                String name = rs.getString("Name_Cata");
                list.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list.toArray(new String[list.size()]);
    }

    public void getListCatalog() {
        ResultSet rs = db.getDB("SELECT * FROM catalog");

        try {
            modelCatalog.setRowCount(0);

            while (rs.next()) {
                String id = rs.getString("ID_Catalog");
                String name = rs.getString("Name_Cata");

                modelCatalog.addRow(new Object[]{id, name});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getID_Catalog() {
        String catalog = "";
        ResultSet rs = db.getDB("SELECT ID_Catalog FROM catalog WHERE Name_Cata = '"+nameCata+"'");

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

    public void getListItem() {
        ResultSet rs = db.getDB("SELECT * FROM item WHERE ID_Catalog = '" + getID_Catalog() + "'");

        try {
            modelItems.setRowCount(0);

            while (rs.next()) {
                String id = rs.getString("ID_Item");
                String name = rs.getString("Name_Item");
                int price = rs.getInt("Price");
                String quantity = rs.getString("Quantity");

                modelItems.addRow(new Object[]{id, name, FMMoney.format(price), quantity});
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
        bt.setForeground(color.darkbr);
        bt.setFont(font.fontAR16);
        bt.setBorderPainted(false);
        Color last = bt.getBackground();

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(color.brownmilkdr);
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(last);
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(color.darkbr);
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
