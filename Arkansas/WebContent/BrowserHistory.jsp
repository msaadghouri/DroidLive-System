<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<body>
	<h3>
		Accounts Information<br>
	</h3>
	<table style="border: 1px none; width: 600px;" border="1">
		<thead>
			<tr>
				<th>Account Type</th>
				<th>Account Name</th>
			</tr>
		</thead>

		<tbody>
		<tbody>
			<c:set var="i" value="0" />
			<c:forEach items="${hisData}" var="s12" begin="${i}">
				<tr>
					<td>${s12.accType}</td>
					<td style="width: 300px;">${s12.accName}</td>
				</tr>
			</c:forEach>
		</tbody>

		</tbody>
	</table>
</body>
