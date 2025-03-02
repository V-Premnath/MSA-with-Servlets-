package com.medical.model;

public class Cart extends Medicine{
	private int quantity;
	private String userEmail;



	public Cart() {
	}


	public int getQuantity() {
		return quantity;
	}

	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String useremail) {
		this.userEmail = useremail;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
