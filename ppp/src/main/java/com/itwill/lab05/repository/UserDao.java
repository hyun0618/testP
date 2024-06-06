package com.itwill.lab05.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.lab05.datasource.DataSourceUtil;
import com.zaxxer.hikari.HikariDataSource;

public enum UserDao {
    INSTANCE;
    
    private final HikariDataSource ds = DataSourceUtil.getInstance().getDataSource();

    private static final String SQL_INSERT = 
            "insert into users (userid, password, email) values (?, ?, ?)";
    
    public int insert(User user) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, user.getUserid());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt);
        }
        
        return result;
    }
    
    private static final String SQL_UPDATE_POINTS = 
            "update users set points = points + ? where userid = ?";
    
    public int updatePoints(String userid, int points) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_POINTS);
            stmt.setInt(1, points);
            stmt.setString(2, userid);
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt);
        }
        
        return result;
    }
    
    private static final String SQL_SELECT_BY_USERID = 
            "select * from users where userid = ?";
    
    public User selectByUserid(String userid) {
     
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_USERID);
            stmt.setString(1, userid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = fromResultSetToUser(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return user;
    }
    
    private User fromResultSetToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String userid = rs.getString("userid");
        String password = rs.getString("password");
        String email = rs.getString("email");
        int points = rs.getInt("points");
        
        return User.builder()
                .id(id).userid(userid).password(password).email(email).points(points)
                .build();
    }
    
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void closeResources(Connection conn, Statement stmt) {
        closeResources(conn, stmt, null);
    }
}
