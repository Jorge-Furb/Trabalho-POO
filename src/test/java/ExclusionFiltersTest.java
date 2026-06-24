package com.facul.trabalhofinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitarios para filtros por data e categoria.
 */
public class ExclusionFiltersTest {

    @Test
    void filtroPorDataIncluiDatasInicialEFinal() {
        Receita r1 = new Receita("R1", "A", 100.0, LocalDate.of(2024, 1, 1));
        Receita r2 = new Receita("R2", "A", 200.0, LocalDate.of(2024, 1, 15));
        Receita r3 = new Receita("R3", "A", 300.0, LocalDate.of(2024, 2, 1));
        ArrayList<Receita> lista = new ArrayList<>(List.of(r1, r2, r3));

        ArrayList<Receita> filtrada = ExclusionFilters.filterByDate(
                lista,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 15)
        );

        assertEquals(2, filtrada.size());
        assertTrue(filtrada.contains(r1));
        assertTrue(filtrada.contains(r2));
        assertFalse(filtrada.contains(r3));
    }

    @Test
    void filtroPorDataComInicioMaiorQueFimLancaExcecao() {
        ArrayList<Receita> lista = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () ->
                ExclusionFilters.filterByDate(
                        lista,
                        LocalDate.of(2024, 2, 1),
                        LocalDate.of(2024, 1, 1)
                )
        );
    }

    @Test
    void filtroPorCategoriaMantemSomenteCategoriasSelecionadas() {
        Receita r1 = new Receita("R1", "Salario", 100.0, LocalDate.now());
        Receita r2 = new Receita("R2", "Investimentos", 200.0, LocalDate.now());
        ArrayList<Receita> lista = new ArrayList<>(List.of(r1, r2));
        ArrayList<String> categorias = new ArrayList<>(List.of("Salario"));

        ArrayList<Receita> filtrada = ExclusionFilters.filterByCategoria(lista, categorias);

        assertEquals(1, filtrada.size());
        assertEquals("Salario", filtrada.get(0).getCategoria());
    }
}
