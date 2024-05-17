package com.micasa.notificationservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Exception model format.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionModel
{
    private String message;
    private long timestamp;
    private String fieldName;
    private String moreInfo;
}
