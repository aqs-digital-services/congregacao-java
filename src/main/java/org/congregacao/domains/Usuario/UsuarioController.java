package org.congregacao.domains.Usuario;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para criar um novo usuário.
     *
     * @param usuarioCreateDTO Dados do usuário a ser criado.
     * @return DTO do usuário criado.
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioDTO criado = usuarioService.criarUsuario(usuarioCreateDTO);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    /**
     * Endpoint para atualizar um usuário existente.
     *
     * @param id               ID do usuário a ser atualizado.
     * @param usuarioUpdateDTO Dados atualizados do usuário.
     * @return DTO do usuário atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioDTO atualizado = usuarioService.atualizarUsuario(id, usuarioUpdateDTO);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Endpoint para listar todos os usuários.
     *
     * @return Lista de DTOs de usuários.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint para excluir um usuário por ID.
     *
     * @param id ID do usuário a ser excluído.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}