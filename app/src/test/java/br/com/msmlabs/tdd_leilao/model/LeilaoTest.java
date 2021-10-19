package br.com.msmlabs.tdd_leilao.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;

public class LeilaoTest {

    // Criar cenário de teste
    private final Leilao LEILAO = new Leilao("console");
    private final Usuario JOAO = new Usuario("Joao");
    private static final double DELTA = 0.00001;

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
        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        LEILAO.propoe(new Lance(JOAO, 100.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 200.0));

        double maiorLanceDevolvido = LEILAO.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeUmLance() {
        LEILAO.propoe(new Lance(JOAO, 200.0));

        double menorLanceDevolvido = LEILAO.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        LEILAO.propoe(new Lance(JOAO, 100.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 200.0));

        double menorLanceDevolvido = LEILAO.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_RetornarTresMaioresLances_QuandoRecebeExatosTresLances() {
        LEILAO.propoe(new Lance(JOAO, 200.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 400.0));
        LEILAO.propoe(new Lance(JOAO, 500.0));

        List<Lance> tresMaioresLancesDevolvidos = LEILAO.getTresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidos.size());
        assertEquals(500.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(400.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLancesDevolvidos = LEILAO.getTresMaioresLances();

        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeUmLance() {
        LEILAO.propoe(new Lance(JOAO, 200.0));

        List<Lance> tresMaioresLancesDevolvidos = LEILAO.getTresMaioresLances();

        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeDoisLances() {
        LEILAO.propoe(new Lance(JOAO, 200.0));
        LEILAO.propoe(new Lance(JOAO, 300.0));

        List<Lance> tresMaioresLancesDevolvidos = LEILAO.getTresMaioresLances();

        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        LEILAO.propoe(new Lance(JOAO, 200.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 300.0));
        LEILAO.propoe(new Lance(JOAO, 100.0));
        LEILAO.propoe(new Lance(JOAO, 600.0));

        List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = LEILAO.getTresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());
        assertEquals(600, tresMaioresLancesDevolvidosParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(300, tresMaioresLancesDevolvidosParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(200, tresMaioresLancesDevolvidosParaQuatroLances.get(2).getValor(), DELTA);

        LEILAO.propoe(new Lance(JOAO, 700.0));

        List<Lance> tresMaioresLancesDevolvidosParaCincoLances = LEILAO.getTresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());
        assertEquals(700.0, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(600.0, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLances() {
        double maiorLanceDevolvido = LEILAO.getMaiorLance();

        assertEquals(0.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMenorLance_QuandoNaoTiverLances() {
        double menorLanceDevolvido = LEILAO.getMenorLance();

        assertEquals(0.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void nadDeve_AdicionarLance_QuandoForMenorQueMaiorLance() {
        LEILAO.propoe(new Lance(JOAO, 300.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 200.0));

        int quantidadeLancesDevolvidas = LEILAO.quantidadeLances();

        assertEquals(1, quantidadeLancesDevolvidas);
    }
}