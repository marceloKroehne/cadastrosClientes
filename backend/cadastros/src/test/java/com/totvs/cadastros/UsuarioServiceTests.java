package com.totvs.cadastros;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.exceptions.UsuarioException;
import com.totvs.cadastros.repositories.UsuarioRepository;
import com.totvs.cadastros.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UsuarioServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarUsuario_ComDadosValidos_DeveSalvar() {
        String nome = "Usuario Teste";
        String cpf = "52998224725";

        when(usuarioRepository.findByCpf(cpf)).thenReturn(null);
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuario = usuarioService.cadastrarUsuario(nome, cpf);

        assertNotNull(usuario);
        assertEquals(nome, usuario.getNome());
        assertEquals(cpf, usuario.getCpf());
    }

    @Test
    public void testCadastrarUsuario_ComNomeInvalido_DeveLancarExcecao() {
        String nome = "Curto";
        String cpf = "52998224725";

        Exception exception = assertThrows(UsuarioException.class, () -> {
            usuarioService.cadastrarUsuario(nome, cpf);
        });

        assertEquals("O nome do usuário deve conter pelo menos 10 caractéres!", exception.getMessage());
    }

    @Test
    public void testCadastrarUsuario_ComCpfInvalido_DeveLancarExcecao() {
        String nome = "Nome Completo";
        String cpf = "12345678900"; // CPF inválido

        Exception exception = assertThrows(UsuarioException.class, () -> {
            usuarioService.cadastrarUsuario(nome, cpf);
        });

        assertEquals("O cpf digitado é inválido!", exception.getMessage());
    }

    @Test
    public void testCadastrarUsuario_ComCpfJaCadastrado_DeveLancarExcecao() {
        String nome = "Nome Completo";
        String cpf = "52998224725";

        when(usuarioRepository.findByCpf(cpf)).thenReturn(new Usuario(UUID.randomUUID(), nome, cpf));

        Exception exception = assertThrows(UsuarioException.class, () -> {
            usuarioService.cadastrarUsuario(nome, cpf);
        });

        assertTrue(exception.getMessage().contains("Já existe um usuário cadastrado com esse CPF"));
    }

    @Test
    public void testUpdateUsuario_ComDadosValidos_DeveAtualizar() {
        String nome = "Novo Nome Teste";
        String cpf = "52998224725";
        UUID id = UUID.randomUUID();

        when(usuarioRepository.findByCpf(cpf)).thenReturn(new Usuario(id, nome, cpf));
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuario = usuarioService.updateUsuario(nome, cpf, id);

        assertNotNull(usuario);
        assertEquals(nome, usuario.getNome());
        assertEquals(cpf, usuario.getCpf());
        assertEquals(id, usuario.getId());
    }

    @Test
    public void testDeletarUsuario_DeveChamarDeleteById() {
        UUID id = UUID.randomUUID();

        usuarioService.deletarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testBuscarUsuario_SemFiltro_DeveBuscarTodos() {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<Usuario> usuarios = usuarioService.buscarUsuario("");

        assertNotNull(usuarios);
        assertEquals(0, usuarios.size());
    }

    @Test
    public void testBuscarUsuario_ComFiltroCpf_DeveBuscarPorCpf() {
        String filtro = "12345678901";

        when(usuarioRepository.findByCpfLike(filtro)).thenReturn(Collections.emptyList());

        List<Usuario> usuarios = usuarioService.buscarUsuario(filtro);

        assertNotNull(usuarios);
        assertEquals(0, usuarios.size());
    }

    @Test
    public void testBuscarUsuario_ComFiltroNome_DeveBuscarPorNome() {
        String filtro = "Nome";

        when(usuarioRepository.findByNomeLike(filtro)).thenReturn(Collections.emptyList());

        List<Usuario> usuarios = usuarioService.buscarUsuario(filtro);

        assertNotNull(usuarios);
        assertEquals(0, usuarios.size());
    }
}
