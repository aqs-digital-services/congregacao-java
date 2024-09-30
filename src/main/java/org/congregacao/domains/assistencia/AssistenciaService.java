package org.congregacao.domains.assistencia;

import org.congregacao.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssistenciaService {

    @Autowired
    private AssistenciaRepository assistenciaRepository;

    public List<Assistencia> listarAssistencias() {
        return assistenciaRepository.findAll();
    }

    public Optional<Assistencia> obterAssistenciaPorId(Long id) {
        return assistenciaRepository.findById(id);
    }

    public Assistencia criarAssistencia(Assistencia assistencia) {
        return assistenciaRepository.save(assistencia);
    }

    public Assistencia atualizarAssistencia(Long id, Assistencia assistenciaDetalhes) {
        Assistencia assistencia = assistenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assistência não encontrada com id " + id));
        assistencia.setTipoReuniao(assistenciaDetalhes.getTipoReuniao());
        assistencia.setQuantidadePessoas(assistenciaDetalhes.getQuantidadePessoas());
        // Atualize outros campos conforme necessário
        return assistenciaRepository.save(assistencia);
    }

    public void excluirAssistencia(Long id) {
        Assistencia assistencia = assistenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assistência não encontrada com id " + id));
        assistenciaRepository.delete(assistencia);
    }
}