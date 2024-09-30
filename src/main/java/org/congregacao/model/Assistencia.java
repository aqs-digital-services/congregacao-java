package org.congregacao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "assistencias")
public class Assistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAssistencia tipoAssistencia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_reuniao")
    private TipoReuniao tipoReuniao;

    @Column(name = "quantidade_pessoas", nullable = false)
    private Integer quantidadePessoas;
}
