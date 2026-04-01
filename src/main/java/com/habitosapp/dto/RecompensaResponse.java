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
public class RecompensaResponse {
    private Long id;
    private String descricao;
    private LocalDateTime dataCriacao;
}
