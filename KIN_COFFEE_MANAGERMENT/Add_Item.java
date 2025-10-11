package KIN_COFFEE_MANAGERMENT;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class Add_Item extends JPanel {
    JLabel lbAdd = new JLabel("ADD ITEM");
    JLabel lbID = new JLabel("ID");
    JLabel lbName = new JLabel("Name");
    JLabel lbQuantity = new JLabel("Quantity");
    JLabel lbPrice = new JLabel("Ori/Price");
    JLabel lbDescription = new JLabel("Description");
    JTextField tfID = new JTextField();
    JTextField tfName = new JTextField();
    JTextField tfQuantity = new JTextField();
    JTextField tfPrice = new JTextField();
    JTextField tfsell = new JTextField();
    JTextArea taDescription = new JTextArea();
    JButton btImg;
    JButton btAdd = new JButton("ADD");

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    Image img = imgF.getImage();
    Image img2 = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon imgL = new ImageIcon(img2);
    JButton btexit = new JButton("", imgL);
    ImageIcon imF = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\ImageMode.png");
    Image im = imF.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    ImageIcon imL = new ImageIcon(im);

    static Font_CF font_CF = new Font_CF();
    static Color_CF color_CF = new Color_CF();
    static Button_Custum buttonCustum;

    Database db;

    private byte[] imgBytes;

    private String nameCata;

    public Add_Item(String nameCata) {
        this.nameCata = nameCata;
        addItemClass();
    }

    public void addItemClass() {
        setSize(800, 220);
        setLayout(null);

        db = new Database();
        db.connect();

        lbAdd.setBounds(30, 30, 300, 30);
        lbAdd.setFont(font_CF.fontAR20);
        add(lbAdd);
        btImg = new JButton();
        btImg.setBounds(30, 60, 150, 150);
        btImg.setBackground(Color.BLUE);
        btImg.setIcon(imL);
        btImg.setBorder(new LineBorder(color_CF.darkbr, 5));
        add(btImg);

        btAdd.setBounds(600, 30, 180, 30);
        buttonCustum = new Button_Custum(btAdd);
        add(btAdd);

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
        add(tfID);
        tfName.setBounds(280, 95, 200, 30);
        tfName.setFont(font_CF.fontAR16);
        buttonCustum = new Button_Custum(tfName);
        add(tfName);
        tfQuantity.setBounds(280, 130, 200, 30);
        tfQuantity.setFont(font_CF.fontAR16);
        buttonCustum = new Button_Custum(tfQuantity);
        add(tfQuantity);
        tfPrice.setBounds(382, 165, 98, 30);
        tfPrice.setFont(font_CF.fontAR16);
        buttonCustum = new Button_Custum(tfPrice);
        tfPrice.setText("Price");
        tfPrice.setForeground(Color.GRAY);
        add(tfPrice);
        tfsell.setBounds(280, 165, 98, 30);
        tfsell.setFont(font_CF.fontAR16);
        buttonCustum = new Button_Custum(tfsell);
        tfsell.setText("Original price");
        tfsell.setForeground(Color.GRAY);
        add(tfsell);

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
        add(scrollPane);

        btexit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btexit);

        btImg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    openFX();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = tfID.getText();
                if (id.length() >= 4) {
                    addItem();
                } else if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter ID");
                } else JOptionPane.showMessageDialog(null, "ID must be at least 4 characters");
            }
        });

        tfsell.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (tfsell.getText().equals("Original price")) {
                    tfsell.setText("");
                    tfsell.setForeground(color_CF.darkbr);
                }
            }
        });

        tfPrice.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (tfPrice.getText().equals("Price")) {
                    tfPrice.setText("");
                    tfPrice.setForeground(color_CF.darkbr);
                }
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
        fc.setCurrentDirectory(new File("E:\\IT3\\ImageCode\\KIN Coffee\\COFFEE"));

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

    public void addItem() {
        String id = getID_Catalog();
        try {
            int rs = db.executeDB("INSERT INTO item(ID_Item, Name_Item, Quantity, Ori_price, Price, Describe_Item, ID_Catalog)"
                    + " VALUES ('"
                    + tfID.getText()
                    +"', '"
                    + tfName.getText()
                    +"', "
                    + tfQuantity.getText()
                    +", "
                    + tfsell.getText()
                    +", "
                    + tfPrice.getText()
                    +", '"
                    + taDescription.getText()
                    +"', '"
                    + id
                    + "')"
            );
            if (rs > 0) JOptionPane.showMessageDialog(null, "Item Added Successfully");
            else JOptionPane.showMessageDialog(null, "Item Not Added Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.saveImage(imgBytes, "UPDATE item SET Image = (?) WHERE ID_Item = '" + tfID.getText() + "'");
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
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return catalog;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color_CF.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
