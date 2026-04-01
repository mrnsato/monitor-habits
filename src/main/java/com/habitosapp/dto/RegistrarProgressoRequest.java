package com.habitosapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrarProgressoRequest {
    private Integer habitosConcluidos;
    private Integer totalHabitos;
}
