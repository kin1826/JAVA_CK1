package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Table_Show extends JPanel {
    private LinkedHashMap<String, String> tableStatusMap = new LinkedHashMap<>();
    private HashMap<String, JButton> buttonMap = new HashMap<>();

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();

    private int num = 0;
    private String numtable;

    Database db;

    private ButtonClickListener buttonClickListener;

    public interface ButtonClickListener {
        void onButtonClick(String buttonText);
    }

    public Table_Show(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
        ordertb();
    }

    public Table_Show() {
        ordertb();
    }

    public void ordertb(){
        setLayout(null);
        setSize(700, 1000);
        setBackground(Color.WHITE);
        db = new Database();
        db.connect();

        Timer timer = new Timer(1000, e -> {
            removeAll();
            getTable();
            createTable();
            repaint();
            revalidate();
        });
        timer.start();
    }

    public void createTable() {
        int y = 10;
        int x = 40;

        for (Map.Entry<String, String> entry : tableStatusMap.entrySet()) {
            String tableName = entry.getKey();
            String statusValue = entry.getValue();

            if (x > 700) {
                y = y + 125;
                x = 40;
            }

            JButton button = new JButton(tableName);
            button.setBounds(x, y, 100, 100);
            button.setForeground(Color.WHITE);
            button.setFont(font.fontAR32);

            if ("Y".equals(statusValue)) {
                button.setBackground(color.greenBt);
            } else {
                button.setBackground(color.redBt);
            }

            button.addActionListener(e -> {
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(button.getText());
                }
            });

            buttonMap.put(tableName, button);
            add(button);

            x = x + 125;
        }
        revalidate();
        repaint();
    }

    public void getTable() {
        ResultSet rs = db.getDB("SELECT Name, Status FROM tablecf");

        try {
            tableStatusMap.clear();
            while (rs.next()) {
                String name = rs.getString("Name");
                String status = rs.getString("Status");

                tableStatusMap.put(name, status);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

