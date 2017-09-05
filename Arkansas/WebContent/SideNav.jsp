<div id="mySidenav" class="sidenava">
	<a href="<%=request.getContextPath()%>/LoginServlet" onclick="closeNav()">User
		Table</a>
	<p align="center">Actions</p>
	<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a> <a
		href="<%=request.getContextPath()%>/Test?flowname=Discover" onclick="closeNav()">Device
		Info</a> <a href="<%=request.getContextPath()%>/Test?flowname=BrowserHistory"
		onclick="closeNav()">Browser History</a> <a
		href="<%=request.getContextPath()%>/Test?flowname=CallLogs" onclick="closeNav()">Call
		Logs</a> <a href="<%=request.getContextPath()%>/Test?flowname=DeviceContacts"
		onclick="closeNav()">Device Contacts</a> <a
		href="<%=request.getContextPath()%>/Test?flowname=ShortMessage"
		onclick="closeNav()">SMS</a><a
		href="<%=request.getContextPath()%>/Test?flowname=GetAccounts" onclick="closeNav()">Accounts</a>
	<a href="<%=request.getContextPath()%>/Test?flowname=UsageStats"
		onclick="closeNav()">Usage Stats</a>

	<!-- <a onclick="myFunction()">Usage Stats</a> -->

	<p align="center">Visual</p>
	<a href="VisBrowser.jsp" onclick="closeNav()">View History</a> <a
		href="VisCallLogs.jsp" onclick="closeNav()">View Call Logs</a> <a href="VisSMS.jsp"
		onclick="closeNav()">View SMS</a>

	<p style="position: absolute; right: 0; bottom: 0;">
		<a href="logout.jsp" onclick="closeNav()">Log Out</a>
	</p>


</div>