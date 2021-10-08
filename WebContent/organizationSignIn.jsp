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
		<h1 align="center" style="color: blue;">Organization SignIn</h1>
		<form:form method="POST" action="organizationSignIn" modelAttribute="signInDetails">
			<table>
				
				
				<tr>
					<td>User Id:</td>
					<td><form:input path="userId" size="12" required="required"
							maxlength="12" minlength="6" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><form:input path="password" type="password" size="12"
							required="required" maxlength="30" minlength="8" /></td>
				</tr>


			</table>
			<input type="submit" value="SignIn"
				style="font-size: 10pt; color: white; background-color: green; border: 2px solid #336600; padding: 3px;[COLOR=" Red"]margin-bottom:30px[/COLOR]" />
		</form:form>
	</div>
</body>
</html>