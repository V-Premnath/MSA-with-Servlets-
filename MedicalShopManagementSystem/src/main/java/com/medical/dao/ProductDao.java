package com.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.medical.model.Cart;
import com.medical.model.Medicine;

public class ProductDao {
	private Connection con;
	private String query;
	private ResultSet rs;
	private PreparedStatement pst;

	public ProductDao(Connection con) {
		this.con = con;
	}

	public List<Medicine> getAllMedicines() {
		List<Medicine> Medicines = new ArrayList<>();
		try {
			query = "select * from medicine";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				Medicine row = new Medicine();
				row.setMedicineId(rs.getInt("medicine_id"));
				row.setPrice(rs.getFloat("cost"));
				row.setName(rs.getString("name"));
				Medicines.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Medicines;
	}
	public List<Cart> getCartMedicines (ArrayList<Cart> cart_list){
		List<Cart> Medicines = new ArrayList();
		try {
			if(cart_list.size()>0) {
				for(Cart item:cart_list) {
					query="select * from medicine where medicine_id=?";
					pst=this.con.prepareStatement(query);
					pst.setInt(1, item.getMedicineId());
					rs=pst.executeQuery();
					while(rs.next()) {
						Cart row = new Cart();
						row.setMedicineId(rs.getInt("medicine_id"));
						row.setName(rs.getString("name"));
						row.setPrice(rs.getFloat("cost")*item.getQuantity());
						row.setQuantity(item.getQuantity());
						Medicines.add(row);
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return Medicines;
	}

	public Medicine getSingleMedicine(int id) {
		Medicine row = new Medicine() ;
		try {
			query="select * from medicine where medicine_id=?";
			pst=this.con.prepareStatement(query);
			pst.setInt(1, id);
			rs =pst.executeQuery();

			while(rs.next()) {
				row= new Medicine();
				row.setName(rs.getString("name"));
				row.setPrice(rs.getFloat("cost"));
				row.setMedicineId(rs.getInt("medicine_id"));
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return row;

	}
	public  double getTotalPrice(ArrayList<Cart> cart_list) {
		double sum=0;
		try {
			if(cart_list.size()>0) {
				for(Cart item : cart_list) {
					query="select * from medicine where medicine_id=?";
					pst=this.con.prepareStatement(query);
					pst.setInt(1, item.getMedicineId());
					rs=pst.executeQuery();
					while(rs.next()) {
						sum+=rs.getDouble("cost")*item.getQuantity();
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return sum;
	}

}
