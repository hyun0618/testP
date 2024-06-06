package com.itwill.lab05.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.lab05.datasource.DataSourceUtil;
import com.zaxxer.hikari.HikariDataSource;

// DAO(Data Access Object). 데이터베이스 CRUD 
public enum UserDao {
	INSTANCE;
	
	private static final Logger log=LoggerFactory.getLogger(UserDao.class);
	
	private final HikariDataSource ds=DataSourceUtil.getInstance().getDataSource();
	
	private static final String SQL_SIGN_IN=
			"select * from users where userid = ? and password = ?";
	/**
	 * 로그인할 때 필요한 메서드.
	 * @param user 로그인을 시도한 userid, password룰 저장한 객체
	 * @return 데이터베이스의 users 테이블에서 
	 * userid와 password가 일치하는 레코드가 있으면 null이 아닌 유저 타입 객체를 리턴, 
	 * userid 또는 password가 일치하지 않으면 null을 리턴.
	 */
	public User selectByUseridAndPassword(User user) {
		log.debug("selectByUseridAndPassword({})",user);
		log.debug(SQL_SIGN_IN);
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		User result = null;
		try {
			conn=ds.getConnection();
			stmt=conn.prepareStatement(SQL_SIGN_IN);
			stmt.setString(1, user.getUserid() );
			stmt.setString(2, user.getPassword());
			rs=stmt.executeQuery();
			if(rs.next()) {
				result=fromResultSetToUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	// TODO: SQL 문자열, 메서드 추가(USERS.POINTS 컬럼 업데이트)
	private static final String SQL_UPDATE_POINTS = "update users set points = points + ? where userid = ?";
	public int updatePoints(String author,int points) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int result =0;
		try {
			conn= ds.getConnection();
			stmt=conn.prepareStatement(SQL_UPDATE_POINTS);
			stmt.setInt(1, points);
			stmt.setString(2, author);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt);
		}
		
		return result;
	}
	
	private User fromResultSetToUser(ResultSet rs) throws SQLException {
		int id=rs.getInt("id");
		String userid=rs.getString("userid");
		String password=rs.getString("password");
		String email=rs.getString("email");
		int points=rs.getInt("points");
		return User.builder().id(id).userid(userid).password(password).email(email).points(points).build();
	}
	
	private void closeResources(Connection conn, PreparedStatement stmt) {
		closeResources(conn, stmt, null);
	}
	
	private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
