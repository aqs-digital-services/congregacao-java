package org.congregacao.service;

import lombok.AllArgsConstructor;
import org.congregacao.common.exception.ApiException;
import org.congregacao.common.exception.ErrorResponse;
import org.congregacao.common.exception.ResourceNotFoundException;
import org.congregacao.dto.AssistenciaRequestDTO;
import org.congregacao.dto.AssistenciaResponseDTO;
import org.congregacao.model.Assistencia;
import org.congregacao.repository.AssistenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssistenciaService {

    @Autowired
    private AssistenciaRepository assistenciaRepository;

    private static final Logger logger = LoggerFactory.getLogger(AssistenciaService.class);

    public List<AssistenciaResponseDTO> listarAssistencias() {
        return assistenciaRepository.findAll().stream()
                .map(assistencia -> {
                    AssistenciaResponseDTO dto = new AssistenciaResponseDTO();
                    dto.setData(assistencia.getData());
                    dto.setTipoAssistenciaEnum(assistencia.getTipoAssistencia());
                    dto.setTipoReuniaoEnum(assistencia.getTipoReuniao());
                    dto.setQuantidadePessoas(assistencia.getQuantidadePessoas());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public AssistenciaResponseDTO obterAssistenciaPorDia(LocalDate data) {
        Assistencia assistencia = assistenciaRepository.findByData(data)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, new ErrorResponse(HttpStatus.NOT_FOUND.toString(), "Assistência não encontrada para a data: " + data)));

        AssistenciaResponseDTO dto = new AssistenciaResponseDTO();
        dto.setData(assistencia.getData());
        dto.setTipoAssistenciaEnum(assistencia.getTipoAssistencia());
        dto.setTipoReuniaoEnum(assistencia.getTipoReuniao());
        dto.setQuantidadePessoas(assistencia.getQuantidadePessoas());

        return dto;
    }


    public void registrarAssistencia(AssistenciaRequestDTO assistencia) {
        logger.info("registrando assistência: {}", assistencia);
        try {
            var assistenciaEntity = new Assistencia();
            assistenciaEntity.setData(assistencia.getData());
            assistenciaEntity.setTipoReuniao(assistencia.getTipoReuniaoEnum());
            assistenciaEntity.setTipoAssistencia(assistencia.getTipoAssistenciaEnum());
            assistenciaEntity.setQuantidadePessoas(assistencia.getQuantidadePessoas());

            assistenciaRepository.save(assistenciaEntity);
        } catch (Exception e) {
            logger.error("Erro ao regristrar a assistência: {}", e.getMessage());
            throw new ApiException(HttpStatus.BAD_REQUEST, new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
        }
    }

    public void excluirAssistencia(Long id) {
        Assistencia assistencia = assistenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assistência não encontrada com id " + id));
        assistenciaRepository.delete(assistencia);
    }
}