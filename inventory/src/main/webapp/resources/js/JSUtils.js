//$.fn.getAllFishSpecies=function(){
function getAllFishSpecies(){
	$.ajax({
		url : '/inventory/getAllFishSpecies',
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : '',
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var ids,species;
			var options='<option value="" class="font-weight-bold">Select Species</option>';
			$.each( data, function( index, value ){
				if(index==="IDList")
					ids=value
				else if(index==="SpeciesList")
					species=value;
			});
			console.log(ids);
			console.log(species);
			for (var i = 0; i < species.length; i++) {
		        options += '<option value="' + ids[i] + '">' + species[i] + '</option>';
		    }
			$('#fishspecies').html(options);
		},
		error: function(jqXhr,textStatus,errorThrown){
            console.log( errorThrown );
        },
        complete: function(xhr, textStatus) {
        	console.log(textStatus);
        }
	});
}

function addFishSpecies(){
	var specieName= $('#fishCategory').val();
	$.ajax({
		url : $('#FishCatgForm form').attr('action'),
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : {'name':specieName},
		beforeSend : function(){
			$(".modal").show();
		},
		success : function(data,textStatus,xhr){
			
		},
		error : function(jqXhr,textStatus,errorThrown){
			console.warn('Error occured at server: '+textStatus);
		},
		complete : function(xhr, textStatus) {
			$(".modal").hide();
			console.log(xhr);
			$("#msgspan").html(xhr.responseText);
        }
	});
}
function addPlantName(){
	var plantname=$('#txtplantName').val();
	$.ajax({
		url : $('#PlantMasterForm form').attr('action'),
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		datatype : 'json',
		data : {'plantname':plantname},
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			
		},
		error : function(xhr,textStatus,errorThrown){
			
		},
		complete : function(xhr,textStatus){
			console.log(xhr);
			$("#msgspan").html(xhr.responseText);
		}
	});
}

function getAllPlantNames(){
	$.ajax({
		url : '/inventory/getAllPlantNames',
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : '',
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var ids,names;
			var options='<option value="" class="font-weight-bold">Select Plant Name</option>';
			$.each( data, function( index, value ){
				if(index==="IDList")
					ids=value
				else if(index==="NameList")
					names=value;
			});
			console.log(ids);
			console.log(names);
			for (var i = 0; i < names.length; i++) {
		        options += '<option value="' + ids[i] + '">' + names[i] + '</option>';
		    }
			$('#plantnames').html(options);
		},
		error: function(jqXhr,textStatus,errorThrown){
            console.log( errorThrown );
        },
        complete: function(xhr, textStatus) {
        	console.log(textStatus);
        }	
	});
}

function getAllBirdNames(){
	$.ajax({
		url : '/inventory/getAllBirdNames',
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data: '',
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var ids,names;
			var options='<option value="" class="font-weight-bold">Select Bird Name</option>';
			$.each( data, function( index, value ){
				if(index==="IDList")
					ids=value
				else if(index==="NameList")
					names=value;
			});
			console.log(ids);
			console.log(names);
			for (var i = 0; i < names.length; i++) {
		        options += '<option value="' + ids[i] + '">' + names[i] + '</option>';
		    }
			$('#ddlbirdnames').html(options);
		},
		error : function(){
			
		},
		complete : function(){
			
		}
	});
}

function addBirdName(){
	var birdname=$('#txtbirdname').val();
	$.ajax({
		url : $('#addBirdsFrm form').attr('action'),
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		datatype : 'json',
		data : {'birdname':birdname},
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			
		},
		error : function(xhr,textStatus,errorThrown){
			
		},
		complete : function(xhr,textStatus){
			console.log(xhr);
			$("#msgspan").html(xhr.responseText);
		}
	});
}

function addMaterialName(){
	var materialname=$('#rmName').val();
	$.ajax({
		url : $('#RMCategoryForm form').attr('action'),
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		datatype : 'json',
		data : {'materialname':materialname},
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			
		},
		error : function(xhr,textStatus,errorThrown){
			
		},
		complete : function(xhr,textStatus){
			console.log(xhr);
			$("#msgspan").html(xhr.responseText);
		}
	});
}

