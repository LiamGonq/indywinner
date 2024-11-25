/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

/**
 * <p>
 * This is a simple servlet that will use JDBC to gather all of the Indy 500
 * winner information from a database and format it into an HTML table.
 * No guarantees of meeting:
 *			Thread safety
 *			Does not adhere to "SOLID:
 *			No DAO pattern etc.
 *			No page scolling
 * This is "quick and dirty" simple DB table query, formats DB resultset to
 * an HTML table format
 */
@WebServlet(urlPatterns = {"/IndyWinnerSimpleSV"})
public class IndyWinnerSimpleSV extends HttpServlet {


	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException {

		/*	Get the URI, Set the content type, and create a printwriter		*/
		String uri = request.getRequestURI();
		response.setContentType("text/html");
                StringBuilder buffer = new StringBuilder();
		
		formatPageHeader(buffer);
		
		sqlQuery("com.mysql.cj.jdbc.Driver",					// Database Driver
			"jdbc:mysql://localhost/IndyWinners",				// Database Name
			"root", "",										// username and password
			"SELECT * from IndyWinners order by year desc",	// select statement
			buffer, uri);												// buffer space and URI

		/*	Wrap up																			*/
		buffer.append("</html>");

		/*	Send the formatted page back to the client							*/
		try (java.io.PrintWriter out = new java.io.PrintWriter(response.getOutputStream())) {
			out.println(buffer.toString());
			out.flush();
		} catch (Exception ex) {
		}
	}

	/**
	 * Create an HTML page header for this output
	 * @param buffer Place to build the string
	 */
	private void formatPageHeader(StringBuilder buffer)	{
		/*	Foramt the HTML header page												*/
		buffer.append("<html>");
		buffer.append("<head>");
		buffer.append("<title>Indianapolis 500 Winners</title>");
		buffer.append("</head>");
		buffer.append("<h2><center>");
		buffer.append("Indianapolis 500 Winners");
		buffer.append("</center></h2>");
		buffer.append("<br>");
	}
	
	
	/**
	 * <p>
	 * Given the JDBC driver name, URL, and query string, execute the query and
	 * format the results into an HTML table
	 *
	 * @param driverName JDBC driver name
	 * @param connectionURL JDBC connection URL
	 * @param user	Database user name
	 * @param pass	Database password for this app.
	 * @param query SQL query to execute
	 * @param buffer	Output string buffer to build and populate with DB row details
	 * @param uri Request URI
	 * @return true if the query was successful
	 */
	private void sqlQuery(String driverName,
			  String connectionURL,
			  String user, String pass,
			  String query,
			  StringBuilder buffer,
			  String uri) {
		boolean rc = true;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		/*	Keep stats for how long it takes to connect and execute the query		*/
		long startMS = System.currentTimeMillis();
		/*	Keep the number of rows in the ResultSet										*/
		int rowCount = 0;

		try {
			/*	Create an instance of the JDBC driver so that it has
			 *	a chance to register itself
			 *		Get a new database connection.
			 *		Create a statement object that we can execute queries	with
			 *		Execute the query
			 */
			Class.forName(driverName);
			con = DriverManager.getConnection(connectionURL, user, pass);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			/*	Format the result set into an HTML table								*/
			rowCount = resultSetToHTML(rs, buffer, uri);

		} catch (Exception ex) {
			/*	Send the error back to the client										*/
			buffer.append("Exception!");
			buffer.append(ex.toString());
			rc = false;
		} finally {
			try {
				/*	Always close properly													*/
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException sqlEx) {
				/*	Ignore any errors here													*/
			}
		}

		/*	If we queried the table successfully, output some statistics		*/
		if (rc) {
			long elapsed = System.currentTimeMillis() - startMS;
			buffer.append("<br><i> (");
			buffer.append(rowCount);
			buffer.append(" rows in ");
			buffer.append(elapsed);
			buffer.append("ms) </i>");
		}

	}

	/**
	 * <p>
	 * Given a JDBC ResultSet, format the results into a simple HTML table
	 * @param rs JDBC ResultSet
	 * @param buffer Free space for building strings
	 * @param uri Requesting URI (could be used for form actions)
	 * @return The number of rows in the ResultSet
	 */
	private int resultSetToHTML(java.sql.ResultSet rs,
			  StringBuilder buffer,
			  String uri)
			  throws Exception {

		int rowCount = 0;

		/*	Create the table and center it												*/
		buffer.append("<center><table border>");

		/*	Start the table row
		 *	create table header values from meta data
		 *	Process all available rows in the database for the query
		 */
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		buffer.append("<tr>");
		for (int i = 0; i < columnCount; i++) {
			buffer.append("<th>");
			buffer.append(rsmd.getColumnLabel(i + 1));
			buffer.append("</th>");
		}

		buffer.append("</tr>");

		/*	Now walk through the entire ResultSet and get each row				*/
		while (rs.next()) {
			rowCount++;

			buffer.append("<tr>");
			for (int i = 0; i < columnCount; i++) {
				String data = rs.getString(i + 1);
				buffer.append("<td>");
				buffer.append(data);
				buffer.append("</td>");
			}
			/*	End the table row																*/
			buffer.append("</tr>");
		}

		/*	End the table																		*/
		buffer.append("</table></center>");

		/*	Could create a new form here and add a button resulting
		 *	in new input from the client for maybe a next page??
		 */
		return rowCount;
	}

}
