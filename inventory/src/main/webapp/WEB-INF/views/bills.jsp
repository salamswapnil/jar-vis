<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/JSUtils.js"></script>
<title>Bills</title>
</head>
<body>
	<%@ include file="header.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#ddlPurchaseDate').change(function(event){
				//var table = $("#tblcustprod");
				if(this.selectedIndex > 0){
					var selectedDate = $(this).val();
					var custID = $("#hiddenCustID").val();
					/* table.find('tbody tr').each(function (i, el) {
				        var $tds = $(this).find('td'),
				            sdate = $tds.eq(3).text();
				        	if(selectedDate !=sdate){
				        		$(this).hide();
				        	}
				    }); */
					getCustomerPurchaseHistoryByIdAndDate(custID,selectedDate);
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
			   		getCustomerDetailsById1(index.data);
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
		<div class="row margin-bottom-inputs margin-top-inputs">
			<div class="col-md-6">
				<input id="txtCustName" class="form-control autocomplete" required placeholder="Enter Customer Name"/>
			</div>
			<div class="col-md-3">
				<select id="ddlPurchaseDate" class="form-control" >
					
				</select>
			</div>
			<div class="col-md-1">
				<input type="hidden" id="hiddenCustID"/>
			</div>
		</div>
		<div class="row margin-bottom-inputs">
				<div class="col-md-12">
					<hr />
				</div>
			</div>
	</div>
	<div class="container border border-dark">
		<div class="row margin-bottom-inputs">
			<div class="col-md-12" style="padding-left:40%;padding-right:40%"><h5>Bill of Material</h5></div>
		</div>
		<div class="row margin-bottom-inputs">
			<div class="col-md-3">
				<span style="text-align: justify;"> Opp Babar Estate, Dattawadi Rd Near Vitthal Mandir, Vitthalwadi Akurdi Gaon Pimpri-Chinchwad, Maharashtra 411035</span>
			</div>
			<div class="col-md-7"></div>
			<div class="col-md-2 bill-logo">
			</div>
		</div>
		<div class="row margin-bottom-inputs">
			<div class="col-md-1"></div>
			<div class="col-md-1">
				<span class="font-weight-bold">Customer Name</span>
			</div>
			<div class="col-md-2">
				<span id="spnCustName"></span>
			</div>
			<div class="col-md-1">
				<span class="font-weight-bold">Address</span>
			</div>
			<div class="col-md-3">
				<div id="txtAddress"></div>
			</div>
			<div class="col-md-1">
				<span class="font-weight-bold">Conatact No.</span>
			</div>
			<div class="col-md-2">
				<span id="spnContact"></span>
			</div>
		</div>
		<div class="row margin-bottom-inputs " style="margin-top:50px;">
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<table id="tblcustprod" class="table" style="border: 1px solid #dee2e6;">
					<thead class="">
						<tr>
							<th class="">Product Name</th>
							<th class="">Quantity Purchased (In Units)</th>
							<th class="">Rate (In Rupees)</th>
							<th class="">Purchase Date</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div class="row margin-bottom-inputs" style="margin-top:50px;">
			<div class="col-md-4"></div>
			<div class="font-weight-bold">Total Quantity</div>
			<div class="col-md-1"><span id="spnTQty" style="width:100px;height:20px;"></span></div>
			<div class=" font-weight-bold">Total Amount</div>
			<div class="col-md-1"><span id="spnTAmt" style="width:100px;height:20px;"></span></div>
			<div class="col-md-2">Authorized Signatory</div>
		</div>
	</div>
</body>
</html>