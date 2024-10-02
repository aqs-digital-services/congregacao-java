package org.congregacao.dto;

import lombok.Getter;
import lombok.Setter;
import org.congregacao.model.Endereco;

@Getter
@Setter
public class GrupoRequestDTO {

    private String nome;
    private Endereco endereco;
}