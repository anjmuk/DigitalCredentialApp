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
<a href="learnerHome?userId=${learnerDetails.userId}">Learner
						Home</a>
	<div align="center">
		<%@page import="java.util.ArrayList"%>
		<%--Importing all the dependent classes--%>
		<%@page import="com.digitalcredentials.vo.SearchLearnerRecordsVO"%>

		<h1 align="center" style="color: blue;">Grant Transcript Access
			to an Organization</h1>
		<form:form method="POST" action="createTranscriptAccess"
			modelAttribute="searchLearnerRecords">
			<table>

				<tr>
					<td>User Id:</td>
					<td><form:input path="userId" readonly="true"
							value="${searchLearnerRecords.userId}" /></td>
				</tr>
				<tr>
					<td>Organization EIN:</td>
					<td><form:input path="organizationEIN" size="9"
							required="required" maxlength="9" minlength="9" /></td>
				</tr>
				<tr>
					<td>University EIN:</td>
					<td><form:input path="universityEIN" size="9"
							required="required" maxlength="9" minlength="9" /></td>
				</tr>
				<tr>
						<td>Learner University ID:</td>
					<td><form:input path="learnerId" size="9" required="required"
							type="number" maxlength="9" minlength="4" /></td>
				</tr>
				<tr>
					<td>Select Career:</td>
					<td><form:select path="career" items="${careerList}" /></td>
				</tr>
				<tr>
					<td>Select Subject:</td>
					<td><form:select path="subject" items="${subjectList}" /></td>
				</tr>
				<tr>
					<td>Select Concentration:</td>
					<td><form:select path="concentration"
							items="${concentrationList}" /></td>
				</tr>
				
			</table>
			<input type="submit" value="Submit"
				style="font-size: 10pt; color: white; background-color: green; border: 2px solid #336600; padding: 3px;[COLOR=" Red"]margin-bottom:30px[/COLOR]" />
		</form:form>
	</div>
</body>
</html>