/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.validation;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author apprentice
 */
@ControllerAdvice         //telling spring that this is our advice.....we did XML before for advice, here is by annotation
public class RestValidationHandler {

    //we need a Java object to serialize into a JSON array
    @ExceptionHandler(MethodArgumentNotValidException.class)          //telling spring that our advice should be used whenever this error happens
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody  //serialize with JSON
    public ValidationErrorContainer processValidationErrors(MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();  //grab errors off of binding result

        ValidationErrorContainer container = new ValidationErrorContainer();

        for (FieldError error : fieldErrors) {

            ValidationError valError = new ValidationError();
            valError.setFieldName(error.getField());
            valError.setMessage(error.getDefaultMessage());

            container.addError(valError);
        }
        return container;
    }
}
