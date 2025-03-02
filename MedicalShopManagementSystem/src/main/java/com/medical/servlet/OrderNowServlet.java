package com.medical.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.medical.connection.DbCon;
import com.medical.dao.OrderDao;
import com.medical.model.Cart;
import com.medical.model.Order;
import com.medical.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {


            LocalDate currentDate = LocalDate.now();
             // Define the format you want
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Format the date and time as a string
            //String dateString = currentDate.format(formatter);

            User auth = (User) request.getSession().getAttribute("auth");
            //String var_userEmail=auth.getEmail();
            System.out.println("user : "+auth+" date:"+currentDate+" formatter:"+formatter);
            if (auth == null) {
            	System.out.println("User not logged in @ OrderNowServlet");
            	response.sendRedirect("login.jsp");
            	return;
            }
                String productId = request.getParameter("id");
                int productQuantity = Integer.parseInt(request.getParameter("quantity"));
                if (productQuantity <= 0) {
                	productQuantity = 1;
                }
                Order order = new Order();
                order.setMedicineId(Integer.parseInt(productId));
                order.setUserId(auth.getId());
                order.setOrderQuantity(productQuantity);
                order.setOrderDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                OrderDao orderDao = new OrderDao(DbCon.getConnection());
                //CartDao cartDao = new CartDao();

                int result = orderDao.insertOrder(order);

               if (result!=0) {
                   ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    if (!cart_list.isEmpty()) {
                       for (Cart c : cart_list) {
                            if (c.getMedicineId() == Integer.parseInt(productId)) {
                               cart_list.remove(cart_list.indexOf(c));
                               response.sendRedirect("remove-from-cart?id="+c.getMedicineId()+"");
                               break;
                         }
                       }
                    }
                    response.sendRedirect("order.jsp");
                } else {
                    out.println("order failed");
                }

        } catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}