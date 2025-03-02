package com.medical.servlet;
import java.io.IOException;

import com.medical.util.MailUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sendOtp")
public class SendOtpServlet extends HttpServlet {
    /**
	 *
	 */
	private static final long serialVersionUID = 480097237168774881L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("login-email");
        // Generate OTP and expiration time (5 minutes from now)
        String otp = MailUtil.generateOTP();
        long expirationTime = System.currentTimeMillis() + 5 * 60 * 1000;

        // Send OTP email
        try {
            MailUtil.sendOTPEmail(email, otp);
            System.out.println("otp : "+otp);       } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().write("Error sending OTP email.");
            return;
        }

        // Store OTP and expiration time in the session
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("otpExpiresAt", expirationTime);

        response.getWriter().write("OTP sent successfully.");
        response.sendRedirect("verifyOtp.jsp");
    }
}
