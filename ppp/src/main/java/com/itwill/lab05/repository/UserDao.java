package com.itwill.lab05.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.lab05.datasource.DataSourceUtil;
import com.zaxxer.hikari.HikariDataSource;

public enum UserDao {
	INSTANCE;
	
	private static final Logger log = LoggerFactory.getLogger(UserDao.class);
	private final HikariDataSource ds = DataSourceUtil.getInstance().getDataSource();
	
	private static final String SQL_SELECT_BY_USERID =
			"select * from users where userid = ?";
	
	public User selectByUserid(String userid) {
		log.debug("userid={}", userid);
		log.debug(SQL_SELECT_BY_USERID);
		
		User user = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT_BY_USERID);
			stmt.setString(1, userid);
			rs = stmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				userid = rs.getString("userid");
				String password = rs.getString("password");
				String email = rs.getString("email");
				Integer points = rs.getInt("points");
				
				user = User.builder().id(id).userid(userid).email(email).password(password).points(points).build();	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		
		return user;
	}

}
