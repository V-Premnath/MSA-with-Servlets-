<%@ page import="com.medical.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.medical.dao.*"%>
<%@ page import="com.medical.connection.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/includes/cachePrevention.jsp"%>
<%

List<Order> orders=null;
User auth = (User) request.getSession().getAttribute("auth");

if (auth != null) {
	request.setAttribute("auth", auth);
	OrderDao orderDao = new OrderDao(DbCon.getConnection());
	orders = orderDao.userOrders(auth.getId());
} else {
	response.sendRedirect("login.jsp");
}


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>orders page</title>
<%@include file="includes/head.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="card-header my-3">All orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Order Id</th>
				    <th scope="col">Date</th>
					<th scope="col">Name</th>
					<th scope="col">price</th>
					<th scope="col">Quantity</th>
				</tr>
			</thead>
			<tbody>
			<% if(orders != null){
				for(Order o :orders){%>
				<tr>
					<td><%= o.getOrderId() %></td>
					<td><%= o.getOrderDate() %></td>
					<td><%= o.getName() %></td>
					<td><%= o.getPrice() %></td>
					<td><%= o.getOrderQuantity() %></td>
				</tr><%}
				
			}%>
			</tbody>
		</table>


	</div>



	<%@include file="includes/footer.jsp"%>
</body>
</html>