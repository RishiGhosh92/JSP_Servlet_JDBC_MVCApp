package org.rishi.projects.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Define datasource/connection pool for Resource Injection
	@Resource(name="jdbc/web_student_tracker")  //exact same name as defined in context.xml
	private DataSource dataSource;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Setup printWriter
		PrintWriter pw=response.getWriter();
		response.setContentType("text/plain");
		
		//Get connection to database
		Connection myConn=null;
		Statement myStmt=null;
		ResultSet myRes=null;
		
		try {
			myConn=(Connection) dataSource.getConnection();	 //datasource-->connection pool
			
			//Create SQL statements
			String sql="select * from student";
			myStmt=(Statement) myConn.createStatement();
			
			//Execute SQL Query
			myRes=myStmt.executeQuery(sql);
			
			//Process result set
			while(myRes.next()) {
				String email=myRes.getString("email");
				pw.println(email);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
