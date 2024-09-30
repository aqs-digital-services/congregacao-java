package org.congregacao.domains.Usuario;

import org.congregacao.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Cria um novo usuário.
     *
     * @param usuarioCreateDTO Dados do usuário a ser criado.
     * @return DTO do usuário criado.
     */
    public UsuarioDTO criarUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        // Verifica se o username já está em uso
        if (usuarioRepository.findByUsername(usuarioCreateDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioCreateDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioCreateDTO.getPassword()));
        usuario.setRole(usuarioCreateDTO.getRole());

        Usuario salvo = usuarioRepository.save(usuario);

        return mapToDTO(salvo);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id               ID do usuário a ser atualizado.
     * @param usuarioUpdateDTO Dados atualizados do usuário.
     * @return DTO do usuário atualizado.
     */
    public UsuarioDTO atualizarUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (usuarioUpdateDTO.getUsername() != null && !usuarioUpdateDTO.getUsername().isBlank()) {
            // Verifica se o novo username já está em uso por outro usuário
            usuarioRepository.findByUsername(usuarioUpdateDTO.getUsername())
                    .filter(u -> !u.getId().equals(id))
                    .ifPresent(u -> {
                        throw new IllegalArgumentException("Username já está em uso");
                    });
            usuario.setUsername(usuarioUpdateDTO.getUsername());
        }

        if (usuarioUpdateDTO.getPassword() != null && !usuarioUpdateDTO.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(usuarioUpdateDTO.getPassword()));
        }

        if (usuarioUpdateDTO.getRole() != null && !usuarioUpdateDTO.getRole().isBlank()) {
            usuario.setRole(usuarioUpdateDTO.getRole());
        }

        Usuario atualizado = usuarioRepository.save(usuario);
        return mapToDTO(atualizado);
    }

    /**
     * Lista todos os usuários.
     *
     * @return Lista de DTOs de usuários.
     */
    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Exclui um usuário por ID.
     *
     * @param id ID do usuário a ser excluído.
     */
    public void excluirUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }

    /**
     * Mapeia a entidade Usuario para o DTO UsuarioDTO.
     *
     * @param usuario Entidade Usuario.
     * @return DTO do usuário.
     */
    private UsuarioDTO mapToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setRole(usuario.getRole());
        return dto;
    }

}
