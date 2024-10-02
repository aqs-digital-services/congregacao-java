package org.congregacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "funcoes")
public class Funcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    // Responsável pela supervisão da função (relaciona-se com Pessoa)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Pessoa responsavel;

    // Relacionamento Many-to-Many entre funções e pessoas
    @ManyToMany(mappedBy = "funcoes")
    private List<Pessoa> pessoas;
}