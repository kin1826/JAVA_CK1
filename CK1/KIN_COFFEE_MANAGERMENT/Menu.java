package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;

public class Menu extends JPanel {
    JButton btback = new JButton();
    JButton btnext = new JButton();
    JButton btre = new JButton();
    JButton btall = new JButton();

    ImageIcon imgF = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    Image img = imgF.getImage();
    Image img2 = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon imgL = new ImageIcon(img2);
    JButton btexit = new JButton("", imgL);

    JPanel panel = new JPanel();

    private Vector<Object> vector = new Vector<>();
    private HashMap<String, JButton> btmap = new HashMap<>();

    ImageIcon imbL = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\back.png");
    ImageIcon imgB = new ImageIcon(imbL.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH));
    ImageIcon ibnL = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\next.png");
    ImageIcon imgN = new ImageIcon(ibnL.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH));
    ImageIcon imF = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\reload.png");
    ImageIcon imgre = new ImageIcon(imF.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    Database db;

    private int x;
    private int y;
    private String typeCataID;
    private ButtonClickListener buttonClickListener;
    private int pageNow = 1;
    private int page_F = 1;

    public interface ButtonClickListener {
        void onButtonClick(String buttonText);
    }

    public String getTypeCataID() {
        return typeCataID;
    }

    public void setTypeCataID(String typeCataID) {
        this.typeCataID = typeCataID;
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    public Vector<Object> getVector() {
        return vector;
    }

    public Menu(String typeCataID) {
        this.typeCataID = typeCataID;
        menuDisplay();
    }

    public void menuDisplay() {
        setLayout(null);
        setSize(800, 470);
        setBackground(Color.DARK_GRAY);

        db = new Database();
        db.connect();

        btback.setBounds(10, 210, 30, 50 );
        btback.setIcon(imgB);
        btback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((page_F - 15) >= 0) {
                    page_F -= 15;
                    crMenu();
                    showALL();
                }
            }
        });

        btnext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((page_F + 15) <= vector.size()) {
                    page_F += 15;
                    crMenu();
                    showALL();
                    repaint();
                    revalidate();
                }

            }
        });
        btnext.setBounds(760, 210, 30, 50 );
        btnext.setIcon(imgN);

        btall.setBounds(760, 390, 30, 30 );
        btall.setBackground(color.brownmilk);
        add(btall);
        btre.setBounds(760, 430, 30, 30 );
        btre.setIcon(imgre);
        btre.setOpaque(false);
        add(btre);

        btexit.setBounds(getWidth() - 30, 3, 20, 20);
        btexit.setOpaque(false);
        add(btexit);

        btexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAll();
                setVisible(false);
            }
        });

        btre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getAllItem();
                crMenu();
                showALL();
                repaint();
                revalidate();
            }
        });

        btall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getAllItemm();
                crMenu();
                showALL();
                repaint();
                revalidate();
            }
        });

        getAllItem();
        crMenu();
        add(btback);
        add(btnext);
    }

    public void showALL() {
        add(btback);
        add(btnext);
        add(btexit);
        add(btre);
        add(btall);
    }

    public void getAllItem() {
        ResultSet rs = db.getDB("SELECT * FROM item WHERE ID_Catalog = '" +typeCataID+"'");

        try {
            vector.clear();
            while (rs.next()) {
                String id = rs.getString("ID_Item");
                String name = rs.getString("Name_Item");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                String description = rs.getString("Describe_Item");
                Blob bl = rs.getBlob("Image");
                InputStream is = bl.getBinaryStream();
                ImageIcon img = new ImageIcon(is.readAllBytes());
                ImageIcon imageIcon = new ImageIcon(img.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH));
                String id_catalog = rs.getString("ID_Catalog");

                Object[] ob = new Object[]{id,name,quantity,price,description,imageIcon,id_catalog};

                vector.add(ob);
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

    public void getAllItemm() {
        ResultSet rs = db.getDB("SELECT * FROM item");

        try {
            vector.clear();
            while (rs.next()) {
                String id = rs.getString("ID_Item");
                String name = rs.getString("Name_Item");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                String description = rs.getString("Describe_Item");
                Blob bl = rs.getBlob("Image");
                InputStream is = bl.getBinaryStream();
                ImageIcon img = new ImageIcon(is.readAllBytes());
                ImageIcon imageIcon = new ImageIcon(img.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH));
                String id_catalog = rs.getString("ID_Catalog");

                Object[] ob = new Object[]{id,name,quantity,price,description,imageIcon,id_catalog};

                vector.add(ob);
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

    public void crMenu() {
        x = 5;
        y = 5;

        btmap.clear();

        panel.removeAll();
        panel.setLayout(null);
        panel.setSize(700, 470);

        int leng = 0;

        if (page_F + 14 > vector.size()) {
            leng = vector.size();
        } else {
            leng = page_F + 14;
        }

        for (int i = page_F-1; i < leng; i++) {
//            if (page_F >= vector.size()) {
//                break;
//            }
            Object[] ob = (Object[]) vector.get(i);
            ImageIcon img = (ImageIcon) ob[5];
            String name = (String) ob[1];
            String id = (String) ob[0];
            int price = (int) ob[3];

            if (x > 610) {
                y = y + 155;
                x = 5;
            }

            JButton bt = new JButton();
            bt.setBounds(x + 10, y + 5, 110, 110);
            bt.setIcon(img);
            JLabel labelN = new JLabel(name);
            labelN.setBounds(x + 10, y + 110, 120, 30 );
            JLabel lbPrice = new JLabel(FMMoney.format(price) + "");
            lbPrice.setBounds(x + 10, y + 125, 120, 30 );

            bt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (buttonClickListener != null) {
                        buttonClickListener.onButtonClick(id);
                    }
                }
            });

            btmap.put(id, bt);

            panel.add(bt);
            panel.add(labelN);
            panel.add(lbPrice);

            x = x + 140;
        }
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 0, 700, 470);
        add(panel);
        repaint();
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(50, 0, 700, 470);
        g.setColor(color.brownmilk);
        g.fillRect(55, 5, 130, 150);
        g.fillRect(55, 160, 130, 150);
        g.fillRect(55, 315, 130, 150);
        g.fillRect(195, 5, 130, 150);
        g.fillRect(195, 160, 130, 150);
        g.fillRect(195, 315, 130, 150);
        g.fillRect(335, 5, 130, 150);
        g.fillRect(335, 160, 130, 150);
        g.fillRect(335, 315, 130, 150);
        g.fillRect(475, 5, 130, 150);
        g.fillRect(475, 160, 130, 150);
        g.fillRect(475, 315, 130, 150);
        g.fillRect(615, 5, 130, 150);
        g.fillRect(615, 160, 130, 150);
        g.fillRect(615, 315, 130, 150);
    }
}