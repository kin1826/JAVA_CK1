package KIN_COFFEE_MANAGERMENT;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UpdateDelete_Staff extends JPanel {
    JLabel lbAdd = new JLabel("EDIT STAFF");
    JLabel lbID = new JLabel("ID_Staff");
    JLabel lbName = new JLabel("Name");
    JLabel lbPosi = new JLabel("Position");
    JLabel lbPhone = new JLabel("Phone");
    JLabel lbSex = new JLabel("Gender");
    JLabel lbSalary = new JLabel("Salary");
    JLabel lbnamelg = new JLabel("Name login");
    ImageIcon imgL = new ImageIcon("E:\\IT3\\ImageCode\\KIN Coffee\\ImageMode.png");
    ImageIcon imgMode = new ImageIcon(imgL.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    JButton btImg = new JButton(imgMode);
    JButton btUpdate = new JButton("UPDATE");
    JButton btDelete = new JButton("DELETE");
    JTextField tfID = new JTextField(15);
    JTextField tfName = new JTextField(15);
    JTextField tfPosition = new JTextField(15);
    JTextField tfPhone = new JTextField(15);
    JRadioButton rbmale = new JRadioButton("Male");
    JRadioButton rbfemale = new JRadioButton("Female");
    JRadioButton rbother = new JRadioButton("Other");
    JTextField tfSalary = new JTextField(15);
    JTextField tfNameLogin = new JTextField(15);

    ImageIcon im = new ImageIcon("E:\\IT3\\ImageCode\\exit.png");
    ImageIcon imgex = new ImageIcon(im.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    JButton btExit = new JButton(imgex);

    static Vector<Object> vec = new Vector<>();

    static Font_CF font = new Font_CF();
    static Color_CF color = new Color_CF();
    static Button_Custum buttonCustum;

    private byte[] imgBytes;
    private String gender = "";

    private String ID_Staff;
    private boolean checked = false;

    Database db;

    public UpdateDelete_Staff(String ID) {
        this.ID_Staff = ID;
        display();
    }

    public void display() {
        setSize(360, 470);
        setLayout(null);

        db = new Database();
        db.connect();

        ButtonGroup btrg = new ButtonGroup();
        btrg.add(rbmale);
        btrg.add(rbfemale);
        btrg.add(rbother);

        rbmale.setSelected(true);

        getInfor();
        Object[] ob = (Object[]) vec.get(0);

        ImageIcon img = (ImageIcon) ob[6];
        Image image = img.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);

        lbAdd.setBounds(95, 20, 300, 50);
        lbAdd.setFont(font.fontAR32);
        lbAdd.setForeground(color.darkbr);
        add(lbAdd);
        btImg.setBounds(20, 80, 120, 120);
        btImg.setBorder(new LineBorder(color.darkbr, 5));
        btImg.setIcon(new ImageIcon(image));
        add(btImg);
        lbID.setBounds(160, 80, 100, 30);
        lbID.setFont(font.fontAR16);
        lbID.setForeground(color.darkbr);
        add(lbID);
        tfID.setBounds(160, 110, 180, 30);
        tfID.setFont(font.fontAR14);
        buttonCustum = new Button_Custum(tfID);
        tfID.setText(ID_Staff);
        add(tfID);
        lbName.setBounds(160, 140, 100, 30);
        lbName.setFont(font.fontAR16);
        lbName.setForeground(color.darkbr);
        add(lbName);
        tfName.setBounds(160, 170, 180, 30);
        tfName.setFont(font.fontAR14);
        buttonCustum = new Button_Custum(tfName);
        tfName.setText(ob[1].toString());
        add(tfName);
        lbPosi.setBounds(40, 210, 100, 30);
        lbPosi.setFont(font.fontAR16);
        lbPosi.setForeground(color.darkbr);
        add(lbPosi);
        tfPosition.setBounds(160, 210, 180, 30);
        tfPosition.setFont(font.fontAR14);
        buttonCustum = new Button_Custum(tfPosition);
        tfPosition.setText(ob[2].toString());
        add(tfPosition);
        lbPhone.setBounds(40, 250, 100, 30);
        lbPhone.setFont(font.fontAR16);
        lbPhone.setForeground(color.darkbr);
        add(lbPhone);
        tfPhone.setBounds(160, 250, 180, 30);
        tfPhone.setFont(font.fontAR14);
        buttonCustum = new Button_Custum(tfPhone);
        tfPhone.setText(ob[3].toString());
        add(tfPhone);
        lbSex.setBounds(40, 290, 100, 30);
        lbSex.setFont(font.fontAR16);
        lbSex.setForeground(color.darkbr);
        add(lbSex);
        rbmale.setBounds(120, 290, 65, 30);
        rbmale.setFont(font.fontAR14);
        rbmale.setForeground(color.darkbr);
        add(rbmale);
        rbfemale.setBounds(190, 290, 80, 30);
        rbfemale.setFont(font.fontAR14);
        rbfemale.setForeground(color.darkbr);
        add(rbfemale);
        rbother.setBounds(270, 290, 80, 30);
        rbother.setFont(font.fontAR14);
        rbother.setForeground(color.darkbr);
        add(rbother);
        lbSalary.setBounds(40, 330, 100, 30);
        lbSalary.setFont(font.fontAR16);
        lbSalary.setForeground(color.darkbr);
        add(lbSalary);
        tfSalary.setBounds(160, 330, 180, 30);
        tfSalary.setFont(font.fontAR14);
        buttonCustum = new Button_Custum(tfSalary);
        tfSalary.setText(ob[5].toString());
        add(tfSalary);
        lbnamelg.setBounds(40, 370, 100, 30);
        lbnamelg.setFont(font.fontAR16);
        lbnamelg.setForeground(color.darkbr);
        add(lbnamelg);
        tfNameLogin.setBounds(160, 370, 180, 30);
        tfNameLogin.setFont(font.fontAR16);
        buttonCustum = new Button_Custum(tfNameLogin);
        tfNameLogin.setText(ob[7].toString());
        add(tfNameLogin);
        btUpdate.setBounds(50, 420, 120, 30);
        butcus(btUpdate);
        add(btUpdate);
        btDelete.setBounds(190, 420, 120, 30);
        butcus(btDelete);
        add(btDelete);
        btExit.setBounds(getWidth() - 30, 3, 20, 20);
        add(btExit);

        btImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openFX();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                checked = true;
            }
        });

        btUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!checked){
                    try {
                        imgBytes = convert(image);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (rbmale.isSelected()) {
                    gender = "Male";
                } else if (rbfemale.isSelected()) {
                    gender = "Female";
                } else if (rbother.isSelected()) {
                    gender = "Other";
                }
                String idlength = tfID.getText();
                if (idlength.length() >= 4) {
                    updateStaff();
                } else if (idlength.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a valid ID");
                } else JOptionPane.showMessageDialog(null, "ID must be at least 4 characters");
            }
        });

        btDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStaff();
            }
        });

        btExit.addActionListener(new ActionListener() {
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

    public byte[] convert(Image image) throws IOException {
        BufferedImage bufferedImage;

        if (image instanceof BufferedImage) {
            bufferedImage = (BufferedImage) image;
        } else {
            bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        return baos.toByteArray();
    }


    public byte[] convert(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Image img = ImageIO.read(file);
        ImageIO.write((java.awt.image.BufferedImage) img, "jpg", bos);

        return bos.toByteArray();
    }

    public void updateStaff() {
        try {
            int rs = db.executeDB("UPDATE staff"
                    + " SET ID_Staff = '"
                    + tfID.getText()
                    +"', Name_Staff = '"
                    + tfName.getText()
                    +"', Position = '"
                    + tfPosition.getText()
                    +"', Phone = '"
                    + tfPhone.getText()
                    +"', Sex = '"
                    + gender
                    +"', Salary = "
                    + tfSalary.getText()
                    +", Name_Login = '"
                    + tfNameLogin.getText()
                    + "' WHERE ID_Staff = '"
                    + ID_Staff
                    +"'"
            );
            if (rs > 0) JOptionPane.showMessageDialog(null, "Item Added Successfully");
            else JOptionPane.showMessageDialog(null, "Item Not Added Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.saveImage(imgBytes, "UPDATE staff SET Image = (?) WHERE ID_Staff = '" + tfID.getText() + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStaff() {
        try {
            int rs = db.executeDB("DELETE FROM staff WHERE ID_Staff = '" + tfID.getText() + "'");
            if (rs > 0) JOptionPane.showMessageDialog(null, "Item Deleted Successfully");
            else JOptionPane.showMessageDialog(null, "Item Not Deleted Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getInfor() {
        ResultSet rs = db.getDB("SELECT * FROM staff WHERE ID_Staff = '" + ID_Staff + "'");

        try {
            vec.clear();
            if (rs.next()) {
                String id = rs.getString("ID_Staff");
                String name = rs.getString("Name_Staff");
                String position = rs.getString("Position");
                String phone = rs.getString("Phone");
                String gender = rs.getString("Sex");
                String salary = rs.getString("Salary");
                Blob bl = rs.getBlob("Image");
                InputStream is = bl.getBinaryStream();
                ImageIcon im = new ImageIcon(is.readAllBytes());
                ImageIcon imgBL = new ImageIcon(im.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
                String namelg = rs.getString("Name_Login");

                vec.add(new Object[]{id, name, position, phone, gender, salary,imgBL, namelg});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void butcus(JButton bt) {
        bt.setForeground(Color.WHITE);
        bt.setFont(font.fontAR20);
        bt.setBackground(color.greenBt);
        bt.setBorderPainted(false);

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(color.brownmilk2);
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(color.greenBt);
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(color.darkbr);
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color.darkbr);
        g.fillRect(0, 0, getWidth(), 26);
    }
}
