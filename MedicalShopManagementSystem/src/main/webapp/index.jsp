<%@ page import="com.medical.connection.DbCon"%>
<%@ page import="com.medical.model.User"%>
<%@ page import="com.medical.model.Medicine"%>
<%@ page import="com.medical.model.Cart"%>
<%@ page import="java.util.*"%>
<%@ page import="com.medical.dao.ProductDao"%>
<%@ page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	User auth = (User) request.getSession().getAttribute("auth");
	if (auth != null) {
		request.setAttribute("auth", auth);
	}
	Connection c= DbCon.getConnection();
	ProductDao pd = new ProductDao(c);
	
	List<Medicine> products = pd.getAllMedicines();
	System.out.print("Index page retrieved data\n"+products+"\n");
	ArrayList<Cart> cart_list=(ArrayList<Cart>)session.getAttribute("cart-list");
	if(cart_list!=null){
		request.setAttribute("cart-list",cart_list);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%if(auth!=null){ %>
<title>Welcome <%= auth.getName() %></title>
<%} %>
<%@include file="/includes/head.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="card-header my-3">All Medicines </div>
		<div class="row">
			<%if(!products.isEmpty()){
			   for(Medicine p:products){%>
			<div class="col-md-3">
				<div class="card w-100" style="width: 18rem;">
					<img  src="includes/Untitled	.jpg" class="card-img-top" width="100px" height="200px"
						alt="...card image">
					<div class="card-body">
						<h5 class="card-title"><%= p.getName() %></h5>
						<h6 class="price"><%= p.getPrice() %></h6>
						<div class="mt-3 d-flex justify-content-between">
							<a href="add-to-cart?id=<%= p.getMedicineId() %>" class="btn btn-dark">Add
								to cart</a> <a href="order-now?id=<%= p.getMedicineId() %> %>"
								class="btn btn-primary">Buy Now</a>

						</div>
					</div>
				</div>
			</div>
			<%
		   }
		   }	   
		else {
					out.println("There is no products");
					
		   } 
		   %>

		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>