package com.WebDemo;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: LonginServlet").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String username = request.getParameter("username");
		String pw = request.getParameter("pw");
		//数据库验证
		Connection con = null;
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//数据库url
			String dburl = "jdbc:mysql://localhost/niit?"
					+ "user=guest&password=password123";
					
			//获取连接
			con = DriverManager.getConnection(dburl);
			//执行查询语句
			String sql = "select count(1) cnt from account where username=? and password=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, username);
			ps.setString(2, pw);
			
			ResultSet rs = ps.executeQuery();
			//获取结果
			if(rs.next()) {
				int cnt = rs.getInt("cnt");
				//如果验证成功
				if(cnt==1) {
					request.getSession().setAttribute("usename", username);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		//失败
		response.sendRedirect("Index");
		
	}

}
