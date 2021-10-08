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
		<h1 align="center" style="color: blue;">Learner Homepage</h1>
	
		<table>
			<tr>
				<td>View Certificate</td>
				<td><a
					href="searchLearnerCredential?userId=${learnerDetails.userId}">Search
						Certification</a></td>
			</tr>
			<tr>
				<td>View Program Transcripts</td>
				<td><a
					href="searchCourse?userId=${learnerDetails.userId}">Search
						Transcript</a></td>
			</tr>
			<tr>
				<td>Grant Certificate Access</td>
				<td><a
					href="showGrantCertificateAccess?userId=${learnerDetails.userId}">Grant Certificate Access
						</a></td>
			</tr>
			<tr>
				<td>Grant Transcript Access</td>
				<td><a
					href="showGrantTranscriptAccess?userId=${learnerDetails.userId}">Grant Transcript Access
						</a></td>
			</tr>
		</table>
	</div>
</body>
</html>