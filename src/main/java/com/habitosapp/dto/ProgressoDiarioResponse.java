package com.habitosapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressoDiarioResponse {
    private Long id;
    private LocalDate data;
    private Integer habitosConcluidos;
    private Integer totalHabitos;
    private Double percentualConclusao;
    private Long pontosGanhos;
    private Boolean elixirRecompensa;
}
