<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous" />
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/universal-style.css" />
</head>
<body>
	<form:form action="${pageContext.request.contextPath}/login" method="post" commandName="loginForm">
		<div class="container login-container">
			<div class="row margin-bottom-inputs">
		  		<div class="col-md-8 logo">
		  		</div>
		  		<div class="col-md-4">
	
		  		</div>
			</div>
			<div class="row margin-bottom-inputs">
				<div class="">
					<label for="exampleFormControlInput1">Email ID</label>
				</div>
				<div class="col-md-6">
					<form:input path="userName" class="form-control" name="txtUsername" placeholder="Email ID"/>
				</div>
				<div class="col-md-3"></div>
			</div>
			<div class="row margin-bottom-inputs">
				<div class="">
					<label for="exampleInputPassword1">Password</label>
				</div>
				<div class="col-md-6">
					<form:password path="password" class="form-control" name="txtPassword" placeholder="Password"/>
				</div>
			</div>
			<div class="row">
				<!-- <div class="col-md-1"></div> -->
				<div class="col-md-1">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
				<div class="col-md-2">
					<button type="submit" class="btn btn-secondary">Forgot Password?</button>
				</div>
				<div class="col-md-6">
					<span style="color:red;">${msg}</span>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
