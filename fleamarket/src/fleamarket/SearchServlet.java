package fleamarket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class SearchServlet extends HttpServlet {
	private static String SEARCH_SQL = "SELECT no,account_id,type,title,price,note,TO_CHAR(regist,'yyyy-mm-dd') as \"regist\"FROM post WHERE TITLE LIKE  '%' || ? || '%'";
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String word = request.getParameter("word");
		request.setAttribute("rows", resultList(word));
		request.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(request, response);
	}
	
	private List<Map<String,String>> resultList(String word) {
		List<Map<String,String>> rows = new ArrayList<Map<String,String>>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/mybatis");
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SEARCH_SQL);
			
			preparedStatement.setString(1, word);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Map<String,String> row = new HashMap<String,String>();
				
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
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
			}
		}
		
		return rows;
	}
}
