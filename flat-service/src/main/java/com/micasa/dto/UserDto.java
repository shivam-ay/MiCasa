package com.micasa.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
}