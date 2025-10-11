package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button_Custum {
    static Color_CF colorCf = new Color_CF();
    static Font_CF fontCf = new Font_CF();

    public Button_Custum(JButton bt) {
        bt.setForeground(Color.WHITE);
        bt.setFont(fontCf.fontAR20);
        bt.setBackground(colorCf.brownmilk);
        bt.setBorderPainted(false);

        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(colorCf.brownmilk2);
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(colorCf.brownmilk);
            }
            public void mousePressed(MouseEvent e) {
                bt.setBackground(colorCf.darkbr);
            }
        });
    }

    public Button_Custum(JComboBox cb) {
        cb.setBackground(colorCf.brownmilk);
        cb.setFont(fontCf.fontAR16);
    }

    public Button_Custum(JTextField tf) {
        tf.setBackground(colorCf.brownmilk);
        tf.setForeground(colorCf.darkbr);
    }

    public Button_Custum(JSpinner sp) {
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) sp.getEditor();
        editor.getTextField().setBackground(colorCf.brownmilk);
        editor.getTextField().setForeground(colorCf.darkbr);
        editor.getTextField().setHorizontalAlignment(SwingConstants.LEFT);

        Component[] comp = sp.getComponents();
        for (Component c : comp) {
            if (c instanceof JSpinner.DefaultEditor) {
                continue;
            }
            if (c instanceof JComponent) {
                JButton bt = (JButton) c;
                bt.setForeground(Color.WHITE);
                bt.setFont(fontCf.fontAR20);
                bt.setBackground(colorCf.brownmilk2);
                bt.setBorderPainted(false);

                bt.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        bt.setBackground(colorCf.brownmilk3);
                    }
                    public void mouseExited(MouseEvent e) {
                        bt.setBackground(colorCf.brownmilk2);
                    }
                    public void mousePressed(MouseEvent e) {
                        bt.setBackground(colorCf.darkbr);
                    }
                });
            }
        }

    }
}
