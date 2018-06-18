<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/JSUtils.js"></script>
	<title>Fish</title>
</head>
<body>
	<%@include file="header.jsp" %>
	<script type="text/javascript">
		$(document).ready(function () {
			$('#msgDiv').hide();
			successAlert();
			getAllFishSpecies();
			$('#FishCatgForm').submit(function(event){
				event.preventDefault();
				addFishSpecies();
				$('#fishspecies').html("");
				getAllFishSpecies();
				$('#msgDiv').fadeToggle(1000);
				setTimeout(function() {
				    $('#msgDiv').fadeOut(1000);
				}, 10000);
			});
			
			//----- OPEN
			$('[data-popup-open]').on('click', function(e)  {
				var targeted_popup_class = jQuery(this).attr('data-popup-open');
				$('[data-popup="' + targeted_popup_class + '"]').fadeIn(500);
				e.preventDefault();
			});
			//----- CLOSE
			$('[data-popup-close]').on('click', function(e)  {
				var targeted_popup_class = jQuery(this).attr('data-popup-close');
				$('[data-popup="' + targeted_popup_class + '"]').fadeOut(500);
				e.preventDefault();
			});
			$('#fishspecies').change(function(event){
				
				if(this.selectedIndex > 0)
					generateBatchNo('fishspecies');
				else{
					$('#txtBatchNo').val(function(n,c){
						return "";
					});
				}
			});
		});
	</script>
	
	<div class="container">  
		<form:form action="${pageContext.request.contextPath}/addFishDetails" method="post" commandName="frmFishDetails">
			<div class="row margin-bottom-inputs margin-top-inputs">
				<div class="col-md-2">
					<label>Available Fish</label>
				</div>
				<div class="col-md-4">
					<form:select path="fishCategory" class="form-control" name="ddlSpecies" id="fishspecies" required="required">
					</form:select>
				</div>
				<div class="col-md-2">
					<!-- <div id="AddFishCatg">Add fish Species</div> -->
					<a class="btn" data-popup-open="popup-1" href="#">Add fish Species</a>
				</div>
			</div>
			<div class="row margin-bottom-inputs">
				<div class="col-md-2"><label>Batch No.</label></div>
				<div class="col-md-4">
					<form:input path="batchNo" class="form-control" required="required" id="txtBatchNo" readonly="true"/>
				</div>
			</div>
			<div class="row margin-bottom-inputs">
				<div class="col-md-2"><label>Rate</label></div>
				<div class="col-md-4">
					<form:input path="rate" type="number" class="form-control" required="required"/> 
				</div>
			</div>
			<div class="row margin-bottom-inputs">
				<div class="col-md-2"><label>Quantity</label></div>
				<div class="col-md-4">
					<form:input path="quantity" type="number" class="form-control" required="required"/>
				</div>
			</div>
			<div class="row margin-bottom-inputs">
				<div class="col-md-2"><label>Purchase Date</label></div>
				<div class="col-md-4">
					<div class="input-append date" id="dp3" data-date="12-02-2012" data-date-format="dd-MM-yyyy">
						<form:input path="purchaseDate" class="form-control" required="required" id="dtPurchaseDate"/>
						<span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
			<div  class="row margin-bottom-inputs">
				<div class="col-md-1">
					<input type="submit" value="ADD" class="btn btn-primary" />
				</div>
				<div class="col-md-2">
					<input type="reset" value="CLEAR" class="btn btn-secondary"/>
				</div>
				<div class="col-md-5 alert alert-success" id="successInfoDiv">${msg}</div>
			</div>
		</form:form>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#dtPurchaseDate').datepicker({
	        	format: "dd-MM-yyyy",
	        	orientation: "bottom left",
	        	clearBtn: true,
	        	todayHighlight: true
	    	});
		});
	</script>
	<div class="popup" data-popup="popup-1">
		<div class="popup-inner">
			<div class="alert alert-success" role="alert" id="msgDiv">
			  	<span id="msgspan"></span>
			</div>
			<div class="modal" style="display: none">
			    <div class="center">
			        <img alt="" src="/inventory/images/giphy.gif" />
			    </div>
			</div>
			<div id="FishCatgForm" class="container" style="position: absolute;top:30%;margin-top:10px;">
			  <form action="${pageContext.request.contextPath}/addFishCategory" method="post">
			  	
			  	<div class="row" style="margin-bottom: 20px;">
			  		<div class="col-md-10">
			  			<input id="fishCategory" name="fishCategoryName" class="form-control" placeholder="Category Name" type="text" required/>
			  		</div>
			  	</div>
			    <div class="row margin-bottom-inputs">
				    <div class="col-md-3"><input name="categorySubmit" class="formBtn btn btn-secondary" type="submit" /></div>
				    <div class="col-md-3"><input name="categoryReset" class="formBtn btn btn-secondary" type="reset" /></div>
			    </div>
			  </form>
			</div>
		<!-- <a data-popup-close="popup-1" href="#">Close</a> -->
		<a class="popup-close" data-popup-close="popup-1" href="#">x</a>
		</div>
	</div>
	<%-- <%@include file="fishStock.jsp" %> --%>
</body>
</html>