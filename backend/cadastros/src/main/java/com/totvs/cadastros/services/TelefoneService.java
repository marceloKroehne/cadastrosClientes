package com.totvs.cadastros.services;

import com.totvs.cadastros.biblioteca.Biblioteca;
import com.totvs.cadastros.domains.Telefone;
import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.exceptions.TelefoneException;
import com.totvs.cadastros.repositories.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    public void cadastrarTelefones(List<String> telefones, Usuario usuario) throws RuntimeException {

        validarTelefones(telefones);

        List<Telefone> novosTelefones = telefones.stream()
            .map(
                tel -> {
                    Telefone novoTel = new Telefone();
                    novoTel.setTelefone(tel);
                    novoTel.setUsuario(usuario);

                    return novoTel;
                }
            )
        .toList();

        telefoneRepository.saveAll(novosTelefones);
    }

    private void validarTelefones(List<String> telefones) throws RuntimeException {
        for (String telefone : telefones){
            if(!Biblioteca.isTelefoneValido(telefone))
                throw new TelefoneException("O telefone informado é inválido! (" +telefone +")");

            if(existeTelefoneCadastrado(telefone))
                throw new TelefoneException("O telefone informado já está cadastrado! (" +telefone +")");
        }
    }

    private boolean existeTelefoneCadastrado(String telefone) {
        return telefoneRepository.findByTelefone(telefone) != null;
    }

    public void updateTelefones(List<String> telefones, Usuario usuario) throws RuntimeException {
        telefoneRepository.deleteByUsuarioId(usuario.getId());
        validarTelefones(telefones);
        cadastrarTelefones(telefones, usuario);
    }

    public List<Telefone> buscarTelefonesPorUsuarioId(UUID id) {
        return telefoneRepository.buscarTelefonesPorUsuarioId(id);
    }
}
