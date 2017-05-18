<body>
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
</body>
