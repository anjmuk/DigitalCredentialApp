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
		<h1 align="center" style="color: blue;">Organization Homepage</h1>

		<table>

			<tr>
				<td>View Certificates</td>
				<td><a
					href="viewGrantedCredentials?userId=${userDetails.userId}">List
						Certificates</a></td>
			</tr>
			<tr>
				<td>View Transcripts</td>
				<td><a
					href="viewGrantedTranscripts?userId=${userDetails.userId}">List
						Transcripts</a></td>
			</tr>
		</table>
	</div>
</body>
</html>