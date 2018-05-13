package org.rishi.projects.web.jdbc.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	private DataSource dataSource;
	
	public StudentDBUtil(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	public List<Student> getStudents(){
		List<Student> students=new ArrayList<>();
		
		Connection myConn=null;
		Statement myStmt=null;
		ResultSet myRes=null;
		
		try {
		//get connection
		myConn=(Connection)dataSource.getConnection();
		
		//create sql statement
		String sql="select * from student order by id";
		
		//execute query
		myStmt=myConn.createStatement();
		myRes=myStmt.executeQuery(sql);
		
		//process resultset
		while(myRes.next()) {
			
			//retrieve data from resultset row
			int id=myRes.getInt("id");
			String firstName=myRes.getString("first_name");
			String lastName=myRes.getString("last_name");
			String email=myRes.getString("email");
			
			Student tempStudent=new Student(id, firstName, lastName, email);
			students.add(tempStudent);
		}
		
		//close JDBC objects
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(myConn,myStmt,myRes);
		}
		return students;
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRes) {
		try {
			if(myRes!=null)
				myRes.close();
			if(myStmt!=null)
				myStmt.close();
			if(myConn!=null)
				myConn.close();   //doesn't really close it...puts back in connection pool
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void addStudent(Student student) {
		Connection myConn=null;
		PreparedStatement myStmt=null;
		
		try {
			//get db connection 
			myConn=dataSource.getConnection();
			
			//create sql for insert
			String sql="insert into student "
			+ "(first_name,last_name,email) "
			+ "values (?,?,?)";
			
			myStmt=myConn.prepareStatement(sql);
			//set param values for student
			myStmt.setString(1, student.getFirstName());
			myStmt.setString(2, student.getLastName());
			myStmt.setString(3, student.getEmail());
			
			//execute sql insert
			myStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			//clean up JDBC objects
			close(myConn,myStmt,null);
		}	
	}
	

	public Student getStudent(String theStudentId){
		Student theStudent=null;
		
		Connection myConn=null;
		PreparedStatement myStmt=null;
		ResultSet myRes=null;
		int studentId;
		
		try {
			//convert student id to int
			studentId=Integer.parseInt(theStudentId);
			
			//get DB connection
			myConn=dataSource.getConnection();
			
			//create sql command to select student
			String sql="select * from student where id=?";
			
			//create prepared statement
			myStmt=myConn.prepareStatement(sql);
			
			//set params
			myStmt.setInt(1, studentId);
			
			//execute statement
			myRes=myStmt.executeQuery();
			
			//retrieve data from resultset row
			if(myRes.next()){
				String firstName=myRes.getString("first_name");
				String lastName=myRes.getString("last_name");
				String email=myRes.getString("email");
				
				//use studentId during construction
				theStudent=new Student(studentId, firstName, lastName, email);
			}
			else {
				throw new Exception("Could not find student id:"+studentId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//clean up JDBC objects
			close(myConn,myStmt,myRes);
		}
		return theStudent;
	}

	public void updateStudent(Student student) throws Exception{
		Connection myConn=null;
		PreparedStatement myStmt=null;
		try {
		//get db connection
		myConn=dataSource.getConnection();
		
		//create sql update statement
		String sql="update student "
		+ "set first_name=?,last_name=?,email=? "
		+ "where id=?";		
		
		//prepare statement
		myStmt=myConn.prepareStatement(sql);
		
		//set params
		myStmt.setString(1, student.getFirstName());
		myStmt.setString(2, student.getLastName());
		myStmt.setString(3, student.getEmail());
		myStmt.setInt(4, student.getId());
		
		//execute sql statement
		myStmt.execute();
		}
		finally {
			close(myConn, myStmt ,null);
		}
	}

	public void deleteStudent(String studentId) {
		Connection myConn=null;
		PreparedStatement myStmt=null;
		
		try {
			//convert studentid to int
			int theStudentId=Integer.parseInt(studentId);
			
			//get db connection
			myConn=dataSource.getConnection();
			
			//create sql to delete student
			String sql="delete from student where id=?";
			
			//prepare sql statement
			myStmt=myConn.prepareStatement(sql);
			
			//get params
			myStmt.setInt(1, theStudentId);
			
			//execute query
			myStmt.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			close(myConn,myStmt,null);
		}
	}
}