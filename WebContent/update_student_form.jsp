<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Student Page</title>
<link type="text/css" rel="stylesheet" href="css/style.css"/>
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
		<h2>RG University</h2>
		</div>
	</div>
	<div id="container">
		<h3>Update Student</h3>
		<form action="StudentControllerServlet" method="get">
		<input type="hidden" name="command" value="UPDATE"/>
		<input type="hidden" name="studentId" value="${THE_STUDENT.id}"/>
			<table>
			<tbody>
				<tr>
					<td><label>First Name:</label></td>
					<td><input type="text" name="firstName"
					value="${THE_STUDENT.firstName}"/></td>
				</tr>
				<tr>
					<td><label>Last Name:</label></td>
					<td><input type="text" name="lastName"
					value="${THE_STUDENT.lastName}"/></td>
				</tr>
				<tr>
					<td><label>Email:</label></td>
					<td><input type="text" name="email"
					value="${THE_STUDENT.email}"/></td>
				</tr>
				<tr>
					<td><label></label></td>
					<td><input type="submit" value="Save" class="add-student-button"></td>
				</tr>
			</tbody>	
			</table>	
		</form>
		<div style="clear: both;">
			<p>
				<a href="StudentControllerServlet">Back to main page</a>
			</p>
		</div>
		
	</div>	
</body>
</html>