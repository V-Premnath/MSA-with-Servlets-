package com.medical.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medical.connection.DbCon;
import com.medical.model.Medicine;
import com.medical.model.Order;

public class OrderDao {
	private Connection con;
	private String query;
	private ResultSet rs;
	private PreparedStatement pst;

	public OrderDao(Connection con) {
		this.con=con;
	}
	public OrderDao() throws ClassNotFoundException, SQLException {
		Connection conn= DbCon.getConnection();
		this.con=conn;
	}

	public int insertOrder(Order order) {
		int result=0;
		ProductDao mdao=new ProductDao(this.con);
		try{
			query="INSERT INTO orders(user_id,medicine_id,order_quantity,order_amount,order_date) VALUES(?,?,?,?,?)";
			pst=this.con.prepareStatement(query);
			pst.setInt(1, order.getUserId());
			pst.setInt(2, order.getMedicineId());
			pst.setInt(3, order.getOrderQuantity());
			pst.setInt(4, (int)order.getPrice());
			System.out.println("OrderDao order.getorderdate : "+order.getOrderDate()+"");
			pst.setString(5, order.getOrderDate());
			int rows=pst.executeUpdate();

			System.out.println("pst : "+pst+"RowsAffected : "+rows);

			pst=null;
			String query2="DELETE FROM cart WHERE medicine_id=? and user_id=?";
			pst=this.con.prepareStatement(query2);
			pst.setInt(1, order.getMedicineId());
			pst.setInt(2, order.getUserId());
			result=pst.executeUpdate();

		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<Order> userOrders(int id){
		List<Order> list = new ArrayList<>();
		try {
			query="select * from orders where user_id=?";
			pst=this.con.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			while(rs.next()) {
				Order order = new Order();
				ProductDao productDao = new ProductDao(this.con);
				int p_id = rs.getInt("medicine_id");
				Medicine product = productDao.getSingleMedicine(p_id);
				order.setOrderDate(rs.getString("order_date"));
				order.setMedicineId(p_id);
				System.out.println("p_id : "+p_id);
				System.out.println("product @OrderDao.userOrders : "+product+"");
				float price= product.getPrice()* rs.getInt("order_quantity");
				order.setPrice(price) ;
				order.setOrderQuantity(rs.getInt("order_quantity"));
				order.setOrderId(rs.getInt("order_id"));
				order.setName(product.getName());
				list.add(order);

			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void cancelOrder(int id) {
		try {
		query="delete from orders where order_id=?";
		pst=this.con.prepareStatement(query);
		pst.setInt(1, id);
		pst.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
