<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.standalone.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.devbridge-autocomplete/1.4.7/jquery.autocomplete.js"></script>


<!-- <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">  
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker.css" rel="stylesheet">  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>  
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>  
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.js"></script> -->

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/universal-style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/menu.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/menumaker.js"></script>

<div class="container header-container">
	<div class="row">
		<div class="col-md-12 logo">
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div id='cssmenu'>  
			   <ul>
			      <li><a href='${pageContext.request.contextPath}/home'>Home</a></li>
			      <li><a href='#'>Add Inventory</a>
			         <ul>
			            <li><a href="${pageContext.request.contextPath}/fish">Fish</a>
			               <!-- <ul>
			                  <li><a href='#'>Sub Product</a></li>
			                  <li><a href='#'>Sub Product</a></li>
			               </ul> -->
			            </li>
			            <li><a href="${pageContext.request.contextPath}/plant">Plants</a>
			               <!-- <ul>
			                  <li><a href='#'>Sub Product</a></li>
			                  <li><a href='#'>Sub Product</a></li>
			               </ul> -->
			            </li>
			            <li>
			            	<a href="${pageContext.request.contextPath}/bird">Birds</a>
			            </li>
			            <li>
			            	<a href="${pageContext.request.contextPath}/rawmaterial">Raw material</a>
			            </li>
			         </ul>
			      </li>
			      <li><a href="${pageContext.request.contextPath}/sales">Sales</a></li>
			      <li><a href='${pageContext.request.contextPath}/bills'>Bills</a></li>
			      <li><a href='#'>Reports</a>
			      		<ul>
			      			<li><a href="${pageContext.request.contextPath}/salebetweendates">Monthly</a></li>
			      			<li><a href="${pageContext.request.contextPath}/dailySales">Daily</a></li>
			      		</ul>
			      </li>
			   </ul>
			</div>
		</div>
	</div>
</div>
