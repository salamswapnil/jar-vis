<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
$(document).ready(function(){
	$('#msgDiv1').hide();
	$('#tblStockForUpdate tr input[type=button]').click(function(){
		var tr=$(this).closest('tr');
		loadDataForUpdate(tr);
	});
	
	$('#frmupdatedetails').submit(function(event){
		event.preventDefault();
		var tblToBeUpdated="plant";
		updateDetails(tblToBeUpdated);
		$('#msgDiv1').fadeToggle(1000);
		setTimeout(function() {
		    $('#msgDiv1').fadeOut(1000);
		}, 10000);
	});
});
</script>

<div class="container">
	<table class="table" id="tblStockForUpdate">
		<thead class="">
			<tr>
				<th class="">Plant Name</th>
				<th class="">Batch</th>
				<th class="">Rate</th>
				<th class="">In Stock</th>
				<th class="">Action</th>
			</tr>
			<c:forEach var="pstockobj" items="${plantStock}">
				<tr>
					<td>${pstockobj.plantName}</td>
					<td>${pstockobj.batchNo}</td>
					<td>${pstockobj.rate }</td>
					<td>${pstockobj.quantity}</td>
					<td><input data-popup-open="popup-2" type="button" value="Update" /></td>
					<td><input type="hidden" value="${pstockobj.recordId}"/></td>
				</tr>
			</c:forEach>
		</thead>
	</table>
	<hr/>
	<div class="popup" data-popup="popup-2">
		<div class="popup-inner" style="height:350px !important;">
			<div class="alert alert-success" role="alert" id="msgDiv1">
			  	<span id="alertspan"></span>
			</div>
			<div id="frmupdatedetails" class="container" style="position: absolute;top:20%;margin-top:10px;">
			  <form action="${pageContext.request.contextPath}/updateDetails" method="post">
			  	
			  	<div class="row" style="margin-bottom: 20px;">
			  		<div class="col-md-3">
						<label>Bird Name</label>
					</div>
			  		<div class="col-md-8">
			  			<input id="txtName" class="form-control" type="text" readonly/>
			  		</div>
			  	</div>
			  	<div class="row margin-bottom-inputs">
					<div class="col-md-3"><label>Batch No.</label></div>
					<div class="col-md-8">
						<input id="txtBatch" type="text" class="form-control" readonly/>
					</div>
				</div>
				<div class="row margin-bottom-inputs">
					<div class="col-md-3"><label>Rate</label></div>
					<div class="col-md-8">
						<input type="number" class="form-control" required="required" id="txtRate"/> 
					</div>
				</div>
				<div class="row margin-bottom-inputs">
					<div class="col-md-3"><label>Quantity</label></div>
					<div class="col-md-8">
						<input type="number" class="form-control" required="required" id="txtQuantity"/>
					</div>
				</div>
			    <div class="row margin-bottom-inputs">
				    <div class="col-md-3"><input class="formBtn btn btn-secondary" type="submit" value="Update"/></div>
				    <div class="col-md-3"><input class="formBtn btn btn-secondary" type="reset" /></div>
				    <div class="col-md-3"><input type="hidden" id="hiddenRecID"/></div>
			    </div>
			  </form>
			</div>
		<!-- <a data-popup-close="popup-1" href="#">Close</a> -->
		<a class="popup-close" data-popup-close="popup-2" href="#">x</a>
		</div>
	</div>
</div>