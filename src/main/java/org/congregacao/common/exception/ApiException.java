package org.congregacao.common.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final String codigo;

    final HttpStatus status;

    public ApiException(@NotNull final HttpStatus status, @NotNull final ErrorResponse response) {
        super(response.getMessage());
        this.codigo = response.getCode();
        this.status = status;
    }
}
