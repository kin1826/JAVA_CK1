package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

public class Order_Show extends JPanel {
    JLabel lbName = new JLabel();
    JLabel lbPrice = new JLabel();
    JLabel lbDes = new JLabel();
    JLabel lbimg = new JLabel();
    JTextArea tfNote = new JTextArea();
    JButton btadd = new JButton("ADD");
    JSpinner spQuan;

    ImageIcon im = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    ImageIcon imgex = new ImageIcon(im.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    JButton btExit = new JButton(imgex);

    static Vector<Object> vec = new Vector<>();

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    private String ID_Item;

    Database db;

    public Order_Show(String ID_Item) {
        this.ID_Item = ID_Item;
        display();
    }

    public void display() {
        setLayout(null);
        setSize(800, 220);
        setBackground(Color.WHITE);

        db = new Database();
        db.connect();

        getInfor();
        Object[] ob = (Object[]) vec.get(0);

        ImageIcon img = (ImageIcon) ob[3];
        Image image = img.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        lbimg.setIcon(new ImageIcon(image));
        lbimg.setBounds(30, 50, 150, 150);
        lbimg.setBorder(new LineBorder(color.darkbr, 5));
        add(lbimg);
        lbName.setBounds(200, 50, 150, 30);
        lbName.setFont(font.fontAR16);
        lbName.setForeground(color.darkbr);
        lbName.setText(ob[0].toString());
        add(lbName);
        lbPrice.setBounds(200, 80, 150, 30);
        lbPrice.setFont(font.fontAR16);
        lbPrice.setForeground(color.darkbr);
        int p = (int) ob[1];
        lbPrice.setText(FMMoney.format(p));
        add(lbPrice);
        lbDes.setBounds(200, 115, 180, 85);
        lbDes.setFont(font.fontAR16);
        lbDes.setForeground(color.darkbr);
        lbDes.setText(ob[2].toString());
        lbDes.setHorizontalAlignment(SwingConstants.LEFT);
        lbDes.setVerticalAlignment(SwingConstants.TOP);
        lbDes.setBorder(new LineBorder(color.darkbr, 2));
        add(lbDes);

        JScrollPane scrollPane = new JScrollPane(tfNote);
        scrollPane.setBounds(400, 50, 200, 150);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                this.thumbColor = color.darkbr;
                this.trackColor = Color.GRAY;
                this.thumbDarkShadowColor = Color.DARK_GRAY;
            }
        });
        tfNote.setFont(font.fontAR16);
        tfNote.setForeground(color.darkbr);
        tfNote.setBackground(color.brownmilk);
        tfNote.setLineWrap(true);
        tfNote.setWrapStyleWord(true);
        add(scrollPane);

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, getQuantity(), 1);
        spQuan = new JSpinner(model);
        spQuan.setBounds(620, 80, 150, 30);
        spQuan.setFont(font.fontAR16);
        buttonCustum = new Button_Custum(spQuan);
        add(spQuan);
        btadd.setBounds(620, 150, 150, 30);
        btadd.setFont(font.fontAR16);
        buttonCustum = new Button_Custum(btadd);
        add(btadd);

        btExit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btExit);

        btExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                repaint();
                revalidate();
            }
        });
    }

    public void getInfor() {
        ResultSet rs = db.getDB("SELECT Name_Item, Price, Describe_Item, Image FROM item WHERE ID_Item = '" + ID_Item + "'");

        try {
            vec.clear();

            if (rs.next()) {
                String name = rs.getString("Name_Item");
                int price = rs.getInt("Price");
                String describe = rs.getString("Describe_Item");
                Blob bl = rs.getBlob("Image");
                InputStream in = bl.getBinaryStream();
                ImageIcon img = new ImageIcon(in.readAllBytes());
                ImageIcon imageIcon = new ImageIcon(img.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));

                vec.add(new Object[]{name, price, describe, imageIcon});
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getMaxNewID_De() {
        ResultSet rs = db.getDB("SELECT MAX(ID) FROM detail");

        int id = 0;
        try {
            if (rs.next()) {
                id = rs.getInt("MAX(ID)");
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

    public void addToDetail(int id_Bill) {
        int id_De = getMaxNewID_De() + 1;

        try {
            int rs = db.executeDB("INSERT INTO detail(ID, ID_Bill, ID_Item, Quantity, Note) "
                    +"VALUE("
                    +id_De
                    +", "
                    +id_Bill
                    +", '"
                    + ID_Item
                    +"', "
                    + spQuan.getValue()
                    +", '"
                    + tfNote.getText()
                    + "')"
            );
            if (rs > 0) JOptionPane.showMessageDialog(this, "Item Added Successfully");
            else JOptionPane.showMessageDialog(this, "Item Not Added Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
