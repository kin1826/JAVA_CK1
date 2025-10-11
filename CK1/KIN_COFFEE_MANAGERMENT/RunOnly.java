package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

public class RunOnly extends JFrame {
    Order_Class test = new Order_Class("ST01");
//    ItemCata_Class test = new ItemCata_Class();
//    Table_Class test = new Table_Class();
//    AddOnly login = new AddOnly();
//    Staff_Class test = new Staff_Class();
//    Login login = new Login();
//    Loyal_Cus_Class test = new Loyal_Cus_Class();
//    Create_Class cre = new Create_Class();
//    Home test = new Home();
//    List_Choice list = new List_Choice();
//    Account_Show test = new Account_Show("ST000");
//    Order_Show show = new Order_Show("DR01");
//    Show_Income_Class test = new Show_Income_Class();
//    Change_Detail changeDetail = new Change_Detail("DR01", "Americano", 3)

    public RunOnly() throws FileNotFoundException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLayout(null);
        setSize(screenWidth, screenHeight);
        setBackground(Color.GRAY);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX()+" "+e.getY());
            }
        });

//        cre.setBounds(0, 0, 400, 450);
//        cre.setVisible(true);
//        add(cre);
        test.setBounds(330, 0, screenWidth - 330, screenHeight);
        add(test);
        test.setVisible(true);
//        login.setBounds(0, 0, screenWidth, screenHeight);
//        login.setVisible(true);
//        add(login);
//        list.setBounds(0, 0, 400, 870);
//        add(list);
//        list.setVisible(true);
//        show.setBounds(10, 550, 800, 220);
//        add(show);
//        show.setVisible(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException {
        new RunOnly();
    }
}
