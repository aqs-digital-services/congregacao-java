package org.congregacao.domains.grupo;

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

    @PostMapping
    public ResponseEntity<Grupo> criarGrupo(@RequestBody GrupoCreateDTO grupo) {
        Grupo novoGrupo = grupoService.cadastrarGrupo(grupo);
        return new ResponseEntity<>(novoGrupo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> atualizarGrupo(@PathVariable Long id, @RequestBody Grupo grupo) {
        Grupo grupoAtualizado = grupoService.atualizarGrupo(id, grupo);
        return ResponseEntity.ok(grupoAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<Grupo>> listarGrupos() {
        List<Grupo> grupos = grupoService.listarGrupos();
        return ResponseEntity.ok(grupos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirGrupo(@PathVariable Long id) {
        grupoService.excluirGrupo(id);
        return ResponseEntity.noContent().build();
    }

}
