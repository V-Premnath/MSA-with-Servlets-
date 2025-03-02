<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/includes/cachePrevention.jsp"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/includes/head.jsp"%>
<title>MEDICAL SHOP</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
    body {
        background-color: #f8f9fa;
    }
    .card {
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
    }
    .card-header {
        background-color: #007bff;
        color: white;
        font-weight: bold;
    }
    .form-control {
        border-radius: 5px;
    }
    .btn-primary {
        background-color: #007bff;
        border: none;
        border-radius: 5px;
        padding: 10px 20px;
        font-size: 16px;
    }
    .btn-primary:hover {
        background-color: #0056b3;
    }
    .error {
        margin-top: 10px;
    }
</style>
</head>
<body>
	
<div class="container" style="padding-top:20px;">
<h1 align="center">Medical Shop Automation</h1>
</div>
    <div class="container">
        <div class="card w-50 mx-auto my-5">
            <div class="card-header text-center">Login</div>
            <div class="card-body">
                <form action="login" method="post">
                    <div class="form-group">
                        <label>Email address</label> 
                        <input type="email" name="login-email" class="form-control" placeholder="Enter email" required>
                    </div>
                    <div class="form-group">
                        <label>Password</label> 
                        <input type="password" name="login-password" class="form-control" placeholder="Password" required>
                    </div>
                    <div class="form-group">
                    <label>User Type: </label>
                    <select name="login-type" class="form-control">
                    <option>CUSTOMER</option>
                    <option>ADMIN</option>
                    <option>PHARMACIST</option> 
                    <option>SUPPLIER</option>
                    <option>DOCTOR</option>
                    </select>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                </form>
                <p class="text-center">No account? 
                <a href="signup.jsp">Sign Up</a>
                </p>
                <% String error= request.getParameter("error"); 
                if (error!=null){
                %>
                <div class="error text-center text-danger">
                    <%=error %>
                </div>
                <%
                }

                %>
            </div>
        </div>
    </div>

    <%@include file="/includes/footer.jsp"%>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script> 
    window.onload = function() { 
        // Check if there are any query parameters 
        if (window.location.search.length > 0) {
            // Use history.pushState to clear the URL parameters 
            var cleanURL = window.location.protocol + "//" + window.location.host + window.location.pathname;
            window.history.pushState({ path: cleanURL }, '', cleanURL); 
        } 
    }; 
    </script>
</body>
</html>
