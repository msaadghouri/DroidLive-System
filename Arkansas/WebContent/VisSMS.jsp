<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="Resources/others/jquery/jquery.min.1.12.4.js"></script>
<script type="text/javascript" src="Resources/others/moment/moment.min.2.18.1.js"></script>
<script type="text/javascript"
	src="Resources/others/datepicker/daterangepicker.2.1.25.js"></script>

<link rel="stylesheet" type="text/css"
	href="Resources/bootstrap/dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="Resources/others/datepicker/daterangepicker.css" />
<link href="Resources/css/Menu.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="Resources/jQCloud-master/jqcloud/jqcloud.css" />

<title>Live Forensics|SMS Analysis</title>
</head>
<body>
	<jsp:include page="SideNav.jsp" />
	<h3>DROID LIVE FORENSIC TOOL</h3>
	<span style="font-size: 30px; cursor: pointer" onclick="openNav()">&#9776; </span>

	<div class="row">
		<div class="col-md-4">
			<div class="boxDesign">
				User ID : <i><b><%=session.getAttribute("clientID")%></b></i>
			</div>
		</div>
		<div class="col-md-4">
			<div class="boxDesign">
				<div align="center">
					<i><b>${smsSelectedDate}</b></i>
				</div>
			</div>
		</div>
		<div class="col-md-4">

			<div class="boxDesign">
				<div align="right">
					<form name="smsDateForm" id="smsDateForm" action="Test" method="post">
						<div id="reportrange" class="pull-right" style="padding: 5px 10px;">
							Select Date <input id="smsDatepicked" name="smsDatepicked" type="hidden"
								onchange="datechanged()"> <b class="caret"></b>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="row">

		<div class="col-md-3">
			<div class="boxDesign" style="height: 50px">
				Total Messages : <i><b>${smsCount.totalSMS}</b></i>
			</div>
		</div>
		<div class="col-md-2">
			<div class="boxDesign" style="height: 50px">
				Sent : <i><b>${smsCount.sentSMS}</b></i>
			</div>

		</div>
		<div class="col-md-2">
			<div class="boxDesign" style="height: 50px">
				Received : <i><b>${smsCount.receivedSMS}</b></i>
			</div>
		</div>
		<div class="col-md-2">
			<div class="boxDesign" style="height: 50px">
				Words : <i><b>${smsCount.totalWords}</b></i>
			</div>
		</div>
		<div class="col-md-3">
			<div class="boxDesign" style="height: 50px">
				Avg. Words/Message : <i><b>${smsCount.averageWords}</b></i>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="boxDesign">
				<b>Day-based Analysis</b>
				<div id="dayofWeekChart" style="height: 300px; margin-top: 20px"></div>
			</div>
		</div>
		<div class="col-md-6">

			<div class="boxDesign" style="height: 350px">
				<b>Frequent Text Trading</b>
				<div class="scroll" style="height: 350px">
					<table style="margin-top: 20px">
						<tr>
							<th>Contact Name</th>
							<th>Message Count</th>
						</tr>
						<c:set var="i" value="0" />
						<c:forEach items="${freqTexter}" var="ru" begin="${i}">
							<tr>
								<td>${ru.address}</td>
								<td>${ru.freq}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<!-- <div class="col-md-5">
			<div class="boxDesign">
				<b>Sent/Received Messages</b>
				<div id="pieMessages" style="height: 300px"></div>
			</div>
		</div> -->
		<div class="col-md-3">
			<div class="boxDesign"></div>
		</div>
		<div class="col-md-6">
			<div class="boxDesign">
				<div align="center">
					<b>SMS Extracted keywords</b>
				</div>
				<div id="extractedKeywords" style="height: 300px; margin-top: 20px"></div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="boxDesign"></div>
		</div>
	</div>
	<script src="Resources/bootstrap/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="Resources/others/canvasjs/canvasjs.min.js"></script>
	<script src="Resources/jQCloud-master/jqcloud/jqcloud-1.0.4.js"></script>

	<script>
		function openNav() {
		document.getElementById("mySidenav").style.display = "block";
		}

		function closeNav() {
		document.getElementById("mySidenav").style.display = "none";
		}

		function datechanged() {
		document.getElementById("smsDateForm").submit();
		}
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {

						var cb = function(start, end, label) {
						$('#reportrange input').val(
								start.format('MMMM D, YYYY') + ' - '
										+ end.format('MMMM D, YYYY')).trigger(
								'change');

						};

						var optionSet1 = {
							format : 'MM/DD/YYYY',
							locale : {
								applyLabel : 'Submit',
								cancelLabel : 'Clear',
							}
						};
						$('#reportrange').daterangepicker(optionSet1, cb);

						$('#reportrange').on('cancel.daterangepicker',
								function(ev, picker) {
								});

						});
	</script>
	<script type="text/javascript">
		window.onload = function() {

		var freqDays = '${dowList}';
		var finals = [];
		if (freqDays.length != 0) {
			tabData = JSON.parse(freqDays);
			for (var i = 0; i < tabData.length; i++) {
				finals.push({
					label : tabData[i].weekDay,
					y : parseInt(tabData[i].frequency)
				});
			}
		}
		renderMyChart(dayofWeekChart, finals);

		function renderMyChart(theDIVid, myData) {
		var chart = new CanvasJS.Chart(theDIVid, {
			animationEnabled : true,
			exportEnabled : true,
			data : [ {
				type : "column",
				toolTipContent : "{label}: <strong>{y}</strong>",
				dataPoints : myData
			} ]
		});

		chart.render();
		}

		/* var piePoints = [ {
			y : '${percentage.Received}',
			name : "Received"
		}, {
			y : '${percentage.Sent}',
			name : "Sent"
		} ];
		renderMyPie(pieMessages, piePoints);

		function renderMyPie(theDIVid, myData) {
		var chart = new CanvasJS.Chart(theDIVid, {
			exportFileName : "Pie Chart",
			exportEnabled : true,
			animationEnabled : true,
			legend : {
				verticalAlign : "bottom",
				horizontalAlign : "center"
			},
			data : [ {
				type : "pie",
				showInLegend : true,
				toolTipContent : "{name}: <strong>{y}</strong>",
				dataPoints : piePoints
			} ]
		});
		chart.render();
		} */
		}
	</script>
	<script type="text/javascript">
		var tabData1 = '${extractedWords}';
		var finalz = new Array();
		if (tabData1.length != 0) {
			tabData = JSON.parse(tabData1);
			for (var i = 0; i < tabData.length; i++) {
				finalz.push({
					text : tabData[i].term,
					weight : tabData[i].value
				});
			}
		}

		$('#extractedKeywords').jQCloud(finalz);
	</script>
</body>
</html>