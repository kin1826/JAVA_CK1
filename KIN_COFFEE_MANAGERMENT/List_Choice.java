package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class List_Choice extends JPanel {
    ImageIcon imgIn = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\2.png");
    Image image2 = imgIn.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
    ImageIcon icon2 = new ImageIcon(image2);
    JLabel labellogo = new JLabel(icon2);
    ImageIcon imgPfb = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\facebook.png");
    Image ifb = imgPfb.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    ImageIcon imgLfb = new ImageIcon(ifb);
    JButton btfb =  new JButton(imgLfb);
    ImageIcon imgPig = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\ig.png");
    Image iig = imgPig.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    ImageIcon imgIig = new ImageIcon(iig);
    JButton btig =  new JButton(imgIig);

    JButton btAcc = new JButton("Account");
    JButton btHome = new JButton("Home");
    JButton btorder = new JButton("Order");
    JButton btstaff = new JButton("Staff");
    JButton btitem = new JButton("Item");
    JButton btloyal = new JButton("Loyal customer");
    JButton bttable = new JButton("Table");
    JButton btshow = new JButton("Show income");

    JLabel lbtime = new JLabel("Date & Time:");
    JLabel lbdate = new JLabel();

    static Color brownmilk = new Color(220, 184, 141);
    static Color brownmilk2 = new Color(185, 152, 108);
    static Color darkbr = new Color(75, 23, 3);
    static Color brownmilkdr = new Color(175, 115, 115);

    static Font font1 = new Font("Arial", Font.BOLD, 28);
    static Font font3 = new Font("Arial", Font.BOLD, 20);

    static private JButton lastButtonClicked = null;

    public List_Choice() {
        setLayout(null);
        setSize(305, 870);

        labellogo.setBounds(75, 15, 150, 200);
        add(labellogo);

        btfb.setBounds(90, 645, 30, 30);
        setButtton(btfb);
        add(btfb);
        btig.setBounds(180, 645, 30, 30);
        setButtton(btig);
        add(btig);

        btAcc.setBounds(0, 230, 300, 50);
        setButtton(btAcc);
        add(btAcc);
        btHome.setBounds(0, 280, 300, 50);
        setButtton(btHome);
        add(btHome);
        btorder.setBounds(0, 330, 300, 50);
        setButtton(btorder);
        add(btorder);
        btstaff.setBounds(0, 380, 300, 50);
        setButtton(btstaff);
        add(btstaff);
        btitem.setBounds(0, 430, 300, 50);
        setButtton(btitem);
        add(btitem);
        btloyal.setBounds(0, 480, 300, 50);
        setButtton(btloyal);
        add(btloyal);
        bttable.setBounds(0, 530, 300, 50);
        setButtton(bttable);
        add(bttable);
        btshow.setBounds(0, 580, 300, 50);
        setButtton(btshow);
        add(btshow);

        lbtime.setBounds(10, 700, 290, 30);
        lbtime.setFont(font3);
        lbtime.setForeground(Color.WHITE);
        add(lbtime);
        lbdate.setBounds(10, 740, 290, 30);
        lbdate.setFont(font1);
        lbdate.setForeground(Color.WHITE);
        add(lbdate);

        addButtonActionListener(btAcc);
        addButtonActionListener(btHome);
        addButtonActionListener(btorder);
        addButtonActionListener(btstaff);
        addButtonActionListener(btitem);
        addButtonActionListener(btloyal);
        addButtonActionListener(bttable);
        addButtonActionListener(btshow);
        btHome.setBackground(brownmilk2);
        lastButtonClicked = btHome;

        btfb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("a");
                try {
                    URI uri = new URI("https://www.facebook.com/tvbang.1811/");
                    Desktop.getDesktop().browse(uri);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException exx) {
                    throw new RuntimeException(exx);
                }
            }
        });
        btig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("b");
                try {
                    URI uri = new URI("https://www.instagram.com/tranvanbang.1811/");
                    Desktop.getDesktop().browse(uri);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException exx) {
                    throw new RuntimeException(exx);
                }
            }
        });

        Timer timer = new Timer(1000, e -> {
            updateClock();
        });
        timer.start();
    }

    private void addButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastButtonClicked != null) {
                    lastButtonClicked.setBackground(brownmilk);
                }
                button.setBackground(brownmilk2);

                lastButtonClicked = button;
            }
        });
    }

    public void updateClock(){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String dateString = date.format(formatter);

        lbdate.setText(dateString);
    }

    public static void setButtton(JButton bt) {
        bt.setForeground(Color.WHITE);
        bt.setFont(font3);
        bt.setBackground(brownmilk);
        bt.setBorderPainted(false);

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(brownmilk2);
            }
            public void mouseExited(MouseEvent e) {
                if (lastButtonClicked != bt) {
                    bt.setBackground(brownmilk);
                }
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(darkbr);
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(brownmilk);
        g.fillRect(0, 0, 300, 870);
        g.setColor(darkbr);
        g.fillRect(0, 227, 300, 3);
        g.fillRect(0, 630, 300, 3);
        g.fillRect(0, 690, 300, 3);
    }
}
