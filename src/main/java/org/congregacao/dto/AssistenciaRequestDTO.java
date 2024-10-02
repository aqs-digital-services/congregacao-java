package org.congregacao.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AssistenciaRequestDTO {

    private Long id;

    private LocalDate data;

    private String tipoAssistenciaEnum;

    private String tipoReuniaoEnum;

    private Integer quantidadePessoas;
}
