package com.example.magalu;

import com.example.magalu.controller.AgendamentoController;
import com.example.magalu.dto.AgendamentoRequestDto;
import com.example.magalu.dto.AgendamentoResponseDto;
import com.example.magalu.enums.StatusAgendamento;
import com.example.magalu.enums.TipoComunicacao;
import com.example.magalu.service.AgendamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgendamentoController.class)
@AutoConfigureMockMvc(addFilters = false) // desliga filtros de segurança, se houver
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Agora o service é injetado do @TestConfiguration
    @Autowired
    private AgendamentoService service;

    // Configuração de teste para sobrescrever o bean real por um mock
    @TestConfiguration
    static class TestConfig {
        @Bean
        AgendamentoService agendamentoService() {
            return mock(AgendamentoService.class);
        }
    }

    private static final String BASE = "/api/agendamentos";

    @Test
    void deveAgendarComunicacao() throws Exception {
        AgendamentoRequestDto requestDto = new AgendamentoRequestDto(
                "cliente@gmail.com",
                "mensagem de teste",
                TipoComunicacao.EMAIL,
                LocalDateTime.now().plusMinutes(1)
        );

        AgendamentoResponseDto responseDto = new AgendamentoResponseDto(
                1L,
                requestDto.destinatario(),
                requestDto.mensagem(),
                requestDto.tipoComunicacao(),
                requestDto.dataHoraEnvio(),
                StatusAgendamento.AGENDADO,
                LocalDateTime.now()
        );

        when(service.agendar(any())).thenReturn(responseDto);

        mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                // ajuste aqui se seu controller retorna 201 CREATED
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destinatario").value("cliente@gmail.com"));
    }

    @Test
    void deveBuscarAgendamentoPorId() throws Exception {
        Long id = 1L;

        AgendamentoResponseDto responseDto = new AgendamentoResponseDto(
                id,
                "cliente@gmail.com",
                "mensagem de teste",
                TipoComunicacao.SMS,
                LocalDateTime.now().plusMinutes(2),
                StatusAgendamento.AGENDADO,
                LocalDateTime.now()
        );

        when(service.buscarPorId(id)).thenReturn(responseDto);

        mockMvc.perform(get(BASE + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.tipoComunicacao").value("SMS"));
    }

    @Test
    void deveRemoverAgendamento() throws Exception {
        Long id = 1L;

        doNothing().when(service).remover(id);

        mockMvc.perform(delete(BASE + "/{id}", id))
                .andDo(print())
                // ajuste se o controller retornar 200 OK
                .andExpect(status().isNoContent());
    }
}
