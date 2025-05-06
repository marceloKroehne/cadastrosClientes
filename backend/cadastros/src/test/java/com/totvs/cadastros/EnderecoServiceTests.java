package com.totvs.cadastros;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.totvs.cadastros.domains.Endereco;
import com.totvs.cadastros.domains.Usuario;
import com.totvs.cadastros.domains.requests.EnderecoRequestDTO;
import com.totvs.cadastros.exceptions.EnderecoException;
import com.totvs.cadastros.repositories.EnderecoRepository;
import com.totvs.cadastros.services.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class EnderecoServiceTests {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private EnderecoRequestDTO criarEnderecoValido() {
        return new EnderecoRequestDTO(
                "Rua Teste",
                "123",
                "Apto 1",
                "Bairro Legal",
                "Cidade Teste",
                "SP",
                "12345678"
        );
    }

    @Test
    public void testCadastrarEnderecos_ComEnderecosValidos_DeveSalvar() {
        Usuario usuario = new Usuario();
        List<EnderecoRequestDTO> enderecos = Arrays.asList(criarEnderecoValido());

        enderecoService.cadastrarEnderecos(enderecos, usuario);

        verify(enderecoRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testCadastrarEnderecos_ComCamposVazios_DeveLancarExcecao() {
        Usuario usuario = new Usuario();
        EnderecoRequestDTO enderecoInvalido = new EnderecoRequestDTO(
                "", // logradouro vazio
                "123",
                "Apto 1",
                "Bairro Legal",
                "Cidade Teste",
                "SP",
                "12345678"
        );

        List<EnderecoRequestDTO> enderecos = Arrays.asList(enderecoInvalido);

        Exception exception = assertThrows(EnderecoException.class, () -> {
            enderecoService.cadastrarEnderecos(enderecos, usuario);
        });

        assertEquals("Todos os campos de endereço devem ser preenchidos!", exception.getMessage());
    }

    @Test
    public void testUpdateEnderecos_ComSucesso() {
        Usuario usuario = new Usuario(UUID.randomUUID(), "Nome", "52998224725");
        List<EnderecoRequestDTO> enderecos = Arrays.asList(criarEnderecoValido());

        enderecoService.updateEnderecos(enderecos, usuario);

        verify(enderecoRepository, times(1)).deleteByUsuarioId(usuario.getId());
        verify(enderecoRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testBuscarEnderecosPorUsuarioId_DeveRetornarLista() {
        UUID usuarioId = UUID.randomUUID();
        List<Endereco> enderecosMock = Collections.singletonList(new Endereco());

        when(enderecoRepository.buscarEnderecosPorUsuarioId(usuarioId)).thenReturn(enderecosMock);

        List<Endereco> enderecos = enderecoService.buscarEnderecosPorUsuarioId(usuarioId);

        assertNotNull(enderecos);
        assertEquals(1, enderecos.size());
        verify(enderecoRepository, times(1)).buscarEnderecosPorUsuarioId(usuarioId);
    }
}
