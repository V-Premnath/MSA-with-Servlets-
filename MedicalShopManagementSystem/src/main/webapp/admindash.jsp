<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.medical.dao.UserDao"  %>
<%@ page import="com.medical.connection.*"  %>
<%@ page import="com.medical.model.User"  %>
<%@ page import="java.sql.*"  %>
<%@ page import="java.util.List"  %>
<%@include file="/includes/cachePrevention.jsp"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            display: flex;
            min-height: 100vh;
            background-color: #f4f6f8;
        }

        .dashboard {
            display: flex;
            width: 100%;
        }

        .sidebar {
            width: 250px;
            background-color: #4e73df;
            color: #fff;
            padding: 20px;
        }

        .sidebar h2 {
            font-size: 1.5em;
            margin-bottom: 1em;
        }

        .sidebar nav a {
            display: block;
            color: #fff;
            padding: 10px;
            margin: 5px 0;
            text-decoration: none;
            border-radius: 4px;
            transition: background 0.3s;
        }

        .sidebar nav a:hover {
            background-color: #2e59d9;
        }

        .main-content {
            flex-grow: 1;
            padding: 20px;
        }

        .card {
            background-color: #fff;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .card h3 {
            margin-bottom: 1em;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        button {
            padding: 5px 10px;
            background-color: #1cc88a;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #17a673;
        }
    </style>
</head>
<body>
    <div class="dashboard">
        <aside class="sidebar">
            <h2>Admin Dashboard</h2>
            <nav>
                <a href="#new-users">New Sign-Ups</a>
                <a href="#user-distribution">User Distribution</a>
                <a href="#inventory">Inventory Stock</a>
                <a href="#history">Inventory History</a>
                <a href="#financials">Financial Stats</a>
            </nav>
        </aside>
        <main class="main-content">
            <!-- New Users Request Section -->
            <section id="new-users" class="card">
                <h3>New Users Requesting Enrollment</h3>
                <table>
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Name</th>
                            <th>Role</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%UserDao userdao = new UserDao();
                    List<User> users=userdao.getUnapprovedUsers();
                    System.out.println("users"+users);
                    for(User u :users){
                    %>
                    	<tr>
                            <td><%=u.getId() %></td>
                            <td><%=u.getUsername() %></td>
                            <td><%=u.getRole() %></td>
                            <td><button onclick="<% userdao.approveUser(u.getId()); %>>">Approve</button></td>
                        </tr>
                    <%	
                    }
                    %>
                    </tbody>
                </table>
            </section>

            <!-- User Distribution Section -->
            <section id="user-distribution" class="card">
                <h3>User Distribution</h3>
                <canvas id="userChart"></canvas>
            </section>

            <!-- Current Inventory Section -->
            <section id="inventory" class="card">
                <h3>Current Inventory Stock</h3>
                <canvas id="inventoryChart"></canvas>
            </section>

            <!-- Inventory Refill History Section -->
            <section id="history" class="card">
                <h3>Inventory Refill Turnover</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Item</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>2024-10-10</td>
                            <td>Product A</td>
                            <td>50</td>
                        </tr>
                        <tr>
                            <td>2024-10-15</td>
                            <td>Product B</td>
                            <td>30</td>
                        </tr>
                    </tbody>
                </table>
            </section>

            <!-- Financial Statistics Section -->
            <section id="financials" class="card">
                <h3>Financial Statistics - Sales Trends</h3>
                <canvas id="salesChart"></canvas>
            </section>
        </main>
    </div>

    <script>
        // Chart for User Distribution
        const userCtx = document.getElementById('userChart').getContext('2d');
        new Chart(userCtx, {
            type: 'pie',
            data: {
                labels: ['Region A', 'Region B', 'Region C'],
                datasets: [{
                    label: 'User Distribution',
                    data: [300, 150, 100],
                    backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc']
                }]
            }
        });

        // Chart for Inventory Stock
        const inventoryCtx = document.getElementById('inventoryChart').getContext('2d');
        new Chart(inventoryCtx, {
            type: 'bar',
            data: {
                labels: ['Product A', 'Product B', 'Product C'],
                datasets: [{
                    label: 'Stock Quantity',
                    data: [120, 80, 60],
                    backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc']
                }]
            }
        });

        // Chart for Sales Trends
        const salesCtx = document.getElementById('salesChart').getContext('2d');
        new Chart(salesCtx, {
            type: 'line',
            data: {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
                datasets: [{
                    label: 'Sales ($)',
                    data: [1500, 2000, 1800, 2200, 2400, 2600],
                    borderColor: '#4e73df',
                    backgroundColor: 'rgba(78, 115, 223, 0.1)'
                }]
            }
        });
    </script>
</body>
</html>
