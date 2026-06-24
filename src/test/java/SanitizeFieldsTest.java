package com.facul.trabalhofinal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitarios para sanitizacao de campos de entrada.
 */
public class SanitizeFieldsTest {

    @Test
    void descricaoRemoveQuebrasPontoEVirgulaEAparaEspacos() {
        String resultado = SanitizeFields.descricao("  Linha 1\nLinha;2\r  ");

        assertEquals("Linha 1. LinhaB===D2.", resultado);
    }

    @Test
    void categoriaLimitaTamanhoParaVinteECincoCaracteres() {
        String texto = "123456789012345678901234567890";

        assertEquals(25, SanitizeFields.categoria(texto).length());
    }

    @Test
    void valorArredondaParaDuasCasasDecimais() {
        assertEquals(10.24, SanitizeFields.valor(10.235), 0.0001);
        assertEquals(10.23, SanitizeFields.valor(10.234), 0.0001);
    }
}
