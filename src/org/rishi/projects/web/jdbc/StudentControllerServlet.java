package org.rishi.projects.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.rishi.projects.web.jdbc.dto.Student;
import org.rishi.projects.web.jdbc.dto.StudentDBUtil;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDBUtil studentDBUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	//good place to put our initialization code
	@Override
	public void init() throws ServletException {
		super.init();
		
		//create our student db util and pass in connection datasource
		try {
			studentDBUtil=new StudentDBUtil(dataSource);
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String theCommand=request.getParameter("command");	
		//if command is missing, default to listing students	
		//list students in MVC fashion
		if(theCommand==null)
			theCommand="LIST";
		
		//route to appropriate method
		switch(theCommand) {
		case "LIST":
			listStudents(request,response);
			break;
		case "ADD":
			addStudents(request,response);
			break;
		case "LOAD":
			loadStudents(request,response);
			break;
		case "UPDATE":
			updateStudent(request,response);
			break;
		case "DELETE":
			deleteStudent(request,response);
			break;	
		default:
			listStudents(request, response);
		}
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read student id from form data
		String studentId=request.getParameter("studentId");
		
		//delete student from db
		studentDBUtil.deleteStudent(studentId);
		
		//redirect back to list students page
		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read student info from form data
		int studentId=Integer.parseInt(request.getParameter("studentId"));
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		
		//create a new student object
		Student student=new Student(studentId, firstName, lastName, email);
		
		//perform db update
		studentDBUtil.updateStudent(student);
		
		//send them back to list_students page
		listStudents(request, response);
	}

	private void loadStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read Student id from form data
		String theStudentId=request.getParameter("studentId");
		
		//get Student from db
		Student theStudent=studentDBUtil.getStudent(theStudentId);
		
		//place student in request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		
		//send to jsp page : update_student_form.jsp
		RequestDispatcher dispatcher=request.getRequestDispatcher("update_student_form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//get student data from form data
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		
		if(firstName==null || lastName==null|| email==null)
		listStudents(request, response);	
		
		//create a new Student object
		Student s=new Student(firstName, lastName, email);
		
		//add student to DB
		studentDBUtil.addStudent(s);
		
		//send back to main page
		listStudents(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		//get students from DButil
		List<Student> students=studentDBUtil.getStudents();
		
		//add students to req
		request.setAttribute("student_list", students);
		
		//send to JSP (view)
		RequestDispatcher dispatcher=request.getRequestDispatcher("list_students.jsp");
		dispatcher.forward(request, response);
	}

}
