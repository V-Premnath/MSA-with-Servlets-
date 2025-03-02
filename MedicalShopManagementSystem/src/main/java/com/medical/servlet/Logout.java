package com.medical.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.medical.connection.DbCon;
import com.medical.dao.UserDao;
import com.medical.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try(PrintWriter out = response.getWriter()){
			if(request.getSession().getAttribute("auth")!=null) {
				UserDao userdao = new UserDao(DbCon.getConnection());
				User user = (User)request.getSession().getAttribute("auth");
				userdao.userLogout(user.getEmail());
				request.getSession().removeAttribute("auth");
				request.getSession().invalidate();
				response.sendRedirect("login.jsp");
			}else {

				response.sendRedirect("login.jsp");
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(request, response);
		}


}
