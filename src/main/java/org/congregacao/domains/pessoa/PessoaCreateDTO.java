package org.congregacao.domains.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaCreateDTO {

    @NotBlank
    @Size(max = 255)
    private String nome;

    @Size(max = 255)
    private String nomeFamilia;

    @Size(max = 255)
    private String endereco;

    @Size(max = 255)
    private String numero;

    @Size(max = 255)
    private String complemento;

    @Size(max = 255)
    private String bairro;

    @Size(max = 255)
    private String cep;

    @Size(max = 255)
    private String municipio;

    @Size(max = 255)
    private String uf;

    @Size(max = 255)
    private String telefone1;

    @Size(max = 255)
    private String telefone2;

    private LocalDate nascimento;

    private LocalDate batismo;

    @Size(max = 255)
    private String email;

    private Boolean anciao;

    private Boolean servoMinisterial;

    private Boolean pioneiroRegular;

    private Boolean ungido;

    @Size(max = 255)
    private String genero;

    @Size(max = 255)
    private String contatoNome;

    @Size(max = 255)
    private String contatoParentesco;

    @Size(max = 255)
    private String contatoTelefone;

    @Size(max = 255)
    private String contatoTelefone1;

    @Size(max = 255)
    private String contatoEmail;

}
