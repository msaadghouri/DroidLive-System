<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<!-- <link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />
 -->

<link rel="stylesheet" type="text/css"
	href="Resources/others/datepicker/daterangepicker.css" />

<script
	src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script>

<link href="Resources/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="Resources/css/Menu.css" rel="stylesheet">

<title>Insert title here</title>

<style>
#network {
	height: 500px;
	width: 100%;
	position: absolute;
	padding: 10px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
}
</style>
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
					<i><b>${selectedDate}</b></i>
				</div>
			</div>
		</div>
		<div class="col-md-4">

			<div class="boxDesign">
				<div align="right">
					<form name="dateForm" id="dateForm" action="Test" method="post">
						<div id="reportrange" class="pull-right" style="padding: 5px 10px;">
							Select Date <input id="datepicked" name="datepicked" type="hidden"
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
				Total Call Count : <i><b>${count.totalCalls}</b></i>
			</div>
		</div>
		<div class="col-md-3">
			<div class="boxDesign" style="height: 50px">
				Incoming Calls : <i><b>${count.incomingCalls}</b></i>
			</div>

		</div>
		<div class="col-md-3">
			<div class="boxDesign" style="height: 50px">
				Outgoing Calls : <i><b>${count.outgoingCalls}</b></i>
			</div>
		</div>
		<div class="col-md-3">
			<div class="boxDesign" style="height: 50px">
				Missed Calls : <i><b>${count.missedCalls}</b></i>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-6">
			<div class="boxDesign">
				<b>Minutes Spent on Calls</b>
				<div id="piechartContainer" style="height: 300px"></div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="boxDesign">
				<b>Day of Week Analysis</b>
				<div id="chartContainer" style="height: 350px; margin-top: 20px"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3">
			<div class="boxDesign"></div>
		</div>
		<div class="col-md-6">
			<div class="boxDesign">

				<b>Monthly Analysis</b><small>-( Click on any segment )</small>
				<div id="interestContainer" style="height: 350px; margin-top: 20px"></div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="boxDesign"></div>
		</div>
	</div>


	<div class="row">
		<div class="col-md-6">
			<div class="boxDesign">
				<b>Calls of Interest-Based on Call Duration</b>
				<div id="theDIVid" style="height: 350px; margin-top: 20px"></div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="boxDesign">
				<b>Calls of Interest-Based on Call Frequency</b>
				<div id="theDIVid2" style="height: 350px; margin-top: 20px"></div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<div id="network">
				<b>Call Log Network Graph</b> <a href="http://localhost:8080/Arkansas/BSTest.jsp"
					target="_blank">Enlarge</a>
			</div>
		</div>
	</div>
	<script src="Resources/bootstrap/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="Resources/others/canvasjs/canvasjs.min.js"></script>

	<script>
		function openNav() {
		document.getElementById("mySidenav").style.display = "block";
		}

		function closeNav() {
		document.getElementById("mySidenav").style.display = "none";
		}

		function datechanged() {
		console.log("saad");
		document.getElementById("dateForm").submit();
		}
	</script>

	<script type="text/javascript">
		window.onload = function() {

		var freqDays = '${daysofWeeks}';
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

		var tabData1 = '${callofInterest}';
		var durData = [];
		var freqData = [];
		if (tabData1.length != 0) {
			tabData = JSON.parse(tabData1);
			for (var i = 0; i < tabData.length; i++) {
				var firstdate = tabData[i].startDate;
				var res = firstdate.split('-');
				durData.push({
					x : new Date(res[0], res[1] - 1, res[2]),
					y : parseInt(tabData[i].totalDur)
				});
				freqData.push({
					x : new Date(res[0], res[1] - 1, res[2]),
					y : parseInt(tabData[i].frequency)
				});
			}
		}

		var piePoints = [ {
			y : '${pieData.inDur}',
			name : "Incoming"
		}, {
			y : '${pieData.outDur}',
			name : "Outgoing"
		} ];

		var sessDur = '${callofID}';
		var duration1 = [];
		var frequency1 = [];
		if (sessDur.length != 0) {
			tabData = JSON.parse(sessDur);
			for (var i = 0; i < tabData.length; i++) {
				duration1.push({
					x : i,
					y : parseInt(tabData[i].totalDur),
					label : tabData[i].cNumber
				});
				frequency1.push({
					x : i,
					y : parseInt(tabData[i].frequency),
					label : tabData[i].cNumber
				});
			}
		}

		var sessFreq = '${callofIF}';
		var duration2 = [];
		var frequency2 = [];
		if (sessFreq.length != 0) {
			tabData = JSON.parse(sessFreq);
			for (var i = 0; i < tabData.length; i++) {
				duration2.push({
					x : i,
					y : parseInt(tabData[i].totalDur),
					label : tabData[i].cNumber
				});
				frequency2.push({
					x : i,
					y : parseInt(tabData[i].frequency),
					label : tabData[i].cNumber
				});
			}
		}

		renderMyChart(chartContainer, finals);
		renderMyPie(piechartContainer, piePoints);
		renderMyInterest(interestContainer, freqData, durData);
		renderMyInt(theDIVid, duration1, frequency1);
		renderMyInt2(theDIVid2, duration2, frequency2);

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
				dataPoints : myData
			} ]
		});
		chart.render();
		}

		function renderMyInterest(theDIVid, myFreq, myDur) {
		var chart = new CanvasJS.Chart(theDIVid, {
			exportEnabled : true,
			animationEnabled : true,
			axisX : {
				interval : 1,
				intervalType : "month",
			},
			/* axisY : {
				title : "Frequency | Duration",
			}, */
			data : [ {
				click : function(e) {
				alert(e.dataPoint.x);
				},
				type : "column",
				showInLegend : true,
				legendText : "Call Frequency",
				dataPoints : myFreq
			}, {
				click : function(e) {
				push(e.dataPoint.x);
				},
				type : "area",
				showInLegend : true,
				legendText : "Call Duration",
				dataPoints : myDur
			} ],
			legend : {
				cursor : "pointer",
				itemclick : function(e) {
				if (typeof (e.dataSeries.visible) === "undefined"
						|| e.dataSeries.visible) {
					e.dataSeries.visible = false;
				} else {
					e.dataSeries.visible = true;
				}
				chart.render();
				}
			}
		});

		chart.render();
		}
		window.alert = function(title, message) {
		$.post('Test', {
			data : title
		}, function(data) {
		location.reload();

		});
		}
		window.push = function(title, message) {
		$.post('Test', {
			data1 : title
		}, function(data1) {
		location.reload();

		});
		}
		function renderMyInt(theDIVid, myduration1, myfrequency1) {
		var chart = new CanvasJS.Chart(theDIVid, {
			axisY2 : {
				title : "Call Frequency"
			},
			animationEnabled : true,
			axisY : {
				title : "Call Duration"
			},
			axisX : {
				labelFontSize : 12
			},
			legend : {
				verticalAlign : "bottom"
			},
			data : [

			{
				type : "bar",
				showInLegend : true,
				legendText : "Call Duration",
				dataPoints : myduration1
			}, {
				type : "bar",
				axisYType : "secondary",
				showInLegend : true,
				legendText : "Call Frequency",
				dataPoints : myfrequency1
			}

			],
			legend : {
				cursor : "pointer",
				itemclick : function(e) {
				if (typeof (e.dataSeries.visible) === "undefined"
						|| e.dataSeries.visible) {
					e.dataSeries.visible = false;
				} else {
					e.dataSeries.visible = true;
				}
				chart.render();
				}
			}
		});

		chart.render();
		}
		function renderMyInt2(theDIVid2, myduration2, myfrequency2) {
		var chart = new CanvasJS.Chart(theDIVid2, {
			axisY2 : {
				title : "Call Frequency"
			},
			animationEnabled : true,
			axisY : {
				title : "Call Duration"
			},
			axisX : {
				labelFontSize : 12
			},
			legend : {
				verticalAlign : "bottom"
			},
			data : [

			{
				type : "bar",
				showInLegend : true,
				legendText : "Call Duration",
				dataPoints : myduration2
			}, {
				type : "bar",
				axisYType : "secondary",
				showInLegend : true,
				legendText : "Call Frequency",
				dataPoints : myfrequency2
			}

			],
			legend : {
				cursor : "pointer",
				itemclick : function(e) {
				if (typeof (e.dataSeries.visible) === "undefined"
						|| e.dataSeries.visible) {
					e.dataSeries.visible = false;
				} else {
					e.dataSeries.visible = true;
				}
				chart.render();
				}
			}
		});

		chart.render();
		}
		}
	</script>
	<script type="text/javascript">
		$(function() {

		$('#network').cytoscape({
			layout : {
				name : 'cose',
				padding : 10,
				randomize : true
			},

			style : cytoscape.stylesheet().selector('node').css({

				'content' : 'data(id)',
				'text-valign' : 'center',
				'text-outline-width' : 2,
				'text-outline-color' : '#0000FF',
				'background-color' : '#0000FF',
				'color' : '#fff'
			})

			.selector('edge').css({
				'curve-style' : 'bezier',
				'content' : 'data(strength)',
				'opacity' : 0.666,
				'target-arrow-shape' : 'triangle',
				'source-arrow-shape' : 'circle',
				'line-color' : '#FF3232',
				'source-arrow-color' : '#FF3232',
				'target-arrow-color' : '#FF3232'
			}),
			elements : {
				nodes : ${nodes},
				edges : ${edges}
			},
			minZoom: 0.2,
			maxZoom: 2,
			ready : function() {
			window.network = this;
			}
		});

		});
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

</body>
</html>