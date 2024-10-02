package org.congregacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.congregacao.dto.GrupoRequestDTO;
import org.congregacao.model.Grupo;
import org.congregacao.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Operation(summary = "Criar um novo grupo", description = "Cria um novo grupo com os detalhes fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grupo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<String> criarGrupo(
            @Valid @RequestBody GrupoRequestDTO requestDTO) {
        grupoService.cadastrarGrupo(requestDTO);
        return new ResponseEntity<>("Grupo " + requestDTO.getNome() + " registrado com sucesso!", HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um grupo existente", description = "Atualiza os detalhes de um grupo existente identificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarGrupo(
            @Parameter(description = "ID do grupo a ser atualizado", required = true)
            @PathVariable Long id,
            @Valid @RequestBody GrupoRequestDTO requestDTO) {
        grupoService.atualizarGrupo(id, requestDTO);
        return ResponseEntity.ok("Grupo " + requestDTO.getNome() + " atualizado com sucesso!");
    }

    @Operation(summary = "Listar todos os grupos", description = "Retorna uma lista de todos os grupos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de grupos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Grupo>> listarGrupos() {
        return ResponseEntity.ok(grupoService.listarGrupos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Grupo por ID", description = "Recupera um grupo específico com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado.")
    })
    public ResponseEntity<Grupo> buscarGrupoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.buscarGrupoPorId(id));
    }

    /**
     * Endpoint para buscar grupos por nome (busca parcial).
     *
     * @param nome Termo de busca para o nome do grupo.
     * @return Lista de grupos que correspondem ao termo de busca.
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar Grupos por Nome", description = "Recupera uma lista de grupos cujo nome corresponde parcialmente ao termo de busca fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupos encontrados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetro de busca inválido.")
    })
    public ResponseEntity<List<Grupo>> buscarGruposPorNome(@RequestParam String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var grupos = grupoService.buscarGruposPorNome(nome);
        return ResponseEntity.ok(grupos);
    }

    @Operation(summary = "Excluir um grupo", description = "Exclui um grupo identificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Grupo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirGrupo(
            @Parameter(description = "ID do grupo a ser excluído", required = true)
            @PathVariable Long id) {
        grupoService.excluirGrupo(id);
        return ResponseEntity.noContent().build();
    }
}