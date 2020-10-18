package fleamarket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class EditServlet extends HttpServlet {
	static public String EDIT_SQL_SELECT = "SELECT * FROM POST WHERE NO=?";
	static public String EDIT_SQL_UPDATE = "UPDATE POST SET TITLE=?, PRICE=?, NOTE=? WHERE NO=?";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!Boolean.parseBoolean(String.valueOf(request.getSession().getAttribute("isAuth")))) {
			response.sendRedirect(request.getContextPath() + "/loginServlet");
		}
		
		Post post = new Post();
		post.setNo(Integer.parseInt((String)request.getParameter("no")));
		post.setAccountId("testId");
		
		Map<String, String> row = serachData(post);
		request.setAttribute("row", row);
		
		request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Post post = new Post();
		post.setNo(Integer.parseInt((String)request.getParameter("no")));
		post.setPrice(request.getParameter("price"));
		post.setNote(request.getParameter("note"));
		post.setTitle(request.getParameter("title"));
		
		updateData(post);
		response.sendRedirect(request.getContextPath() + "/detailServlet?no=" + post.getNo());
	}
	
	private void updateData(Post post) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			InitialContext initContext = new InitialContext();
			Context envContext  = (Context) initContext.lookup("java:/comp/env");
			DataSource dataSource = (DataSource) envContext.lookup("jdbc/mybatis");
			
			connection = dataSource.getConnection();
			System.out.println(post);
			preparedStatement = connection.prepareStatement(EDIT_SQL_UPDATE);
			preparedStatement.setString(1, post.getTitle());
			preparedStatement.setString(2, post.getPrice());
			preparedStatement.setString(3, post.getNote());
			preparedStatement.setInt(4, post.getNo());
			
			int resultCount = preparedStatement.executeUpdate();
			System.out.println(resultCount);
			
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
	
	private Map<String, String> serachData(Post post) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Map<String, String> row = new HashMap();

		try {
			InitialContext initContext = new InitialContext();
			Context envContext  = (Context) initContext.lookup("java:/comp/env");
			DataSource dataSource = (DataSource) envContext.lookup("jdbc/mybatis");
			
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(EDIT_SQL_SELECT);
			preparedStatement.setInt(1, post.getNo());
			
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			row.put("no", resultSet.getString(1));
			row.put("accountId", resultSet.getString(2));
			row.put("price", resultSet.getString(5));
			row.put("title", resultSet.getString(4));
			row.put("note", resultSet.getString(6));
			row.put("regist", resultSet.getString(7));
			row.put("type", resultSet.getString(3));
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
		return row;
	}
}