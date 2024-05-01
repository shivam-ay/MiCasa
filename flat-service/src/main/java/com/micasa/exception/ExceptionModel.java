package com.micasa.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
