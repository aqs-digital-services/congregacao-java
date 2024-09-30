package org.congregacao.domains.grupo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.congregacao.domains.pessoa.Pessoa;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupos")
@Getter
@Setter
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "local_saida_campo")
    private String localSaidaCampo;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "superintendente_id", nullable = true)
    private Pessoa superintendente;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ajudante_id", nullable = true)
    private Pessoa ajudante;

    @OneToMany(mappedBy = "grupo", orphanRemoval = true)
    private List<Pessoa> membros = new ArrayList<>();

}
