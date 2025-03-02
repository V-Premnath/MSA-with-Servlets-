package com.medical.model;

public class Order extends Medicine{
	private int orderId;
	private int userId;
	private int orderQuantity;
	private String orderDate;

	public Order() {
	}

	public Order(int orderId, int uid, int qunatity, String date) {
		super();
		this.orderId = orderId;
		this.userId = uid;
		this.orderQuantity = qunatity;
		this.orderDate = date;
	}

	public Order(int uid, int qunatity, String date) {
		super();
		this.userId = uid;
		this.orderQuantity = qunatity;
		this.orderDate = date;
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int quantity) {
		this.orderQuantity = quantity;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String date) {
		this.orderDate = date;
	}
}
