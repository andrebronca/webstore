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
		<title>Products</title>
	</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Products</h1>
				<p>Add products</p>
			</div>
		</div>
	</section>
	
	<section class="container">
		<f:form modelAttribute="newProduct" class="form-horizontal">
			<fieldset>
				<legend>Add new Product</legend>
				<div class="form-group">
					<label class="control-label col-sd-2 col-md-3 col-lg-2" for="productId">
					<s:message code="addProduct.form.productId.label" />
					</label>
					<div class="col-sd-10 col-md-9 col-lg-10">
						<f:input path="productId" id="productId" type="text" class="f:input-large"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-lg-2" for="name">Name</label>
					<div class="col-lg-10">
						<f:input path="name" id="name" type="text" class="form:input-large" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-lg-2" for="unitPrice">Unit Price</label>
					<div class="col-lg-10">
						<f:input path="unitPrice" id="unitPrice" type="text"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-lg-2" for="manufacturer">Manufacturer</label>
					<div class="col-lg-10">
						<f:input path="manufacturer" id="manufacturer" type="text" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-lg-2" for="category">Category</label>
					<div class="col-lg-10">
						<f:input path="category" id="category" type="text" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-lg-2" for="unitsInStock">Units In Stock</label>
					<div class="col-lg-10">
						<f:input path="unitsInStock" id="unitsInStock" type="text" />
					</div>
				</div>
				
				
				
				<div class="form-group">
					<label class="control-label col-lg-2" for="description">Description</label>
					<div class="col-lg-10">
						<f:textarea path="description" id="description" rows="2"/>
					</div>
				</div>
				
				
				
				<div class="form-group">
					<label class="control-label col-lg-2" for="condition">Condition</label>
					<div class="col-lg-10">
						<f:radiobutton path="condition" value="New"/>New
						<f:radiobutton path="condition" value="Old"/>Old
						<f:radiobutton path="condition" value="Refurbished"/>Refurbished
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<input type="submit" id="btnAdd" class="btn btn-primary" value="Add" />
					</div>
				</div>
			</fieldset>
		</f:form>
	</section>
</body>
</html>