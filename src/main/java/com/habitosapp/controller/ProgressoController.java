package com.habitosapp.controller;

import com.habitosapp.dto.ProgressoDiarioResponse;
import com.habitosapp.dto.RegistrarProgressoRequest;
import com.habitosapp.service.ProgressoDiarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progresso")
@RequiredArgsConstructor
public class ProgressoController {
    private final ProgressoDiarioService progressoDiarioService;

    @PostMapping
    public ResponseEntity<ProgressoDiarioResponse> registrarProgresso(
            @RequestParam Long usuarioId,
            @Valid @RequestBody RegistrarProgressoRequest request) {
        ProgressoDiarioResponse response = progressoDiarioService.registrarProgresso(usuarioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/hoje")
    public ResponseEntity<ProgressoDiarioResponse> obterProgressoDoHoje(@RequestParam Long usuarioId) {
        ProgressoDiarioResponse response = progressoDiarioService.obterProgressoDoHoje(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/historico")
    public ResponseEntity<List<ProgressoDiarioResponse>> obterHistoricoProgresso(
            @RequestParam Long usuarioId,
            @RequestParam(required = false) Integer dias) {
        List<ProgressoDiarioResponse> response = progressoDiarioService.obterHistoricoProgresso(usuarioId, dias);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/elixir-recompensa")
    public ResponseEntity<Boolean> verificarSeElixirRecompensa(@RequestParam Long usuarioId) {
        Boolean podeReceber = progressoDiarioService.verificarSeElixirRecompensa(usuarioId);
        return ResponseEntity.ok(podeReceber);
    }
}
