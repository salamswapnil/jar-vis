<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/JSUtils.js"></script>
	<title>Sales</title>
</head>
<body>
	<%@include file="header.jsp"%>
	<script type="text/javascript">
		$(document).ready(function(){
			successAlert();
			$('#ddlProductType').change(function(event){
				if(this.selectedIndex > 0){
					getProductForSale('ddlProductType');
				}
				else{
					$('#ddlProductName').html('');
				}
			});
			
			$('#ddlProductName').change(function(event){
				if(this.selectedIndex > 0){
					console.log("event triggered");
					getProductQuantity();
				}
			});
			
			$('#txtCustName').autocomplete({
				serviceUrl: '/inventory/getAllCustomerNames',
				paramName: "name",
				delimiter: ",",
				showNoSuggestionNotice: true,
				triggerSelectOnValidInput: false,
				transformResult: function(response) {
					return {      	
				  		//must convert json to javascript object before process
				  		suggestions: $.map($.parseJSON(response), function(item) {        	
				    		return { value:item.Cust_Name, data:item.RecordID };
				    	})
					}; 
			   	},
			   	onSelect:function(index){
			   		$("#hiddenCustID").val(function(n,c){
			   			return index.data;
			   		});
			   		getCustomerDetailsById(index.data);
			   		getCustomerPurchaseHistoryById(index.data);
			   	},
			   	onSearchComplete:function(e,q,d){
			   		//console.log("value of q: "+q);
			   		if(q.length==0){
			   			$("#hiddenCustID").val(function(n,c){
			   				console.log("called");
				   			return "";
				   		});	
			   		}
			   	}
			});
		});
	</script>
	<div class="container">
		<form:form action="${pageContext.request.contextPath}/saleproduct" method="post" commandName="salesobject">
			<div class="row">
				<div class="col-md-6">
					<div class="container">
						<div class="row margin-bottom-inputs margin-top-inputs">
							<div class="col-md-4">Customer Name</div>
							<div class="col-md-8">
								<form:input path="customerName" id="txtCustName" class="form-control autocomplete" required="required"/>
							</div>
						</div>
						<div class="row margin-bottom-inputs">
							<div class="col-md-4">Address</div>
							<div class="col-md-8">
								<form:textarea path="address" class="form-control" id="txtAddress" rows="3" required="required"/>
							</div>
						</div>
						<div class="row margin-bottom-inputs">
							<div class="col-md-4">Contact</div>
							<div class="col-md-8">
								<form:input path="contact" id="txtContact" class="form-control" required="required"/>
							</div>
						</div>
						<div class="row margin-bottom-inputs">
							<div class="col-md-4">Email</div>
							<div class="col-md-8">
								<form:input path="email" type="email" id="txtEmail" class="form-control"/>
							</div>
						</div>
					</div>					
				</div>
				<div class="col-md-6">
					<div class="container">
						<div class="row margin-bottom-inputs">
							<div class="col-md-4">Product Type</div>
							<div class="col-md-8">
								<form:select path="productType" class="form-control" id="ddlProductType" required="required">
									<option value="" class="font-weight-bold">Select Product</option>
									<option value="Fish">Fish</option>
									<option value="Bird">Bird</option>
									<option value="Plant">Plant</option>
									<option value="Raw Material">Raw Material</option>
								</form:select>
							</div>
						</div>
						<div class="row margin-bottom-inputs">
							<div class="col-md-4">Product Name</div>
							<div class="col-md-8">
								<form:select path="productName" class="form-control" id="ddlProductName" required="required">
									
								</form:select>
							</div>
						</div>
						<div class="row margin-bottom-inputs">
							<div class="col-md-4">Rate</div>
							<div class="col-md-8">
								<form:input path="rate" type="number" id="txtRate" class="form-control" required="required" min="1"/>
							</div>
						</div>
						<div class="row margin-bottom-inputs">
							<div class="col-md-4">Quantity</div>
							<div class="col-md-4">
								<form:input path="quantity" type="number" id="txtQuantity" class="form-control" required="required" min="1"/>
							</div>
							<div class="col-md-4"><span id="lblCurrQuantity">Available in Stock:0</span></div>
						</div>
						<div class="row margin-bottom-inputs">
							<div class="col-md-4">Sale Date</div>
							<div class="col-md-8">
								<div class="input-append date" id="dp3" data-date="12-02-2012" data-date-format="dd-MM-yyyy">
									<form:input path="saleDate" class="form-control" required="required" id="dtSaleDate"/>
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-0"></div>
				<div class="col-md-1">
					<input type="submit" value="SUBMIT" class="btn btn-primary" />
				</div>
				<div class="col-md-1">
					<input type="reset" value="CLEAR" class="btn btn-secondary"/>
				</div>
				<div class="col-md-1">
					<form:input path="customerID" type="hidden" id="hiddenCustID"/>
				</div>
				<div class="col-md-5 alert alert-success" id="successInfoDiv">${msg}</div>
			</div>
			<div class="row margin-bottom-inputs">
				<div class="col-md-1"></div>
				<div class="col-md-10">
				<table id="tblcustprod" class="table table-hover">
					<thead class="">
						<tr>
							<th class="">Product Name</th>
							<th class="">Quantity Purchased</th>
							<th class="">Rate</th>
							<th class="">Purchase Date</th>
						</tr>
					</thead>
				</table>
				</div>
				<div class="col-md-1"></div>
			</div>
		</form:form>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#dtSaleDate').datepicker({
	        	format: "dd-MM-yyyy",
	        	orientation: "bottom left",
	        	clearBtn: true,
	        	todayHighlight: true
	    	});
		});
	</script>
</body>
</html>	