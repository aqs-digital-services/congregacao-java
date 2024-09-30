package org.congregacao.controller;

import org.congregacao.model.Atividade;
import org.congregacao.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    /**
     * Listar todas as atividades
     */
    @GetMapping
    public ResponseEntity<List<Atividade>> listarAtividades() {
        List<Atividade> atividades = atividadeService.listarAtividades();
        return ResponseEntity.ok(atividades);
    }

    /**
     * Obter uma atividade por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Atividade> obterAtividadePorId(@PathVariable Long id) {
        Optional<Atividade> atividadeOpt = atividadeService.obterAtividadePorId(id);
        return atividadeOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Criar uma nova atividade
     */
    @PostMapping
    public ResponseEntity<Atividade> criarAtividade(@Validated @RequestBody Atividade atividade) {
        Atividade criada = atividadeService.criarAtividade(atividade);
        return new ResponseEntity<>(criada, HttpStatus.CREATED);
    }

    /**
     * Atualizar uma atividade existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Atividade> atualizarAtividade(@PathVariable Long id, @Validated @RequestBody Atividade atividade) {
        Atividade atualizada = atividadeService.atualizarAtividade(id, atividade);
        return ResponseEntity.ok(atualizada);
    }

    /**
     * Excluir uma atividade
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAtividade(@PathVariable Long id) {
        atividadeService.excluirAtividade(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Relatório: Contar participações em pregação por mês
     */
    @GetMapping("/relatorios/pregacao")
    public ResponseEntity<List<Object[]>> relatorioParticipacaoPregacaoPorMes() {
        List<Object[]> resultado = atividadeService.contarParticipacaoPregacaoPorMes();
        return ResponseEntity.ok(resultado);
    }
}