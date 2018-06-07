<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ACQUA</title>
<style type="text/css">
	.rightborder{
		border-right: 1px solid gray;
	}
</style>
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="container">
		<!-- <div class="row text-center"><div class="col-md-5 text-center"><h3>Current Inventory Status</h3></div></div> -->
		<div class="row">
			<div class="col-md-6 margin-bottom-inputs">
				<h3 class="">Fish Stock Status</h3>
				<table class="table table-hover">
					<thead class="">
						<tr>
							<th class="">Species</th>
							<!-- <th class="">Batch</th> -->
							<th class="">In Stock</th>
						</tr>
					</thead>
					<c:forEach var="fstockobj" items="${fishStock}">
						<tr class="">
							<td class="">${fstockobj.fishCategory}</td>
							<%-- <td class="">${fstockobj.batchNo}</td> --%>
							<td class="">${fstockobj.quantity}</td>
						</div>
					</c:forEach>
				</table>
			</div>
			<div class="col-md-6 margin-bottom-inputs">
				<h3 class="">Plant Stock Status</h3>
				<table class="table table-hover">
					<thead class="">
						<tr>
							<th class="">Plant Name</th>
							<!-- <th class="">Batch</th> -->
							<th class="">In Stock</th>
						</tr>
					</thead>
					<c:forEach var="pstockobj" items="${plantStock}">
						<tr class="">
							<td class="">${pstockobj.plantName}</td>
							<%-- <td class="">${pstockobj.batchNo}</td> --%>
							<td class="">${pstockobj.quantity}</td>
						</div>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-6 margin-bottom-inputs">
				<h3>Raw Material Stock Status</h3>
				<table class="table table-hover">
					<thead class="">
						<tr>
							<th class="">Item Name</th>
							<th class="">In Stock</th>
						</tr>
					</thead>
					<c:forEach var="stockobj" items="${rmStock}">
						<tr class="">
							<td class="">${stockobj.RMCategory}</td>
							<td class="">${stockobj.quantity}</td>
						</div>
					</c:forEach>
				</table>
			</div>
			<div class="col-md-6 margin-bottom-inputs">
				<h3>Birds Stock Status</h3>
				<table class="table table-hover">
					<thead class="">
						<tr>
							<th class="">Item Name</th>
							<th class="">In Stock</th>
						</tr>
					</thead>
					<c:forEach var="stockobj" items="${birdStock}">
						<tr class="">
							<td class="">${stockobj.birdName}</td>
							<td class="">${stockobj.quantity}</td>
						</div>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>