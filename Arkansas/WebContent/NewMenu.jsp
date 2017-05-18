<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="Resources/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="Resources/css/Menu.css" rel="stylesheet">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js"></script>

<title>Live Forensics|Selected Client</title>
</head>
<body>
	<jsp:include page="SideNav.jsp" />
	<h3>DROID LIVE FORENSIC TOOL</h3>
	<span style="font-size: 30px; cursor: pointer" onclick="openNav()">&#9776; </span>
	
	<h4>
		User ID :
		<%=session.getAttribute("clientID")%></h4>


	<div class="scroll">
		<table>
			<thead>
				<tr>
					<th>Flow Name</th>
					<th>Date Created</th>
					<th>Status</th>
					<th style="display: none;"></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="i" value="0" />

				<c:forEach items="${allRequests}" var="s12" begin="${i}">
					<tr>
						<td>${s12.flowNmae}</td>
						<td>${s12.flowDate}</td>
						<td>${s12.status}</td>
						<td style="display: none;">${s12.transactionID}</td>
						<td>
							<button type="button" class="use-address">View</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<hr />
	<%
		if (session.getAttribute("disData") != null) {
	%>
	<jsp:include page="Discovery.jsp" />
	<%
		}
	%>
	<%
		if (session.getAttribute("hisData") != null) {
	%>
	<jsp:include page="BrowserHistory.jsp" />
	<%
		}
	%>
	<script>
		function openNav() {
		document.getElementById("mySidenav").style.display = "block";
		}

		function closeNav() {
		document.getElementById("mySidenav").style.display = "none";
		}
	</script>
	<script type="text/javascript">
		$(".use-address").click(function() {
		var $row = $(this).closest("tr"), $tds = $row.find("td:nth-child(4)");

		var col1 = $row.find("td:nth-child(1)").text();
		var col2 = $row.find("td:nth-child(4)").text();
		var dat = col1 + " " + col2;

		console.log("Dataaa" + dat);
		$.each($tds, function() {
		$.post('DiscoveryServlet', {
			data : dat
		}, function(data) {
		var dataa = '${disData}';
		console.log("Dataaa" + dataa);
		location.reload();

		});
		});

		});
	</script>
</body>
</html>