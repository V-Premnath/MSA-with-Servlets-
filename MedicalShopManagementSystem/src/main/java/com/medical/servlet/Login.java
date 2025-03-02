package com.medical.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.medical.connection.DbCon;
import com.medical.dao.UserDao;
import com.medical.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");
			String userrole = request.getParameter("login-type");
			System.out.println("\ntype  print : "+userrole);
			UserDao udao = new UserDao(DbCon.getConnection());
			User user = udao.getUser(email, password, userrole);
			System.out.println("User : "+user.getEmail()+"\nemail:"+email);
		
			if (email.equals(user.getEmail())) {
				if(user.getStatus()==null || !("APPROVED".equals(user.getStatus()))) {
					System.out.println("You are not yet approved by the Admin");
					response.sendRedirect("login.jsp?error=You are not yet approved by the Admin");
					return;
				}
				switch (userrole) {
					case "CUSTOMER":{
						request.getSession().setAttribute("auth", user);
						System.out.println("Customer "+user.getName()+" logged in");
						 RequestDispatcher dispatcher = request.getRequestDispatcher("/sendOtp");
					     dispatcher.forward(request, response);
						break;
						}
					case "ADMIN":{
						request.getSession().setAttribute("auth", user);
						System.out.print("Admin logged in");
						response.sendRedirect("admindash.jsp");
						break;
						}
					case "DOCTOR":{
						request.getSession().setAttribute("auth", user);
						System.out.print("Doctor "+user.getName()+" logged in");
						response.sendRedirect("doctordash.jsp");
						break;
						}
					case "SUPPLIER":{
						request.getSession().setAttribute("auth", user);
						System.out.print("Supplier "+user.getName()+" logged in");
						response.sendRedirect("supplierdash.jsp");
						break;
						}
					case "PHARMACIST":{
						request.getSession().setAttribute("auth", user);
						System.out.print("Pharmacist "+user.getName()+" logged in");
						response.sendRedirect("pharmacistdash.jsp");
						break;
						}
				}
			}
			else {
				response.sendRedirect("login.jsp?error=User Not Found");
			}

		}
		catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
		}

	}
}
