package com.micasa.notificationservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto
{
    private String to;
    private String subject;
    private String body;
    private String cc;
}
