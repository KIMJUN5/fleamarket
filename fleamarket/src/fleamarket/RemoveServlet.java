package fleamarket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class RemoveServlet extends HttpServlet {
	static public String REMOVE_SQL_DELETE = "DELETE FROM POST WHERE NO=?";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!Boolean.parseBoolean(String.valueOf(request.getSession().getAttribute("isAuth")))) {
			response.sendRedirect(request.getContextPath() + "/loginServlet");
		} else {

			Post post = new Post();
			post.setNo(Integer.parseInt((String) request.getParameter("no")));
			System.out.println(post.getNo());

			deleteData(post);

			response.sendRedirect(request.getContextPath() + "/listServlet");
		}
	}
	
	private void deleteData(Post post) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/mybatis");
			connection = dataSource.getConnection();
			
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(REMOVE_SQL_DELETE);
			preparedStatement.setInt(1, post.getNo());
			System.out.println(post.getNo());
			
			preparedStatement.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
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
