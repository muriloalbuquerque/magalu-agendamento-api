package com.example.magalu.dto;

import com.example.magalu.enums.StatusAgendamento;
import com.example.magalu.enums.TipoComunicacao;

import java.time.LocalDateTime;

public record AgendamentoResponseDto(
        Long id,
        String destinatario,
        String mensagem,
        TipoComunicacao tipoComunicacao,
        LocalDateTime dataHoraEnvio,
        StatusAgendamento status,
        LocalDateTime criadoEm
) {
}