function getAllMaterialNames(){
	$.ajax({
		url : '/inventory/getAllMaterialNames',
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data: '',
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var ids,names;
			var options='<option value="" class="font-weight-bold">Select Material Name</option>';
			$.each( data, function( index, value ){
				if(index==="IDList")
					ids=value
				else if(index==="NameList")
					names=value;
			});
			//console.log(ids);
			//console.log(names);
			for (var i = 0; i < names.length; i++) {
		        options += '<option value="' + ids[i] + '">' + names[i] + '</option>';
		    }
			$('#ddlrm').html(options);
		},
		error : function(){
			console.log("error");
		},
		complete : function(){
		}
	});
}
function successAlert(){
	if($('#successInfoDiv').html()==='' || $('#successInfoDiv').html()==null){
		$('#successInfoDiv').hide();
	}
	else{
		$('#successInfoDiv').show();
		setTimeout(function() {
		    $('#successInfoDiv').fadeOut(1000);
		    $('#successInfoDiv').html('');
		}, 10000);
	}
}
function generateBatchNo(ddlName){
	var name=$('#'+ddlName+' option:selected').text();
	$.ajax({
		url: '/inventory/generateBatchNo',
		type: 'POST',
		ContentType: 'application/json; charset=utf-8',
		dataType: 'text',
		data:{'name':name},
		success : function(data,textStatus,xhr){
			$('#txtBatchNo').value=data;
			document.getElementById('txtBatchNo').value=data
		},
		error : function(jqXhr,textStatus,errorThrown){
			console.warn('Error occured at server: '+textStatus);
		},
		complete : function(xhr,textStatus){
			console.log(textStatus);
		}
	});
}
/*function generateBatchNo(){
	var name=$('#fishspecies option:selected').text();
	var xmlhttprequest=null;
	if (window.XMLHttpRequest) { 
		// Mozilla, Safari would use this method ...
		xmlhttprequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) { 
		// IE would use this method ...
		xmlhttprequest = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttprequest.onreadystatechange=function ()
	{
		if((xmlhttprequest.readyState==4) && (xmlhttprequest.status==200))
		{
			console.log(xmlhttprequest.responseText);
			document.getElementById('txtBatchNo').value = xmlhttprequest.responseText;
		}
	}
	xmlhttprequest.open("post","/inventory/generateBatchNo","true");
	xmlhttprequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttprequest.send("name="+name);
}*/


function loadDataForUpdate(currenttr){
	$('#txtName').val(function(n,c){
		return currenttr.children('td:eq(0)').html();
	});
	
	$('#txtBatch').val(function(n,c){
		return currenttr.children('td:eq(1)').html();
	});
	
	$('#txtRate').val(function(n,c){
		return currenttr.children('td:eq(2)').html();
	});
	
	$('#txtQuantity').val(function(n,c){
		return currenttr.children('td:eq(3)').html();
	});
	
	$('#hiddenRecID').val(function(n,c){
		return currenttr.children('td:eq(5)').find('input:hidden').val();
	});
	
	console.log(currenttr.children('td:eq(5)').find('input:hidden').val());
}

function updateDetails(tableName){
	var jsonparam={};
	jsonparam['recordID']=$('#hiddenRecID').val();
	jsonparam['rate']=$('#txtRate').val();
	jsonparam['quantity']=$('#txtQuantity').val();
	jsonparam['tblName']=tableName;
	/*console.log("jsonparam"+jsonparam['recordID']);
	console.log("jsonparam"+jsonparam['rate']);
	console.log("jsonparam"+jsonparam['quantity']);*/
	$.ajax({
		url : $('#frmupdatedetails form').attr('action'),
		type :'POST',
		contentType : 'application/json; charset=utf-8',
		dataType : 'text',
		data : JSON.stringify(jsonparam),
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			$("#alertspan").html(xhr.responseText);
		},
		error : function(xhr,textStatus,errorThrown){
			console.log(xhr);
			$("#alertspan").html(xhr.responseText);
		}
	});
}

function getProductForSale(ddlProductType){
	var url;
	var defaultoption;
	var productType=$('#'+ddlProductType+' option:selected').text();
	if(productType==='Fish'){
		url='/inventory/getfishnamesforsale';
		defaultoption='Select Fish';
	}
	else if(productType==='Bird'){
		url='/inventory/getbirdnamesforsale';
		defaultoption='Select Bird';
	}
	else if(productType==='Plant'){
		url='/inventory/getplantnamesforsale';
		defaultoption='Select Plant';
	}
	else if(productType==='Raw Material'){
		url="/inventory/getrmnamesforsale";
		defaultoption='Select Material';
	}
	$.ajax({
		url : url,
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : '',
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var ids,species;
			var options='<option value="" class="font-weight-bold">'+defaultoption+'</option>';
			$.each( data, function( index, value ){
				if(index==="IDList")
					ids=value
				else if(index==="SpeciesList")
					species=value;
			});
			console.log(ids);
			console.log(species);
			for (var i = 0; i < species.length; i++) {
		        options += '<option value="' + ids[i] + '">' + species[i] + '</option>';
		    }
			$('#ddlProductName').html(options);
		},
		error: function(jqXhr,textStatus,errorThrown){
            console.log( errorThrown );
        },
        complete: function(xhr, textStatus) {
        	console.log(textStatus);
        }
	});
}

function getProductQuantity(){
	var jsonparam={};
	jsonparam['tblName']=$('#ddlProductType option:selected').val();
	jsonparam['productID']=$('#ddlProductName option:selected').val();
	$.ajax({
		url : '/inventory/getCurrentQuantity',
		type : 'POST',
		contentType : 'application/json; charset=utf-8',
		dataType : 'text',
		data : JSON.stringify(jsonparam),
		beforeSend: function(){
			
		},
		success: function(data,textStatus,xhr){
			$("#lblCurrQuantity").html("Availabel in Stock: "+data);
			$("#txtQuantity").attr("max",data);
		},
		error: function(jqXhr,textStatus,errorThrown){
            console.log( errorThrown );
        },
        complete: function(xhr, textStatus) {
        	console.log(textStatus);
        }
	});
}

