package org.congregacao.service;

import org.congregacao.dto.PessoaCreateDTO;
import org.congregacao.exception.ApiException;
import org.congregacao.exception.ErrorResponse;
import org.congregacao.model.Pessoa;
import org.congregacao.exception.ResourceNotFoundException;
import org.congregacao.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    /**
     * Cadastrar uma nova pessoa.
     *
     * @param pessoa A pessoa a ser cadastrada.
     * @return A pessoa cadastrada.
     */
    public Pessoa cadastrarPessoa(PessoaCreateDTO pessoa) {
        try {
            var pessoaEntity = new Pessoa();

            pessoaEntity.setNome(pessoa.getNome());
            pessoaEntity.setNomeFamilia(pessoa.getNomeFamilia());
            pessoaEntity.setEndereco(pessoa.getEndereco());
            pessoaEntity.setNumero(pessoa.getNumero());
            pessoaEntity.setComplemento(pessoa.getComplemento());
            pessoaEntity.setBairro(pessoa.getBairro());
            pessoaEntity.setCep(pessoa.getCep());
            pessoaEntity.setMunicipio(pessoa.getMunicipio());
            pessoaEntity.setUf(pessoa.getUf());
            pessoaEntity.setTelefone1(pessoa.getTelefone1());
            pessoaEntity.setTelefone2(pessoa.getTelefone2());
            pessoaEntity.setNascimento(pessoa.getNascimento());
            pessoaEntity.setBatismo(pessoa.getBatismo());
            pessoaEntity.setEmail(pessoa.getEmail());
            pessoaEntity.setGenero(pessoa.getGenero());
            pessoaEntity.setAnciao(pessoa.getAnciao());
            pessoaEntity.setServoMinisterial(pessoa.getServoMinisterial());
            pessoaEntity.setPioneiroRegular(pessoa.getPioneiroRegular());
            pessoaEntity.setUngido(pessoa.getUngido());
            pessoaEntity.setContatoNome(pessoa.getContatoNome());
            pessoaEntity.setContatoParentesco(pessoa.getContatoParentesco());
            pessoaEntity.setContatoTelefone(pessoa.getContatoTelefone());
            pessoaEntity.setContatoTelefone1(pessoa.getContatoTelefone1());
            pessoaEntity.setContatoEmail(pessoa.getContatoEmail());

            return pessoaRepository.save(pessoaEntity);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
        }
    }

    /**
     * Atualizar uma pessoa existente e limpar o cache de buscas.
     *
     * @param id            ID da pessoa a ser atualizada.
     * @param pessoaDetalhe Dados atualizados da pessoa.
     * @return A pessoa atualizada.
     */
    @CacheEvict(value = "buscaPessoasPorNome", allEntries = true)
    public Pessoa atualizarPessoa(Long id, Pessoa pessoaDetalhe) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        // Atualizar campos
        pessoa.setNome(pessoaDetalhe.getNome());
        pessoa.setNomeFamilia(pessoaDetalhe.getNomeFamilia());
        pessoa.setEndereco(pessoaDetalhe.getEndereco());
        pessoa.setNumero(pessoaDetalhe.getNumero());
        pessoa.setComplemento(pessoaDetalhe.getComplemento());
        pessoa.setBairro(pessoaDetalhe.getBairro());
        pessoa.setCep(pessoaDetalhe.getCep());
        pessoa.setMunicipio(pessoaDetalhe.getMunicipio());
        pessoa.setUf(pessoaDetalhe.getUf());
        pessoa.setTelefone1(pessoaDetalhe.getTelefone1());
        pessoa.setTelefone2(pessoaDetalhe.getTelefone2());
        pessoa.setNascimento(pessoaDetalhe.getNascimento());
        pessoa.setBatismo(pessoaDetalhe.getBatismo());
        pessoa.setGrupo(pessoaDetalhe.getGrupo());
        pessoa.setEmail(pessoaDetalhe.getEmail());
        pessoa.setAnciao(pessoaDetalhe.getAnciao());
        pessoa.setServoMinisterial(pessoaDetalhe.getServoMinisterial());
        pessoa.setPioneiroRegular(pessoaDetalhe.getPioneiroRegular());
        pessoa.setUngido(pessoaDetalhe.getUngido());
        pessoa.setGenero(pessoaDetalhe.getGenero());
        pessoa.setContatoNome(pessoaDetalhe.getContatoNome());
        pessoa.setContatoParentesco(pessoaDetalhe.getContatoParentesco());
        pessoa.setContatoTelefone(pessoaDetalhe.getContatoTelefone());
        pessoa.setContatoTelefone1(pessoaDetalhe.getContatoTelefone1());
        pessoa.setContatoEmail(pessoaDetalhe.getContatoEmail());

        return pessoaRepository.save(pessoa);
    }

    /**
     * Listar todas as pessoas com paginação.
     *
     * @param pageable Informações de paginação.
     * @return Página de pessoas.
     */
    public Page<Pessoa> listarPessoas(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    /**
     * Excluir uma pessoa e limpar o cache de buscas.
     *
     * @param id ID da pessoa a ser excluída.
     */
    @CacheEvict(value = "buscaPessoasPorNome", allEntries = true)
    public void excluirPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        pessoaRepository.delete(pessoa);
    }

    /**
     * Buscar pessoas pelo nome com correspondência parcial.
     *
     * @param nome     O trecho do nome a ser buscado.
     * @param pageable Informações de paginação.
     * @return Página de pessoas que correspondem à busca.
     */
    @Cacheable(value = "buscaPessoasPorNome", key = "#nome + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Pessoa> buscarPessoasPorNome(String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

}
