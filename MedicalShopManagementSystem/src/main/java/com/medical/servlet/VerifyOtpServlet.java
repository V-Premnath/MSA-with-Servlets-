package com.medical.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.medical.dao.UserDao;
import com.medical.model.User;

/**
 * Servlet implementation class VerifyOtpServlet
 */
@WebServlet("/verifyOtp")
public class VerifyOtpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyOtpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String inputOtp="";
		for (int i=1;i<7;i++) {
			String attributeName="otp"+i;
			String temp=(String)request.getParameter(attributeName);
			inputOtp=inputOtp+temp;
		}
		if(inputOtp.equals(request.getSession().getAttribute("otp"))) {
			try {
				User user=(User)request.getSession().getAttribute("auth");
				UserDao udao= new UserDao();
				udao.userLogin(user.getEmail(),user.getRole());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("index.jsp");
		}
		else {
			System.out.println("Incorrect OTP");
			response.sendRedirect("verifyOtp.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
