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
		<%@page import="com.digitalcredentials.vo.CourseVerificationVO"%>
		<%@page import="com.digitalcredentials.vo.CourseVO"%>
		<%@page import="java.util.ArrayList"%>
		<%--Importing all the dependent classes--%>
		<%@page import="com.digitalcredentials.vo.UserVO"%>
		<%@page import="java.util.ArrayList"%>
		<%--Importing all the dependent classes--%>
		<%@page import="com.digitalcredentials.vo.GrantedCredentialVO"%>
		<%@page import="java.util.Iterator"%>
		<%
			CourseVerificationVO courseVerificationVO = (CourseVerificationVO) (request.getAttribute("courseDetails"));
		%>


		<h1 align="center" style="color: blue;">Program Transcripts</h1>
		<br>


		<h2 align="center" style="color: blue;">Program Details</h2>
		<table>

			<tr>
				<td>University Name:</td>
				<td><%=courseVerificationVO.getUniversityName()%></td>
			</tr>
			<tr>
				<td>University EIN:</td>
				<td><%=courseVerificationVO.getUniversityEIN()%></td>
			</tr>
			<tr>
				<td>Career:</td>
				<td><%=courseVerificationVO.getCareer()%></td>
			</tr>
			<tr>
				<td>Subject:</td>
				<td><%=courseVerificationVO.getSubject()%></td>
			</tr>
			<tr>
				<td>Concentration:</td>
				<td><%=courseVerificationVO.getConcentration()%></td>
			</tr>
		</table>
		<h2 align="center" style="color: blue;">Transcripts</h2>

		<h2 align="center" style="color: blue;">Learner Info</h2>
		<table>
			<tr>
				<td>First Name:</td>
				<td><%=courseVerificationVO.getLearnerPIIVO().getGivenName()%></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><%=courseVerificationVO.getLearnerPIIVO().getLastName()%></td>
			</tr>
			<tr>
				<td>SSN:</td>
				<td><%=courseVerificationVO.getLearnerPIIVO().getSSN()%></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><%=courseVerificationVO.getLearnerPIIVO().getAddress()%></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><%=courseVerificationVO.getLearnerPIIVO().getPhone()%></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><%=courseVerificationVO.getLearnerPIIVO().getEmail()%></td>
			</tr>
		

			<tr>
					<td>Learner University ID:</td>
				<td><%=courseVerificationVO.getLearnerPIIVO().getLearnerID()%></td>
			</tr>

		</table>
		<table cellspacing="4" cellpadding="4" border="1" bgcolor="#FCF3CF">

			<tr bgcolor="#D6EAF8">
				<th>Course</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Grade</th>
				<th>Cumulative GPA</th>
			</tr>
			<%
				// Iterating through subjectList
				if (courseVerificationVO.getCourseList() != null) // Null check for the object
				{
					Iterator<CourseVO> courseIterator = courseVerificationVO.getCourseList().iterator();
					while (courseIterator.hasNext()) // iterate through all the data until the last record
					{
						CourseVO resultVO = courseIterator.next(); //assign individual employee record to the employee class object
			%>
			<tr>
				<td><%=resultVO.getCourse()%></td>

				<td><%=resultVO.getStartDate()%></td>
				<td><%=resultVO.getEndDate()%></td>
				<td><%=resultVO.getCourseGrade()%></td>
				<td><%=resultVO.getCumulativeGPA()%></td>


			</tr>

			<%
				}
				}
			%>

		</table>
		<h2 align="center" style="color: blue;">Verification Status</h2>
		<table>
			<tr>
				<td>Tamper Proof Verified:</td>
				<td><%=courseVerificationVO.getIsDataVerified()%></td>
			</tr>
			<tr>
				<td>University Digital Signature Verified:</td>
				<td><%=courseVerificationVO.getIsDigitalSignatureVerified()%></td>
			</tr>

			<tr>
				<td>PII Hash Verified:</td>
				<td><%=courseVerificationVO.getIsPIIHashMatched()%></td>
			</tr>



		</table>
		<h2 align="center" style="color: blue;">Ledger Verification Data</h2>
		<table>

			<tr>
				<td>Document Id:</td>
				<td><%=courseVerificationVO.getDocumentId()%></td>
			</tr>
			<tr>
				<td>Document Revision Block Address:</td>
				<td><%=courseVerificationVO.getRevisionBlockAddress()%></td>
			</tr>
			<tr>
				<td>Digest Block Tip Address:</td>
				<td><%=courseVerificationVO.getDigestTipAddressString()%></td>
			</tr>
			<tr>
				<td>Digest String:</td>
				<td><%=courseVerificationVO.getDigestString()%></td>
			</tr>


		</table>
		<h2 align="center" style="color: blue;">Verification Data</h2>
		<table>

			<tr>
				<td>PII Hash:</td>
				<td><%=courseVerificationVO.getPIIHashString()%></td>
			</tr>
			<tr>
				<td>Encrypted PII:</td>
				<TD><TEXTAREA NAME="textarea2" ROWS="5" cols="50" readonly><%=courseVerificationVO.getOrganizationEncryptedPII()%>  </TEXTAREA></TD>
			</tr>
			<tr>
				<td>University Digital Signature:</td>
				<TD><TEXTAREA NAME="textarea3" ROWS="5" cols="50" readonly><%=courseVerificationVO.getDigitalSignatureString()%>  </TEXTAREA></TD>
			</tr>

		</table>

	</div>
</body>
</html>