package org.congregacao.dto;

import lombok.Getter;
import lombok.Setter;
import org.congregacao.model.ContatoEmergencia;
import org.congregacao.model.Endereco;
import org.congregacao.model.Funcao;
import org.congregacao.model.Grupo;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PessoaResponseDTO {
    private Long id;
    private String nome;
    private String nomeCompleto;
    private String genero;
    private LocalDate dataNascimento;
    private Endereco endereco;
    private String telefone;
    private String email;
    private Boolean batizado;
    private LocalDate dataBatismo;
    private String privilegio;
    private String pioneiro;
    private Grupo grupo;
    private Boolean ungido;
    private List<ContatoEmergencia> contatosEmergencia;
    private List<Funcao> funcoes;
}
