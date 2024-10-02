package org.congregacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    
    @Column(nullable = false)
    private String genero;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Embedded
    private Endereco endereco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "batizado")
    private Boolean batizado = false;

    @Column(name = "data_batismo")
    private LocalDate dataBatismo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilegio_id")
    private Privilegio privilegio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pioneiro_id")
    private Pioneiro pioneiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @Column(name = "ungido")
    private Boolean ungido = false;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContatoEmergencia> contatosEmergencia;

    @ManyToMany
    @JoinTable(
            name = "pessoa_funcoes",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "funcao_id")
    )
    private List<Funcao> funcoes;
}