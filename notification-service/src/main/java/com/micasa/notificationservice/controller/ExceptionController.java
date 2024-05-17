package com.micasa.notificationservice.controller;

import com.micasa.notificationservice.exception.ExceptionModel;
import com.micasa.notificationservice.exception.ExceptionResponse;
import com.micasa.notificationservice.exception.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Global exception handler
 */
@Slf4j
@ControllerAdvice
public class ExceptionController
{

    /**
     * Exception handler to handle InternalServerException
     * Structure:
     *  1. Build exceptionModel with exception object.
     *  2. Build a exceptionResponse.
     *  3. Build a responseEntity.
     *  4. Return responseEntity, HttpStatus: 500;
     * @param exception : Exception object.
     * @return : ResponseEntity with status 500.
     */
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ExceptionResponse> handleInternalServerException(InternalServerException exception)
    {
        ExceptionModel exceptionModel = ExceptionModel.builder()
                .fieldName(exception.getFieldName())
                .message(exception.getMessage())
                .timestamp(exception.getTimestamp())
                .moreInfo(exception.getMoreInfo())
                .build();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionResponse(List.of(exceptionModel)).build();
        log.error("Handled InternalServerException, sending exception response with status: {}", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
