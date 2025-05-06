package com.totvs.cadastros.services;

import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.domains.requests.BuscarUsuarioRequestDTO;
import com.totvs.cadastros.domains.requests.CadastroRequestDTO;
import com.totvs.cadastros.domains.requests.UpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CadastroService {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TelefoneService telefoneService;
    @Autowired
    private EnderecoService enderecoService;


    @Transactional
    public void cadastrarUsuario(CadastroRequestDTO body) throws RuntimeException {
        Usuario usuario = usuarioService.cadastrarUsuario(body.usuario().nome(), body.usuario().cpf());
        telefoneService.cadastrarTelefones(body.telefones(), usuario);
        enderecoService.cadastrarEnderecos(body.enderecos(), usuario);
    }

    public void deletarUsuario(UUID id){
        usuarioService.deletarUsuario(id);
    }

    @Transactional
    public void updateUsuario(UpdateRequestDTO body) throws RuntimeException {
        Usuario usuario = usuarioService.updateUsuario(body.cadastro().usuario().nome(), body.cadastro().usuario().cpf(), body.id());
        telefoneService.updateTelefones(body.cadastro().telefones(), usuario);
        enderecoService.updateEnderecos(body.cadastro().enderecos(), usuario);
    }

    public List<BuscarUsuarioRequestDTO> listarUsuarios(String filtro) {

        List<Usuario> usuarios = usuarioService.buscarUsuario(filtro);

        List<BuscarUsuarioRequestDTO> novosUsuarios = usuarios.stream()
            .map(
                usuario ->
                    new BuscarUsuarioRequestDTO(
                        usuario,
                        telefoneService.buscarTelefonesPorUsuarioId(usuario.getId()),
                        enderecoService.buscarEnderecosPorUsuarioId(usuario.getId()
                    )
                )
            )
        .toList();

        return novosUsuarios;
    }
}
