package org.congregacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.congregacao.dto.AssistenciaRequestDTO;
import org.congregacao.dto.AssistenciaResponseDTO;
import org.congregacao.service.AssistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/assistencias")
public class AssistenciaController {

    @Autowired
    private AssistenciaService assistenciaService;

    @Operation(summary = "Criar uma nova assistência", description = "Cria uma nova assistência com os detalhes fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assistência criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<String> registrarAssistencia(
            @Valid @RequestBody AssistenciaRequestDTO requestDTO) {
        assistenciaService.registrarAssistencia(requestDTO);
        return new ResponseEntity<>("Assistência regristada com sucesso!", HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todas as assistências", description = "Retorna uma lista de todas as assistências cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de assistências retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<AssistenciaResponseDTO>> listarAssistencias() {
        List<AssistenciaResponseDTO> assistencias = assistenciaService.listarAssistencias();
        return ResponseEntity.ok(assistencias);
    }

    @GetMapping("/dia")
    public AssistenciaResponseDTO obterAssistenciasPorDia(@RequestParam("data") String data) {
        LocalDate dataFormatada = LocalDate.parse(data);
        return assistenciaService.obterAssistenciaPorDia(dataFormatada);
    }

    @Operation(summary = "Excluir uma assistência", description = "Exclui uma assistência identificada pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assistência excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Assistência não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAssistencia(
            @PathVariable Long id) {
        assistenciaService.excluirAssistencia(id);
        return ResponseEntity.noContent().build();
    }
}