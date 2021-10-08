<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<title></title>
</head>
<body bgcolor="#FADBD8">
<a href="/DigitalCredential">Home</a>
	<div align="center">
		<h1 align="center" style="color: blue;">University SignUp</h1>
		<form:form method="POST" action="universitySignUp" modelAttribute="signUpDetails">
			<table>
				<tr>
					<td>University Name:</td>
					<td><form:input path="name" size="30" required="required"
							maxlength="30" minlength="4" /></td>
				</tr>
				<tr>
					<td>University Address:</td>
					<td><form:input path="address" size="40" required="required"
							maxlength="40" minlength="6" /></td>
				</tr>
				<tr>
					<td>EIN:</td>
					<td><form:input path="EIN" size="9" required="required"
							maxlength="9" minlength="9" /></td>
				</tr>
				<tr>
					<td>Phone:</td>
					<td><form:input path="phone" size="12" required="required"
							maxlength="12" minlength="12" /></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><form:input path="email" size="30" required="required"
							maxlength="30" minlength="6" /></td>
				</tr>
				<tr>
					<td>User Id:</td>
					<td><form:input path="userId" size="12" required="required"
							maxlength="12" minlength="6" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><form:input path="password" type="password" size="30"
							required="required" maxlength="30" minlength="8" /></td>
				</tr>


			</table>
			<input type="submit" value="Submit"
				style="font-size: 10pt; color: white; background-color: green; border: 2px solid #336600; padding: 3px;[COLOR=" Red"]margin-bottom:30px[/COLOR]" />
		</form:form>
	</div>
</body>
</html>