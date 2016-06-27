<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Display Flooring Order</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/starter-template.css" rel="stylesheet">

        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png">

        <style>
            th {
                background-color: #449D44;
                color: #E7FFE1;
            }

        </style>

    </head>
    <body>
        <div class="container">
            <h1>Flooring Mastery</h1>
            <h3>Order Details</h3>
            <hr/>


            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home"></span> Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/admin/"><span class="glyphicon glyphicon-dashboard"></span> Admin Panel</a></li>
                </ul>    
            </div>

            <div class="row">

                <div class="col-md-12">

                    <table class="table table-bordered">
                        <tr>
                            <th>Customer Name</th>
                            <th>State</th>
                            <th>Tax Rate</th>
                            <th>Material</th>
                            <th>Area</th>
                            <th>Material $/SF</th>
                            <th>Labor $/SF</th>
                            <th>Material Cost</th>
                            <th>Labor Cost</th>
                            <th>Tax</th>
                            <th>Total Cost</th>
                        </tr>
                        <tr>
                            <td>${order.customerName}</td>
                            <td>${order.state}</td>
                            <td>${order.taxRate}%</td>
                            <td>${order.productType}</td>
                            <td>${order.area}</td>
                            <td>$${order.materialCostPerSf}</td>
                            <td>$${order.laborCostPerSf}</td>
                            <td>$${order.materialCost}</td>
                            <td>$${order.laborCost}</td>
                            <td>$${order.totalTax}</td>
                            <td>$${order.totalCost}</td>

                        </tr>
                    </table>
                </div>   


            </div>

        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

