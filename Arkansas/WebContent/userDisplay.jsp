<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%
	if (session.getAttribute("userName") == null || session.getAttribute("userName") == "") {
		response.sendRedirect("Index.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="Resources/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="Resources/css/Menu.css" rel="stylesheet">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js"></script>

<title>Live Forensics|Enrolled Clients</title>
</head>
<body>
	<h3>DROID LIVE FORENSIC TOOL</h3>
	<h4 style="text-align: center;">Enrolled Clients</h4>

	Logged In As:
	<b>${userName}</b>
	<br> Hunt:
	<a href="<%=request.getContextPath()%>/Test?flowname=HuntDiscover"
		onclick="myFunction()">All Device info</a>
	<br>
	<a href="logout.jsp"> Log out</a>

	<table>
		<thead>
			<tr>
				<th>Client ID</th>
				<th>User Name</th>
				<th>Full Name</th>
				<th>First LogIn</th>
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



	<div id="snackbar">Hunt Started</div>
</body>
<script type="text/javascript">
	function getTaskId(var1, var2) {
	document.getElementById("hello").action = "/Arkansas/DiscoveryServlet";
	document.getElementById("hello").method = var1;
	document.getElementById("myField").value = var2;
	document.getElementById("hello").submit();
	}
</script>
<script>
	function myFunction() {
	var x = document.getElementById("snackbar")
	x.className = "show";
	setTimeout(function() {
	x.className = x.className.replace("show", "");
	}, 3000);
	}
</script>
</html>