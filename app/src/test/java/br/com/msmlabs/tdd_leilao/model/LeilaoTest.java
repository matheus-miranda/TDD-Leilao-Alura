package br.com.msmlabs.tdd_leilao.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeilaoTest {

    // Criar cenário de teste
    private final Leilao LEILAO = new Leilao("console");
    private final Usuario JOAO = new Usuario("Joao");

    // https://dzone.com/articles/7-popular-unit-test-naming Should_ExpectedBehavior_WhenStateUnderTest
    // [Nome do método] [Estado do teste] [Resultado esperado] ou
    // [Deve] [Resultado esperado] [Estado do teste] -> nao precisa refatorar quando nome do metodo muda
    @Test
    public void deve_DevolveDescricao_QuandoRecebeUmaDescricao() {
        // Executar ação esperada
        String descricaoDevolvida = LEILAO.getDescricao();

        // Testar resultado esperado
        assertEquals("console", descricaoDevolvida);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeApenasUmLance() {
        LEILAO.propoe(new Lance(JOAO, 200.0));

        double maiorLanceDevolvido = LEILAO.getMaiorLance();

        // Valor delta é a diferença entre os valores com pontos flutuantes e se ele for maior,
        // significa que os valores são equivalentes
        assertEquals(200.0, maiorLanceDevolvido, 0.000001);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        LEILAO.propoe(new Lance(JOAO, 100.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 200.0));

        double maiorLanceDevolvido = LEILAO.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, 0.000001);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        LEILAO.propoe(new Lance(JOAO, 200.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 100.0));

        double maiorLanceDevolvido = LEILAO.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, 0.000001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeUmLance() {
        LEILAO.propoe(new Lance(JOAO, 200.0));

        double menorLanceDevolvido = LEILAO.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, 0.00001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        LEILAO.propoe(new Lance(JOAO, 100.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 200.0));

        double menorLanceDevolvido = LEILAO.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, 0.00001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        LEILAO.propoe(new Lance(JOAO, 200.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 100.0));

        double menorLanceDevolvido = LEILAO.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, 0.00001);
    }
}