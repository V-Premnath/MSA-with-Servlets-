package com.medical.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.medical.dao.CartDao;
import com.medical.model.Cart;
import com.medical.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter out = response.getWriter()) {

			ArrayList<Cart> cartList = new ArrayList<>();
			HttpSession session = request.getSession();

			int id = Integer.parseInt(request.getParameter("id"));
			User usr=(User)session.getAttribute("auth");

			if(usr==null) {
				System.out.println("User not logged in");
				response.sendRedirect("login.jsp");
				return;
			}
			String usremail=usr.getEmail();
			System.out.println("User "+usremail+" logged in");
			Cart cm = new Cart();
			cm.setMedicineId(id);
			cm.setQuantity(1);
			cm.setUserEmail(usremail);

			//CartDao cd=new CartDao();
			ArrayList<Cart> cart_list = CartDao.getAllCartItems(usremail);
			if (cart_list == null) {
				cartList.add(cm);
				CartDao.addToCart(cm,usremail);
				session.setAttribute("cart-list", cartList);
				response.sendRedirect("index.jsp");
			} else {
				cartList = cart_list;
				boolean exist = false;
				for (Cart c : cart_list) {
					if (c.getMedicineId() == id) {
						exist = true;
						out.print("<h3 style='color:crimson;text-align:center;'>Item already exist in cart<a href='cart.jsp'>Go to cart page</h3>");
					}

				}
				if (!exist) {
					cartList.add(cm);
					CartDao.addToCart(cm,usremail);
					response.sendRedirect("index.jsp");
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
