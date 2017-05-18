<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="//cdn.jsdelivr.net/jquery/1/jquery.min.js"></script>
<script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap/3/css/bootstrap.css" />
<script type="text/javascript"
	src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="Resources/others/datepicker/daterangepicker.css" />

<link href="Resources/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="Resources/css/Menu.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="Resources/jQCloud-master/jqcloud/jqcloud.css" />

<title>Insert title here</title>


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
					<i><b>${bhSelectedDate}</b></i>
				</div>
			</div>
		</div>
		<div class="col-md-4">

			<div class="boxDesign">
				<div align="right">
					<form name="bhDateForm" id="bhDateForm" action="Test" method="post">
						<div id="reportrange" class="pull-right" style="padding: 5px 10px;">
							Select Date <input id="bhDatePicked" name="bhDatePicked" type="hidden"
								onchange="datechanged()"> <b class="caret"></b>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	<div class="row">
		<div class="col-md-6">
			<div class="boxDesign">
				<b>Days of Week</b>
				<div id="chartContainer" style="height: 350px; margin-top: 20px"></div>

			</div>
		</div>
		<div class="col-md-6">
			<div class="boxDesign">
				<b>Monthly Usage of Browser</b>
				<div id="linechartContainer" style="height: 350px; margin-top: 20px"></div>

			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-3">
			<div class="boxDesign"></div>
		</div>
		<div class="col-md-6">
			<div class="boxDesign">
				<div align="center">
					<b>Searched keywords</b>
				</div>
				<div id="my_favorite_latin_words" style="height: 300px; margin-top: 20px"></div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="boxDesign"></div>
		</div>
	</div>


	<div class="row">
		<div class="col-md-6">
			<div class="boxDesign" style="height: 350px">
				<b>Table of Domains</b>

				<table style="margin-top: 20px">
					<tr>
						<th>Domain Name</th>
						<th>Frequency</th>
					</tr>
					<tr>
						<td>www.google.com</td>
						<td>50</td>
					</tr>
					<tr>
						<td>www.facebook.com</td>
						<td>70</td>
					</tr>
				</table>

			</div>
		</div>

		<div class="col-md-6">
			<div class="boxDesign" style="height: 350px">
				<b>Table of Last 10 url visits</b>

				<table style="margin-top: 20px">
					<tr>
						<th>URL Name</th>
						<th>Frequency</th>
					</tr>
					<tr>
						<td>https://www.google.com/1234567890</td>
						<td>5</td>
					</tr>
					<tr>
						<td>https://www.google.com/1234567890</td>
						<td>5</td>
					</tr>
					<tr>
						<td>https://www.google.com/1234567890</td>
						<td>5</td>
					</tr>
					<tr>
						<td>https://www.google.com/1234567890</td>
						<td>5</td>
					</tr>
				</table>

			</div>
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
		document.getElementById("bhDateForm").submit();
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
		var tabData1 = '${searchedTerms}';
		var finalz = new Array();
		if (tabData1.length != 0) {
			tabData = JSON.parse(tabData1);
			for (var i = 0; i < tabData.length; i++) {
				finalz.push({
					text : tabData[i].term,
					weight : tabData[i].freq
				});
			}
		}

		$('#my_favorite_latin_words').jQCloud(finalz);
	</script>

	<script type="text/javascript">
		window.onload = function() {
		var freqDays = '${bhDayAnalysis}';
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
		renderMyChart(chartContainer, finals);

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

		var yearLine = [ {
			x : new Date(2012, 00, 1),
			y : 450
		}, {
			x : new Date(2012, 01, 1),
			y : 414
		}, {
			x : new Date(2012, 02, 1),
			y : 520,
		}, {
			x : new Date(2012, 03, 1),
			y : 460
		}, {
			x : new Date(2012, 04, 1),
			y : 450
		}, {
			x : new Date(2012, 05, 1),
			y : 500
		}, {
			x : new Date(2012, 06, 1),
			y : 480
		}, {
			x : new Date(2012, 07, 1),
			y : 480
		}, {
			x : new Date(2012, 08, 1),
			y : 410,
		}, {
			x : new Date(2012, 09, 1),
			y : 500
		}, {
			x : new Date(2012, 10, 1),
			y : 480
		}, {
			x : new Date(2012, 11, 1),
			y : 510
		} ];

		renderMyYear(linechartContainer, yearLine);

		function renderMyYear(theDIVid, myData) {
		var chart = new CanvasJS.Chart(theDIVid, {
			animationEnabled : true,
			axisX : {
				valueFormatString : "MMM",
				interval : 1,
				intervalType : "month"
			},
			axisY : {
				includeZero : false

			},
			data : [ {
				type : "area",
				dataPoints : myData
			} ]
		});

		chart.render();
		}
		}
	</script>


</body>
</html>