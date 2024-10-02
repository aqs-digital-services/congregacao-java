package org.congregacao.controller;

import org.congregacao.dto.PessoaRequestDTO;
import org.congregacao.dto.PessoaResponseDTO;
import org.congregacao.dto.PrivilegioRequestDTO;
import org.congregacao.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/buscar")
    public ResponseEntity<List<PessoaResponseDTO>> buscarPessoas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long grupoId) {
        List<PessoaResponseDTO> pessoas = pessoaService.buscarPessoas(nome, grupoId);
        return ResponseEntity.ok(pessoas);
    }

    // 2. Buscar pessoa por ID
    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPessoaPorId(@PathVariable Long id) {
        PessoaResponseDTO pessoa = pessoaService.buscarPessoaPorId(id);
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dados")
    public ResponseEntity<Map<String, Long>> obterDadosResumidos() {
        Map<String, Long> dadosResumidos = pessoaService.obterDadosResumidos();
        return ResponseEntity.ok(dadosResumidos);
    }

    // 3. Criar nova pessoa
    @PostMapping
    public ResponseEntity<PessoaResponseDTO> criarPessoa(@RequestBody PessoaRequestDTO pessoaRequest) {
        PessoaResponseDTO novaPessoa = pessoaService.criarPessoa(pessoaRequest);
        return ResponseEntity.status(201).body(novaPessoa);
    }

    // 4. Atualizar uma pessoa existente
    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaRequestDTO pessoaRequest) {
        PessoaResponseDTO pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaRequest);
        if (pessoaAtualizada != null) {
            return ResponseEntity.ok(pessoaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Deletar uma pessoa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {
        boolean deletado = pessoaService.deletarPessoa(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 7. Associar privilégio a uma pessoa
    @PostMapping("/{id}/privilegio")
    public ResponseEntity<String> associarPrivilegio(@PathVariable Long id, @RequestBody PrivilegioRequestDTO privilegioRequest) {
        boolean sucesso = pessoaService.associarPrivilegio(id, privilegioRequest.getPrivilegio());
        if (sucesso) {
            return ResponseEntity.ok("Privilégio atribuído com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Falha ao atribuir privilégio.");
        }
    }

    // 8. Listar os privilégios de uma pessoa
    @GetMapping("/{id}/privilegios")
    public ResponseEntity<List<String>> listarPrivilegiosPessoa(@PathVariable Long id) {
        List<String> privilegios = pessoaService.listarPrivilegiosPessoa(id);
        if (privilegios != null) {
            return ResponseEntity.ok(privilegios);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
