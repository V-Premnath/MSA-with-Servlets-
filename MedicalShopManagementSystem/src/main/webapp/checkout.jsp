<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="com.medical.model.*" %>

<%@ page import="java.text.DecimalFormat"%>
<%@ page import ="com.medical.dao.*" %>
<%@ page import ="java.util.List" %>
<%@include file="/includes/cachePrevention.jsp"%>
<%

User auth = (User) request.getSession().getAttribute("auth");
List<Order> order_items=null;
if (auth != null) {
	request.setAttribute("auth", auth);
	int order_id=Integer.parseInt(request.getParameter("id"));
}	
else {
	response.sendRedirect("login.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CheckOut</title>
	
</head>
<body>
<%@include file="/includes/navbar.jsp"%>
	<div class="container">
		<div class="card-header my-3">All orders</div>
		<table class="table table-light">
			<thead>
				<tr>
				    <th scope="col">Date</th>
					<th scope="col">Name</th>
					<th scope="col">price</th>
					<th scope="col">Quantity</th>
					<th scope="col">cancel</th>
				</tr>
			</thead>
			<tbody>
			<% if(order_items != null){
				for(Order o :order_items){%>
				<tr>
					<td><%= o.getOrderDate() %></td>
					<td><%= o.getName() %></td>
					<td><%= o.getPrice() %></td>
					<td><%= o.getOrderQuantity() %></td>
					<td><a class="btn btn-danger" href="cancel-order?id=<%= o.getOrderId()%>">Cancel</a></td>
				
				</tr><%}
				
			}%>
			</tbody>
		</table>


	</div>
</body>

	<%@include file="/includes/footer.jsp"%>
</html>