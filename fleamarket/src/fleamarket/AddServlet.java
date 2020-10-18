package fleamarket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class AddServlet extends HttpServlet {
	static public String ADD_SQL_INSERT = "INSERT INTO POST VALUES((SELECT NVL(MAX(NO),0) FROM POST) + 1 , ?, ?, ?, ?, ?, ?)";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!Boolean.parseBoolean(String.valueOf(request.getSession().getAttribute("isAuth")))) {
			response.sendRedirect(request.getContextPath() + "/loginServlet");
		} else {
			String id = (String) request.getSession().getAttribute("id");
			
			request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Post post = new Post();
		post.setAccountId(request.getParameter("id"));
		post.setType(request.getParameter("type"));
		post.setPrice(request.getParameter("price"));
		post.setNote(request.getParameter("note"));
		post.setRegist(request.getParameter("regist"));
		post.setTitle(request.getParameter("title"));

		insertData(post);
		response.sendRedirect(request.getContextPath() + "/listServlet");
	}
	
	private void insertData(Post post) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/mybatis");
			connection = ds.getConnection();
			
			preparedStatement = connection.prepareStatement(ADD_SQL_INSERT);
			preparedStatement.setString(1, post.getAccountId());
			preparedStatement.setString(2, post.getType());
			preparedStatement.setString(3, post.getTitle());
			preparedStatement.setString(4, post.getPrice());
			preparedStatement.setString(5, post.getNote());
			preparedStatement.setString(6, post.getRegist());
			
			preparedStatement.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				} 
				if (preparedStatement != null) {
					preparedStatement.close();	
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException e) {
			e.printStackTrace();
			}
		}
	}
}
