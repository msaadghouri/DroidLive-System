<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js"></script>
<title>Live Forensics|Selected Client</title>
<style type="text/css">
div.scroll {
	height: 100px;
	overflow: scroll;
}

body {
	margin: 0;
	padding: 50px 0 0 120px;
}

.style1 {
	color: #900;
	font-weight: bold;
	font-size: 150%;
	text-transform: uppercase;
}

div#header {
	position: relative;
	top: 0;
	left: 0;
	border-bottom-style: groove;
	width: 100%;
	height: 50px;
}

div#left-sidebar {
	position: absolute;
	border-right-style: groove;
	top: 55px;
	left: 10px;
	height: 100%;
	width: 100px;
}

@media screen {
	body>div#header {
		position: absolute;
	}
	body>div#left-sidebar {
		position: absolute;
	}
}

* html body {
	overflow: hidden;
}

* html div#content {
	height: 100%;
	overflow: auto;
}
</style>
</head>
<body>

	<div id="header">
		<h3 align="justify">
			Selected Client :
			<%=session.getAttribute("clientID")%></h3>
	</div>
	<div id="left-sidebar">
		<!-- <form action="DiscoveryServlet" method="post">
			<input type="submit" value="View Data" name="discoveryButton" class="style1">
		</form> -->

		<form action="DiscoveryServlet" method="post">
			<input type="submit" value="Discover" name="discoverFlow" class="style1">
		</form>
	</div>

	<div class="scroll" id="alert" style="height: 200px;">

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

	<div id="content">
		<h3>
			System Information<br>
		</h3>
		<table style="border: 1px none; width: 500px;" border="1">
			<tbody>
				<tr>
					<td>SYSTEM</td>
					<td style="width: 300px;">${disData.system}</td>
				</tr>
				<tr>
					<td>NODE</td>
					<td>${disData.node}</td>
				</tr>
				<tr>
					<td>RELEASE</td>
					<td>${disData.release}</td>
				</tr>
				<tr>
					<td>VERSION</td>
					<td>${disData.version}</td>
				</tr>
				<tr>
					<td>MACHINE</td>
					<td>${disData.machine}</td>
				</tr>
				<tr>
					<td>KERNAL</td>
					<td>${disData.kernel}</td>
				</tr>
				<tr>
					<td>FQDN</td>
					<td>${disData.FQDN}</td>
				</tr>
				<tr>
					<td>INSTALL DATE</td>
					<td>${disData.installDate}</td>
				</tr>
			</tbody>
		</table>
		<h3>
			Client Information<br>
		</h3>
		<table style="border: 1px none; width: 500px;" border="1">
			<tbody>
				<tr>
					<td>CLIENT NAME</td>
					<td style="width: 300px;">${disData.clientName}</td>
				</tr>
				<tr>
					<td>CLIENT VERSION</td>
					<td>${disData.clientVersion}</td>
				</tr>
				<tr>
					<td>CLIENT DESCRIPTION</td>
					<td>${disData.clientDescription}</td>
				</tr>
				<tr>
					<td>BUILD TIME</td>
					<td>${disData.buildTime}</td>
				</tr>
			</tbody>
		</table>
		<h3>
			Interface Information<br>
		</h3>
		<table style="border: 1px none; width: 500px;" border="1">
			<tbody>
				<tr>
					<td>MAC ADDRESS</td>
					<td style="width: 300px;">${disData.macAddress}</td>
				</tr>
				<tr>
					<td>IPv4</td>
					<td>${disData.ipv4}</td>
				</tr>
				<tr>
					<td>IPv6</td>
					<td>${disData.ipv6}</td>
				</tr>
				<tr>
					<td>CREATED DATE</td>
					<td>${disData.createdDate}</td>
				</tr>

			</tbody>
		</table>
	</div>
	<script type="text/javascript">
		$(".use-address").click(function() {
		var $row = $(this).closest("tr"), $tds = $row.find("td:nth-child(4)");

		$.each($tds, function() {
		console.log($(this).text());
		
		$.post('DiscoveryServlet', {
			data : $(this).text()
		}, function(data) {
		var dataa = '${disData}';
		console.log("Dataaa" + dataa);
		location.reload();

		});
		/* $('#blogpost').text($(this).text()); */
		});

		});
	</script>
</body>
</html>