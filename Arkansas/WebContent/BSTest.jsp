<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="//cdn.jsdelivr.net/jquery/1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap/3/css/bootstrap.css" />

<script type="text/javascript"
	src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>

<script
	src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script>

<link href="Resources/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

<title>Insert title here</title>

<style>
#network {
	height: 100%;
	width: 100%;
	position: absolute;
	padding: 10px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
}
</style>
</head>
<body>

	<div id="network"></div>
	<script src="Resources/bootstrap/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="Resources/others/canvasjs/canvasjs.min.js"></script>


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
			minZoom: 0.5,
			maxZoom: 5,
			ready : function() {
			window.network = this;
			}
		});

		});
	</script>

</body>
</html>