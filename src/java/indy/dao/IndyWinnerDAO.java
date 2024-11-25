/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package indy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import indy.IndyWinner;
import java.util.ArrayList;

/**
 *
 * @author strif
 */
public class IndyWinnerDAO {
    private static Connection connection = null;
    
    public List<IndyWinner> findWinners(int page) {
        List<IndyWinner> result = new ArrayList<>();
        
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM indywinners ORDER BY year ASC LIMIT 10 OFFSET " + (((page - 1) * 10) + 1);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                IndyWinner indyWinner = new IndyWinner();
                indyWinner.setYear(rs.getString("year"));
                indyWinner.setDriver(rs.getString("driver"));
                indyWinner.setAverageSpeed(rs.getFloat("averageSpeed"));
                indyWinner.setCountry(rs.getString("country"));
                result.add(indyWinner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int recordCount() {
        int count = 0;
        
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM indywinners";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/IndyWinners", "root", "");
        }
        
        return connection;
    }
}
