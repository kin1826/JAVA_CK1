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

public class Home extends JPanel {
    ImageIcon imgIL1 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\INTRO\\img1.jpg");
    Image imgIL1Image = imgIL1.getImage().getScaledInstance(1140, 400, Image.SCALE_SMOOTH);
    ImageIcon imgIL2 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\INTRO\\img2.jpg");
    Image imgIL2Image = imgIL2.getImage().getScaledInstance(1140, 400, Image.SCALE_SMOOTH);
    ImageIcon imgIL3 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\INTRO\\img3.jpg");
    Image imgIL3Image = imgIL3.getImage().getScaledInstance(1140, 400, Image.SCALE_SMOOTH);
    ImageIcon imgIL4 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\INTRO\\img4.jpg");
    Image imgIL4Image = imgIL4.getImage().getScaledInstance(1140, 400, Image.SCALE_SMOOTH);
    ImageIcon imgIL5 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\INTRO\\img5.jpg");
    Image imgIL5Image = imgIL5.getImage().getScaledInstance(1140, 400, Image.SCALE_SMOOTH);

    ImageIcon imgBeL1 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\COFFEE\\cacaonong.jpg");
    Image imgBeL1Image = imgBeL1.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
    ImageIcon imgBe1 = new ImageIcon(imgBeL1Image);
    JLabel lbimgBe1 = new JLabel(imgBe1);
    ImageIcon imgBeL2 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\COFFEE\\cafeden.jpg");
    Image imgBeL2Image = imgBeL2.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
    ImageIcon imgBe2 = new ImageIcon(imgBeL2Image);
    JLabel lbimgBe2 = new JLabel(imgBe2);
    ImageIcon imgBeL3 = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\COFFEE\\cafephin.jpg");
    Image imgBeL3Image = imgBeL3.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
    ImageIcon imgBe3 = new ImageIcon(imgBeL3Image);
    JLabel lbimgBe3 = new JLabel(imgBe3);

    JLabel lbbest = new JLabel("Best seller");
    JLabel lbcacaoHot = new JLabel("Hot Cacao");
    JLabel lbcfbl = new JLabel("Black Coffee");
    JLabel lbcfphin = new JLabel("Phin Coffee");

    static Color_CF color_CF = new Color_CF();

    static Font font1 = new Font("Brush Script MT", Font.BOLD, 50);
    static Font font2 = new Font("Brush Script MT", Font.BOLD, 40);
    static Font font3 = new Font("Arial", Font.BOLD, 20);

    private int i = 0;

    public Home() {
        setLayout(null);
        setSize(1160, 770);
        setBackground(Color.WHITE);

        lbcacaoHot.setBounds(155, 740, 200, 50);
        lbcacaoHot.setForeground(Color.WHITE);
        lbcacaoHot.setFont(font1);
        add(lbcacaoHot);
        lbcfbl.setBounds(480, 740, 250, 50);
        lbcfbl.setForeground(Color.WHITE);
        lbcfbl.setFont(font1);
        add(lbcfbl);
        lbcfphin.setBounds(845, 740, 250, 50);
        lbcfphin.setForeground(Color.WHITE);
        lbcfphin.setFont(font1);
        add(lbcfphin);

        lbimgBe1.setBounds(95, 500, 300, 300);
        add(lbimgBe1);
        lbimgBe2.setBounds(445, 500, 300, 300);
        add(lbimgBe2);
        lbimgBe3.setBounds(795, 500, 300, 300);
        add(lbimgBe3);

        lbbest.setBounds(510, 410, 300, 50);
        lbbest.setFont(font1);
        add(lbbest);
    }

    public static void setButtton(JButton bt) {
        bt.setForeground(Color.WHITE);
        bt.setFont(font3);
        bt.setBackground(color_CF.brownmilk);
        bt.setBorderPainted(false);

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(color_CF.brownmilk2);
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(color_CF.brownmilk);
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(color_CF.darkbr);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        if (i == 0){
            g2d.drawImage(imgIL1Image, 30, 0, 1140, 400, null);
            g.fillOval(517, 372, 21, 21);
            g.fillOval(545, 375, 15, 15);
            g.fillOval(570, 375, 15, 15);
            g.fillOval(595, 375, 15, 15);
            g.fillOval(620, 375, 15, 15);
            i++;
        } else if (i == 1) {
            g2d.drawImage(imgIL2Image, 30, 0, 1140, 400, null);
            g.fillOval(520, 375, 15, 15);
            g.fillOval(543, 372, 21, 21);
            g.fillOval(570, 375, 15, 15);
            g.fillOval(595, 375, 15, 15);
            g.fillOval(620, 375, 15, 15);
            i++;
        } else if (i == 2) {
            g2d.drawImage(imgIL3Image, 30, 0, 1140, 400, null);
            g.fillOval(520, 375, 15, 15);
            g.fillOval(545, 375, 15, 15);
            g.fillOval(567, 372, 21, 21);
            g.fillOval(595, 375, 15, 15);
            g.fillOval(620, 375, 15, 15);
            i++;
        } else if (i == 3) {
            g2d.drawImage(imgIL4Image, 30, 0, 1140, 400, null);
            g.fillOval(520, 375, 15, 15);
            g.fillOval(545, 375, 15, 15);
            g.fillOval(570, 375, 15, 15);
            g.fillOval(592, 372, 21, 21);
            g.fillOval(620, 375, 15, 15);
            i++;
        } else if (i == 4) {
            g2d.drawImage(imgIL5Image, 30, 0, 1140, 400, null);
            g.fillOval(520, 375, 15, 15);
            g.fillOval(545, 375, 15, 15);
            g.fillOval(570, 375, 15, 15);
            g.fillOval(595, 375, 15, 15);
            g.fillOval(617, 372, 21, 21);
            i = 0;
        }
    }
}


