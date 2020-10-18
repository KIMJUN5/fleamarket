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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		boolean isMatch = checkIsMatch(id, password);
		if (isMatch) {
			System.out.println("[ " + id + " ] 에 대한 로그인 시도 성공");
			
			HttpSession session = request.getSession();
			session.setAttribute("isAuth", "true");
			session.setAttribute("id", id);
			response.sendRedirect(request.getContextPath() + "/listServlet");
		} else {
			System.out.println("[ " + id + " ] 에 대한 로그인 시도 실패");
			response.sendRedirect(request.getContextPath() + "/loginServlet");
		}
	}
	
	private boolean checkIsMatch(String id, String password) {
		InitialContext initialContext = null;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String login_sql = "SELECT ID, PASSWORD FROM ACCOUNT WHERE ID = ? AND PASSWORD = ?";
		boolean isMatch = false;
		
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/mybatis");
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(login_sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				String exist_id = resultSet.getString("ID");
				String exist_password = resultSet.getString("PASSWORD");
				
				if (id.equals(exist_id)) {
					if (password.equals(exist_password)) {
						isMatch = true;
					} else {
						System.out.println(id + "의 비밀번호가 일치하지 않습니다.");
					}
				} else {
					System.out.println(id + "는 없는 아이디 입니다.");
				}
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
		
		return isMatch;
	}
}
