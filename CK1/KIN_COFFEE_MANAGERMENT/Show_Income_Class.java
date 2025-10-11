package KIN_COFFEE_MANAGERMENT;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.jfree.chart.ChartFactory.createAreaChart;

public class Show_Income_Class extends JPanel {
    JLabel lb$1 = new JLabel("$");
    JLabel lbtoday = new JLabel("Today sales results");
    JLabel lbNumBill = new JLabel("0đ");
    JLabel lbInBill = new JLabel("0đ");
    JLabel lbIncome = new JLabel("Income");
    JLabel lbNOfBill = new JLabel("0");
    JLabel lbRevenue = new JLabel("Revenue");
    JLabel lbper = new JLabel("0%");
    JLabel lb$2 = new JLabel("$");
    JLabel lbcom = new JLabel("Compared to yesterday");
    JLabel lbSumMonth = new JLabel("Sum: ");
    JLabel lbIncomeMonth = new JLabel("Income: ");
    JLabel lbAverMonth = new JLabel("Average: ");
    JLabel lbMaxBillMonth = new JLabel("Max bill in month: ");
    JLabel lbCountBillMonth = new JLabel();
    JTable tbbill;
    DefaultTableModel tableModel;
    JScrollPane scrollpane;
    JComboBox comboBox;

