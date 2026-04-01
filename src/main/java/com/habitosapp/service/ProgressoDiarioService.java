package com.habitosapp.service;

import com.habitosapp.dto.ProgressoDiarioResponse;
import com.habitosapp.dto.RegistrarProgressoRequest;
import com.habitosapp.model.ProgressoDiario;
import com.habitosapp.model.Usuario;
import com.habitosapp.repository.ProgressoDiarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgressoDiarioService {
    private final ProgressoDiarioRepository progressoDiarioRepository;
    private final UsuarioService usuarioService;

    public ProgressoDiarioResponse registrarProgresso(Long usuarioId, RegistrarProgressoRequest request) {
        Usuario usuario = usuarioService.obterUsuarioEntity(usuarioId);
        LocalDate hoje = LocalDate.now();

        ProgressoDiario progresso = progressoDiarioRepository
                .findByUsuarioIdAndData(usuarioId, hoje)
                .orElse(ProgressoDiario.builder()
                        .usuario(usuario)
                        .data(hoje)
                        .habitosConcluidos(0)
                        .totalHabitos(0)
                        .pontosGanhos(0L)
                        .build());

        progresso.setHabitosConcluidos(request.getHabitosConcluidos());
        progresso.setTotalHabitos(request.getTotalHabitos());

        // Calcular pontos ganhos
        if (progresso.getTotalHabitos() > 0) {
            Long pontosGanhos = (long) (progresso.getHabitosConcluidos() * 10);
            progresso.setPontosGanhos(pontosGanhos);
            
            // Adicionar pontos ao usuário
            usuarioService.adicionarPontos(usuarioId, pontosGanhos);
        }

        progresso = progressoDiarioRepository.save(progresso);
        return mapToResponse(progresso);
    }

    public ProgressoDiarioResponse obterProgressoDoHoje(Long usuarioId) {
        usuarioService.obterUsuarioEntity(usuarioId);
        LocalDate hoje = LocalDate.now();

        ProgressoDiario progresso = progressoDiarioRepository
                .findByUsuarioIdAndData(usuarioId, hoje)
                .orElseThrow(() -> new EntityNotFoundException("Progresso não registrado para hoje"));

        return mapToResponse(progresso);
    }

    public List<ProgressoDiarioResponse> obterHistoricoProgresso(Long usuarioId, Integer dias) {
        usuarioService.obterUsuarioEntity(usuarioId);
        
        List<ProgressoDiario> progresso = progressoDiarioRepository
                .findByUsuarioIdOrderByDataDesc(usuarioId);

        if (dias != null && dias > 0) {
            progresso = progresso.stream().limit(dias).collect(Collectors.toList());
        }

        return progresso.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Boolean verificarSeElixirRecompensa(Long usuarioId) {
        LocalDate hoje = LocalDate.now();
        ProgressoDiario progresso = progressoDiarioRepository
                .findByUsuarioIdAndData(usuarioId, hoje)
                .orElseThrow(() -> new EntityNotFoundException("Progresso não registrado para hoje"));

        return progresso.elixirRecompensa();
    }

    private ProgressoDiarioResponse mapToResponse(ProgressoDiario progresso) {
        return ProgressoDiarioResponse.builder()
                .id(progresso.getId())
                .data(progresso.getData())
                .habitosConcluidos(progresso.getHabitosConcluidos())
                .totalHabitos(progresso.getTotalHabitos())
                .percentualConclusao(progresso.getPercentualConclusao())
                .pontosGanhos(progresso.getPontosGanhos())
                .elixirRecompensa(progresso.elixirRecompensa())
                .build();
    }
}
