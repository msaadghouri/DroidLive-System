<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<title>Insert title here</title>
</head>
<body>

	<table id="myTable">
		<tr>
			<!--When a header is clicked, run the sortTable function, with a parameter, 0 for sorting by names, 1 for sorting by country:-->
			<th onclick="sortTable(0)">Name <i class="fa fa-fw fa-sort"></i></th>
			<th onclick="sortTable(1)">Country<i class="fa fa-fw fa-sort"></i></th>
		</tr>
		<tr>
			<td>Berglunds snabbkop</td>
			<td>10</td>
		</tr>
		<tr>
			<td>North/South</td>
			<td>2</td>
		</tr>
		<tr>
			<td>Alfreds Futterkiste</td>
			<td>4</td>
		</tr>
		<tr>
			<td>Koniglich Essen</td>
			<td>5</td>
		</tr>
		<tr>
			<td>Magazzini Alimentari Riuniti</td>
			<td>9</td>
		</tr>
		<tr>
			<td>Paris specialites</td>
			<td>7</td>
		</tr>
		<tr>
			<td>Island Trading</td>
			<td>11</td>
		</tr>
		<tr>
			<td>Laughing Bacchus Winecellars</td>
			<td>3</td>
		</tr>
	</table>

	<script>
		function sortTable(n) {
		var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
		table = document.getElementById("myTable");
		switching = true;
		//Set the sorting direction to ascending:
		dir = "asc";
		/*Make a loop that will continue until
		no switching has been done:*/
		while (switching) {
			//start by saying: no switching is done:
			switching = false;
			rows = table.getElementsByTagName("TR");
			/*Loop through all table rows (except the
			first, which contains table headers):*/
			for (i = 1; i < (rows.length - 1); i++) {
				//start by saying there should be no switching:
				shouldSwitch = false;
				/*Get the two elements you want to compare,
				one from current row and one from the next:*/
				x = rows[i].getElementsByTagName("TD")[n];
				y = rows[i + 1].getElementsByTagName("TD")[n];
				/*check if the two rows should switch place,
				based on the direction, asc or desc:*/
				if (dir == "asc") {
					if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
						//if so, mark as a switch and break the loop:
						shouldSwitch = true;
						break;
					}
				} else if (dir == "desc") {
					if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
						//if so, mark as a switch and break the loop:
						shouldSwitch = true;
						break;
					}
				}
			}
			if (shouldSwitch) {
				/*If a switch has been marked, make the switch
				and mark that a switch has been done:*/
				rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
				switching = true;
				//Each time a switch is done, increase this count by 1:
				switchcount++;
			} else {
				/*If no switching has been done AND the direction is "asc",
				set the direction to "desc" and run the while loop again.*/
				if (switchcount == 0 && dir == "asc") {
					dir = "desc";
					switching = true;
				}
			}
		}
		}
	</script>

</body>
</html>
