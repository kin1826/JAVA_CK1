package KIN_COFFEE_MANAGERMENT;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class Bill {
    JFrame frame = new JFrame();
    JLabel lbkin = new JLabel("KIN COFFEE");
    JLabel lbaddress = new JLabel("Address: 03 Ng Sinh Sac, DB, QN");
    JLabel lbsdt = new JLabel("Phone: 0334082946");
    JLabel lbdate = new JLabel("Date:");
    JLabel lbcode = new JLabel("Code:");
    JLabel lbsum = new JLabel("Price:");
    JLabel lbsell = new JLabel("Sell:");
    JLabel lbTotal = new JLabel("Total:");
    DefaultTableModel model;
    JTable table;
    JScrollPane scrollPane;

    ImageIcon iconF = new ImageIcon("E:\\IT3\\ImageCode\\qr.jpg");
    Image image = iconF.getImage();
    Image reSize = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    ImageIcon iconL = new ImageIcon(reSize);
    JLabel lbimg = new JLabel(iconL);

    Font font = new Font("Times New Roman", Font.PLAIN, 20);
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    private Vector<Object> vec = new Vector<>();
    private int Sum;
    private int ID_Bill;
    private double sell;

    Database db = new Database();

    public Bill(Vector<Object> vec, int sum, double sell) {
        this.vec = vec;
        this.Sum = sum;
        this.sell = sell;
        display();
    }

    public void display() {
        frame.setTitle("Bill");
        frame.setLayout(null);

        frame.setSize(300, 550);
        frame.setLocationRelativeTo(null);

        db.connect();

        String[] columnName = {"STT", "Name", "Quantity", "Price"};
        model = new DefaultTableModel(columnName, 0);
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(110);
        table.getColumnModel().getColumn(2).setPreferredWidth(55);
        table.getColumnModel().getColumn(3).setPreferredWidth(55);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 105, 250, 200); // Thêm chiều cao cho bảng
        frame.add(scrollPane);

        lbkin.setBounds(85, 15, 200, 30);
        lbkin.setFont(font);
        frame.add(lbkin);

        lbaddress.setBounds(10, 40, 200, 30);
        frame.add(lbaddress);
        lbsdt.setBounds(10, 55, 200, 30);
        frame.add(lbsdt);

        lbdate.setBounds(25, 80, 200, 30);
        lbdate.setText("Date: " + getDateBill());
        frame.add(lbdate);

        lbimg.setBounds(90, 310, 100, 100);  // Thêm khoảng cách với số lượng hàng
        frame.add(lbimg);

        lbTotal.setBounds(20, 420, 100, 30);  // Thêm khoảng cách với số lượng hàng
        lbTotal.setText("Total: " + FMMoney.format(Sum));
        frame.add(lbTotal);

        lbsell.setBounds(20, 440, 100, 30);  // Thêm khoảng cách với số lượng hàng
        lbsell.setText("Sell: " + sell*100 +"%");
        frame.add(lbsell);

        lbsum.setBounds(20, 460, 100, 30);  // Thêm khoảng cách với số lượng hàng
        lbsum.setText("Price: " + FMMoney.format(Sum - (Sum * sell)));
        frame.add(lbsum);
        System.out.println(ID_Bill);

        for (int i = 0; i < vec.size(); i++) {
            Object[] ob = (Object[]) vec.get(i);
            int stt = i + 1;
            String name = ob[0].toString();
            String quantity = ob[1].toString();
            String price = ob[2].toString();

            model.addRow(new Object[]{stt, name, quantity, price});
        }

        frame.setVisible(true);

        String pdfFilePath = "E:\\IT3\\ImageCode\\KIN Coffee\\BILL\\screenshot.pdf";

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    cap(frame, pdfFilePath);
                } catch (IOException | AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    cap(frame, pdfFilePath);
                } catch (IOException | AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public String getDateBill() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

    public void cap(JFrame frame, String pdtFile) throws AWTException, IOException {
        Rectangle frameBounds = frame.getBounds();

        int x = frameBounds.x + 7;
        int y = frameBounds.y + 30;
        int width = frameBounds.width - 16;
        int height = frameBounds.height - 43;

        Rectangle rectangle = new Rectangle(x, y, width, height);

        Robot robot = new Robot();
        BufferedImage screenshot = robot.createScreenCapture(rectangle);

        File screenshotFile = new File("E:\\IT3\\ImageCode\\KIN Coffee\\BILL\\screenshot.png");
        ImageIO.write(screenshot, "PNG", screenshotFile);

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDImageXObject pdImage = PDImageXObject.createFromFile(screenshotFile.getAbsolutePath(), document);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(pdImage, 150, 0);

        contentStream.close();

        document.save(pdtFile);

        document.close();
    }
}
