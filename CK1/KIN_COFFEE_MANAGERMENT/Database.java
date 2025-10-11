package KIN_COFFEE_MANAGERMENT;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.FileInputStream;

public class Database {
    java.sql.Connection con;
    Statement stmt = null;

    public void connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/kinmanagement";
            String user = "root";
            String password = "1234";

            con = DriverManager.getConnection(url, user, password);

//            JOptionPane.showMessageDialog(null, "Connected successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int executeDB(String sql) {
        int re = 0;

        try {
            stmt = con.createStatement();
            re = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return re;
    }

    public ResultSet getDB(String sql) {
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public void saveImage(byte[] imgByte, String sql) {
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setBytes(1, imgByte);

            int result = statement.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Image Saved");
            } else {
                JOptionPane.showMessageDialog(null, "Image Not Saved");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
