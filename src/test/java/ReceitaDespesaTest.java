package com.facul.trabalhofinal;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitarios para as entidades Receita, Despesa e Lancamento.
 */
public class ReceitaDespesaTest {

    @Test
    void receitaComDescricaoVaziaUsaDescricaoPadrao() {
        Receita receita = new Receita("   ", "Salario", 1000.0, LocalDate.of(2024, 1, 10));

        assertEquals("Lancamento Receita", receita.getDescricao());
    }

    @Test
    void despesaComDescricaoVaziaUsaDescricaoPadrao() {
        Despesa despesa = new Despesa(null, "Mercado", 200.0, LocalDate.of(2024, 1, 11));

        assertEquals("Lancamento Despesa", despesa.getDescricao());
    }

    @Test
    void receitaMantemValorPositivo() {
        Receita receita = new Receita("Salario", "Trabalho", 1500.0, LocalDate.of(2024, 2, 1));

        assertEquals(1500.0, receita.getValor(), 0.0001);
    }

    @Test
    void despesaRetornaValorNegativo() {
        Despesa despesa = new Despesa("Aluguel", "Moradia", 800.0, LocalDate.of(2024, 2, 5));

        assertEquals(-800.0, despesa.getValor(), 0.0001);
    }

    @Test
    void valorZeroOuNegativoLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () ->
                new Receita("Teste", "Categoria", 0.0, LocalDate.now())
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Despesa("Teste", "Categoria", -10.0, LocalDate.now())
        );
    }
}
