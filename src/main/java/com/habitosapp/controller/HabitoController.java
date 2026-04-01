package com.habitosapp.controller;

import com.habitosapp.dto.CriarHabitoRequest;
import com.habitosapp.dto.HabitoResponse;
import com.habitosapp.service.HabitoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitos")
@RequiredArgsConstructor
public class HabitoController {
    private final HabitoService habitoService;

    @PostMapping
    public ResponseEntity<HabitoResponse> criar(
            @RequestParam Long usuarioId,
            @Valid @RequestBody CriarHabitoRequest request) {
        HabitoResponse response = habitoService.criarHabito(usuarioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<HabitoResponse>> listar(@RequestParam Long usuarioId) {
        List<HabitoResponse> response = habitoService.listarHabitos(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{habitoId}")
    public ResponseEntity<HabitoResponse> obter(@PathVariable Long habitoId) {
        HabitoResponse response = habitoService.obterHabito(habitoId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{habitoId}")
    public ResponseEntity<HabitoResponse> atualizar(
            @PathVariable Long habitoId,
            @Valid @RequestBody CriarHabitoRequest request) {
        HabitoResponse response = habitoService.atualizarHabito(habitoId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{habitoId}")
    public ResponseEntity<Void> desativar(@PathVariable Long habitoId) {
        habitoService.desativarHabito(habitoId);
        return ResponseEntity.noContent().build();
    }
}
