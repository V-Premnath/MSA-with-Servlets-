package com.medical.servlet;

import java.io.IOException;

import com.medical.dao.UserDao;
import com.medical.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Signup")
public class Signup extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

          try  {
        	  	UserDao userdao=new UserDao();
        	  	User user = userdao.getUser(email, password, role);
                if (user.getEmail()!=null) {
                    System.out.println( "User with this email already exists");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                
                // Insert new user
                user.setEmail(email);
                user.setName(username);
                user.setPassword(password);
                user.setRole(role);
                userdao.createNewUser(user);
                
                response.sendRedirect("login.jsp");
            }
        catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }


   
}
