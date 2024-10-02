package org.congregacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "contatos_emergencia")
public class ContatoEmergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "nome")
    private String nome;

    @Column(name = "parentesco")
    private String parentesco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;
}