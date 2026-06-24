package com.facul.trabalhofinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitarios para ordenacao de lancamentos por data.
 */
public class SortFiltersTest {

    @Test
    void ordenaReceitasPorDataSemAlterarListaOriginal() {
        Receita maisNova = new Receita("Nova", "A", 100.0, LocalDate.of(2024, 3, 1));
        Receita maisAntiga = new Receita("Antiga", "A", 100.0, LocalDate.of(2024, 1, 1));
        ArrayList<Receita> original = new ArrayList<>(List.of(maisNova, maisAntiga));

        ArrayList<Receita> ordenada = SortFilters.sortByDate(original);

        assertEquals(maisAntiga, ordenada.get(0));
        assertEquals(maisNova, ordenada.get(1));
        assertEquals(maisNova, original.get(0));
    }

    @Test
    void combinaReceitasEDespesasOrdenandoPorData() {
        Despesa despesa = new Despesa("D", "A", 50.0, LocalDate.of(2024, 2, 1));
        Receita receita = new Receita("R", "A", 100.0, LocalDate.of(2024, 1, 1));

        ArrayList<Lancamento> ordenada = SortFilters.sortByDate(
                new ArrayList<>(List.of(despesa)),
                new ArrayList<>(List.of(receita))
        );

        assertEquals(receita, ordenada.get(0));
        assertEquals(despesa, ordenada.get(1));
    }
}
