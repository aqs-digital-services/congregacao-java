package org.congregacao.domains.atividade;

import org.congregacao.common.exception.DuplicateResourceException;
import org.congregacao.common.exception.ResourceNotFoundException;
import org.congregacao.domains.pessoa.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Atividade> listarAtividades() {
        return atividadeRepository.findAll();
    }

    public Optional<Atividade> obterAtividadePorId(Long id) {
        return atividadeRepository.findById(id);
    }

    public Atividade criarAtividade(Atividade atividade) {
        List<Atividade> existentes = atividadeRepository.findByPessoaAndAnoMes(atividade.getPessoa(), atividade.getAnoMes());
        if (!existentes.isEmpty()) {
            throw new DuplicateResourceException("Atividade já registrada para esta pessoa no mês especificado.");
        }
        return atividadeRepository.save(atividade);
    }

    public Atividade atualizarAtividade(Long id, Atividade atividadeDetalhes) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada com id " + id));
        atividade.setParticipouPregacao(atividadeDetalhes.isParticipouPregacao());
        atividade.setEstudosBiblicos(atividadeDetalhes.getEstudosBiblicos());
        atividade.setPioneiroAuxiliar(atividadeDetalhes.isPioneiroAuxiliar());
        atividade.setHoras(atividadeDetalhes.getHoras());
        atividade.setObservacoes(atividadeDetalhes.getObservacoes());
        return atividadeRepository.save(atividade);
    }

    public void excluirAtividade(Long id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada com id " + id));
        atividadeRepository.delete(atividade);
    }

    public List<Object[]> contarParticipacaoPregacaoPorMes() {
        return atividadeRepository.countParticipacaoPregacaoPorMes();
    }
}