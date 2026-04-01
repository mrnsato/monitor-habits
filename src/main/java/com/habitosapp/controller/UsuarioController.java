package com.habitosapp.controller;

import com.habitosapp.dto.CriarUsuarioRequest;
import com.habitosapp.dto.UsuarioResponse;
import com.habitosapp.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody CriarUsuarioRequest request) {
        UsuarioResponse response = usuarioService.criarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponse> obter(@PathVariable Long usuarioId) {
        UsuarioResponse response = usuarioService.obterUsuario(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        List<UsuarioResponse> response = usuarioService.listarUsuarios();
        return ResponseEntity.ok(response);
    }
}