function getCustomerDetailsById(id){
	$.ajax({
		url : '/inventory/getCustomerDetailsById',
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		datatype : 'json',
		data : {'id':id},
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var d=jQuery.parseJSON(data);
			$('#txtAddress').val(function(n,c){
				return d.address;
			});
			$('#txtContact').val(function(n,c){
				return d.contact;
			});
			$('#txtEmail').val(function(n,c){
				return d.email;
			});
		},
		error : function(xhr,textStatus,errorThrown){
			
		},
		complete : function(xhr,textStatus){
			console.log(xhr);
		}
	});
}
function getCustomerDetailsById1(id){
	$.ajax({
		url : '/inventory/getCustomerDetailsById',
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		datatype : 'json',
		data : {'id':id},
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var d=jQuery.parseJSON(data);
			/*$('#txtAddress').val(function(n,c){
				return d.address;
			});
			$('#txtContact').val(function(n,c){
				return d.contact;
			});
			$('#txtEmail').val(function(n,c){
				return d.email;
			});*/
			$('#txtAddress').html(d.address)
			$('#spnCustName').html(d.customerName);
			$('#spnContact').html(d.contact);
		},
		error : function(xhr,textStatus,errorThrown){
			
		},
		complete : function(xhr,textStatus){
			console.log(xhr);
		}
	});
}

function getCustomerPurchaseHistoryById(id){
	var options='<option value="" class="font-weight-bold">Select Purchase Date</option>';
	$.ajax({
		url : '/inventory/getCustomerProductsById',
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		datatype : 'json',
		data : {'custId':id},
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var d=jQuery.parseJSON(data);
			/*$('#txtAddress').val(function(n,c){
				return d.address;
			});
			$('#txtContact').val(function(n,c){
				return d.contact;
			});
			$('#txtEmail').val(function(n,c){
				return d.email;
			});*/
			var totalQty=0;
			var totalAmt=0;
			$('#tblcustprod tbody').empty();
			$.each(d, function (i, obj) {
				options += '<option value="' + obj.strSaleDate + '">' + obj.strSaleDate + '</option>';
			    $('#tblcustprod').append('<tbody><tr><td>'+obj.productName+'</td><td>'+obj.quantity+'</td><td>'+obj.rate+'</td><td>'+obj.strSaleDate+'</td></tr></tbody>');
			    totalQty = totalQty + obj.quantity;
			    totalAmt = totalAmt + (obj.quantity*obj.rate);
			});
			$("#spnTQty").html(totalQty+" Units");
			$("#spnTAmt").html("Rs. "+totalAmt);
			$('#ddlPurchaseDate').html(options);
		},
		error : function(xhr,textStatus,errorThrown){
			console.log("Error occured: "+errorThrown);
		},
		complete : function(xhr,textStatus){
		}
	});
}

function getCustomerPurchaseHistoryByIdAndDate(id,pdate){
	/*var jsonparam={};
	jsonparam['custId']=id;
	jsonparam['pdate']=pdate;*/
	$.ajax({
		url : '/inventory/getCustomerProductsByDate?custId='+id+'&pdate='+pdate,
		type : 'POST',
		ContentType : 'application/json; charset=utf-8',
		datatype : 'json',
		data : '',
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			var d=jQuery.parseJSON(data);
			/*$('#txtAddress').val(function(n,c){
				return d.address;
			});
			$('#txtContact').val(function(n,c){
				return d.contact;
			});
			$('#txtEmail').val(function(n,c){
				return d.email;
			});*/
			var totalQty=0;
			var totalAmt=0;
			$('#tblcustprod tbody').empty();
			$.each(d, function (i, obj) {
			    $('#tblcustprod').append('<tbody><tr><td>'+obj.productName+'</td><td>'+obj.quantity+'</td><td>'+obj.rate+'</td><td>'+obj.strSaleDate+'</td></tr></tbody>');
			    totalQty = totalQty + obj.quantity;
			    totalAmt = totalAmt + (obj.quantity*obj.rate);
			});
			$("#spnTQty").html(totalQty+" Units");
			$("#spnTAmt").html("Rs. "+totalAmt);
		},
		error : function(xhr,textStatus,errorThrown){
			console.log("Error occured: "+errorThrown);
		},
		complete : function(xhr,textStatus){
		}
	});
}
function saleReportBetweenDates(){
	var startdate = $("#startdate").val();
	var enddate = $("#enddate").val();
	var products = $("#ddlProduct").val();
	var jsonparams={};
	jsonparams['startDate']=startdate;
	jsonparams['endDate']=enddate;
	jsonparams['products']=products;
	$.ajax({
		url : "/inventory/monthlyReport",
		type : "POST",
		data : JSON.stringify(jsonparams),
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		beforeSend : function(){
			
		},
		success : function(data,textStatus,xhr){
			//console.log(data);
			$("#sdate").html(startdate);
			$("#edate").html(enddate);
			$('#report-table').bootstrapTable('load',data);
		},
		error : function(xhr,textStatus,errorThrown){
			console.log("Error occured: "+errorThrown);
		},
		complete : function(xhr,textStatus){
		}
	});
}

