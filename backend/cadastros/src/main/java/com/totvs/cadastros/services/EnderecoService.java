package com.totvs.cadastros.services;

import com.totvs.cadastros.biblioteca.Biblioteca;
import com.totvs.cadastros.domains.Endereco;
import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.domains.requests.EnderecoRequestDTO;
import com.totvs.cadastros.exceptions.EnderecoException;
import com.totvs.cadastros.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public void cadastrarEnderecos(List<EnderecoRequestDTO> enderecos, Usuario usuario) throws RuntimeException {

        validarEnderecos(enderecos);

        List<Endereco> novosEndereco = enderecos.stream()
            .map(
                endereco ->{
                    Endereco novoEndereco = new Endereco();
                    novoEndereco.setLogradouro(endereco.logradouro());
                    novoEndereco.setNumero(endereco.numero());
                    novoEndereco.setComplemento(endereco.complemento());
                    novoEndereco.setBairro(endereco.bairro());
                    novoEndereco.setCidade(endereco.cidade());
                    novoEndereco.setUf(endereco.uf());
                    novoEndereco.setCep(endereco.cep());
                    novoEndereco.setUsuario(usuario);

                    return novoEndereco;
                }
            ).toList();

        enderecoRepository.saveAll(novosEndereco);
    }

    private void validarEnderecos(List<EnderecoRequestDTO> enderecos) throws RuntimeException {
        for(EnderecoRequestDTO endereco : enderecos){
            if(
                Biblioteca.isCamposVazios(
                    endereco.logradouro(),
                    endereco.numero(),
                    endereco.complemento(),
                    endereco.bairro(),
                    endereco.cidade(),
                    endereco.uf(),
                    endereco.cep()
                )
            )
                throw new EnderecoException("Todos os campos de endereço devem ser preenchidos!");
        }

    }

    public void updateEnderecos(List<EnderecoRequestDTO> enderecos, Usuario usuario) throws RuntimeException {

        enderecoRepository.deleteByUsuarioId(usuario.getId());
        validarEnderecos(enderecos);
        cadastrarEnderecos(enderecos, usuario);

    }

    public List<Endereco> buscarEnderecosPorUsuarioId(UUID id) {
        return enderecoRepository.buscarEnderecosPorUsuarioId(id);
    }
}
