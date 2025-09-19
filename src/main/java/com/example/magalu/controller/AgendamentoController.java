package com.example.magalu.controller;

import com.example.magalu.dto.AgendamentoRequestDto;
import com.example.magalu.dto.AgendamentoResponseDto;
import com.example.magalu.service.AgendamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> agendar(@RequestBody AgendamentoRequestDto request) {
        AgendamentoResponseDto response = service.agendar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> buscarPorId(@PathVariable Long id) {
        AgendamentoResponseDto response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        // ✅ Agora retorna 204 No Content (sem body), que bate com o seu teste
        return ResponseEntity.noContent().build();
    }
}
