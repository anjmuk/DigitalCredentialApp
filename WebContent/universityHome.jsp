<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.digitalcredentials.vo.UserVO"%>
<title></title>
</head>
<body bgcolor="#FADBD8">
<a href="/DigitalCredential">Home</a>
	<div align="center">
		<h1 align="center" style="color: blue;">University Homepage</h1>
		
		<table>
		<tr>
			<td>Register a learner for a Program:</td>
			<td><a href="showProgramRegistration?userId=${userDetails.userId}">Register Program</a></td>
		</tr>
		<tr>
			<td>Add Course Completion Record:</td>
			<td><a href="showAddCourse?userId=${userDetails.userId}">Add Course Completion</a></td>
		</tr>
		
		<tr>
			<td>Update Program Registration:</td>
			<td><a href="showRegistrationSearch?userId=${userDetails.userId}">Search Program Registration</a></td>
		</tr>
		</table>
	</div>
</body>
</html>