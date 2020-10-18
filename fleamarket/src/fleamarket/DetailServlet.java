package fleamarket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class DetailServlet extends HttpServlet {
	private static String DETAIL_SQL = "SELECT no,account_id,type,title,price,note,TO_CHAR(regist,'yyyy-mm-dd') as \"regist\" FROM POST WHERE NO = ?";
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Boolean.parseBoolean(String.valueOf(request.getSession().getAttribute("isAuth")))) {
			response.sendRedirect(request.getContextPath() + "/loginServlet");
		} else {

			String no = request.getParameter("no");
			request.setAttribute("row", getData(no));

			request.getRequestDispatcher("/WEB-INF/jsp/detail.jsp").forward(request, response);
		}
	}
	
	private Map<String,String> getData(String no) {
		Map<String,String> row = new HashMap<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/mybatis");
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DETAIL_SQL);
			
			preparedStatement.setString(1, no);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				row.put("no", resultSet.getString("no"));
				row.put("accountId", resultSet.getString("account_id"));
				row.put("type", resultSet.getString("type"));
				row.put("title", resultSet.getString("title"));
				row.put("price", resultSet.getString("price"));
				row.put("note", resultSet.getString("note"));
				row.put("regist", resultSet.getString("regist"));
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
			} catch (Exception e) {
			}
		}
		
		return row;
	}
}
