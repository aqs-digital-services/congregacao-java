package org.congregacao.domains.pessoa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.congregacao.domains.grupo.Grupo;
import org.congregacao.domains.privilegio.Privilegio;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "pessoas")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nome_familia")
    private String nomeFamilia;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cep")
    private String cep;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "uf")
    private String uf;

    @Column(name = "telefone1")
    private String telefone1;

    @Column(name = "telefone2")
    private String telefone2;

    @Column(name = "nascimento")
    private LocalDate nascimento;

    @Column(name = "batismo")
    private LocalDate batismo;

    @Column(name = "email")
    private String email;

    @Column(name = "genero")
    private String genero;

    @Column(name = "anciao", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean anciao = false;

    @Column(name = "servo_ministerial", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean servoMinisterial = false;

    @Column(name = "pioneiro_regular", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean pioneiroRegular = false;

    @Column(name = "ungido", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean ungido = false;

    @Column(name = "contato_nome")
    private String contatoNome;

    @Column(name = "contato_parentesco")
    private String contatoParentesco;

    @Column(name = "contato_telefone")
    private String contatoTelefone;

    @Column(name = "contato_telefone1")
    private String contatoTelefone1;

    @Column(name = "contato_email")
    private String contatoEmail;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "grupo_id", nullable = true)
    private Grupo grupo;

    @ManyToMany
    @JoinTable(
            name = "pessoa_privilegio",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "privilegio_id")
    )
    private Set<Privilegio> privilegios = new HashSet<>();

}