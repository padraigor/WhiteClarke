<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- Includes JSP standard tag library, JSTL --> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Available Cars</title>
	</head>
	<body>
		<H1>Available Cars</H1>
		
		<p>	make
			<select id = "makes" onchange = "makeChanged()">
				<c:forEach var="make" items="${makes}">
        			<option value=<c:out value="${make.id}" /> <c:if test="${make.id==lastSelectedMakeId}">	selected</c:if>> 
        				<c:out value="${make.name}" />				
        			</option>
        			
      			</c:forEach>
			</select>
		</p>
		
		<p>	model
			<select id = "models" onchange="modelChanged()" >
				<c:forEach var="model" items="${models}" >
        			<option value=<c:out value="${model.id}" /> <c:if test="${model.id==lastSelectedModelId}"> selected</c:if>> 
        				<c:out value="${model.name}" />
        			</option>
      			</c:forEach>
			</select>
		</p> 
	
		<p id = "cars">
			<table border = "1">
				<tr>		
					<td>Registration</td>
					<td>Make</td>
					<td>Model</td>
					<td>Colour</td>
					<td>Year</td>
					<td>Engine Size CC</td>
				</tr>
				<c:forEach var="vehicle" items="${vehicles}">
					<tr>
						<td>	<c:out value="${vehicle.reg}" />	</td>
						<td>	<c:out value="${vehicle.model.make.name}" />	</td>
						<td>	<c:out value="${vehicle.model.name}" />	</td>
						<td>	<c:out value="${vehicle.colour}" />	</td>
						<td>	<c:out value="${vehicle.year}" />	</td>
						<td>	<c:out value="${vehicle.engSizeCC}" />	</td>
					</tr>	
	      		</c:forEach>
			</table>	
		</p>
		
		<script>
			document.getElementById("cars").innerHTML = "List of Cars";
			
			function makeChanged(){
				
				var e = document.getElementById("makes");
				var selectedMakeId = e.options[e.selectedIndex].value;
				
				refreshPage(selectedMakeId, -1);
			}
			
			function modelChanged(){
				var e = document.getElementById("makes");
				var selectedMakeId = e.options[e.selectedIndex].value;
				
				var f = document.getElementById("models");
				var selectedModelId = f.options[f.selectedIndex].value;
				
				refreshPage(selectedMakeId, selectedModelId);
			}
			
			function refreshPage(makeID, modelID)
			{	window.location.replace("availableCars?selectedMakeId=" + makeID + 
													"&selectedModelId=" + modelID);
			}
		</script>
	</body>
</html>