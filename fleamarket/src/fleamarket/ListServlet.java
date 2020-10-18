package fleamarket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class ListServlet extends HttpServlet {
	private static String LIST_SQL = "SELECT no,account_id,type,title,price,note,TO_CHAR(regist,'yyyy-mm-dd') as \"regist\" FROM POST ORDER BY NO";
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Boolean.parseBoolean(String.valueOf(request.getSession().getAttribute("isAuth")))) {
			response.sendRedirect(request.getContextPath() + "/loginServlet");
		} else {

			request.setAttribute("rows", getList());
			request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
		}
	}

	private List<Map<String,String>> getList() {
		List<Map<String,String>> rows = new ArrayList<Map<String,String>>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/mybatis");
			connection = dataSource.getConnection();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(LIST_SQL);

			while(resultSet.next()) {
				Map<String,String> row = new HashMap<>();
				row.put("no", resultSet.getString("no"));
				row.put("accountId", resultSet.getString("account_id"));
				row.put("type", resultSet.getString("type"));
				row.put("title", resultSet.getString("title"));
				row.put("price", resultSet.getString("price"));
				row.put("note", resultSet.getString("note"));
				row.put("regist", resultSet.getString("regist"));

				rows.add(row);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(Exception e) {
			}
		}
		return rows;
	}
}
