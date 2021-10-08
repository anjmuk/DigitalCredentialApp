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
		<form:form method="POST" action="updateRegistration"
			modelAttribute="programRegistrationBlockVO">

			<%@page import="java.util.ArrayList"%>
			<%--Importing all the dependent classes--%>
			<%@page import="com.digitalcredentials.vo.ProgramRegistrationBlockVO"%>

			<%
				ProgramRegistrationBlockVO programRegistrationBlockVO = (ProgramRegistrationBlockVO) (request
							.getAttribute("programRegistrationBlockVO"));
			%>


			<h1 align="center" style="color: blue;">Update Registration</h1>
			<br>

			<h2 align="center" style="color: blue;">Registration Info</h2>
			<table>
				<tr>
					<td>University Name:</td>
					<td><form:input path="universityName" readonly="true"
							value="${programRegistrationBlockVO.universityName}" /></td>
				</tr>
				<tr>
					<td>University EIN:</td>
					<td><form:input path="universityEIN" readonly="true"
							value="${programRegistrationBlockVO.universityEIN}" /></td>
				</tr>
			
				<tr>
					<td>Career:</td>
					<td><form:input path="career" readonly="true"
							value="${programRegistrationBlockVO.career}" /></td>
				</tr>
				<tr>
					<td>Subject:</td>
					<td><form:input path="subject" readonly="true"
							value="${programRegistrationBlockVO.subject}" /></td>
				</tr>
				<tr>
					<td>Concentration:</td>
					<td><form:input path="concentration" readonly="true"
							value="${programRegistrationBlockVO.concentration}" /></td>
				</tr>
				<tr>
					<td>Start Date:</td>
					<td><form:input path="startDate" readonly="true"
							value="${programRegistrationBlockVO.startDate}" /></td>
				</tr>
				<tr>
					<td>PII Hash:</td>
					<td><form:input path="PIIHashString" readonly="true"
							value="${programRegistrationBlockVO.PIIHashString}" /></td>
				</tr>
				<tr>
					<td>Encrypted PII:</td>
					<td><form:input path="encryptedPIIString" readonly="true"
							value="${programRegistrationBlockVO.encryptedPIIString}" /></td>
				</tr>

				<tr>
					<td>End Date(yyyymmdd):</td>
					<td><form:input path="endDate" size="8" type="number" required="required"
							maxlength="8" minlength="8" /></td>
				</tr>
				<tr>
					<td>Degree Class:</td>
					<td><form:select path="degreeClass" required="required" items="${degreeClassList}" /></td>
				</tr>
				<tr>
					<td>Program Status:</td>
					<td><form:select path="programStatus"
							items="${programStatusList}" required="required"
							value="<%=programRegistrationBlockVO.getProgramStatus()%>" /></td>
				</tr>
				<tr>
					<td>GPA</td>
					<td><form:input path="GPA" size="4" maxlength="4"  required="required"
							minlength="1" /></td>
				</tr>

			</table>
			<input type="submit" value="Submit"
				style="font-size: 10pt; color: white; background-color: green; border: 2px solid #336600; padding: 3px;[COLOR=" Red"]margin-bottom:30px[/COLOR]" />
		</form:form>
	</div>
</body>
</html>