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
		<%@page import="java.util.ArrayList"%>
		<%--Importing all the dependent classes--%>
		<%@page import="com.digitalcredentials.vo.SearchLearnerRecordsVO"%>

		<h1 align="center" style="color: blue;">Find Granted Credentials
			</h1>
		<form:form method="POST" action="findCertificate"
			modelAttribute="searchLearnerRecords">
			<table>
				<tr>
					<td>User Id:</td>
					<td><form:input path="userId" readonly="true"
							value="${searchLearnerRecords.userId}" /></td>
				</tr>
				<tr>
					<td>Certificate Grant ID:</td>
					<td><form:input path="grantId" size="50" required="required"
							maxlength="50" minlength="1" /></td>
				</tr>
			</table>
			<input type="submit" value="Search"
				style="font-size: 10pt; color: white; background-color: green; border: 2px solid #336600; padding: 3px;[COLOR=" Red"]margin-bottom:30px[/COLOR]" />
		</form:form>
	</div>
</body>
</html>