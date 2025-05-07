package com.totvs.cadastros.services;

import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.domains.requests.BuscarUsuarioRequestDTO;
import com.totvs.cadastros.domains.requests.CadastroRequestDTO;
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
        Usuario usuario = usuarioService.cadastrarUsuario(body.nome(), body.cpf());
        telefoneService.cadastrarTelefones(body.telefones(), usuario);
        enderecoService.cadastrarEnderecos(body.enderecos(), usuario);
    }

    public void deletarUsuario(UUID id){
        usuarioService.deletarUsuario(id);
    }

    @Transactional
    public void updateUsuario(CadastroRequestDTO body, UUID id) throws RuntimeException {
        Usuario usuario = usuarioService.updateUsuario(body.nome(), body.cpf(), id);
        telefoneService.updateTelefones(body.telefones(), usuario);
        enderecoService.updateEnderecos(body.enderecos(), usuario);
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

    public BuscarUsuarioRequestDTO buscarPorId(UUID id){
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        return new BuscarUsuarioRequestDTO(
            usuario,
            telefoneService.buscarTelefonesPorUsuarioId(usuario.getId()),
            enderecoService.buscarEnderecosPorUsuarioId(usuario.getId())
        );
    }
}
