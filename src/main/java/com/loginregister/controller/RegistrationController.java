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
@WebServlet(urlPatterns = {"/register"})
public class RegistrationController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get data from registration.jsp
		String uname = req.getParameter("name");
		String uemail = req.getParameter("email");
		String upwd = req.getParameter("pass");
		String unumber = req.getParameter("contact");
		
		Connection con = null;
		// insert account
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginregister", "root", "123Hieu123@");
			String QUERY = "insert into users(uname, upwd, uemail, unumber) values(?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(QUERY);
			pst.setString(1, uname);
			pst.setString(3, uemail);
			pst.setString(2, upwd);
			pst.setString(4, unumber);
			int index = pst.executeUpdate();
			RequestDispatcher rd = null;
			if(index > 0 ) {
				resp.sendRedirect("login.jsp");
			}else {
				System.out.println("dang ky that bai");
				rd = req.getRequestDispatcher("register.jsp");
				rd.forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
