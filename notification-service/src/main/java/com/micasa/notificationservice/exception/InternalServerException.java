package com.micasa.notificationservice.exception;

import lombok.*;

/**
 * InternalServerException class.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternalServerException extends RuntimeException
{
    private String message;
    private long timestamp;
    private String fieldValue;
    private String fieldName;
    private String moreInfo;
}
