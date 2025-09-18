package com.example.magalu.controller;

import com.example.magalu.dto.AgendamentoRequestDto;
import com.example.magalu.dto.AgendamentoResponseDto;
import com.example.magalu.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos") // também corrigi a grafia: estava "agentamentos"
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> agendar(@RequestBody AgendamentoRequestDto requestDto) {
        AgendamentoResponseDto responseDto = agendamentoService.agendar(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> buscarPorId(@PathVariable Long id) {
        AgendamentoResponseDto responseDto = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        agendamentoService.remover(id); // aqui estava o erro
        return ResponseEntity.ok().build();
    }
}