    ImageIcon i = new ImageIcon("E:\\IT3\\ImageCode\\Remove\\up.png");
    ImageIcon imgup = new ImageIcon(i.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    JLabel lbup = new JLabel(imgup);

    static Color_CF color = new Color_CF();
    static Font_CF font = new Font_CF();
    static Button_Custum buttonCustum;
    DecimalFormat FMMoney = new DecimalFormat("#,###");

    Map<String, Integer> sumMap = new LinkedHashMap<>();
    Map<String, Integer> incomeMap = new LinkedHashMap<>();

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private XYSeriesCollection areaDataset = new XYSeriesCollection();

    Database db;

    private double percent;

    public Show_Income_Class() {
        setLayout(null);
        setSize(1160, 770);
        setBackground(color.brownmilk);

        db = new Database();
        db.connect();

        getIncome();

        JFreeChart chart = ChartFactory.createBarChart(
                "Profit and loss chart",
                "Date",
                "Amount",
                dataset
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        renderer.setMaximumBarWidth(0.05);
        renderer.setItemMargin(0);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(10, 520, getWidth() - 355, 310);  // Vị trí và kích thước của biểu đồ
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setPreferredSize(new Dimension(350, 310));
        add(chartPanel);

        addAreaChart();
        String[] listtt = getCombo();
        comboBox = new JComboBox(listtt);

        String[] columnName = {"ID_Bill", "ID_Staff", "ID_Cus", "ID_Table","Date", "Sum", "Income", "Sell_off", "Note"};
        tableModel = new DefaultTableModel(columnName, 0);
        tbbill = new JTable(tableModel);
        tbbill.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbbill.getColumnModel().getColumn(1).setPreferredWidth(80);
        tbbill.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbbill.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbbill.getColumnModel().getColumn(4).setPreferredWidth(150);
        tbbill.getColumnModel().getColumn(5).setPreferredWidth(75);
        tbbill.getColumnModel().getColumn(6).setPreferredWidth(75);
        tbbill.getColumnModel().getColumn(7).setPreferredWidth(50);
        tbbill.getColumnModel().getColumn(8).setPreferredWidth(50);
        scrollpane = new JScrollPane(tbbill);
        getBill();

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter);

        for (Map.Entry<String, Integer> map : sumMap.entrySet()) {
            if (Objects.equals(map.getKey(), dateString)) {
                lbNumBill.setText(FMMoney.format(map.getValue()) + "đ");
            }
        }
        Integer previousValue = null;

        for (Map.Entry<String, Integer> map : incomeMap.entrySet()) {
            if (Objects.equals(map.getKey(), dateString)) {
                if (previousValue != null) {
                    percent = getpercent(map.getValue(), previousValue);
                }
                lbInBill.setText(FMMoney.format(map.getValue()) + "đ");
            }
            previousValue = map.getValue();
        }

        lbtoday.setBounds(30, 25, 500, 35);
        lbtoday.setFont(font.fontAR28);
        lbtoday.setForeground(color.darkbr);
        add(lbtoday);
        lbNOfBill.setBounds(90, 85, 200, 30);
        lbNOfBill.setFont(font.fontAR14);
        lbNOfBill.setForeground(color.darkbr);
        lbNOfBill.setText(getNumBill(dateString) +" bills today");
        add(lbNOfBill);
        lbNumBill.setBounds(90, 115, 200, 25);
        lbNumBill.setForeground(color.darkbr);
        lbNumBill.setFont(font.fontAR32);
        add(lbNumBill);
        lbRevenue.setBounds(90, 140, 200, 20);
        lbRevenue.setForeground(color.darkbr);
        lbRevenue.setFont(font.fontAR14);
        add(lbRevenue);
        lbup.setBounds(625, 105, 40, 40);
        add(lbup);
        lbInBill.setBounds(380, 115, 200, 25);
        lbInBill.setForeground(color.darkbr);
        lbInBill.setFont(font.fontAR32);
        add(lbInBill);
        lbIncome.setBounds(380, 140, 200, 25);
        lbIncome.setForeground(color.darkbr);
        lbIncome.setFont(font.fontAR14);
        add(lbIncome);
        lbper.setBounds(680, 115, 200, 25);
        lbper.setFont(font.fontAR32);
        lbper.setText(String.format("%.2f", percent) +"%");
        add(lbper);
        lbcom.setBounds(680, 140, 200, 25);
        lbcom.setForeground(color.darkbr);
        lbcom.setFont(font.fontAR14);
        add(lbcom);
        scrollpane.setBounds(35, 180, getWidth()- 400, 330);
        add(scrollpane);
        comboBox.setBounds(830, 190, 200, 30);
        buttonCustum = new Button_Custum(comboBox);
        add(comboBox);

        lbSumMonth.setBounds(850, 250, 350, 30);
        lbSumMonth.setFont(font.fontAR28);
        lbSumMonth.setForeground(color.darkbr);
        add(lbSumMonth);
        lbIncomeMonth.setBounds(850, 300, 300, 30);
        lbIncomeMonth.setFont(font.fontAR28);
        lbIncomeMonth.setForeground(color.darkbr);
        add(lbIncomeMonth);
        lbCountBillMonth.setBounds(900, 380, 200, 30);
        lbCountBillMonth.setForeground(color.darkbr);
        lbCountBillMonth.setFont(font.fontAR20);
        add(lbCountBillMonth);
        lbAverMonth.setBounds(900, 420, 300, 30);
        lbAverMonth.setFont(font.fontAR20);
        lbAverMonth.setForeground(color.darkbr);
        add(lbAverMonth);
        lbMaxBillMonth.setBounds(900, 460, 300, 30);
        lbMaxBillMonth.setFont(font.fontAR20);
        lbMaxBillMonth.setForeground(color.darkbr);
        add(lbMaxBillMonth);

        String monthchoice = comboBox.getSelectedItem().toString();
        getSumMonth(monthchoice);

        lb$1.setBounds(30, 100, 50, 50);
        lb$1.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lb$1.setForeground(color.darkbr);
        lb$1.setHorizontalAlignment(SwingConstants.CENTER);
        add(lb$1);
        lb$2.setBounds(320, 100, 50, 50);
        lb$2.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lb$2.setForeground(color.darkbr);
        lb$2.setHorizontalAlignment(SwingConstants.CENTER);
        add(lb$2);

        Timer timer = new Timer(1000, e -> {
            repaint();
            revalidate();
        } );
        timer.start();
    }

    public double getpercent(int today, int last) {
        if (last == 0) {
            return 0;
        }
        double cal = ((double) (today - last) / last) * 100;
        return cal;
    }

    public int getNumBill(String date) {
        ResultSet rs = db.getDB("SELECT COUNT(ID_Bill) FROM bill WHERE Date LIKE '" + date + "%'");

        int count = 0;
        try {
            if (rs.next()) {
                count = rs.getInt("COUNT(ID_Bill)");
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
        return count;
    }

    public void getIncome() {
        ResultSet rs = db.getDB("SELECT Date, Sum, Income FROM bill");

        try {
            dataset.clear();

            while (rs.next()) {
                String datetime = rs.getString("Date");
                int sum = rs.getInt("Sum");
                int income = rs.getInt("Income");

                String date = datetime.substring(0, datetime.length() - 9);

                sumMap.put(date, sumMap.getOrDefault(date, 0) + sum);
                incomeMap.put(date, incomeMap.getOrDefault(date, 0) + sum - income);
            }

            for (String date : sumMap.keySet()) {
                int sum = sumMap.get(date);
                int income = incomeMap.get(date);

                dataset.addValue(sum, "Sum", date);
                dataset.addValue(income, "Income", date);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void getBill() {
        ResultSet rs = db.getDB("SELECT * FROM bill WHERE Status = 'Done'");

        try {
            tableModel.setRowCount(0);
            while (rs.next()) {
                int id = rs.getInt("ID_Bill");
                String id_Staff = rs.getString("ID_Staff");
                String ID_Cus = rs.getString("ID_Cus");
                String ID_Table = rs.getString("ID_Table");
                String date = rs.getString("Date");
                int sum = rs.getInt("Sum");
                int income = rs.getInt("Income");
                double sell = rs.getDouble("Sell_Off");
                String note = rs.getString("Note");

                tableModel.addRow(new Object[]{id, id_Staff, ID_Cus, ID_Table, date, FMMoney.format(sum), FMMoney.format(income),sell, note});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAreaChart() {
        JFreeChart areaChart = createAreaChart(
                "Profit and Loss Area Chart",
                "Date",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = (CategoryPlot) areaChart.getPlot();
        AreaRenderer renderer = new AreaRenderer();
        plot.setRenderer(renderer);

        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        ChartPanel chartPanel = new ChartPanel(areaChart);
        chartPanel.setBounds(825, 520, 370, 310);
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setPreferredSize(new Dimension(350, 310));
        add(chartPanel);
    }

    public String[] getCombo() {
        ArrayList<String> li = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : sumMap.entrySet()) {
            String month = entry.getKey().substring(0, 7);

            if (!li.contains(month)) {
                li.add(month);
            }
        }
        return li.toArray(new String[0]);
    }

    public void getSumMonth(String date) {
        ResultSet rs = db.getDB("SELECT SUM(Sum), SUM(Income), COUNT(ID_Bill), MAX(Sum) FROM bill WHERE Date LIKE '" + date + "%'");

        int count = 0;
        int sum = 0;
        int income = 0;
        int maxBillMonth = 0;
        try {
            while (rs.next()) {
                sum = rs.getInt("SUM(Sum)");
                income = rs.getInt("SUM(Income)");
                count = rs.getInt("COUNT(ID_Bill)");
                maxBillMonth = rs.getInt("MAX(Sum)");
            }

            lbSumMonth.setText("Sum:           " + FMMoney.format(sum) +"đ");
            lbIncomeMonth.setText("Income:       " + FMMoney.format(income) +"đ");
            lbCountBillMonth.setText(count +" bill in month");
            lbAverMonth.setText("Average:          " +FMMoney.format(sum / count) +"đ/1 bill");
            lbMaxBillMonth.setText("Highest bill:     " +FMMoney.format(maxBillMonth) +"đ");
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRoundRect(10, 20, getWidth()- 20, 150, 10, 10);
        g.fillRect(10, 180, getWidth()- 400, 330);
        g.fillRect(825, 180, 370, 330);
        g.fillRect(825, 520, 370, 310);

        g.setColor(color.brownmilk);
        g.fillOval(30, 100, 50, 50);
        g.setColor(color.blueRound);
        g.fillOval(320, 100, 50, 50);
        if (percent >= 0) {
            g.setColor(color.blueRound);
            g.fillOval(620, 100, 50, 50);
            lbper.setForeground(color.blueRound);
        } else {
            g.setColor(color.redBt);
            g.fillOval(620, 100, 50, 50);
            lbper.setForeground(color.redBt);
        }

        g.setColor(color.darkbr);
        g.fillRect(300, 100, 1, 50);
        g.fillRect(600, 100, 1, 50);
        g.fillRoundRect(840, 360, 340, 3, 3, 3);
    }
}
