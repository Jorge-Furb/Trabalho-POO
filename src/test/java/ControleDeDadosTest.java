package com.facul.trabalhofinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitarios para a classe de controle da camada de negocio.
 */
public class ControleDeDadosTest {

    @Test
    void balancoSomaReceitasEDespesasConsiderandoDespesaNegativa() {
        ControleDeDados controle = new ControleDeDados();
        Receita receita = new Receita("Salario", "Trabalho", 1000.0, LocalDate.of(2024, 1, 1));
        Despesa despesa = new Despesa("Mercado", "Comida", 250.0, LocalDate.of(2024, 1, 2));
        ArrayList<Lancamento> lista = new ArrayList<>(List.of(receita, despesa));

        assertEquals(750.0, controle.balanco(lista), 0.0001);
    }

    @Test
    void adicionarLancamentoNuloLancaExcecao() {
        ControleDeDados controle = new ControleDeDados();

        assertThrows(IllegalArgumentException.class, () -> controle.adicionarLancamento((Receita) null));
        assertThrows(IllegalArgumentException.class, () -> controle.adicionarLancamento((Despesa) null));
    }

    @Test
    void adicionarCategoriasDuplicadasLancaExcecao() {
        ControleDeDados controle = new ControleDeDados();

        controle.adicionarCategoriaReceita("FreelanceJUnit");
        controle.adicionarCategoriaDespesa("TransporteJUnit");

        assertThrows(IllegalArgumentException.class, () -> controle.adicionarCategoriaReceita("FreelanceJUnit"));
        assertThrows(IllegalArgumentException.class, () -> controle.adicionarCategoriaDespesa("TransporteJUnit"));
    }
}
