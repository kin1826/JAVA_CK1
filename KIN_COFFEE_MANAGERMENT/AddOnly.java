package KIN_COFFEE_MANAGERMENT;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AddOnly extends JPanel {
    JLabel lbUpdate = new JLabel("UPDATE ITEM");
    JLabel lbID = new JLabel("ID");
    JLabel lbName = new JLabel("Name");
    JLabel lbQuantity = new JLabel("Quantity");
    JLabel lbPrice = new JLabel("Price");
    JLabel lbDescription = new JLabel("Description");
    JTextField tfID = new JTextField();
    JTextField tfName = new JTextField();
    JTextField tfQuantity = new JTextField();
    JTextField tfPrice = new JTextField();
    JTextArea taDescription = new JTextArea();
    JButton btImg;
    JButton btUpdate = new JButton("UPDATE");
    JButton btDel = new JButton("DELETE");

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    Image img = imgF.getImage();
    Image img2 = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon imgL = new ImageIcon(img2);
    JButton btexit = new JButton("", imgL);
    ImageIcon imF = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\ImageMode.png");
    Image im = imF.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    ImageIcon imL = new ImageIcon(im);

    Vector<Object> vec = new Vector<>();

    static Font_CF font_CF = new Font_CF();
    static Color_CF color_CF = new Color_CF();
    static Button_Custum buttonCustum;

    Database db;

    private byte[] imgBytes;

    private String nameCata;
    private String idItem;
    private boolean check = false;

    public AddOnly() {
        this.nameCata = nameCata;
        this.idItem = idItem;
        addItemClass();
    }

    public void addItemClass() {
        setSize(800, 220);
        setLayout(null);

        db = new Database();
        db.connect();

        lbUpdate.setBounds(30, 30, 300, 30);
        lbUpdate.setFont(font_CF.fontAR20);
        add(lbUpdate);
        btImg = new JButton();
        btImg.setBounds(30, 60, 150, 150);
        btImg.setBackground(Color.BLUE);
        btImg.setIcon(imL);
        btImg.setBorder(new LineBorder(color_CF.darkbr, 5));
        add(btImg);

        btUpdate.setBounds(530, 30, 120, 30);
        buttonCustum = new Button_Custum(btUpdate);
        add(btUpdate);
        btDel.setBounds(660, 30, 120, 30);
        buttonCustum = new Button_Custum(btDel);
        add(btDel);

        lbID.setBounds(200, 60, 100, 30);
        lbID.setFont(font_CF.fontAR16);
        lbID.setForeground(color_CF.darkbr);
        add(lbID);
        lbName.setBounds(200, 95, 100, 30);
        lbName.setFont(font_CF.fontAR16);
        lbName.setForeground(color_CF.darkbr);
        add(lbName);
        lbQuantity.setBounds(200, 130, 100, 30);
        lbQuantity.setFont(font_CF.fontAR16);
        lbQuantity.setForeground(color_CF.darkbr);
        add(lbQuantity);
        lbPrice.setBounds(200, 165, 100, 30);
        lbPrice.setFont(font_CF.fontAR16);
        lbPrice.setForeground(color_CF.darkbr);
        add(lbPrice);
        lbDescription.setBounds(530, 60, 100, 30);
        lbDescription.setFont(font_CF.fontAR16);
        lbDescription.setForeground(color_CF.darkbr);
        add(lbDescription);

        tfID.setBounds(280, 60, 200, 30);
        tfID.setFont(font_CF.fontAR16);
        buttonCustum = new Button_Custum(tfID);
        tfID.setText(idItem);
        add(tfID);

        JScrollPane scrollPane = new JScrollPane(taDescription);
        scrollPane.setBounds(530, 95, 250, 120);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                this.thumbColor = color_CF.darkbr;
                this.trackColor = Color.GRAY;
                this.thumbDarkShadowColor = Color.DARK_GRAY;
            }
        });
        taDescription.setFont(font_CF.fontAR16);
        taDescription.setForeground(color_CF.darkbr);
        taDescription.setBackground(color_CF.brownmilk);
        taDescription.setLineWrap(true);
        taDescription.setWrapStyleWord(true);
//        taDescription.setText(ob[4].toString());
        add(scrollPane);
//        ImageIcon img = (ImageIcon) ob[5];
//        Image img2 = img.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
//        btImg.setIcon(new ImageIcon(img2));

        btexit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btexit);

        btImg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    openFX();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                check = true;
            }
        });

        btUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateItem();
                setVisible(false);
            }
        });

        btexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public void openFX() throws IOException {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open File");
        fc.setCurrentDirectory(new File("D:\\IT3\\ImageCode\\KIN Coffee\\COFFEE"));

        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));

        int re = fc.showOpenDialog(this);
        if (re == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            imgBytes = convert(file);

            try {
                Image image = ImageIO.read(file).getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                btImg.setIcon(new ImageIcon(image));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public byte[] convert(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Image img = ImageIO.read(file);
        ImageIO.write((java.awt.image.BufferedImage) img, "jpg", bos);

        return bos.toByteArray();
    }

    public void updateItem() {

            try {
                db.saveImage(imgBytes, "UPDATE staff SET Image = (?) WHERE ID_Staff = 'ST01'");
            } catch (Exception e) {
                e.printStackTrace();
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
        }
        return catalog;
    }

    public void getInfor() {
        ResultSet rs = db.getDB("SELECT * FROM item WHERE ID_Item = '"+idItem+"'");

        try {
            vec.clear();
            if (rs.next()) {
                String id = rs.getString("ID_Item");
                String name = rs.getString("Name_Item");
                String quantity = rs.getString("Quantity");
                String price = rs.getString("Price");
                String describe = rs.getString("Describe_Item");
                Blob bl = rs.getBlob("Image");
                InputStream is = bl.getBinaryStream();
                ImageIcon img = new ImageIcon(is.readAllBytes());
                ImageIcon imageIcon = new ImageIcon(img.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                Object[] ob = new Object[]{id, name, quantity, price, describe, imageIcon};
                vec.add(ob);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color_CF.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
