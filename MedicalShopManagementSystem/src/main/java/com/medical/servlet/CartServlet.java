package com.medical.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.medical.connection.DbCon;
import com.medical.dao.CartDao;
import com.medical.model.Cart;
import com.medical.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class CartQuantityServlet
 */
@WebServlet("/quantity-inc-dec")
public class CartServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));
        User user=(User) request.getSession().getAttribute("auth");
        // Fetch the medicine object based on ID
        CartDao pd;
        Cart medicine=new Cart();
		try {
			pd = new CartDao(DbCon.getConnection());

			medicine = pd.getItem(user.getEmail(),id);
			System.out.println("Action : "+action+"\n");
			if ("inc".equals(action)) {
		           medicine.setQuantity(medicine.getQuantity() + 1);
		       } else if ("dec".equals(action)) {
		           medicine.setQuantity(medicine.getQuantity() - 1);
		       }

		        // Update the quantity in the database
		    CartDao.updateMedicine(medicine,user.getEmail());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}


        // Increment or decrement quantity


        // Redirect to the same page to reflect the updated quantity
        response.sendRedirect("cart.jsp"); // Replace 'currentPage.jsp' with the actual JSP page name
    }

}
