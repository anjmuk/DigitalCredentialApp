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
	<a href="organizationHome?userId=${searchLearnerRecords.userId}">Organization
						Home</a>
	<div align="center">
		<%@page import="java.util.ArrayList"%>
		<%--Importing all the dependent classes--%>
		<%@page import="com.digitalcredentials.vo.UserVO"%>
		<%@page import="java.util.ArrayList"%>
		<%--Importing all the dependent classes--%>
		<%@page import="com.digitalcredentials.vo.GrantedCredentialVO"%>
		<%@page import="java.util.Iterator"%>


		<%
			java.util.ArrayList<GrantedCredentialVO> grantList = (java.util.ArrayList) request
					.getAttribute("grantList");
		%>
		<h1 align="center" style="color: blue;">Select Granted
			Certificate</h1>
		<form:form method="POST" action="findCertificate"
			modelAttribute="searchLearnerRecords">
			<table>
				<tr>
					<td>User Id:</td>
					<td><form:input path="userId" readonly="true"
							value="${searchLearnerRecords.userId}" /></td>
				</tr>
			</table>
			<table cellspacing="4" cellpadding="4" border="1" bgcolor="#FCF3CF">

				<tr bgcolor="#D6EAF8">
					<th>Grant Id</th>
					<th>University EIN</th>
					<th>Career</th>
					<th>Subject</th>
					<th>Concentration</th>



				</tr>
				<%
					// Iterating through subjectList
						if (grantList != null) // Null check for the object
						{
							Iterator<GrantedCredentialVO> iterator = grantList.iterator(); // Iterator interface
							while (iterator.hasNext()) // iterate through all the data until the last record
							{
								GrantedCredentialVO resultVO = iterator.next(); //assign individual employee record to the employee class object
				%>
				<tr>
					<td><form:radiobutton path="grantId"
							value="<%=resultVO.getId()%>" /> <form:label path="grantId"><%=resultVO.getId()%></form:label></td>

					<td><%=resultVO.getUniversityEIN()%></td>
					<td><%=resultVO.getCareer()%></td>
					<td><%=resultVO.getSubject()%></td>
					<td><%=resultVO.getConcentration()%></td>


				</tr>

				<%
					}
						}
				%>

			</table>
			<input type="submit" value="Search"
				style="font-size: 10pt; color: white; background-color: green; border: 2px solid #336600; padding: 3px;[COLOR=" Red"]margin-bottom:30px[/COLOR]" />
		</form:form>
	</div>
</body>
</html>