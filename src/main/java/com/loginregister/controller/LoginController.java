package com.loginregister.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uemail = req.getParameter("username");
		String upwd = req.getParameter("password");

		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginregister?useSSL=false", "root", "123Hieu123@");
			String QUERY = "select * from users where uemail = ? and upwd = ?";
			PreparedStatement pst = con.prepareStatement(QUERY);
			pst.setString(1, uemail);
			pst.setString(2, upwd);
			ResultSet rs = pst.executeQuery();
			HttpSession session = req.getSession();
			if(rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				resp.sendRedirect("index.jsp");
			}else {
				RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
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
