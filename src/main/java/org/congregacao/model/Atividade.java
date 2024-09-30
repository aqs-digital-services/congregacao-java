package org.congregacao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "atividades")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @NotNull
    @Column(name = "ano_mes", length = 7)
    private String anoMes;

    @NotNull
    @Column(name = "participou_pregacao")
    private boolean participouPregacao;

    @Min(0)
    @Column(name = "quantidade_estudos_biblicos")
    private int estudosBiblicos;

    @NotNull
    @Column(name = "pioneiro_auxiliar")
    private boolean pioneiroAuxiliar;

    @Min(0)
    @Column(name = "horas")
    private Integer horas;

    @Size(max = 500)
    @Column(name = "observacoes", length = 500)
    private String observacoes;

}
