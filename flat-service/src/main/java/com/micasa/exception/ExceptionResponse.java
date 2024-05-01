package com.micasa.exception;

import lombok.*;

/**
 * Exception response that will be sent in case of exception.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse
{
    Iterable<ExceptionModel> exceptionResponse;
}
