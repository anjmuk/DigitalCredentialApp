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
<a href="organizationHome?userId=${userDetails.userId}">Organization Home</a>
	<div align="center">
		<%@page import="java.util.ArrayList"%>
		<%--Importing all the dependent classes--%>
		<%@page import="com.digitalcredentials.vo.CompleteRegistrationVO"%>

		<%
			CompleteRegistrationVO completeRegistrationDetails = (CompleteRegistrationVO) (request
					.getAttribute("completeRegistrationDetails"));
		%>
			
			
		<h1 align="center" style="color: blue;">Program Details</h1>
		<br>

		<h2 align="center" style="color: blue;">Degree Info</h2>
		<table>

			<tr>
				<td>University Name:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getUniversityName()%></td>
			</tr>
			<tr>
				<td>University EIN:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getUniversityEIN()%></td>
			</tr>
			<tr>
				<td>Career:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getCareer()%></td>
			</tr>
			<tr>
				<td>Subject:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getSubject()%></td>
			</tr>
			<tr>
				<td>Concentration:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getConcentration()%></td>
			</tr>
			<tr>
				<td>Status:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getProgramStatus()%></td>
			</tr>
			<tr>
				<td>Start Date:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getStartDate()%></td>
			</tr>
			<tr>
				<td>End Date:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getEndDate()%></td>
			</tr>


			<tr>
				<td>Degree Class:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getDegreeClass()%></td>
			</tr>
			<tr>
				<td>GPA:</td>
				<td><%=completeRegistrationDetails.getProgramRegistrationBlockVO().getGPA()%></td>
			</tr>

		</table>
		<h2 align="center" style="color: blue;">Learner Info</h2>
		<table>
			<tr>
				<td>First Name:</td>
				<td><%=completeRegistrationDetails.getLearnerPIIVO().getGivenName()%></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><%=completeRegistrationDetails.getLearnerPIIVO().getLastName()%></td>
			</tr>
			<tr>
				<td>SSN:</td>
				<td><%=completeRegistrationDetails.getLearnerPIIVO().getSSN()%></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><%=completeRegistrationDetails.getLearnerPIIVO().getAddress()%></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><%=completeRegistrationDetails.getLearnerPIIVO().getPhone()%></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><%=completeRegistrationDetails.getLearnerPIIVO().getEmail()%></td>
			</tr>
		

			<tr>
				<td>Learner Id:</td>
				<td><%=completeRegistrationDetails.getLearnerPIIVO().getLearnerID()%></td>
			</tr>

		</table>
		<h2 align="center" style="color: blue;">Verification Status</h2>
		<table>
		<tr>
				<td>Tamper Proof Verified:</td>
				<td><%=completeRegistrationDetails.getIsDataVerified()%></td>
			</tr>
			<tr>
				<td>University Digital Signature Verified:</td>
				<td><%=completeRegistrationDetails.getIsDigitalSignatureVerified()%></td>
			</tr>
			
			<tr>
				<td>PII Hash Verified:</td>
				<td><%=completeRegistrationDetails.getIsPIIHashMatched()%></td>
			</tr>
						
			
		</table>
		<h2 align="center" style="color: blue;">Ledger Verification Data</h2>
		<table>
		
			<tr>
				<td>Document Id:</td>
				<td><%=completeRegistrationDetails.getDocumentId()%></td>
			</tr>
			<tr>
				<td>Document Revision Block Address:</td>
				<td><%=completeRegistrationDetails.getRevisionBlockAddress()%></td>
			</tr>
			<tr>
				<td>Digest Block Tip Address:</td>
				<td><%=completeRegistrationDetails.getDigestTipAddress()%></td>
			</tr>
			<tr>
				<td>Digest String:</td>
				<td><%=completeRegistrationDetails.getDigest()%></td>
			</tr>
			
			
		</table>
		<h2 align="center" style="color: blue;">Verification Data</h2>
		<table>
			
			<tr>
				<td>PII Hash:</td>
				<td><%=completeRegistrationDetails.getPIIHashString()%></td>
			</tr>
			<tr>
				<td>Encrypted PII:</td>
				<TD><TEXTAREA NAME="textarea2" ROWS="5" cols="50" readonly><%=completeRegistrationDetails.getEncryptedPIIString()%>  </TEXTAREA></TD>
			</tr>
			<tr>
				<td>University Digital Signature:</td>
				<TD><TEXTAREA NAME="textarea3" ROWS="5" cols="50" readonly><%=completeRegistrationDetails.getDigitalSignatureString()%>  </TEXTAREA></TD>
			</tr>
			
		</table>
		
	</div>
</body>
</html>