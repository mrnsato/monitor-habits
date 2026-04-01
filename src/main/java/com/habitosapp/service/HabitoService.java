package com.habitosapp.service;

import com.habitosapp.dto.CriarHabitoRequest;
import com.habitosapp.dto.HabitoResponse;
import com.habitosapp.model.Habito;
import com.habitosapp.model.Usuario;
import com.habitosapp.repository.HabitoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitoService {
    private final HabitoRepository habitoRepository;
    private final UsuarioService usuarioService;

    public HabitoResponse criarHabito(Long usuarioId, CriarHabitoRequest request) {
        Usuario usuario = usuarioService.obterUsuarioEntity(usuarioId);
        
        Habito habito = Habito.builder()
                .usuario(usuario)
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .pontosPorConclusao(request.getPontosPorConclusao())
                .build();
        
        habito = habitoRepository.save(habito);
        return mapToResponse(habito);
    }

    public List<HabitoResponse> listarHabitos(Long usuarioId) {
        usuarioService.obterUsuarioEntity(usuarioId); // Validar se usuário existe
        
        return habitoRepository.findByUsuarioIdAndAtivoTrue(usuarioId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public HabitoResponse obterHabito(Long habitoId) {
        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new EntityNotFoundException("Hábito não encontrado"));
        return mapToResponse(habito);
    }

    public HabitoResponse atualizarHabito(Long habitoId, CriarHabitoRequest request) {
        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new EntityNotFoundException("Hábito não encontrado"));
        
        habito.setTitulo(request.getTitulo());
        habito.setDescricao(request.getDescricao());
        habito.setPontosPorConclusao(request.getPontosPorConclusao());
        
        habito = habitoRepository.save(habito);
        return mapToResponse(habito);
    }

    public void desativarHabito(Long habitoId) {
        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new EntityNotFoundException("Hábito não encontrado"));
        habito.setAtivo(false);
        habitoRepository.save(habito);
    }

    public List<Habito> obterHabitosAtivosDoUsuario(Long usuarioId) {
        return habitoRepository.findByUsuarioIdAndAtivoTrue(usuarioId);
    }

    private HabitoResponse mapToResponse(Habito habito) {
        return HabitoResponse.builder()
                .id(habito.getId())
                .titulo(habito.getTitulo())
                .descricao(habito.getDescricao())
                .ativo(habito.getAtivo())
                .pontosPorConclusao(habito.getPontosPorConclusao())
                .dataCriacao(habito.getDataCriacao())
                .build();
    }
}
