package org.congregacao.service;

import lombok.AllArgsConstructor;
import org.congregacao.dto.EnderecoDTO;
import org.congregacao.dto.PessoaRequestDTO;
import org.congregacao.dto.PessoaResponseDTO;
import org.congregacao.model.*;
import org.congregacao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PrivilegioRepository privilegioRepository;

    @Autowired
    private PioneiroRepository pioneiroRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private FuncaoRepository funcaoRepository;

    public List<PessoaResponseDTO> buscarPessoas(String nome, Long grupoId) {
        List<Pessoa> pessoas;

        if (nome != null && grupoId != null) {
            pessoas = pessoaRepository.findByNomeContainingAndGrupoId(nome, grupoId);
        } else if (nome != null) {
            pessoas = pessoaRepository.findByNomeContaining(nome);
        } else if (grupoId != null) {
            pessoas = pessoaRepository.findByGrupoId(grupoId);
        } else {
            pessoas = pessoaRepository.findAll();
        }

        return pessoas.stream()
                .map(this::mapearParaPessoaResponseDTO)
                .collect(Collectors.toList());
    }

    // 2. Buscar pessoa por ID com todos os dados
    public PessoaResponseDTO buscarPessoaPorId(Long id) {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
        return pessoaOpt.map(this::mapearParaPessoaResponseDTO).orElse(null);
    }

    public Map<String, Long> obterDadosResumidos() {
        Map<String, Long> dadosResumidos = new HashMap<>();

        long totalPublicadores = pessoaRepository.count();
        long totalAnciaos = pessoaRepository.countByPrivilegio_Nome("Ancião");
        long totalServosMinisteriais = pessoaRepository.countByPrivilegio_Nome("Servo Ministerial");
        long totalPioneirosRegulares = pessoaRepository.countByPioneiro_Tipo("Pioneiro Regular");

        dadosResumidos.put("publicadores", totalPublicadores);
        dadosResumidos.put("anciaos", totalAnciaos);
        dadosResumidos.put("servosMinisteriais", totalServosMinisteriais);
        dadosResumidos.put("pioneirosRegulares", totalPioneirosRegulares);

        return dadosResumidos;
    }


    // 3. Criar nova pessoa
    public PessoaResponseDTO criarPessoa(PessoaRequestDTO pessoaRequest) {
        Pessoa pessoa = new Pessoa();
        preencherDadosPessoa(pessoa, pessoaRequest);
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return mapearParaPessoaResponseDTO(pessoaSalva);
    }

    // 4. Atualizar uma pessoa existente
    public PessoaResponseDTO atualizarPessoa(Long id, PessoaRequestDTO pessoaRequest) {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
        if (pessoaOpt.isPresent()) {
            Pessoa pessoa = pessoaOpt.get();
            preencherDadosPessoa(pessoa, pessoaRequest);
            Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);
            return mapearParaPessoaResponseDTO(pessoaAtualizada);
        } else {
            return null;
        }
    }

    // 5. Deletar uma pessoa
    public boolean deletarPessoa(Long id) {
        if (pessoaRepository.existsById(id)) {
            pessoaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // 7. Associar privilégio a uma pessoa
    public boolean associarPrivilegio(Long id, String privilegio) {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
        Optional<Privilegio> privilegioOpt = privilegioRepository.findByPrivilegio(privilegio);
        if (pessoaOpt.isPresent() && privilegioOpt.isPresent()) {
            Pessoa pessoa = pessoaOpt.get();
            Privilegio privilegioEntity = privilegioOpt.get();
            pessoa.setPrivilegio(privilegioEntity);
            pessoaRepository.save(pessoa);
            return true;
        }
        return false;
    }

    // 8. Listar os privilégios de uma pessoa
    public List<String> listarPrivilegiosPessoa(Long id) {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
        if (pessoaOpt.isPresent()) {
            Privilegio privilegio = pessoaOpt.get().getPrivilegio();
            if (privilegio != null) {
                return List.of(privilegio.getPrivilegio());
            }
        }
        return List.of();
    }

    // Métodos auxiliares

    private void preencherDadosPessoa(Pessoa pessoa, PessoaRequestDTO pessoaRequest) {
        pessoa.setNome(pessoaRequest.getNome());
        pessoa.setNomeCompleto(pessoaRequest.getNomeCompleto());
        pessoa.setGenero(pessoaRequest.getGenero());
        pessoa.setDataNascimento(pessoaRequest.getDataNascimento());
        pessoa.setEndereco(mapearParaEndereco(pessoaRequest.getEndereco()));
        pessoa.setTelefone(pessoaRequest.getTelefone());
        pessoa.setEmail(pessoaRequest.getEmail());
        pessoa.setBatizado(pessoaRequest.getBatizado());
        pessoa.setDataBatismo(pessoaRequest.getDataBatismo());
        if (pessoaRequest.getPrivilegio() != null) {
            Privilegio privilegio = privilegioRepository.findByPrivilegio(pessoaRequest.getPrivilegio())
                    .orElse(null);
            pessoa.setPrivilegio(privilegio);
        }
        if (pessoaRequest.getPioneiro() != null) {
            Pioneiro pioneiro = pioneiroRepository.findByTipo(pessoaRequest.getPioneiro())
                    .orElse(null);
            pessoa.setPioneiro(pioneiro);
        }
        if(pessoaRequest.getGrupoId() != null) {
        pessoa.setGrupo(grupoRepository.findById(pessoaRequest.getGrupoId()).orElse(null));
        }
        pessoa.setUngido(pessoaRequest.getUngido());

        if (pessoaRequest.getContatosEmergencia() != null) {
            List<ContatoEmergencia> contatos = pessoaRequest.getContatosEmergencia().stream()
                    .map(dto -> {
                        ContatoEmergencia contato = new ContatoEmergencia();
                        contato.setNome(dto.getNome());
                        contato.setParentesco(dto.getParentesco());
                        contato.setTelefone(dto.getTelefone());
                        contato.setEmail(dto.getEmail());
                        return contato;
                    })
                    .collect(Collectors.toList());
            pessoa.setContatosEmergencia(contatos);
        }

        if (pessoaRequest.getFuncoesIds() != null) {
            List<Funcao> funcoes = pessoaRequest.getFuncoesIds().stream()
                    .map(id -> {
                        Optional<Funcao> funcaoOpt = funcaoRepository.findById(id); // Use FuncaoRepository aqui
                        return funcaoOpt.orElse(null);
                    })
                    .collect(Collectors.toList());
            pessoa.setFuncoes(funcoes);
        }
    }

    private PessoaResponseDTO mapearParaPessoaResponseDTO(Pessoa pessoa) {
        PessoaResponseDTO dto = new PessoaResponseDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setNomeCompleto(pessoa.getNomeCompleto());
        dto.setGenero(pessoa.getGenero());
        dto.setDataNascimento(pessoa.getDataNascimento());
        dto.setEndereco(pessoa.getEndereco());
        dto.setTelefone(pessoa.getTelefone());
        dto.setEmail(pessoa.getEmail());
        dto.setBatizado(pessoa.getBatizado());
        dto.setDataBatismo(pessoa.getDataBatismo());
        dto.setPrivilegio(pessoa.getPrivilegio() != null ? pessoa.getPrivilegio().getPrivilegio() : null);
        dto.setPioneiro(pessoa.getPioneiro() != null ? pessoa.getPioneiro().getTipo() : null);
        dto.setGrupo(pessoa.getGrupo());
        dto.setUngido(pessoa.getUngido());
        if (pessoa.getContatosEmergencia() != null) {
            List<ContatoEmergencia> contatos = pessoa.getContatosEmergencia().stream()
                    .map(dtos -> {
                        ContatoEmergencia contato = new ContatoEmergencia();
                        contato.setPessoa(dtos.getPessoa());
                        contato.setNome(dtos.getNome());
                        contato.setParentesco(dtos.getParentesco());
                        contato.setTelefone(dtos.getTelefone());
                        contato.setEmail(dtos.getEmail());
                        return contato;
                    })
                    .collect(Collectors.toList());
            pessoa.setContatosEmergencia(contatos);
        }
        // Funções
        if (pessoa.getFuncoes() != null) {
            List<Funcao> funcoes = pessoa.getFuncoes().stream()
                    .map(id -> {
                        Optional<Funcao> funcaoOpt = funcaoRepository.findById(id.getId());
                        return funcaoOpt.orElse(null);
                    })
                    .collect(Collectors.toList());
            pessoa.setFuncoes(funcoes);
        }
        return dto;
    }


    private Endereco mapearParaEndereco(EnderecoDTO enderecoDTO) {
        if (enderecoDTO == null) return null;
        Endereco endereco = new Endereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setUf(enderecoDTO.getUf());
        return endereco;
    }

}
