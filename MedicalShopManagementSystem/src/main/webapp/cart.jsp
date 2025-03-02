<%@ page import="com.medical.model.*"%>
<%@ page import="com.medical.dao.*"%>
<%@ page import="com.medical.connection.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/includes/cachePrevention.jsp"%>
<%
	DecimalFormat dcf = new DecimalFormat("#.##");
	request.setAttribute("dcf", dcf);
	User auth = (User) request.getSession().getAttribute("auth");
	if (auth != null) {
		request.setAttribute("auth", auth);
	}
	else{
		response.sendRedirect("login.jsp");
		return;
	}
	new CartDao();
	ArrayList<Cart> cart_list = CartDao.getAllCartItems(auth.getEmail());
	System.out.print("cartlist : "+cart_list+"\n");
	List<Cart> cartproducts = null;
	if (cart_list != null) {
		ProductDao pDao = new ProductDao(DbCon.getConnection());
		cartproducts = pDao.getCartMedicines(cart_list);
		double total = pDao.getTotalPrice(cart_list);
		request.setAttribute("cart-list", cart_list);
		request.setAttribute("total", total);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>&#128722;cart</title>
<%@include file="includes/head.jsp"%>
<style type="text/css">
.table thead td {
	vertical-align: middle;
}

.btn-incre, .btn-decre {
	box-shadow: none;
	font-size: 15px;
}
</style>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="d-flex py-3">
			<h3>total price : $ ${(total>0)?dcf.format(total):0 }</h3>
			<a class="mx-3 btn btn-primary" href="cart-check-out">check out</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">price</th>
					<th scope="col">quantity</th>
					<th scope="col">Buy now</th>
					<!--<th scope="col">cancel</th>-->
				</tr>
			</thead>
			<tbody>
				<%
				if (cart_list != null) {
					for (Cart c : cartproducts) {
				%>
				<tr>
					<td><%=c.getName()%></td>
					<td><%=dcf.format(c.getPrice())%></td>
					<td>
						<form action="order-now?id=<%=c.getMedicineId() %>" class="form-inline" method="post">
							<input type="hidden" name="id" value="<%= c.getMedicineId()%>" class="form-input">
							<div class="form-group d-flex justify-content-between w-50" >
								<a class="btn btn-sm btn-decre"
									href="quantity-inc-dec?action=dec&id=<%=c.getMedicineId()%>"
									>
									<%	
									
									//The minus button is not being disabled when the quantity is 1
									//negative quantity is allowe
									//bug not fixed
									
									
									String disable="auto";
									if(c.getQuantity()==1){
										System.out.println("Setting diasable to none");
										disable="none";
									} 
									%>
					
									<i
										class="fas fa-minus-square"
										style="pointer-events:<%=disable %>;">
										
									</i>
								</a>
								<input type="text"
									name="quantity" value="<%=c.getQuantity()%>"
									class="form-control w-25" readonly> 
								<a
									class="btn btn-sm btn-incre"
									href="quantity-inc-dec?action=inc&id=<%=c.getMedicineId()%>">
								<i	class="fas fa-plus-square" >
									</i></a>&nbsp;&nbsp;
								<button type="submit" class="btn btn-primary">Buy</button>
							</div>
						</form>
					</td>
					<td><a href="remove-from-cart?id=<%=c.getMedicineId()%>"
						class="btn btn-sm btn-danger">remove</a></td>

				</tr>

				<%
				}
				}
				%>

			</tbody>

		</table>

	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>