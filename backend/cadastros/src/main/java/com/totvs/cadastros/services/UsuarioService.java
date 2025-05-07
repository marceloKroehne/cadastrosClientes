package com.totvs.cadastros.services;

import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.biblioteca.Biblioteca;
import com.totvs.cadastros.exceptions.UsuarioException;
import com.totvs.cadastros.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(String nome, String cpf) throws RuntimeException {

        validarInformacoesUsuario(nome, cpf, null);

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);

        return this.usuarioRepository.save(usuario);

    }

    private void validarInformacoesUsuario(
        String nome,
        String cpf,
        UUID id
    ) throws RuntimeException {
        if(!Biblioteca.StringMaiorIgual(nome,10))
            throw new UsuarioException("O nome do usuário deve conter pelo menos 10 caractéres!");
        if(!Biblioteca.isCpfValido(cpf))
            throw new UsuarioException("O cpf digitado é inválido!");

        if(existeCpfCadastrado(cpf, id))
            throw new UsuarioException("Já existe um usuário cadastrado com esse CPF!("+cpf+"| " + id + ")");

    }

    private boolean existeCpfCadastrado(String cpf, UUID id) {
        Usuario usuario = this.usuarioRepository.findByCpf(cpf);

        if(usuario == null)
            return false;

        return !usuario.getId().equals(id);

    }

    public Usuario updateUsuario(String nome, String cpf, UUID id) throws RuntimeException {
        validarInformacoesUsuario(nome, cpf, id);

        Usuario usuario = new Usuario(id, nome, cpf);

        return usuarioRepository.save(usuario);

    }

    public void deletarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> buscarUsuario(String filtro) {

        if(Biblioteca.isStringVazia(filtro))
            return usuarioRepository.findAll();
        else if(Biblioteca.numeros(filtro).length() == filtro.length())
            return usuarioRepository.findByCpfLike(filtro);
        else
            return usuarioRepository.findByNomeLike(filtro);
    }

    public Usuario buscarUsuarioPorId(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioException("Usuário não encontrado com o ID: " + id));
    }
}
