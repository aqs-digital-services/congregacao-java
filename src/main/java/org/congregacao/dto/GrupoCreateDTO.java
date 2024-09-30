package org.congregacao.dto;

import lombok.Getter;
import lombok.Setter;
import org.congregacao.model.Pessoa;

@Getter
@Setter
public class GrupoCreateDTO {

    private String nome;
    private String localSaidaCampo;
    private Pessoa superintendente;
    private Pessoa ajudante;

}