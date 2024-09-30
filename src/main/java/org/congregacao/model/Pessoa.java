package org.congregacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "uf", length = 2)
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

    @Column(name = "genero", length = 10)
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

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @ManyToMany
    @JoinTable(
            name = "pessoa_privilegio",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "privilegio_id")
    )
    private Set<Privilegio> privilegios = new HashSet<>();

}