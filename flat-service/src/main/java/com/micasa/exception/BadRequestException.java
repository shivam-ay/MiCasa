package com.micasa.exception;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadRequestException extends RuntimeException
{
    private String message;
    private long timestamp;
    private String fieldValue;
    private String fieldName;
    private String moreInfo;
}
