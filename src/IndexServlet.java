

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Page1Servlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String driverName = "oracle.jdbc.driver.OracleDriver";
		final String url = "jdbc:oracle:thin:@192.168.54.226:1521/orcl";
		final String id = "OUBO";
		final String pass = "TOUSEN";
		
		try {
			Class.forName(driverName);
			Connection connection=DriverManager.getConnection(url,id,pass);
			
			PreparedStatement st = 
					connection.prepareStatement(
							"Select ceil(SYSDATE-kaisi) as kaisi From Kigen"
						);
			ResultSet rs = st.executeQuery();
			rs.next();
			String kaisi = rs.getString("kaisi");
			if(kaisi.charAt(0)=='-') {
				int K = Integer.parseInt(kaisi);
				K = Math.abs(K);
				//System.out.print(K);
				request.getSession().setAttribute("K", K);
				request.getSession().setAttribute("kaisiflag", "1");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/page1.jsp");
				rd.forward(request, response);
				
			}else {
				PreparedStatement st1 = 
						connection.prepareStatement(
								"Select SYSDATE-KIGEN as DIFF From Kigen"
							);
				ResultSet rs1 = st1.executeQuery();
				rs1.next();
				String diff = rs1.getString("DIFF");
				if(diff.charAt(0)=='-') {
					request.getSession().setAttribute("K", -1);
					RequestDispatcher rd = request.getRequestDispatcher("/page1");
					rd.forward(request, response);
					
				}else {
					RequestDispatcher rd = request.getRequestDispatcher("/page3");
					rd.forward(request, response);
					
				}
				
			}
			
			
		}catch(SQLException e) {
			System.out.println("SQLException");
			response.getWriter().println("SQLException");
			e.printStackTrace();
			e.printStackTrace(response.getWriter());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
			response.getWriter().println("ClassNotFoundException");
			e.printStackTrace();
			e.printStackTrace(response.getWriter());
		}


	}

}
