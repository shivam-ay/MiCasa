package com.micasa.exception;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse
{
    Iterable<ExceptionModel> exceptionResponse;
}
