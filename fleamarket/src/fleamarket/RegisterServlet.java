package fleamarket;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class RegisterServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		
		boolean isNotOverlapped = checkIsNotOverlapped(id);
		if (isNotOverlapped) {
			registerAccount(id, password, name);
			
			response.sendRedirect(request.getContextPath() + "/loginServlet");
		} else {
			System.out.println(id + "는 중복 아이디");
			
			response.sendRedirect(request.getContextPath() + "/registerServlet");
		}
	}
	
	private void registerAccount(String id, String password, String name) {
		InitialContext initialContext = null;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String register_sql = "INSERT INTO ACCOUNT VALUES(?, ?, ?)";
		
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/mybatis");
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(register_sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				
				if (connection != null) {
					connection.close();
				}
				
				if (initialContext != null) {
					initialContext.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean checkIsNotOverlapped(String id) {
		InitialContext initialContext = null;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String search_id_sql = "SELECT ID FROM ACCOUNT WHERE ID = ?";
		boolean isNotOverlapped = false;
		
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/mybatis");
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(search_id_sql);
			preparedStatement.setString(1, id);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				//겹치는 아이디가 있다
				isNotOverlapped = false;
			} else {
				//겹치는 아이디가 없다
				isNotOverlapped = true;
			}
		} catch (Exception e) {
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
				
				if (initialContext != null) {
					initialContext.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return isNotOverlapped;
	}
}