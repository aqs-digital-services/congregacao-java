package org.congregacao.domains.atividade;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class AtividadeUpdateDTO {

    @NotNull(message = "ID da pessoa é obrigatório")
    private Long pessoaId;

    @NotNull(message = "Mês e ano são obrigatórios")
    private YearMonth anoMes;

    @NotNull(message = "Participação em pregação é obrigatório")
    private Boolean participouPregacao;

    private Integer estudosBiblicos;

    @NotNull(message = "Campo pioneiro auxiliar é obrigatório")
    private Boolean pioneiroAuxiliar;

    private Boolean pioneiroRegular;

    private Integer horasPioneiro;

    private String observacoes;
}
