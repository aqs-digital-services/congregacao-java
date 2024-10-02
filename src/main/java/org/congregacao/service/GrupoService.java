package org.congregacao.service;

import lombok.AllArgsConstructor;
import org.congregacao.common.exception.ResourceNotFoundException;
import org.congregacao.dto.GrupoRequestDTO;
import org.congregacao.model.Grupo;
import org.congregacao.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public void cadastrarGrupo(GrupoRequestDTO grupo) {
        var grupoEntity = new Grupo();
        grupoEntity.setNome(grupo.getNome());
        grupoEntity.setEndereco(grupo.getEndereco());
        grupoRepository.save(grupoEntity);
    }

    /**
     * Busca um grupo por seu ID.
     *
     * @param id ID do grupo.
     * @return GrupoResponseDTO correspondente.
     */
    public Grupo buscarGrupoPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo não encontrado com ID: " + id));
    }

    /**
     * Busca grupos cujo nome contém o termo fornecido (busca parcial).
     *
     * @param nome Termo de busca para o nome do grupo.
     * @return Lista de GrupoResponseDTO correspondentes.
     */
    public List<Grupo> buscarGruposPorNome(String nome) {
        return grupoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public void atualizarGrupo(Long id, GrupoRequestDTO requestDTO) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo não encontrado"));
        grupo.setNome(requestDTO.getNome());
        grupo.setEndereco(requestDTO.getEndereco());
        grupoRepository.save(grupo);
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
