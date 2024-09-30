package org.congregacao.controller;

import org.congregacao.model.Assistencia;
import org.congregacao.service.AssistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assistencias")
public class AssistenciaController {

    @Autowired
    private AssistenciaService assistenciaService;

    @PostMapping
    public ResponseEntity<Assistencia> criarAssistencia(@Validated @RequestBody Assistencia assistencia) {
        Assistencia criada = assistenciaService.criarAssistencia(assistencia);
        return new ResponseEntity<>(criada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assistencia> atualizarAssistencia(@PathVariable Long id, @Validated @RequestBody Assistencia assistencia) {
        Assistencia atualizada = assistenciaService.atualizarAssistencia(id, assistencia);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    public ResponseEntity<List<Assistencia>> listarAssistencias() {
        List<Assistencia> assistencias = assistenciaService.listarAssistencias();
        return ResponseEntity.ok(assistencias);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAssistencia(@PathVariable Long id) {
        assistenciaService.excluirAssistencia(id);
        return ResponseEntity.noContent().build();
    }
}