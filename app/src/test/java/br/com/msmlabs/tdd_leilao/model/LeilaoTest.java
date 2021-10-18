package br.com.msmlabs.tdd_leilao.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeilaoTest {

    // https://dzone.com/articles/7-popular-unit-test-naming Should_ExpectedBehavior_WhenStateUnderTest
    // [Nome do método] [Estado do teste] [Resultado esperado] ou
    // [Deve] [Resultado esperado] [Estado do teste] -> nao precisa refatorar quando nome do metodo muda
    @Test
    public void deve_DevolveDescricao_QuandoRecebeUmaDescricao() {
        // Criar cenário de test
        Leilao leilao = new Leilao("console");

        // Executar ação esperada
        String descricaoDevolvida = leilao.getDescricao();

        // Testar resultado esperado
        assertEquals("console", descricaoDevolvida);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeApenasUmLance() {
        Leilao leilao = new Leilao("console");
        leilao.propoe(new Lance(new Usuario("Joao"), 200.0));

        double maiorLanceDevolvido = leilao.getMaiorLance();

        // Valor delta é a diferença entre os valores com pontos flutuantes e se ele for maior,
        // significa que os valores são equivalentes
        assertEquals(200.0, maiorLanceDevolvido, 0.000001);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        Leilao computador = new Leilao("computador");
        computador.propoe(new Lance(new Usuario("Joao"), 100.0));
        computador.propoe(new Lance(new Usuario("Joana"), 200.0));

        double maiorLanceDevolvido = computador.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, 0.000001);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        Leilao carro = new Leilao("carro");
        carro.propoe(new Lance(new Usuario("Joao"), 200.0));
        carro.propoe(new Lance(new Usuario("Joana"), 100.0));

        double maiorLanceDevolvido = carro.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, 0.000001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeUmLance() {
        Leilao console = new Leilao("console");
        console.propoe(new Lance(new Usuario("Joao"), 200.0));

        double menorLanceDevolvido = console.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, 0.00001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        Leilao computador = new Leilao("computador");
        computador.propoe(new Lance(new Usuario("Joao"), 100.0));
        computador.propoe(new Lance(new Usuario("Joana"), 200.0));

        double menorLanceDevolvido = computador.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, 0.00001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        Leilao computador = new Leilao("computador");
        computador.propoe(new Lance(new Usuario("Joao"), 200.0));
        computador.propoe(new Lance(new Usuario("Joana"), 100.0));

        double menorLanceDevolvido = computador.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, 0.00001);
    }
}