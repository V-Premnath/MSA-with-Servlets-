package com.medical.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.medical.connection.DbCon;
import com.medical.model.Cart;



public class CartDao {
	private static Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;

	public CartDao(Connection con) {
		CartDao.con = con;
	}

	public CartDao() {
		try {
			Connection conn=DbCon.getConnection();
			CartDao.con = conn;
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}


	public Cart getItem(String user,int id) throws SQLException {

		query="SELECT * FROM cart WHERE useremail=? and medicine_id=?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1,user);
		pstmt.setInt(2, id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Cart returnObj=new Cart();
		returnObj.setMedicineId(rs.getInt("medicine_id"));
		returnObj.setUserEmail(rs.getString("useremail"));
		returnObj.setQuantity(rs.getInt("quantity"));

		return returnObj;

	}

	public static ArrayList<Cart> getAllCartItems(String user)throws SQLException{

		String query="SELECT * FROM cart WHERE useremail=?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1,user);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("\nfetching cart Items from db");

		ArrayList<Cart> returnObj=new ArrayList<>();

		while(rs.next()) {
			Cart cartObj=new Cart();
			cartObj.setUserEmail(rs.getString("useremail"));
			cartObj.setMedicineId(rs.getInt("medicine_id"));
			cartObj.setQuantity(rs.getInt("quantity"));
			returnObj.add(cartObj);
		}
		System.out.println("\nreturning to callback function ");
		return returnObj;

	}


	public static void updateMedicine(Cart medicine,String user) throws SQLException {
		// TODO Auto-generated method stub

		String query="UPDATE cart SET quantity=? WHERE useremail=? and medicine_id=?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, medicine.getQuantity());
		pstmt.setString(2,user);
		pstmt.setInt(3, medicine.getMedicineId());
		int rows_affected = pstmt.executeUpdate();

	}

	public static void addToCart(Cart medicine,String user)throws SQLException {
		// TODO Auto-generated method stub
		String query2="SELECT user_id from users where useremail=?";
		PreparedStatement ps=con.prepareStatement(query2);
		ps.setString(1,medicine.getUserEmail());
		ResultSet r=ps.executeQuery();
		r.next();
		String query="INSERT INTO cart(useremail,medicine_id,quantity,user_id) VALUES(?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(query);

		pstmt.setString(1,user);
		pstmt.setInt(2, medicine.getMedicineId());
		pstmt.setInt(3, medicine.getQuantity());
		pstmt.setInt(4,	r.getInt("user_id"));
		int ret = pstmt.executeUpdate();
		if(ret!=0) {
			System.out.println("Inserted into db");
		}
		else {
			System.out.println("Insert into db failed");
		}
	}

	public void remove(int id,String email) throws SQLException {
		// TODO Auto-generated method stub
		String query="DELETE FROM cart WHERE useremail=? and medicine_id=?";
		PreparedStatement pstmt = con.prepareStatement(query);

		pstmt.setString(1,email);
		pstmt.setInt(2, id);
		pstmt.executeUpdate();
		System.out.println("Item with Id "+id+" removed from cart");
	}

}
