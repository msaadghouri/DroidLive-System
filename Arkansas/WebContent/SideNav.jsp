<div id="mySidenav" class="sidenava">
	<a href="<%=request.getContextPath()%>/LoginServlet" onclick="closeNav()">User
		Table</a>
	<p align="center">Actions</p>
	<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a> <a
		href="<%=request.getContextPath()%>/Test?flowname=Discover" onclick="closeNav()">Client
		Data</a> <a href="<%=request.getContextPath()%>/Test?flowname=BrowserHistory"
		onclick="closeNav()">Browser History</a> <a
		href="<%=request.getContextPath()%>/Test?flowname=CallLogs" onclick="closeNav()">Call
		Logs</a> <a href="<%=request.getContextPath()%>/Test?flowname=DeviceContacts"
		onclick="closeNav()">Device Contacts</a> <a
		href="<%=request.getContextPath()%>/Test?flowname=ShortMessage"
		onclick="closeNav()">SMS</a>

	<p align="center">Visual</p>
	<a href="VisBrowser.jsp" onclick="closeNav()">View History</a>
	<a href="VisCallLogs.jsp" onclick="closeNav()">View Call Logs</a>

</div>