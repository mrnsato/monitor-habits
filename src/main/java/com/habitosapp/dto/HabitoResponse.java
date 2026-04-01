package com.habitosapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitoResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private Boolean ativo;
    private Integer pontosPorConclusao;
    private LocalDateTime dataCriacao;
}
