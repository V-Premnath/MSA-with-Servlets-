package com.medical.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;

import com.medical.connection.DbCon;
import com.medical.model.User;

public class UserDao {
	private Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;

	public UserDao(Connection con) {
		this.con = con;
	}

	public UserDao() throws ClassNotFoundException, SQLException {
		this.con=DbCon.getConnection();
		}

	public User getUser(String email, String password,String role) throws SQLException{
		
		User user = new User();
    	password=hashPassword(password);
    	
    	//Check whether the particular user with given userEmail, password and Role exists or not
        query = "select * from users where useremail=? and user_role=?";
        pst = this.con.prepareStatement(query);
        pst.setString(1, email);
        pst.setString(2, role);
        rs = pst.executeQuery();
        
        if(rs.next() ){
        	if(rs.getString("password").equals(password)) {
        			user.setId(rs.getInt("user_id"));
        			user.setName(rs.getString("username"));
        			user.setEmail(rs.getString("useremail"));
        			user.setRole(rs.getString("user_role"));
        			user.setStatus(rs.getString("status"));
        			return user;
            }
        	else {
        		System.out.println("Wrong password");
        	}
        }
        else {
        	System.out.println("User Not Found");
        }
        return user;
    }


	public PreparedStatement admin() {
		try {
            query = "select * from users ;";
            pst = this.con.prepareStatement(query);
            return pst;
            }
		catch (SQLException e) {
            System.out.print(e.getMessage());
        }
		return null;
	}
	public void userLogout(String email) throws SQLException {
		
		String updateSQL = "UPDATE user_log SET logout_time = CURRENT_TIMESTAMP, status = 'completed' " +
                "WHERE user_id = (SELECT a.user_id FROM users a WHERE a.useremail=?) AND action = 'login' AND status = 'active'";
		pst = this.con.prepareStatement(updateSQL);

		pst.setString(1, email);
		int rows=pst.executeUpdate();
		System.out.println("updated logout rows affected : "+rows+"");

	}

	public int userLogin(String email,String role) throws SQLException {
				
		String insertSQL = "INSERT INTO user_log (user_id, action) VALUES ((SELECT user_id FROM users WHERE useremail=? AND user_role=?), 'login')";
		pst = this.con.prepareStatement(insertSQL);
		pst.setString(1,email);
		pst.setString(2, role);		
		int rows=pst.executeUpdate();
		System.out.println("rows affected in log : "+rows+"");
		return rows;
		
	}


	public User getUser(int user_id) throws SQLException {

		query = "SELECT * from users WHERE user_id=?";
		pst = this.con.prepareStatement(query);
		pst.setInt(1, user_id);
		rs = pst.executeQuery(query);
		User user=new User();
		if(rs.next()) {
		user.setEmail(rs.getString("useremail"));
		user.setId(rs.getInt("user_id"));
		user.setName(rs.getString("username"));
		user.setStatus(rs.getString("status"));
		}

		return user;

	}

	public void createNewUser(User user) throws SQLException {
		PreparedStatement pstmt = this.con.prepareStatement(
                "INSERT INTO users (username, useremail, password, user_role) VALUES (?, ?, ?, ?)"
            );
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, hashPassword(user.getPassword())); // Remember to implement password hashing
            pstmt.setString(4, user.getRole());

            int result = pstmt.executeUpdate();
            System.out.println("requested approval done : "+result);
	}
	private String hashPassword(String password) {
	        // Implement password hashing here (e.g., using BCrypt)
			try {
				// Create MessageDigest instance for SHA-256
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
		        
	            // Perform hashing
	            byte[] hash = digest.digest(password.getBytes());
	
	            // Convert to Base64 for readability
	            return Base64.getEncoder().encodeToString(hash);
	        } 
			catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	         }
	        // Don't use plain text in production!
	}

	public List<User> getUnapprovedUsers() throws SQLException{
		List<User> retObj = new ArrayList<>();
		query="SELECT * FROM users WHERE status is null";
		pst=this.con.prepareStatement(query);
		rs=pst.executeQuery();
		while(rs.next())
		{
			User user=new User(
					"",  							//name argument
					rs.getString("useremail"),
					"",								//password argument
					rs.getString("username"),
					rs.getString("user_role")
					);
			user.setStatus(rs.getString("status"));
			user.setId(rs.getInt("user_id"));
			retObj.add(user);
		}
		return retObj;
	}
	
	public void approveUser(int id) throws SQLException{
		query="UPDATE users SET status='APPROVED' WHERE user_id=?";
		pst=this.con.prepareStatement(query);
		pst.setInt(1, id);
		pst.executeUpdate();
	}
}