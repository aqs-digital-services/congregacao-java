package org.congregacao.model;

import jakarta.persistence.*;
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

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "tipo_assistencia")
    private String tipoAssistencia;

    @Column(name = "tipo_reuniao")
    private String tipoReuniao;

    @Column(name = "quantidade_pessoas", nullable = false)
    private Integer quantidadePessoas;
}
