package org.congregacao.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    @Getter
    private final String codigo;

    @Getter
    final HttpStatus status;

    public ApiException(@NotNull final HttpStatus status, @NotNull final ErrorResponse response) {
        super(response.getMessage());
        this.codigo = response.getCode();
        this.status = status;
    }
}
