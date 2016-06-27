<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Flooring Order</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/starter-template.css" rel="stylesheet">

        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png">

    </head>
    <body>
        <div class="container">
            <h1>Flooring Mastery - Edit Order</h1>
            <hr/>


            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home"></span> Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/admin/"><span class="glyphicon glyphicon-dashboard"></span> Admin Panel</a></li>
                </ul>    
            </div> 

            <div class="row">

                <div class="col-md-6">

                    <form method="POST" action="./" class="form-horizontal">
                        <input type="hidden" name="id" value="${order.id}" />

                        <div class="form-group">
                            <label for="customerName" class="col-md-4 control-lable">Customer Name:</label>
                            <div class="col-md-8">
                                <input name="customerName" class="form-control" value="${order.customerName}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="area" class="col-md-4 control-lable">State:</label>
                            <div class="col-md-8">
                                <select name="stateSelect" class="form-control" id="stateSelect">
                                    <c:forEach items="${taxes}" var="tax">
                                        <option value="${tax.state}" ${selectedState.equals(tax.state)? 'selected':''}>${tax.state} - ${tax.taxRate}% sales tax</option>
                                    </c:forEach>
                                </select>
                            </div>                               
                        </div>
                        <div class="form-group">
                            <label for="area" class="col-md-4 control-lable"> Flooring material:</label>
                            <div class="col-md-8">
                                <!--<div class="input-group">-->
                                <!--<span class="input-group-addon">Material:</span>-->
                                <select name="productSelect" class="form-control" id="productSelect">
                                    <c:forEach items="${products}" var="product">
                                        <option value="${product.productType}" ${selectedProduct.equals(product.productType)? 'selected':''}>${product.productType} - $${product.materialCostPerSf + product.laborCostPerSf} per square foot</option>
                                    </c:forEach>  
                                </select>
                                <!--</div>-->
                            </div>                               
                        </div>

                        <div class="form-group">
                            <label for="area" class="col-md-4 control-lable">Area:</label>
                            <div class="col-md-8">
                                <input path="area" class="form-control" value="${order.area}" />
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="orderDate" class="col-md-4 control-lable">Install Date:</label>
                            <div class="col-md-8">
                                <input path="orderDate" class="form-control" value="${order.orderDate}" />
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label"></label>
                            <div class="col-md-8">
                                <input type="submit" class="btn pull-right btn-success"/>
                            </div>
                        </div>

                    </form>

                </div>

            </div>



        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script>

        </script>

    </body>
</html>

