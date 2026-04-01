package com.habitosapp.service;

import com.habitosapp.dto.CriarUsuarioRequest;
import com.habitosapp.dto.UsuarioResponse;
import com.habitosapp.model.Usuario;
import com.habitosapp.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse criarUsuario(CriarUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .build();

        usuario = usuarioRepository.save(usuario);
        return mapToResponse(usuario);
    }

    public UsuarioResponse obterUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return mapToResponse(usuario);
    }

    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Usuario obterUsuarioEntity(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Usuario obterUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public void adicionarPontos(Long usuarioId, Long pontos) {
        Usuario usuario = obterUsuarioEntity(usuarioId);
        usuario.setPontos(usuario.getPontos() + pontos);
        
        // Calcular novo nível (a cada 100 pontos, sobe um nível)
        Integer novoNivel = (int) (usuario.getPontos() / 100) + 1;
        usuario.setNivel(novoNivel);
        
        usuarioRepository.save(usuario);
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .pontos(usuario.getPontos())
                .nivel(usuario.getNivel())
                .build();
    }
}
