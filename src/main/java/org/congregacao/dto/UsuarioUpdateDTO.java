package org.congregacao.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO {

    @Size(max = 255, message = "Username deve ter no máximo 255 caracteres")
    private String username;

    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String password;

    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER", message = "Role deve ser ROLE_ADMIN ou ROLE_USER")
    private String role;
}
