<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   <!-- this tag allows you to do validation-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Flooring Mastery Admin Panel</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/starter-template.css" rel="stylesheet">

        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png">
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.min.css">-->
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


        <style>
            th {
                background-color: #449D44;
                color: #E7FFE1;
            }

            #tax-rate-error-message,
            #tax-rate-edit-error-message,
            #product-type-error-message,
            #product-type-edit-error-message,
            #material-cost-sf-error-message,
            #material-cost-sf-edit-error-message,
            #labor-cost-sf-error-message,
            #labor-cost-sf-edit-error-message
            {
                display: none;
            }

        </style>



    </head>
    <body>
        <div class="container">
            <h1>Flooring Mastery</h1>
            <hr/>


            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home"></span> Home</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/admin/"><span class="glyphicon glyphicon-dashboard"></span> Admin Panel</a></li>
                </ul>    
            </div>

            <div class="row">

                <div class="col-md-6">

                    <form method="POST" class="form-horizontal">
                        <input type="hidden" name="id" value="${tax.id}" />
                        <div class="form-group">
                            <label for="state" class="col-md-4 control-label">State</label>


                            <div class="col-md-8">
                                <select name="stateSelect" class="form-control" id="state" >
                                    <jsp:include page="statesForDropdown.jsp" />
                                </select>

                                <!--<input id="state" type="text" class="form-control" />-->
                            </div>
                        </div>
                        <div class="form-group" id="tax-rate-div">
                            <label for="taxRate" class="col-md-4 control-label">Tax Rate</label>
                            <div class="col-md-8">
                                <input id="tax-rate" type="text" class="form-control" />
                                <div id="tax-rate-error-message" class="text-danger">You must enter the percent tax rate</div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label"></label>
                            <div class="col-md-8">
                                <input id="tax-create-submit" type="submit" class="btn pull-right btn-success" value="Create State"/>
                            </div>
                        </div>

                    </form>
                </div>   

                <div class="col-md-6">
                    <form:form method="POST" commandName="product" action="${pageContext.request.contextPath}/admin/createProduct" class="form-horizontal">
                        <input type="hidden" name="id" value="${product.id}" />
                        <div class="form-group" id="product-type-div">
                            <label for="productType" class="col-md-4 control-lable">Material</label>
                            <div class="col-md-8">
                                <input id="product-type" type="text" class="form-control" />
                                <div id="product-type-error-message" class="text-danger">You must enter the product type</div>
                            </div>
                        </div>
                        <div class="form-group" id="material-cost-sf-div"> 
                            <label for="materialCostPerSf" class="col-md-4 control-lable">Material Cost Per Square Foot</label>
                            <div class="col-md-8">
                                <input id="material-cost-sf" type="text" class="form-control" />
                                <div id="material-cost-sf-error-message" class="text-danger">You must enter the material cost per square foot</div>
                            </div>
                        </div>
                        <div class="form-group" id="labor-cost-sf-div">
                            <label for="laborCostPerSf" class="col-md-4 control-lable">Labor Cost Per Square Foot</label>
                            <div class="col-md-8">
                                <input id="labor-cost-sf" type="text" class="form-control" />
                                <div id="labor-cost-sf-error-message" class="text-danger">You must enter the labor cost per square foot</div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label"></label>
                            <div class="col-md-8">
                                <input id="product-create-submit" type="submit" class="btn pull-right btn-success" value="Create Product"/>
                            </div>
                        </div>

                    </form:form>

                </div>

            </div>

            <div class="row">

                <div class="col-md-6">

                    <table id="taxes-table" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>State</th>
                                <th>Tax Rate</th>
                                <th><span class="glyphicon glyphicon-edit"></span> Edit</th>
                                <th><i class="glyphicon glyphicon-remove"></i> Delete</th>
                            </tr>
                        </thead>
                        <c:forEach items="${taxes}" var="tax">
                            <tr id="tax-row-${tax.id}">
                                <td>${tax.state}</td>
                                <td>${tax.taxRate} %</td>
                                <td><a data-tax-id="${tax.id}" data-toggle="modal" data-target=#editTaxModal>Edit</a></td>
                                <td><a data-tax-id="${tax.id}" class="delete-link">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>   

                <div class="col-md-6">

                    <table id="products-table" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>Material</th>
                                <th>Material Cost Per SF</th>
                                <th>Labor Cost Per SF</th>
                                <th><span class="glyphicon glyphicon-edit"></span> Edit</th>
                                <th><i class="glyphicon glyphicon-remove"></i> Delete</th>
                            </tr>
                        </thead>
                        <c:forEach items="${products}" var="product">
                            <tr id="product-row-${product.id}">
                                <td>${product.productType}</td>
                                <td>$ ${product.materialCostPerSf}</td>
                                <td>$ ${product.laborCostPerSf}</td>
                                <td><a data-product-id="${product.id}" data-toggle="modal" data-target=#editProductModal>Edit</a></td>
                                <td><a data-product-id="${product.id}" class="delete-link">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>  

            </div>

        </div>

        <div id="editTaxModal" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">State Tax Detail</h4>
              </div>
              <div class="modal-body">
                        <table class="table table-bordered" id="edit-tax-table">
                            <input type="hidden" id="edit-id"/>
                            <tr>
                                <th>State:</th>
                                <td>
                                    <select class="form-control" id="edit-state" >
                                        <jsp:include page="statesForDropdown.jsp" />
                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <th>Tax Rate:</th>
                                <td>
                                    <input type="text" id="edit-tax-rate" class="form-control"/>
                                    <div id="tax-rate-edit-error-message" class="text-danger">You must enter the percent tax rate</div>
                                </td>
                            </tr>

                        </table>
                    </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-danger" id="edit-tax-button"  >Save</button>
              </div>
                    <div id="add-tax-edit-validation-errors">
                    </div>
            </div>

          </div>
        </div>

        <div id="editProductModal" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Product Details</h4>
              </div>
              <div class="modal-body">
                        <table class="table table-bordered" id="edit-tax-table">
                            <input type="hidden" id="edit-id"/>
                            <tr>
                                <th>Material:</th>
                                <td>
                                    <input type="text" id="edit-product-type" class="form-control"/>
                                    <div id="product-type-edit-error-message" class="text-danger">You must enter the product type</div>
                                </td>
                            </tr>

                            <tr>
                                <th>Material Cost per Square Foot:</th>
                                <td>
                                    <input type="text" id="edit-material-cost-sf" class="form-control"/>
                                    <div id="material-cost-sf-edit-error-message" class="text-danger">You must enter the material cost per square foot</div>
                                </td>
                            </tr>

                            <tr>
                                <th>Labor Cost per Square Foot:</th>
                                <td>
                                    <input type="text" id="edit-labor-cost-sf" class="form-control"/>
                                    <div id="labor-cost-sf-edit-error-message" class="text-danger">You must enter the labor cost per square foot</div>
                                </td>
                            </tr>

                        </table>
                    </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-danger" id="edit-product-button"  >Save</button>
              </div>
            </div>

          </div>
        </div>

        <script>
            var contextRoot = '${pageContext.request.contextPath}';
        </script>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/adminApp.js"></script>

        <script>
//            var element = document.getElementById('stateSelect');
//            element.value = 3;
        </script>

    </body>
</html>

