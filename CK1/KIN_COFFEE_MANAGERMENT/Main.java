package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

public class Main extends JFrame {
    private String id;

    Login login = new Login();
    Home home = new Home();
    List_Choice list = new List_Choice();
    Order_Class order;
    Table_Class table = new Table_Class();
    ItemCata_Class item = new ItemCata_Class();
    Loyal_Cus_Class cus = new Loyal_Cus_Class();
    Staff_Class staff = new Staff_Class();
    Account_Show acc;
    Show_Income_Class show = new Show_Income_Class();

    Color_CF color = new Color_CF();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Main() throws FileNotFoundException {
        setTitle("Kin Coffee Manager");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLayout(null);
        setSize(screenWidth, screenHeight);

        login.setBounds(0,0,screenWidth,screenHeight);
        add(login);
        login.setVisible(true);
        home.setBounds(330, 0, screenWidth - 330, screenHeight);
        add(home);
        table.setBounds(330, 0, screenWidth - 330, screenHeight);
        add(table);
        table.setVisible(false);
        item.setBounds(330, 0, screenWidth - 330, screenHeight);
        add(item);
        item.setVisible(false);
        cus.setBounds(330, 0, screenWidth - 330, screenHeight);
        add(cus);
        cus.setVisible(false);
        staff.setBounds(330, 0, screenWidth - 330, screenHeight);
        add(staff);
        staff.setVisible(false);
        show.setBounds(330, 0, screenWidth - 330, screenHeight);
        add(show);
        show.setVisible(false);
        list.setBounds(0, 0, 305, 870);
        add(list);
        list.setVisible(false);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX()+" "+e.getY());
            }
        });

        login.btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (login.checkOwner()){
                    id = login.getID();
                    order = new Order_Class(id);
                    order.setBounds(330, 0, screenWidth - 330, screenHeight);
                    add(order);
                    acc = new Account_Show(id);
                    acc.setBounds(330, 0, screenWidth - 330, screenHeight);
                    add(acc);
                    home.setVisible(true);
                    list.setVisible(true);
                    login.setVisible(false);
                    cus.setVisible(false);
                    staff.setVisible(false);
                    acc.setVisible(false);
                    order.setVisible(false);
                    show.setVisible(false);
                }
                else JOptionPane.showMessageDialog(null, "Name login or password wrong!");
            }
        });

        list.btAcc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acc.setVisible(true);
                home.setVisible(false);
                order.setVisible(false);
                table.setVisible(false);
                item.setVisible(false);
                cus.setVisible(false);
                staff.setVisible(false);
                show.setVisible(false);
            }
        });

        list.btHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                home.setVisible(true);
                order.setVisible(false);
                table.setVisible(false);
                item.setVisible(false);
                cus.setVisible(false);
                staff.setVisible(false);
                acc.setVisible(false);
                show.setVisible(false);
            }
        });

        list.btorder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                order.setVisible(false);
                order = new Order_Class(id);
                order.setBounds(330, 0, screenWidth - 330, screenHeight);
                add(order);
                home.setVisible(false);
                order.setVisible(true);
                table.setVisible(false);
                item.setVisible(false);
                cus.setVisible(false);
                staff.setVisible(false);
                acc.setVisible(false);
                show.setVisible(false);
                repaint();
                revalidate();
            }
        });

        list.bttable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                home.setVisible(false);
                order.setVisible(false);
                table.setVisible(true);
                item.setVisible(false);
                cus.setVisible(false);
                staff.setVisible(false);
                acc.setVisible(false);
                show.setVisible(false);
            }
        });

        list.btitem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                home.setVisible(false);
                order.setVisible(false);
                table.setVisible(false);
                item.setVisible(true);
                cus.setVisible(false);
                staff.setVisible(false);
                acc.setVisible(false);
                show.setVisible(false);
            }
        });

        list.btloyal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cus = new Loyal_Cus_Class();
                cus.setBounds(330, 0, screenWidth - 330, screenHeight);
                add(cus);
                home.setVisible(false);
                order.setVisible(false);
                table.setVisible(false);
                item.setVisible(false);
                cus.setVisible(true);
                staff.setVisible(false);
                acc.setVisible(false);
                show.setVisible(false);
            }
        });

        list.btstaff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                staff = new Staff_Class();
                staff.setBounds(330, 0, screenWidth - 330, screenHeight);
                add(staff);
                home.setVisible(false);
                order.setVisible(false);
                table.setVisible(false);
                item.setVisible(false);
                cus.setVisible(false);
                staff.setVisible(true);
                acc.setVisible(false);
                show.setVisible(false);
            }
        });

        list.btshow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                show.setVisible(false);
                show = new Show_Income_Class();
                show.setBounds(330, 0, screenWidth - 330, screenHeight);
                add(show);
                home.setVisible(false);
                order.setVisible(false);
                table.setVisible(false);
                item.setVisible(false);
                cus.setVisible(false);
                staff.setVisible(false);
                acc.setVisible(false);
                show.setVisible(true);
            }
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Timer timer = new Timer(3000, e -> {
            repaint();
        });
        timer.start();
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Main();
    }
}
