package org.congregacao.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.congregacao.dto.PessoaCreateDTO;
import org.congregacao.model.Pessoa;
import org.congregacao.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    /**
     * Endpoint para cadastrar uma nova pessoa.
     *
     * @param pessoa A pessoa a ser cadastrada.
     * @return A pessoa cadastrada.
     */
    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@Valid @RequestBody PessoaCreateDTO pessoa) {
        Pessoa novaPessoa = pessoaService.cadastrarPessoa(pessoa);
        return ResponseEntity.status(201).body(novaPessoa);
    }

    /**
     * Endpoint para atualizar uma pessoa existente.
     *
     * @param id     ID da pessoa a ser atualizada.
     * @param pessoa Detalhes atualizados da pessoa.
     * @return A pessoa atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizarPessoa(
            @PathVariable Long id,
            @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    /**
     * Endpoint para listar todas as pessoas com paginação.
     *
     * @param page Número da página (opcional, padrão 0).
     * @param size Tamanho da página (opcional, padrão 10).
     * @return Página de pessoas.
     */
    @GetMapping
    public ResponseEntity<Page<Pessoa>> listarPessoas(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pessoa> pessoas = pessoaService.listarPessoas(pageable);
        return ResponseEntity.ok(pessoas);
    }

    /**
     * Endpoint para excluir uma pessoa.
     *
     * @param id ID da pessoa a ser excluída.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para buscar pessoas pelo nome com correspondência parcial.
     *
     * @param nome O trecho do nome a ser buscado.
     * @param page Número da página (opcional, padrão 0).
     * @param size Tamanho da página (opcional, padrão 10).
     * @return Página de pessoas que correspondem à busca.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<Pessoa>> buscarPessoasPorNome(
            @RequestParam @Size(min = 1, max = 100) String nome,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {

        if (size > 100) {
            size = 100;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Pessoa> pessoas = pessoaService.buscarPessoasPorNome(nome, pageable);
        return ResponseEntity.ok(pessoas);
    }

}
