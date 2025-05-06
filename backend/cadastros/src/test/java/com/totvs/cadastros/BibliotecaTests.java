package com.totvs.cadastros;

import com.totvs.cadastros.biblioteca.Biblioteca;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BibliotecaTests {

    @Test
    public void testIsCpfValido_ComCpfValido() {
        assertTrue(Biblioteca.isCpfValido("52998224725"));
    }

    @Test
    public void testIsCpfValido_ComCpfInvalido() {
        assertFalse(Biblioteca.isCpfValido("12345678900"));
    }

    @Test
    public void testIsCpfValido_ComTamanhoIncorreto() {
        assertFalse(Biblioteca.isCpfValido("12345"));
    }

    @Test
    public void testStringMaiorIgual_TextoMaiorOuIgual() {
        assertTrue(Biblioteca.StringMaiorIgual("Texto", 3));
    }

    @Test
    public void testStringMaiorIgual_TextoMenor() {
        assertFalse(Biblioteca.StringMaiorIgual("Oi", 3));
    }

    @Test
    public void testStringMaiorIgual_TextoNulo() {
        assertFalse(Biblioteca.StringMaiorIgual(null, 3));
    }

    @Test
    public void testNumeros_ComCaracteresMistos() {
        assertEquals("123456", Biblioteca.numeros("abc123def456"));
    }

    @Test
    public void testNumeros_SemNumeros() {
        assertEquals("", Biblioteca.numeros("abcdef"));
    }

    @Test
    public void testNumeros_ApenasNumeros() {
        assertEquals("123456", Biblioteca.numeros("123456"));
    }

    @Test
    public void testIsTelefoneValido_ComTelefoneValido() {
        assertTrue(Biblioteca.isTelefoneValido("(11) 91234-5678"));
    }

    @Test
    public void testIsTelefoneValido_ComTelefoneInvalido() {
        assertFalse(Biblioteca.isTelefoneValido("12345"));
    }

    @Test
    public void testIsCamposVazios_ComCampoVazio() {
        assertTrue(Biblioteca.isCamposVazios("teste", "", "outro"));
    }

    @Test
    public void testIsCamposVazios_TodosPreenchidos() {
        assertFalse(Biblioteca.isCamposVazios("teste", "outro"));
    }

    @Test
    public void testIsCamposVazios_ComCampoNulo() {
        assertTrue(Biblioteca.isCamposVazios("teste", null));
    }

    @Test
    public void testIsStringVazia_ComTextoVazio() {
        assertTrue(Biblioteca.isStringVazia(""));
    }

    @Test
    public void testIsStringVazia_ComTextoNulo() {
        assertTrue(Biblioteca.isStringVazia(null));
    }

    @Test
    public void testIsStringVazia_ComTextoPreenchido() {
        assertFalse(Biblioteca.isStringVazia("texto"));
    }
}
