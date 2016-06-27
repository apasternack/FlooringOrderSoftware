<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   <!-- this tag allows you to do validation-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Flooring Mastery</title>
        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/starter-template.css" rel="stylesheet">

        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png">
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <!--<script src="http://code.jquery.com/jquery-1.10.2.js"></script>-->
        <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <style>
            th {
                background-color: #449D44;
                color: #E7FFE1;
            }

            #warning {
                float: right;
            }

            #orderDropdown {
                margin-bottom: 15px;
                /*                margin-left: auto;
                                margin-right: auto;
                                align-content: center;*/
            }

            #no-order-alert {
                display: none;
            }

            #customer-name-error-message, #customer-name-edit-error-message {
                display: none;
            }

            #area-error-message, #area-edit-error-message {
                display: none;
            }

        </style>

    </head>

    <body>
        <div class="container">
            <h1>Flooring Mastery</h1>
            <div class="alert alert-danger" id="warning">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>Alert!</strong>
                <c:if test="${testMode == 1}">
                    Program running in test mode.  All changes to orders will not be saved to file
                    and will be deleted from memory at the end of the session.  To save changes to file please 
                    set config file to program mode.
                </c:if>
                <c:if test="${testMode == 2}">
                    Program running in production mode.  All changes will be saved to file.
                </c:if>
                <c:if test="${testMode == 3}">
                    Configuration file not set to test or production mode.
                    The program will proceed in test mode.  No changes will be written to file
                    To run in production mode please enter 'prod' in the first line of the config.txt file.
                </c:if> 
            </div>
            <hr/>


            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home"></span> Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/admin/"><span class="glyphicon glyphicon-dashboard"></span> Admin Panel</a></li>
                </ul>    
            </div>


            <div class="row">

                <div class="col-md-6">
                    <div class="col-md-12">
                        <div id="orderDropdown" class="input-group">
                            <span class="input-group-addon">Display orders for:</span>
                            <select name="dateSelect" class="form-control" id="date-select">
                                <option value=""> Select Order Date to Search By</option>
                                <c:forEach items="${orderDates}" var="date">
                                    <option value="${date}">${date}</option>
                                </c:forEach>  
                            </select>
                        </div>
                    </div> 
                    <table class="table table-bordered table-hover" id="orders-table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Date</th>
                                <th>Total Cost</th>
                                <th><span class="glyphicon glyphicon-edit"></span> Edit</th>
                                <th><i class="glyphicon glyphicon-remove"></i> Delete</th>
                            </tr>
                        </thead>
                        <tbody class="orders-table-row-check">
                            <c:forEach items="${orders}" var="order">
                                <tr id="order-row-${order.id}">
                                    <td><a data-order-id="${order.id}" data-toggle="modal" data-target=#showOrderModal>${order.customerName}</a></td>
                                    <td>${order.orderDate}</td>
                                    <td>${order.totalCost}</td>
                                    <td><a data-order-id="${order.id}" data-toggle="modal" data-target=#editOrderModal>Edit</a></td>
                                    <td><a data-order-id="${order.id}" class="delete-link">Delete</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div id="no-order-alert" class="text-primary text-center " />

                    <h2><strong>No Orders In the System</strong></h2>
                </div>
            </div>   

            <div class="col-md-6" id="submit-order-form-div">

                <form method="POST" class="form-horizontal">
                    <input type="hidden" name="id" value="${order.id}" />
                    <div class="form-group" id="customer-name-div">
                        <label for="customer-name" class="col-md-4 control-label">Customer Name:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="customer-name" />
                            <div id="customer-name-error-message" class="text-danger">You must supply a customer name</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="area" class="col-md-4 control-label">State:</label>
                        <div class="col-md-8">
                            <select name="stateSelect" class="form-control" id="state-select" >
                                <c:forEach items="${taxes}" var="tax">
                                    <option value="${tax.state}">${tax.state} - ${tax.taxRate} % sales tax</option>
                                </c:forEach>
                            </select>
                        </div>                               
                    </div>
                    <div class="form-group">
                        <label for="area" class="col-md-4 control-label"> Flooring material:</label>
                        <div class="col-md-8">
                            <!--<div class="input-group">-->
                            <!--<span class="input-group-addon">Material:</span>-->
                            <select name="productSelect" class="form-control" id="product-select">
                                <c:forEach items="${products}" var="product">
                                    <option value="${product.productType}">${product.productType} - $${product.materialCostPerSf + product.laborCostPerSf} per square foot</option>
                                </c:forEach>  
                            </select>
                            <!--</div>-->
                        </div>                               
                    </div>

                    <div class="form-group" id="area-div">
                        <label for="area" class="col-md-4 control-label">Area:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="area" />  
                            <div id="area-error-message" class="text-danger">You must enter an area amount in square feet</div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="orderDate" class="col-md-4 control-label">Install Date:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="order-date" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label"></label>
                        <div class="col-md-8">
                            <input id="create-submit" type="submit" class="btn pull-right btn-success" value="Create Order"/>
                        </div>
                    </div>

                </form>
            </div>

        </div>

    </div>

    <div id="showOrderModal" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title">Contact Details</h3>
              </div>
              <div class="modal-body">
                    <table class="table table-bordered" id="show-order-table">
                        <tr>
                            <th>Name:</th>
                            <td id="order-customer-name"></td>
                        </tr>

                        <tr>
                            <th>Install Date:</th>
                            <td id="order-order-date"></td>
                        </tr>

                        <tr>
                            <th>Area:</th>
                            <td id="order-area">
                            </td>
                        </tr>

                        <tr>
                            <th>State:</th>
                            <td id="order-state"></td>
                        </tr>

                        <tr>
                            <th>Tax Rate:</th>
                            <td id="order-tax-rate"></td>
                        </tr>

                        <tr>
                            <th>Material:</th>
                            <td id="order-product-type"></td>
                        </tr>

                        <tr>
                            <th>Material cost per square foot:</th>
                            <td id="order-material-cost-sf"></td>
                        </tr>

                        <tr>
                            <th>Labor cost per square foot:</th>
                            <td id="order-labor-cost-sf"></td>
                        </tr>

                        <tr>
                            <th>Total material cost:</th>
                            <td id="order-material-cost"></td>
                        </tr>

                        <tr>
                            <th>Total labor cost:</th>
                            <td id="order-labor-cost"></td>
                        </tr>

                        <tr>
                            <th>Tax:</th>
                            <td id="order-tax"></td>
                        </tr>

                        <tr>
                            <th>Total order cost:</th>
                            <td id="order-total-cost"></td>
                        </tr>
                    </table>
                </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              </div>
            </div>

          </div>
        </div>

    <div id="editOrderModal" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Contact Details</h4>
              </div>
              <div class="modal-body">
                    <table class="table table-bordered" id="edit-order-table">
                        <input type="hidden" id="edit-id" />
                        <tr>
                        <div id="edit-name-validation">
                            <th>Name:</th>
                            <td>
                                <input type="text" id="edit-order-customer-name" class="form-control"/>
                                <div id="customer-name-edit-error-message" class="text-danger">You must supply a customer name</div>
                            </td>
                        </div>
                        </tr>

                        <tr>
                            <th>Install Date:</th>
                            <td>
                                <input type="text" id="edit-order-order-date" class="form-control" />
                            </td>
                        </tr>

                        <tr>
                            <th>State:</th>
                            <td>

                                <select name="stateSelect" class="form-control" id="edit-order-state" >
                                    <c:forEach items="${taxes}" var="tax">
                                        <option value="${tax.state}">${tax.state} - ${tax.taxRate} % sales tax</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th>Material:</th>
                            <td>
                                <select name="productSelect" class="form-control" id="edit-order-product-type">
                                    <c:forEach items="${products}" var="product">
                                        <option value="${product.productType}">${product.productType} - $${product.materialCostPerSf + product.laborCostPerSf} per square foot</option>
                                    </c:forEach>  
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th>Area:</th>
                            <td>
                                <input type="text" id="edit-order-area" class="form-control"/>
                                <div id="area-edit-error-message" class="text-danger">You must enter an area amount in square feet</div>

                            </td>
                        </tr>
                        <tr>
                            <th>Total material cost:</th>
                            <td id="edit-order-material-cost" >
                            </td>
                        </tr>

                        <tr>
                            <th>Total labor cost:</th>
                            <td id="edit-order-labor-cost">
                            </td>
                        </tr>

                        <tr>
                            <th>Tax:</th>
                            <td id="edit-order-tax">
                            </td>
                        </tr>

                        <tr>
                            <th>Total order cost:</th>
                            <td id="edit-order-total-cost">
                            </td>
                        </tr>
                    </table>
                </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-danger" id="edit-order-button"  >Save</button>
              </div>
                <div id="add-dvd-edit-validation-errors">
                </div>
            </div>

          </div>
        </div>

    <script>
        var contextRoot = '${pageContext.request.contextPath}';
//
//        $(function () {
//            $("#order-date").datepicker();
//        });
//        
//        $(function () {
//            $("#edit-order-order-date").datepicker();
//        });
        
        
    </script>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>

    <script>
//            var element = document.getElementById('stateSelect');
//            element.value = 3;
    </script>

</body>
</html>

