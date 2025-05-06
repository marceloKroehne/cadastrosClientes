package com.totvs.cadastros;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.totvs.cadastros.domains.Telefone;
import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.exceptions.TelefoneException;
import com.totvs.cadastros.repositories.TelefoneRepository;
import com.totvs.cadastros.services.TelefoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TelefoneServiceTests {

    @Mock
    private TelefoneRepository telefoneRepository;

    @InjectMocks
    private TelefoneService telefoneService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarTelefones_ComTelefonesValidos_DeveSalvar() {
        Usuario usuario = new Usuario();
        List<String> telefones = Arrays.asList("11912345678", "11987654321");

        when(telefoneRepository.findByTelefone(anyString())).thenReturn(null);

        telefoneService.cadastrarTelefones(telefones, usuario);

        verify(telefoneRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testCadastrarTelefones_TelefoneInvalido_DeveLancarExcecao() {
        Usuario usuario = new Usuario();
        List<String> telefones = Arrays.asList("12345"); 

        Exception exception = assertThrows(TelefoneException.class, () -> {
            telefoneService.cadastrarTelefones(telefones, usuario);
        });

        assertTrue(exception.getMessage().contains("O telefone informado é inválido"));
    }

    @Test
    public void testCadastrarTelefones_TelefoneDuplicado_DeveLancarExcecao() {
        Usuario usuario = new Usuario();
        List<String> telefones = Arrays.asList("11912345678");

        when(telefoneRepository.findByTelefone("11912345678")).thenReturn(new Telefone());

        Exception exception = assertThrows(TelefoneException.class, () -> {
            telefoneService.cadastrarTelefones(telefones, usuario);
        });

        assertTrue(exception.getMessage().contains("O telefone informado já está cadastrado"));
    }

    @Test
    public void testUpdateTelefones_ComSucesso() {
        Usuario usuario = new Usuario(UUID.randomUUID(), "Nome", "52998224725");
        List<String> telefones = Arrays.asList("11912345678");

        when(telefoneRepository.findByTelefone(anyString())).thenReturn(null);

        telefoneService.updateTelefones(telefones, usuario);

        verify(telefoneRepository, times(1)).deleteByUsuarioId(usuario.getId());
        verify(telefoneRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testBuscarTelefonesPorUsuarioId_DeveRetornarLista() {
        UUID usuarioId = UUID.randomUUID();
        List<Telefone> telefonesMock = Collections.singletonList(new Telefone());

        when(telefoneRepository.buscarTelefonesPorUsuarioId(usuarioId)).thenReturn(telefonesMock);

        List<Telefone> telefones = telefoneService.buscarTelefonesPorUsuarioId(usuarioId);

        assertNotNull(telefones);
        assertEquals(1, telefones.size());
        verify(telefoneRepository, times(1)).buscarTelefonesPorUsuarioId(usuarioId);
    }
}
