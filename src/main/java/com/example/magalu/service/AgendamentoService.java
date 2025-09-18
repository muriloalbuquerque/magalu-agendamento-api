package com.example.magalu.service;

import com.example.magalu.dto.AgendamentoRequestDto;
import com.example.magalu.dto.AgendamentoResponseDto;
import com.example.magalu.enums.StatusAgendamento;
import com.example.magalu.model.Agendamento;
import com.example.magalu.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AgendamentoService {


    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoResponseDto agendar(AgendamentoRequestDto agendamentoRequestDto) {
        Agendamento agendamento = Agendamento.builder().
                destinatario(agendamentoRequestDto.destinatario())
                .mensagem(agendamentoRequestDto.mensagem())
                .tipoComunicacao(agendamentoRequestDto.tipoComunicacao())
                .dataHoraEnvio(agendamentoRequestDto.dataHoraEnvio())
                .status(StatusAgendamento.AGENDADO)
                .criadoEm(LocalDateTime.now())
                .build();
        return toResponse(agendamentoRepository.save(agendamento));

    }

    public AgendamentoResponseDto buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));
        return toResponse(agendamento);
    }

    public void remover(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento nao encontrado");
        }
        agendamentoRepository.deleteById(id);
    }


    private AgendamentoResponseDto toResponse(Agendamento agendamento) {
        return new AgendamentoResponseDto(
                agendamento.getId(),
                agendamento.getDestinatario(),
                agendamento.getMensagem(),
                agendamento.getTipoComunicacao(),
                agendamento.getDataHoraEnvio(),
                agendamento.getStatus(),
                agendamento.getCriadoEm()
        );
    }

}
