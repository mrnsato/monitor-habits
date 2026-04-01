package com.habitosapp.service;

import com.habitosapp.dto.CriarRecompensaRequest;
import com.habitosapp.dto.RecompensaResponse;
import com.habitosapp.model.Recompensa;
import com.habitosapp.model.RecompensaRecebida;
import com.habitosapp.model.Usuario;
import com.habitosapp.repository.RecompensaRecebidaRepository;
import com.habitosapp.repository.RecompensaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecompensaService {
    private final RecompensaRepository recompensaRepository;
    private final RecompensaRecebidaRepository recompensaRecebidaRepository;
    private final UsuarioService usuarioService;
    private final ProgressoDiarioService progressoDiarioService;

    public RecompensaResponse criarRecompensa(Long usuarioId, CriarRecompensaRequest request) {
        Usuario usuario = usuarioService.obterUsuarioEntity(usuarioId);
        
        Recompensa recompensa = Recompensa.builder()
                .usuario(usuario)
                .descricao(request.getDescricao())
                .build();
        
        recompensa = recompensaRepository.save(recompensa);
        return mapToResponse(recompensa);
    }

    public List<RecompensaResponse> listarRecompensas(Long usuarioId) {
        usuarioService.obterUsuarioEntity(usuarioId);
        
        return recompensaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RecompensaResponse sortearRecompensa(Long usuarioId) {
        // Verificar se o usuário atingiu 80% de conclusão
        Boolean podeReceberRecompensa = progressoDiarioService.verificarSeElixirRecompensa(usuarioId);
        if (!podeReceberRecompensa) {
            throw new IllegalStateException("Usuário não atingiu 80% de conclusão dos hábitos hoje");
        }

        List<Recompensa> recompensas = recompensaRepository.findByUsuarioId(usuarioId);
        if (recompensas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma recompensa cadastrada para este usuário");
        }

        // Sortear uma recompensa aleatoriamente
        Random random = new Random();
        int index = random.nextInt(recompensas.size());
        Recompensa recompensaSorteada = recompensas.get(index);

        // Registrar a recompensa recebida
        Usuario usuario = usuarioService.obterUsuarioEntity(usuarioId);
        RecompensaRecebida recompensaRecebida = RecompensaRecebida.builder()
                .usuario(usuario)
                .recompensa(recompensaSorteada)
                .build();
        recompensaRecebidaRepository.save(recompensaRecebida);

        return mapToResponse(recompensaSorteada);
    }

    public List<RecompensaResponse> listarRecompensasRecebidas(Long usuarioId) {
        usuarioService.obterUsuarioEntity(usuarioId);
        
        return recompensaRecebidaRepository.findByUsuarioIdOrderByDataDesc(usuarioId)
                .stream()
                .map(rr -> mapToResponse(rr.getRecompensa()))
                .collect(Collectors.toList());
    }

    private RecompensaResponse mapToResponse(Recompensa recompensa) {
        return RecompensaResponse.builder()
                .id(recompensa.getId())
                .descricao(recompensa.getDescricao())
                .dataCriacao(recompensa.getDataCriacao())
                .build();
    }
}
