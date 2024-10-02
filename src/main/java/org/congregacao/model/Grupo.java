package org.congregacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "grupos")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private Endereco endereco;

    // Superintendente (referencia Pessoa com privilégio Ancião)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "superintendente_id")
    private Pessoa superintendente;

    // Ajudante (referencia Pessoa com privilégio Ancião ou Servo Ministerial)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ajudante_id")
    private Pessoa ajudante;

    // Membros do grupo (Many-to-Many com Pessoa)
    @ManyToMany
    @JoinTable(
            name = "grupo_membros",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoa_id")
    )
    private List<Pessoa> membros;
}