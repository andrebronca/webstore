<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
		<title>Login</title>
	</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Login</h1>
			</div>
		</div>
	</section>
	
	<section class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="panel-heading">
					<h3 class="panel-title">Please sign in</h3>
				</div>
				<div class="panel-body">
					<c:if test="${not empty error}">
						<div class="alert alert-danger">
							<s:message code="AbstractUserDetailsAuthenticationProvider.badCredentials" />
							<br />
						</div>
					</c:if>
					<form action='<c:url value="/j_spring_security_check" />' method="post">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="User name" name="j_name" type="text" />
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Password" name="j_password" type="password" value="" />
							</div>
							<input class="btn btn-lg btn-success btn-block" type="submit" value="Login" />
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>