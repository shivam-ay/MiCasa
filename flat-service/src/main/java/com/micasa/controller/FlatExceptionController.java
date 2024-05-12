package com.micasa.controller;

import com.micasa.constants.ExceptionMessages;
import com.micasa.exception.BadRequestException;
import com.micasa.exception.ExceptionModel;
import com.micasa.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Global exception handler.
 */
@Slf4j
@ControllerAdvice
public class FlatExceptionController
{
    /**
     * Exception handler to handle BadRequestException
     * Structure:
     *  1. Build exceptionModel with exception object.
     *  2. Build a exceptionResponse.
     *  3. Build a responseEntity.
     *  4. Return responseEntity, HttpStatus: 400;
     * @param exception : Exception object.
     * @return : ResponseEntity with status 400.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException exception)
    {
        ExceptionModel exceptionModel = ExceptionModel.builder()
                .fieldName(exception.getFieldName())
                .message(exception.getMessage().concat(exception.getFieldValue()))
                .timestamp(exception.getTimestamp())
                .moreInfo(exception.getMoreInfo())
                .build();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionResponse(List.of(exceptionModel)).build();
        log.error("Handled BadRequestException, sending exception response with status: {}", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleSQLIntegrityConstraintViolationException
                                                                (DataIntegrityViolationException exception)
    {
        ConstraintViolationException violationException = null;
        if(exception.getCause().getClass().equals(ConstraintViolationException.class))
        {
            violationException = (ConstraintViolationException) exception.getCause();
        }
        log.info(violationException.getConstraintName());
        log.info(violationException.getSQL());
        log.info(violationException.getErrorMessage());
//        exception.
        ExceptionModel exceptionModel = ExceptionModel.builder()
                .message(exception.getLocalizedMessage())
                .moreInfo(exception.getMessage())
//                .fieldName(exception)
                .build();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionResponse(List.of(exceptionModel)).build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(TransientPropertyValueException.class)
    public ResponseEntity<ExceptionResponse> handleTransientPropertyValueException(TransientPropertyValueException exception)
    {
        String propertyName = exception.getPropertyName();
        ExceptionModel exceptionModel = ExceptionModel.builder()
                .timestamp(System.currentTimeMillis())
                .message(String.format(ExceptionMessages.TRANSIENT_PROPERTY_MSG, propertyName, propertyName))
                .moreInfo(exception.getMessage())
                .fieldName(propertyName)
                .build();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionResponse(List.of(exceptionModel)).build();
        log.error("Handled TransientPropertyValueException, sending exception response with status: {}", HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}