package com.micasa.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlatDto implements Serializable
{
    private String flatId;
    @NotBlank(message = "flatNumber can not be blank")
    @Column(name = "flat_number")
    private String flatNumber;
    @NotBlank(message = "flatBlock can not be blank.")
    @Column(name = "flat_block")
    private String flatBlock;
    private boolean occupied;
}
