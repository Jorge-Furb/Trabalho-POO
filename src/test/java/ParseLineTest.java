

import com.facul.trabalhofinal.ParseLine;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

/**
 * Testes unitários para a classe ParseLine
 */
public class ParseLineTest {

    // Testes para parseDescricao
    @Test
    public void testParseDescricaoValido() {
        String linha = "Compra no supermercado;Alimentação;50.00;2024-06-10";
        String resultado = ParseLine.parseDescricao(linha);
        assertEquals("Compra no supermercado", resultado);
    }

    @Test
    public void testParseDescricaoComEspacos() {
        String linha = "  Compra  ;Alimentação;50.00;2024-06-10";
        String resultado = ParseLine.parseDescricao(linha);
        assertEquals("  Compra  ", resultado);
    }

    @Test
    public void testParseDescricaoVazio() {
        String linha = ";Alimentação;50.00;2024-06-10";
        String resultado = ParseLine.parseDescricao(linha);
        assertEquals("Sem descrição", resultado);
    }

    // Testes para parseCategoria
    @Test
    public void testParseCategoriaValido() {
        String linha = "Compra no supermercado;Alimentação;50.00;2024-06-10";
        String resultado = ParseLine.parseCategoria(linha);
        assertEquals("Alimentação", resultado);
    }

    @Test
    public void testParseCategoriaVazio() {
        String linha = "Feijao;;50.00;2024-06-10";
        String resultado = ParseLine.parseCategoria(linha);
        assertEquals("Sem categoria", resultado);
    }

    @Test
    public void testParseCategoriaComEspacos() {
        String linha = "Compra;  Transporte  ;50.00;2024-06-10";
        String resultado = ParseLine.parseCategoria(linha);
        assertEquals("  Transporte  ", resultado);
    }

    // Testes para parseValor
    @Test
    public void testParseValorValido() {
        String linha = "Compra;Alimentação;150.75;2024-06-10";
        double resultado = ParseLine.parseValor(linha);
        assertEquals(150.75, resultado, 0.001);
    }

    @Test
    public void testParseValorInteiro() {
        String linha = "Compra;Alimentação;100;2024-06-10";
        double resultado = ParseLine.parseValor(linha);
        assertEquals(100.0, resultado, 0.001);
    }

    @Test
    public void testParseValorComEspacos() {
        String linha = "Compra;Alimentação;  50.00  ;2024-06-10";
        double resultado = ParseLine.parseValor(linha);
        assertEquals(50.0, resultado, 0.001);
    }

    @Test
    public void testParseValorInvalido() {
        String linha = "Compra;Alimentação;ABC;2024-06-10";
        double resultado = ParseLine.parseValor(linha);
        assertEquals(-1.0, resultado, 0.001);
    }

    @Test
    public void testParseValorZero() {
        String linha = "Compra;Alimentação;0;2024-06-10";
        double resultado = ParseLine.parseValor(linha);
        assertEquals(0.0, resultado, 0.001);
    }

    @Test
    public void testParseValorNegativo() {
        String linha = "Compra;Alimentação;-25.50;2024-06-10";
        double resultado = ParseLine.parseValor(linha);
        assertEquals(-25.50, resultado, 0.001);
    }

    // Testes para parseDate
    @Test
    public void testParseDateValido() {
        String linha = "Compra;Alimentação;50.00;2024-06-10";
        LocalDate resultado = ParseLine.parseDate(linha);
        assertEquals(LocalDate.of(2024, 6, 10), resultado);
    }

    @Test
    public void testParseDateComEspacos() {
        String linha = "Compra;Alimentação;50.00;  2024-12-25  ";
        LocalDate resultado = ParseLine.parseDate(linha);
        assertEquals(LocalDate.of(2024, 12, 25), resultado);
    }

    @Test
    public void testParseDateInvalido() {
        String linha = "Compra;Alimentação;50.00;31/12/2024";
        LocalDate resultado = ParseLine.parseDate(linha);
        // Retorna data padrão 1900-01-01 em caso de erro
        assertEquals(LocalDate.of(1900, 1, 1), resultado);
    }

    @Test
    public void testParseDateFormatoInvalido() {
        String linha = "Compra;Alimentação;50.00;13-13-2024";
        LocalDate resultado = ParseLine.parseDate(linha);
        assertEquals(LocalDate.of(1900, 1, 1), resultado);
    }

    @Test
    public void testParseLinhaCompleta() {
        String linha = "Pagamento de aluguel;Habitação;1500.00;2024-06-05";
        
        String descricao = ParseLine.parseDescricao(linha);
        String categoria = ParseLine.parseCategoria(linha);
        double valor = ParseLine.parseValor(linha);
        LocalDate data = ParseLine.parseDate(linha);
        
        assertEquals("Pagamento de aluguel", descricao);
        assertEquals("Habitação", categoria);
        assertEquals(1500.00, valor, 0.001);
        assertEquals(LocalDate.of(2024, 6, 5), data);
    }

    @Test
    public void testParseLinhaVazios() {
        String linha = ";;100;2024-06-10";
        
        String descricao = ParseLine.parseDescricao(linha);
        String categoria = ParseLine.parseCategoria(linha);
        
        assertEquals("Sem descrição", descricao);
        assertEquals("Sem categoria", categoria);
    }
}
