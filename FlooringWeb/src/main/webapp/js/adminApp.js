/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    $('#tax-create-submit').on('click', function (e) {                 //any object with a css selector that matches in ' ' make an array out of it
        e.preventDefault();
        var orderData = JSON.stringify({
            state: $('#state').val(),
            taxRate: $('#tax-rate').val()
        });
        $.ajax({
            url: contextRoot + "/admin/tax/",
            type: "POST",
            data: orderData, //this is data being sent from client to server
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json"); //setting the accept header because we are sending a JSON file
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success:
                    function (data, status) {

                        var tableRow = buildTaxRow(data);
                        $('#taxes-table').append($(tableRow));

                        //clears user inputed values from our form
                        $('#state').val('');
                        $('#tax-rate').val('');

                        //clears out validation error messages and css
                        $('#tax-rate-div').removeClass('has-error');
                        $('#tax-rate-error-message').hide();

                    },
            error: function (data, status) {

                //clears out validation error messages and css
                $('#tax-rate-div').removeClass('has-error');
                $('#tax-rate-error-message').hide();

                var errors = data.responseJSON.errors;

                //if validation error change styling for matching field to indicate to user
                $.each(errors, function (index, error) {

                    if (error.fieldName === "taxRate") {
                        $('#tax-rate-div').addClass('has-error');
                        $('#tax-rate-error-message').show();
                    }

                });

            }

        });
//        alert("alert after ajax");

    });

    $('#editTaxModal').on('show.bs.modal', function (e) {

        var link = $(e.relatedTarget);
        var orderId = link.data('tax-id');
        $.ajax({
            url: contextRoot + "/admin/tax/" + orderId,
            type: 'GET',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function (data, status) {

                $('#edit-state').val(data.state); //make drop down
                $('#edit-tax-rate').val(data.taxRate); //make drop down
                $('#edit-id').val(data.id);
            },
            error: function (data, status) {
                console.log(data);
            }


        });
    });

    $('#edit-tax-button').on('click', function (e) {

        var orderData = JSON.stringify({
            id: $('#edit-id').val(),
            state: $('#edit-state').val(),
            taxRate: $('#edit-tax-rate').val()
        });
        $.ajax({
            url: contextRoot + "/admin/tax/",
            type: "PUT",
            data: orderData,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json"); //setting the accept header because we are sending a JSON file
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success: function (data, status) {

                $('#editTaxModal').modal('hide');
                var tableRow = buildTaxRow(data);
                $('#tax-row-' + data.id).replaceWith($(tableRow));
                $('#tax-rate-edit-error-message').hide();


            },
            error: function (data, status) {

                $('#tax-rate-edit-error-message').hide();
                var errors = data.responseJSON.errors;
                console.log(errors);
                //if validation error change styling for matching field to indicate to user
                $.each(errors, function (index, error) {

                    if (error.fieldName === "taxRate") {
                        $('#tax-rate-edit-error-message').show();
                    }

                });
            }

        });
//        alert("alert after ajax");

    });


    $(document).on('click', '.delete-link', function (e) {     //we need to do this because '.delete-link' is NOT in the DOM....created via JS, SOOO here we bind to the document instead of '.delete-link' and then pass '.delete-link' as a parameter to .on which delegates, meaning, tells the DOM to communicate to '.delete-link' which is not part of the DOM
        console.log('got here');
        e.preventDefault();
        var taxId = $(e.target).data('tax-id');
        $.ajax({
            type: "DELETE",
            url: contextRoot + "/admin/tax/" + taxId,
            success: function (data, status) {
                console.log(taxId);
                console.log('got success');
                $('#tax-row-' + taxId).remove();

            },
            error: function (data, status) {
                console.log('got error');

            }




        });
    });

    $('#product-create-submit').on('click', function (e) {                 //any object with a css selector that matches in ' ' make an array out of it
        e.preventDefault();
        var productData = JSON.stringify({
            productType: $('#product-type').val(),
            materialCostPerSf: $('#material-cost-sf').val(),
            laborCostPerSf: $('#labor-cost-sf').val()
        });
        $.ajax({
            url: contextRoot + "/admin/product/",
            type: "POST",
            data: productData, //this is data being sent from client to server
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json"); //setting the accept header because we are sending a JSON file
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success:
                    function (data, status) {

                        var tableRow = buildProductRow(data);
                        $('#products-table').append($(tableRow));

                        //clears user inputed values from our form
                        $('#product-type').val('');
                        $('#material-cost-sf').val('');
                        $('#labor-cost-sf').val('');

                        //clears out validation error messages and css
                        $('#product-type-div').removeClass('has-error');
                        $('#product-type-error-message').hide();
                        $('#material-cost-sf-div').removeClass('has-error');
                        $('#material-cost-sf-error-message').hide();
                        $('#labor-cost-sf-div').removeClass('has-error');
                        $('#labor-cost-sf-error-message').hide();

                    },
            error: function (data, status) {

                //clears out validation error messages and css
                $('#product-type-div').removeClass('has-error');
                $('#product-type-error-message').hide();
                $('#material-cost-sf-div').removeClass('has-error');
                $('#material-cost-sf-error-message').hide();
                $('#labor-cost-sf-div').removeClass('has-error');
                $('#labor-cost-sf-error-message').hide();

                var errors = data.responseJSON.errors;

                //if validation error change styling for matching field to indicate to user
                $.each(errors, function (index, error) {

                    if (error.fieldName === "productType") {
                        $('#product-type-div').addClass('has-error');
                        $('#product-type-error-message').show();
                    }
                    if (error.fieldName === "materialCostPerSf") {
                        $('#material-cost-sf-div').addClass('has-error');
                        $('#material-cost-sf-error-message').show();
                    }
                    if (error.fieldName === "laborCostPerSf") {
                        $('#labor-cost-sf-div').addClass('has-error');
                        $('#labor-cost-sf-error-message').show();
                    }

                });

            }

        });
//        alert("alert after ajax");

    });

    $('#editProductModal').on('show.bs.modal', function (e) {

        var link = $(e.relatedTarget);
        var productId = link.data('product-id');
        $.ajax({
            url: contextRoot + "/admin/product/" + productId,
            type: 'GET',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function (data, status) {

                $('#edit-product-type').val(data.productType);
                $('#edit-material-cost-sf').val(data.materialCostPerSf);
                $('#edit-labor-cost-sf').val(data.laborCostPerSf);
                $('#edit-id').val(data.id);
            },
            error: function (data, status) {
                console.log(data);
            }


        });
    });

    $('#edit-product-button').on('click', function (e) {

        var productData = JSON.stringify({
            id: $('#edit-id').val(),
            productType: $('#edit-product-type').val(),
            materialCostPerSf: $('#edit-material-cost-sf').val(),
            laborCostPerSf: $('#edit-labor-cost-sf').val()
        });
        $.ajax({
            url: contextRoot + "/admin/product/",
            type: "PUT",
            data: productData,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json"); //setting the accept header because we are sending a JSON file
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success: function (data, status) {

                $('#editProductModal').modal('hide');
                var tableRow = buildProductRow(data);
                $('#product-row-' + data.id).replaceWith($(tableRow));
                $('#product-type-edit-error-message').hide();
                $('#material-cost-sf-edit-error-message').hide();
                $('#labor-cost-sf-edit-error-message').hide();

            },
            error: function (data, status) {

                $('#product-type-edit-error-message').hide();
                $('#material-cost-sf-edit-error-message').hide();
                $('#labor-cost-sf-edit-error-message').hide();
                var errors = data.responseJSON.errors;
                console.log(errors);
                //if validation error change styling for matching field to indicate to user
                $.each(errors, function (index, error) {

                    if (error.fieldName === "productType") {
                        $('#product-type-edit-error-message').show();
                    }
                    if (error.fieldName === "materialCostPerSf") {
                        $('#material-cost-sf-edit-error-message').show();
                    }
                    if (error.fieldName === "laborCostPerSf") {
                        $('#labor-cost-sf-edit-error-message').show();
                    }


                });
            }

        });
//        alert("alert after ajax");

    });

    $(document).on('click', '.delete-link', function (e) {     //we need to do this because '.delete-link' is NOT in the DOM....created via JS, SOOO here we bind to the document instead of '.delete-link' and then pass '.delete-link' as a parameter to .on which delegates, meaning, tells the DOM to communicate to '.delete-link' which is not part of the DOM
        console.log('got here');
        e.preventDefault();
        var productId = $(e.target).data('product-id');
        $.ajax({
            type: "DELETE",
            url: contextRoot + "/admin/product/" + productId,
            success: function (data, status) {
                console.log(productId);
                console.log('got success');
                $('#product-row-' + productId).remove();

            },
            error: function (data, status) {
                console.log('got error');

            }




        });
    });


    function buildTaxRow(data) {

        return "<tr id='tax-row-" + data.id + "'>  \n\
                <td> " + data.state + "</td>  \n\
                <td> " + data.taxRate + " %</td>    \n\
                <td> <a data-tax-id='" + data.id + "' data-toggle='modal' data-target='#editTaxModal'>Edit</a>  </td>   \n\
                <td> <a data-tax-id='" + data.id + "' class='delete-link'>Delete</a>  </td>   \n\
                </tr>  ";
    }

    function buildProductRow(data) {

        return "<tr id='product-row-" + data.id + "'>  \n\
                <td> " + data.productType + "</td>  \n\
                <td>$ " + data.materialCostPerSf + "</td>    \n\
                <td>$ " + data.laborCostPerSf + "</td>    \n\
                <td> <a data-product-id='" + data.id + "' data-toggle='modal' data-target='#editProductModal'>Edit</a>  </td>   \n\
                <td> <a data-product-id='" + data.id + "' class='delete-link'>Delete</a>  </td>   \n\
                </tr>  ";
    }


});
