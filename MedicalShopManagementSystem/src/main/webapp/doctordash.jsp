<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.medical.model.User" %> <!-- Adjust the import based on your project structure -->
<%@ page import="com.medical.dao.UserDao" %>
<%@include file="/includes/cachePrevention.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor's Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        header {
            background-color: #4CAF50;
            color: white;
            padding: 8px;
            text-align: center;
            position: relative;
        }
        #usericon {
            position: absolute;
            top: 15px;
            right: 15px;
            cursor: pointer;
            font-size: 20px;
        }
        .container {
            max-width: 800px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .search-section, .prescription-section, .profile-section, .doctor-details {
            margin-bottom: 30px;
        }
        input[type="text"], input[type="submit"], textarea {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .profile-details, .doctor-details {
            background-color: #f9f9f9;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .hidden {
            display: none;
        }
        .not-found {
            color: red;
        }
    </style>
</head>
<body>

<header>
    <h1>Doctor's Dashboard</h1>
    <br>
    <% User user=(User)request.getSession().getAttribute("auth"); %>
    <h3>Welcome Dr.<%=user.getName() %></h3>
    
    	<i class="fas fa-user-circle" id="usericon" style="font-size: 24px; color: #333;" onclick="toggleDoctorDetails()"></i>
    	
</header>

<div class="container">

    <section class="search-section">
        <h2>Search Past Prescriptions</h2>
        <form action="/searchPrescriptions" method="GET">
            <input type="text" name="searchId" placeholder="Patient ID/ Prescription ID/ Patient Name" required>
            <input type="submit" value="Search">
        </form>
    </section>

    <section class="prescription-section">
        <h2>Create New Prescription</h2>
        <form action="/createPrescription" method="POST">
            <input type="text" name="patientId" placeholder="Patient ID" required>
            <input type="text" name="medication" placeholder="Medication" required>
            <input type="text" name="dosage" placeholder="Dosage" required>
            <input type="text" name="diagnosis" placeholder="Diagnosis" required>
            <textarea name="instructions" placeholder="Instructions" rows="4" required></textarea>
            <input type="submit" value="Create Prescription">
        </form>
    </section>

    <section class="profile-section">
        <h2>Patient Profile</h2>
        <div id="patient-details" class="profile-details">
            <%
                // Simulate fetching patient data from a database or service
                UserDao userdao=new UserDao();
                User patient = null;//userdao.getUser((int)request.getAttribute("searchId")); // Assuming 'patient' is set in the request
                if (patient != null) {
            %>
                <p><strong>Patient ID:</strong> <%= patient.getId() %></p>
                <p><strong>Name:</strong> <%= patient.getName() %></p>
                <p><strong>Email:</strong> <%= patient.getEmail() %></p>
            <%
                } else {
            %>
                <div class="not-found">Patient not found.</div>
            <%
                }
            %>
        </div>
    </section>

    <section id="doctor-details" class="doctor-details">
        <h2>Doctor Details</h2>
        <div class="profile-details">
            <p><strong>Doctor ID:</strong> <%=user.getId() %></p>
            <p><strong>Name:</strong> <%=user.getName() %></p>
            <p><strong>Specialization:</strong> General Practitioner</p>
            <p><strong>Contact:</strong> 555-6789</p>
            <p><strong>Email:</strong> <%= user.getEmail()%></p>
        </div>
    </section>

</div>

<script>
    function toggleDoctorDetails() {
        const details = document.getElementById("doctor-details");
        details.scrollIntoView({ behavior: 'smooth' });
    }
</script>

</body>
</html>
