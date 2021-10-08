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
		<h1>Error Page</h1>
	
		<table>
			<tr>
				<td>Error Message:</td>
				<td>${errorMsg}</td>
			</tr>

		</table>
	</div>
</body>
</html>