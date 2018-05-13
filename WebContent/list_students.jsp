<%@page import="org.rishi.projects.web.jdbc.dto.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, org.rishi.projects.web.jdbc.*" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css"/>
</head>
<body>
	<div id="wrapper">
		<div id="header">
		<h2>RG University</h2>
		</div>
	</div>
	<div id="container">
	<div id="content">
	<input type="button" value="Add Student" 
	onclick="window.location.href='add_student_form.jsp';return false;"
	class="add-student-button"
	/>
		<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email Id</th>
			<th>Action</th>
		</tr>
		<c:forEach var="temp" items="${student_list}">
		
		<!-- Setup a link for each student -->
		<c:url var="tempLink" value="StudentControllerServlet">
			<c:param name="command" value="LOAD"></c:param>
			<c:param name="studentId" value="${temp.id}"></c:param>
		</c:url>
		<c:url var="tempLink2" value="StudentControllerServlet">
			<c:param name="command" value="DELETE"></c:param>
			<c:param name="studentId" value="${temp.id}"></c:param>
		</c:url>
		
		<tr>
			<td>${temp.firstName}</td>
			<td>${temp.lastName}</td>
			<td>${temp.email}</td>
			<td><label><a href="${tempLink}">Update</a>
			|<a href="${tempLink2}"
			onClick="if(!confirm('Are you sure you want to delete this student')) return false;">
			Delete</a></label></td>
		</tr>	
		</c:forEach>
		</table>
	</div>
	</div>	
</body>
</html>