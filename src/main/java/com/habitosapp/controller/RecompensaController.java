package com.habitosapp.controller;

import com.habitosapp.dto.CriarRecompensaRequest;
import com.habitosapp.dto.RecompensaResponse;
import com.habitosapp.service.RecompensaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recompensas")
@RequiredArgsConstructor
public class RecompensaController {
    private final RecompensaService recompensaService;

    @PostMapping
    public ResponseEntity<RecompensaResponse> criar(
            @RequestParam Long usuarioId,
            @Valid @RequestBody CriarRecompensaRequest request) {
        RecompensaResponse response = recompensaService.criarRecompensa(usuarioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RecompensaResponse>> listar(@RequestParam Long usuarioId) {
        List<RecompensaResponse> response = recompensaService.listarRecompensas(usuarioId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sorteio")
    public ResponseEntity<RecompensaResponse> sortearRecompensa(@RequestParam Long usuarioId) {
        RecompensaResponse response = recompensaService.sortearRecompensa(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recebidas")
    public ResponseEntity<List<RecompensaResponse>> listarRecompensasRecebidas(@RequestParam Long usuarioId) {
        List<RecompensaResponse> response = recompensaService.listarRecompensasRecebidas(usuarioId);
        return ResponseEntity.ok(response);
    }
}
