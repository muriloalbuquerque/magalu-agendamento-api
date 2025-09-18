package com.example.magalu.dto;

import com.example.magalu.enums.TipoComunicacao;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AgendamentoRequestDto(
        @NotBlank(message = "O destinatario eh obrigatorio!")
        String destinatario,
        @NotBlank(message = "A mensagem eh obrigatorio!")
        String mensagem,
        @NotBlank(message = "O tipo de comunicacao eh obrigatorio!")
        TipoComunicacao tipoComunicacao,
        @NotBlank(message = "A data e hora de envio sao obrigatorios")
        @Future(message = "A data e hora de envio deve ser futura")
        LocalDateTime dataHoraEnvio
) {
}
