package org.congregacao.domains.grupo;

import org.congregacao.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo cadastrarGrupo(GrupoCreateDTO grupo) {
        var grupoEntity = new Grupo();

        grupoEntity.setNome(grupo.getNome());
        grupoEntity.setLocalSaidaCampo(grupo.getLocalSaidaCampo());

        return grupoRepository.save(grupoEntity);
    }

    public Grupo atualizarGrupo(Long id, Grupo grupoDetalhe) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo não encontrado"));
        grupo.setNome(grupoDetalhe.getNome());
        grupo.setLocalSaidaCampo(grupoDetalhe.getLocalSaidaCampo());
        return grupoRepository.save(grupo);
    }

    public List<Grupo> listarGrupos() {
        return grupoRepository.findAll();
    }

    public void excluirGrupo(Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo não encontrado"));
        grupoRepository.delete(grupo);
    }

}
