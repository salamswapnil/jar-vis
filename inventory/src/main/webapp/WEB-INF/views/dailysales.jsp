<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Daily Sales</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/JSUtils.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-table.css" />
</head>
<body>
	<%@include file="header.jsp" %>
	<div class="container">
		<form action="" method="post" id="form1">
		<div class="row margin-bottom-inputs margin-top-inputs">
			<div class="col-md-1">
				<span>Start Date</span>
			</div>
			<div class="col-md-2">
				<div class="input-append date" id="dp1" data-date="12-02-2012" data-date-format="dd-MM-yyyy">
					<input type="text" class="form-control" required="required" id="startdate" name="startdate" placeholder="yyyy-mm-dd"/>
					<span class="add-on"><i class="icon-th"></i></span>
				</div>
			</div>
			<div class="col-md-1">
				<span>Select Product</span>
			</div>
			<div class="col-md-3">
				<select class="form-control" id="ddlProduct" multiple="multiple" name="products" required>
					<option value="" class="font-weight-bold">Select Product</option>
					<option value="Fish">Fish</option>
					<option value="Bird">Bird</option>
					<option value="Plant">Plant</option>
					<option value="Raw Material">Raw Material</option>
				</select>
			</div>
			<div class="col-md-2">
				<input type="submit" id="btnReport" value="Generate Report" class="formBtn btn btn-primary"/>
			</div>
		</div>
		</form>
		<hr />
		<div class="row margin-bottom-inputs">
			<div class="col-md-1"></div>
			<div class="col-md-4">
				<span class="font-weight-bold">Report Date:</span>&nbsp;&nbsp;<span id="sdate"></span>
			</div>
			<div class="col-md-4">
				<h5>Sales Report By Date</h5>
			</div>
		</div>
		<div class="row margin-bottom-inputs">
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<table id="report-table" data-toggle="table" data-query-params="queryParams" data-pagination="true" data-search="true" data-height="400">
					<thead>
			            <tr>
			                <th data-field="productType">Product Type</th>
			                <th data-field="productName">Product Name</th>
			                <th data-field="rate">Rate</th>
			                <th data-field="quantity">Quantity</th>
			                <th data-field="strSaleDate">Sale Date</th>
			            </tr>
			        </thead>
				</table>
				<!-- <div id="table" class="table"></div> -->
			</div>
			<div class="col-md-1"></div>
		</div>
		
	</div>
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/JSON-to-Table-1.0.0.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/JSON-to-Table.min.1.0.0.js"></script>--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-table.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#form1 input[type="text"]').datepicker({
	        	//format: "dd-MM-yyyy",
	        	format: "yyyy-mm-dd",
	        	orientation: "bottom left",
	        	clearBtn: true,
	        	todayHighlight: true
	    	});
		});
		
		function queryParams() {
		    return {
		        type: 'owner',
		        sort: 'updated',
		        direction: 'desc',
		        per_page: 100,
		        page: 1
		    };
		}
		
		$("#form1").submit(function(event){
			event.preventDefault();
			saleReportByDate();
		});
	</script>
</body>
</html>