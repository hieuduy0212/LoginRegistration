package com.loginregister.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/register" })
public class RegistrationController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get data from registration.jsp
		String uname = req.getParameter("name");
		String uemail = req.getParameter("email");
		String upwd = req.getParameter("pass");
		String urpwd = req.getParameter("re_pass");
		String unumber = req.getParameter("contact");

		RequestDispatcher rd = null;
		if (uname == null || uname.equals("")) {
			req.setAttribute("status", "invalidUname");
			rd = req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			return;
		} else if (uemail == null || uemail.equals("")) {
			req.setAttribute("status", "invalidUemail");
			rd = req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			return;
		} else if (upwd == null || upwd.equals("")) {
			req.setAttribute("status", "invalidUpwd");
			rd = req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			return;
		} else if (urpwd == null || urpwd.equals("")) {
			req.setAttribute("status", "invalidUrpwd");
			rd = req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			return;
		} else if (urpwd.equalsIgnoreCase(upwd) == false) {
			req.setAttribute("status", "invalidNotEqualsPwd");
			rd = req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			return;
		} else if (unumber == null || unumber.equals("")) {
			req.setAttribute("status", "invalidUnumber");
			rd = req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			return;
		}

		else if (unumber == null || unumber.equals("")) {
			req.setAttribute("status", "invalidUnumber");
			rd = req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			return;
		}
		Connection con = null;
		// insert account
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginregister?useSSL=false", "root",
					"123Hieu123@");
			String QUERY = "insert into users(uname, upwd, uemail, unumber) values(?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(QUERY);
			pst.setString(1, uname);
			pst.setString(3, uemail);
			pst.setString(2, upwd);
			pst.setString(4, unumber);
			int index = pst.executeUpdate();

			if (index > 0) {
				rd = req.getRequestDispatcher("login.jsp");
			} else {
				rd = req.getRequestDispatcher("register.jsp");
			}
			rd.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
