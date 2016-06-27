/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//JQuery function that runs when the DOM has been fully built in memory
$(document).ready(function () {

    //this numerically sorts the drop down menu options upon page load
    $("#date-select").html($("#date-select option").sort(function (a, b) {
        return a.text == b.text ? 0 : a.text < b.text ? -1 : 1;
    }));

    //if no orders exist on page re/load display no orders exist
    var length = $('#date-select > option').length;
    if (length === 1) {
        $('#no-order-alert').show();
    }

    //Formats dates from YYYY-mm-dd to mm/dd/YYYY by creating an array of formatted dates, then loop through again to replace values
    var i = 0;
    var dateArray = [];
    $("#date-select > option").each(function () {
        if (this.value !== "") {
            dateArray[i] = this.value;
            i++;
        }
    });

    $("#date-select").empty();
    for (i = 0; i < dateArray.length; i++) {

        if (this.value !== "") {
            var option = buildOption(dateArray[i]);
            $('#date-select').append($(option));
        }
    }


    $('#date-select').on('change', function () {

        var selectedDate = this.value; //gets value of #dateSelect dropdown
        if (selectedDate !== "") {               //check to make sure the newly clicked dropdown option is a date and not the "select order..." placeholder
            console.log(selectedDate);
            $.ajax({
                url: contextRoot + "/order/search/" + selectedDate,
                type: "GET",
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Accept", "application/json"); // specifying that the client is recieving a json object from the server; setting the accept header because we are sending a JSON file
                    xhr.setRequestHeader("Content-type", "application/json"); //specifying that I am sending a JSON from client to server, not needed in this specific case but does not hurt to have
                },
                success:
                        function (data, status) {
                            //remove current display of orders, keep header
                            $("#orders-table tbody").empty();

                            //display new orders by looping through data, which is an ArrayList of Orders
                            for (var i = 0, len = data.length; i < len; i++) {
                                var tableRow = buildOrderRow(data[i]);
                                $('#orders-table').append($(tableRow));
                                console.log(i);
                            }
                        },
                error: function (data, status) {
                    console.log(data);
                }
            });
        }
    });


    $('#create-submit').on('click', function (e) {
        e.preventDefault();
        var orderData = JSON.stringify({
            customerName: $('#customer-name').val(),
            state: $('#state-select').val(),
            productType: $('#product-select').val(),
            area: $('#area').val(),
            orderDate: $('#order-date').val()

        });
        console.log('starting ajax');
        $.ajax({
            url: contextRoot + "/order/",
            type: "POST",
            data: orderData, //this is data being sent from client to server
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json"); //setting the accept header because we are sending a JSON file
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success:
                    function (data, status) {
                        console.log('success');
                        if ($('#date-select').val() === data.orderDate) {
                            var tableRow = buildOrderRow(data);
                            $('#orders-table').append($(tableRow));
                        } else {
                            //if the date does not exist in the drop down, then make it!!  loop through comparing with boolean
                            if (!$("#date-select option[value='" + data.orderDate + "']").length > 0) {   //this statement evaluates to true with ! if value not in drop down
                                var option = buildOption(data.orderDate);
                                $('#date-select').append($(option));
                                //this sorts the list of dropdown dates after an order with a date not currently in the system exists
                                $("#date-select").html($("#date-select option").sort(function (a, b) {
                                    return a.text == b.text ? 0 : a.text < b.text ? -1 : 1;
                                }));
                            }
                        }
//                        var length = $('#date-select > option').length; //if no orders exist on page re/load display no orders exist
//                        if (length > 0) {
//                            
//                        }


                        $('#customer-name').val(''); //clears user inputed values from our form
                        $('#area').val('');
                        $('#order-date').val('');
                        var length = $('#date-select > option').length; //hide noOrdersExist alert if an order is added
                        if (length > 1) {
                            $('#no-order-alert').hide();
                        }

                        //clears out validation error messages and css
                        $('#customer-name-div').removeClass('has-error');
                        $('#customer-name-error-message').hide();
                        $('#area-div').removeClass('has-error');
                        $('#area-error-message').hide();


                    },
            error: function (data, status) {

                alert('error create');
                //clears out validation error messages and css
                $('#customer-name-div').removeClass('has-error');
                $('#customer-name-error-message').hide();
                $('#area-div').removeClass('has-error');
                $('#area-error-message').hide();
                var errors = data.responseJSON.errors;
                console.log(errors);
                //if validation error change styling for matching field to indicate to user
                $.each(errors, function (index, error) {

                    if (error.fieldName === "customerName") {
                        $('#customer-name-div').addClass('has-error');
                        $('#customer-name-error-message').show();
                    }
                    if (error.fieldName === "area") {
                        $('#area-div').addClass('has-error');
                        $('#area-error-message').show();
                    }
                    if (error.fieldName === "orderDate") {
                        $('#customer-name-div').addClass('has-error');
                        $('#customer-name-error-message').show();
                    }




                });
//                $.each(errors, function (index, error) {
//
//                    $('#add-order-validation-errors').append(error.fieldName + ": " + error.message + "<br />");
//
//
//                });

            }



        });
//        alert("alert after ajax");

    });


    $('#showOrderModal').on('show.bs.modal', function (e) {

        var link = $(e.relatedTarget);
        var orderId = link.data('order-id');
        $.ajax({
            url: contextRoot + "/order/" + orderId,
            type: 'GET',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function (data, status) {
                $('#order-customer-name').text(data.customerName);
                $('#order-state').text(data.state);
                $('#order-tax-rate').text(data.taxRate + " %");
                $('#order-product-type').text(data.productType);
                $('#order-area').text(data.area);
                $('#order-material-cost-sf').text("$ " + data.materialCostPerSf);
                $('#order-labor-cost-sf').text("$  " + data.laborCostPerSf);
                $('#order-material-cost').text("$ " + data.materialCost);
                $('#order-labor-cost').text("$ " + data.laborCost);
                $('#order-tax').text("$ " + data.totalTax);
                $('#order-total-cost').text("$ " + data.totalCost);
                $('#order-order-date').text(data.orderDate);
            },
            error: function (data, status) {
                console.log(data);
                alert('errorShow');
            }


        });
    });
    $('#editOrderModal').on('show.bs.modal', function (e) {

        var link = $(e.relatedTarget);
        var orderId = link.data('order-id');
        $.ajax({
            url: contextRoot + "/order/" + orderId,
            type: 'GET',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function (data, status) {

                $('#edit-order-customer-name').val(data.customerName);
                $('#edit-order-state').val(data.state); //make drop down
//                $('#edit-order-tax-rate').text(data.taxRate);
                $('#edit-order-product-type').val(data.productType); //make drop down
                $('#edit-order-area').val(data.area);
//                $('#edit-order-material-cost-sf').text("$ " + data.materialCostPerSf);
//                $('#edit-order-labor-cost-sf').text("$  " + data.laborCostPerSf);
                $('#edit-order-material-cost').text("$ " + data.materialCost);
                $('#edit-order-labor-cost').text("$ " + data.laborCost);
                $('#edit-order-tax').text("$ " + data.totalTax);
                $('#edit-order-total-cost').text("$ " + data.totalCost);
                $('#edit-order-order-date').val(data.orderDate);
                $('#edit-id').val(data.id);
            },
            error: function (data, status) {
                alert('editError');
                console.log(data);
            }


        });
    });
    $('#edit-order-button').on('click', function (e) {

        var oldOrderDate = $('#edit-order-order-date').val();
        var orderData = JSON.stringify({
            id: $('#edit-id').val(),
            customerName: $('#edit-order-customer-name').val(),
            state: $('#edit-order-state').val(),
            productType: $('#edit-order-product-type').val(),
            area: $('#edit-order-area').val(),
            orderDate: $('#edit-order-order-date').val()

        });
        $.ajax({
            url: contextRoot + "/order/",
            type: "PUT",
            data: orderData,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json"); //setting the accept header because we are sending a JSON file
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success: function (data, status) {

                $('#editOrderModal').modal('hide');
                var tableRow = buildOrderRow(data);
                $('#order-row-' + data.id).replaceWith($(tableRow));
                if (oldOrderDate !== data.orderDate) {
                    location.reload();
                }

            },
            error: function (data, status) {

                $('#customer-name-edit-error-message').hide();
                $('#area-edit-error-message').hide();
                var errors = data.responseJSON.errors;
                console.log(errors);
                //if validation error change styling for matching field to indicate to user
                $.each(errors, function (index, error) {

                    if (error.fieldName === "customerName") {
                        $('#customer-name-edit-error-message').show();
                    }
                    if (error.fieldName === "area") {
                        $('#area-edit-error-message').show();
                    }

                });
            }

        });
//        alert("alert after ajax");

    });
    $(document).on('click', '.delete-link', function (e) {     //we need to do this because '.delete-link' is NOT in the DOM....created via JS, SOOO here we bind to the document instead of '.delete-link' and then pass '.delete-link' as a parameter to .on which delegates, meaning, tells the DOM to communicate to '.delete-link' which is not part of the DOM

        e.preventDefault();
        var orderId = $(e.target).data('order-id');
        $.ajax({
            type: "DELETE",
            url: contextRoot + "/order/" + orderId,
            success: function (data, status) {
                console.log(orderId);
                $('#order-row-' + orderId).remove();
                if ($('.orders-table-row-check').children().length == 0) {
                    location.reload(); //reloads page   ....now display orders for date
                }

            },
            error: function (data, status) {

            }




        });
    });
    function buildOption(date) {
        return "<option value='" + dateFormatterUrl(date) + "'>" + dateFormatter(date) + "</option>";
    }

    function buildOrderRow(data) {

        return "<tr id='order-row-" + data.id + "'>  \n\
                <td><a data-order-id='" + data.id + "' data-toggle='modal' data-target='#showOrderModal'>" + data.customerName + "</a></td>  \n\
                <td> " + dateFormatter(data.orderDate) + "</td>    \n\
                <td> " + "$ " + Number(data.totalCost).toFixed(2) + "</td>    \n\
                <td> <a data-order-id='" + data.id + "' data-toggle='modal' data-target='#editOrderModal'>Edit</a>  </td>   \n\
                <td> <a data-order-id='" + data.id + "' class='delete-link'>Delete</a>  </td>   \n\
                </tr>  ";
    }

    function dateFormatter(date) {
        var month = date.substring(5, 7);
        var day = date.substring(8, 10);
        var year = date.substring(0, 4);

        return month + "/" + day + "/" + year;

    }

    function dateFormatterUrl(date) {
        var month = date.substring(5, 7);
        var day = date.substring(8, 10);
        var year = date.substring(0, 4);

        return month + day + year;

    }


});
