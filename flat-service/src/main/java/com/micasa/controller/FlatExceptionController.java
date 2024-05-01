package com.micasa.controller;

import com.micasa.exception.BadRequestException;
import com.micasa.exception.ExceptionModel;
import com.micasa.exception.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Global exception handler.
 */
@ControllerAdvice
public class FlatExceptionController
{
    /**
     * Exception handler to handle BadRequestException
     * Structure:
     *  1. Build exceptionModel with badRequestException object.
     *  2. Build a exceptionResponse.
     *  3. Build a responseEntity.
     *  4. Return responseEntity, HttpStatus: 400;
     * @param badRequestException : Exception object.
     * @return : ResponseEntity with status 400.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException badRequestException)
    {
        ExceptionModel exceptionModel = ExceptionModel.builder()
                .fieldName(badRequestException.getFieldName())
                .message(badRequestException.getMessage().concat(badRequestException.getFieldValue()))
                .timestamp(badRequestException.getTimestamp())
                .moreInfo(badRequestException.getMoreInfo())
                .build();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionResponse(List.of(exceptionModel)).build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
