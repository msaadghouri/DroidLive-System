<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="Resources/css/Menu.css" rel="stylesheet">
<link href="Resources/css/Basic.css" rel="stylesheet">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js"></script>
<title>Live Forensics|Enrolled Clients</title>
</head>
<body>
	<h3>DROID LIVE FORENSIC TOOL</h3>
	<h4>Enrolled Clients</h4>
	<table>
		<thead>
			<tr>
				<th>Client ID</th>
				<th>User Name</th>
				<th>Full Name</th>
				<th>First LogIn</th>
				<!-- <th>Last LogIn</th> -->
				<th>Home Directory</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:set var="i" value="0" />
			<c:forEach items="${userData}" var="user" begin="${i}">
				<tr>
					<td>${user.userRefId}</td>
					<td>${user.userName}</td>
					<td>${user.fullName}</td>
					<td>${user.firstLogOn}</td>
					<%-- <td>${user.lastLogOn}</td> --%>
					<td>${user.homeDir}</td>
					<td>
						<form id="hello">
							<input type="hidden" name="uname" value="" id="myField" />
							<button type="button" onclick="getTaskId('GET','${user.userRefId}')">View</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
<script type="text/javascript">
	function getTaskId(var1, var2) {
	document.getElementById("hello").action = "/Arkansas/DiscoveryServlet";
	document.getElementById("hello").method = var1;
	document.getElementById("myField").value = var2;
	document.getElementById("hello").submit();
	}
</script>
</html>