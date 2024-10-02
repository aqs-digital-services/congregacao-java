package org.congregacao.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PessoaRequestDTO {
    private String nome;
    private String nomeCompleto;
    private String genero;
    private LocalDate dataNascimento;
    private EnderecoDTO endereco;
    private String telefone;
    private String email;
    private Boolean batizado;
    private LocalDate dataBatismo;
    private String privilegio;
    private String pioneiro;
    private Long grupoId;
    private Boolean ungido;
    private List<ContatoEmergenciaDTO> contatosEmergencia;
    private List<Long> funcoesIds;
}
