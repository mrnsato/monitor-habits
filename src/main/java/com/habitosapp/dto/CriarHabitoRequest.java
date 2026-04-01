package com.habitosapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CriarHabitoRequest {
    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String descricao;

    @Builder.Default
    private Integer pontosPorConclusao = 10;
}
