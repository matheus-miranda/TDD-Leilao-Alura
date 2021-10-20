package br.com.msmlabs.tdd_leilao.model;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;

import br.com.msmlabs.tdd_leilao.builder.LeilaoBuilder;
import br.com.msmlabs.tdd_leilao.exception.LanceMenorQueMaiorLanceException;
import br.com.msmlabs.tdd_leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.msmlabs.tdd_leilao.exception.UsuarioJaDeuCincoLancesException;

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
        //assertEquals("console", descricaoDevolvida);
        assertThat(descricaoDevolvida, is("console"));
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeApenasUmLance() {
        LEILAO.propoe(new Lance(JOAO, 200.0));

        double maiorLanceDevolvido = LEILAO.getMaiorLance();

        // Valor delta é a diferença entre os valores com pontos flutuantes e se ele for maior,
        // significa que os valores são equivalentes
        //assertEquals(200.0, maiorLanceDevolvido, DELTA);
        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
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

        //assertEquals(3, tresMaioresLancesDevolvidos.size());
//        assertThat(tresMaioresLancesDevolvidos, hasSize(equalTo(3)));
        //assertEquals(500.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        //assertThat(tresMaioresLancesDevolvidos, hasItem(new Lance(JOAO, 500.0)));
        //assertEquals(400.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
        //assertEquals(200.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);
        // Ordem deve ser correta
//        assertThat(tresMaioresLancesDevolvidos, contains(
//                new Lance(JOAO, 500.0),
//                new Lance(new Usuario("Joana"), 400.0),
//                new Lance(JOAO, 200.0)
//        ));
        assertThat(tresMaioresLancesDevolvidos,
                both(hasSize(3))
                .and(contains(
                        new Lance(JOAO, 500.0),
                        new Lance(new Usuario("Joana"), 400.0),
                        new Lance(JOAO, 200.0)
                ))
        );
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
        LEILAO.propoe(new Lance(new Usuario("Joana"), 300.0));

        List<Lance> tresMaioresLancesDevolvidos = LEILAO.getTresMaioresLances();

        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        LEILAO.propoe(new Lance(JOAO, 200.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 300.0));
        LEILAO.propoe(new Lance(JOAO, 400.0));
        LEILAO.propoe(new Lance(new Usuario("Fran"), 600.0));

        List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = LEILAO.getTresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());
        assertEquals(600, tresMaioresLancesDevolvidosParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(400, tresMaioresLancesDevolvidosParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(300, tresMaioresLancesDevolvidosParaQuatroLances.get(2).getValor(), DELTA);

        LEILAO.propoe(new Lance(JOAO, 700.0));

        List<Lance> tresMaioresLancesDevolvidosParaCincoLances = LEILAO.getTresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());
        assertEquals(700.0, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(600.0, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(400.0, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(), DELTA);
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

    @Test(expected = LanceMenorQueMaiorLanceException.class)
    public void deve_LancarException_QuandoReceberLanceMenorQueMaiorLance() {
        LEILAO.propoe(new Lance(JOAO, 300.0));
        LEILAO.propoe(new Lance(new Usuario("Joana"), 200.0));
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void naoDeve_AdicionarLance_QuandoMesmoUsuarioDerDoisLancesSeguidos() {
        LEILAO.propoe(new Lance(JOAO, 200.0));
        LEILAO.propoe(new Lance(new Usuario("Joao"), 300.0));

        int qtdLancesDevolvidos = LEILAO.quantidadeLances();

        assertEquals(1, qtdLancesDevolvidos);
    }

    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void naoDeve_AdicionarLance_QuandoMesmoUsuarioDerMaisDeCincoLances() {
        final Usuario JOANA = new Usuario("Joana");

        final Leilao leilao = new LeilaoBuilder("console")
                .lance(JOAO, 200.0)
                .lance(JOANA, 300.0)
                .lance(JOAO, 400.0)
                .lance(JOANA, 500.0)
                .lance(JOAO, 600.0)
                .lance(JOANA, 700.0)
                .lance(JOAO, 800.0)
                .lance(JOANA, 900.0)
                .lance(JOAO, 1000.0)
                .lance(JOANA, 1100.0)
                .build();
        leilao.propoe(new Lance(JOAO, 1200.0));

        int qtdLancesDevolvidos = leilao.quantidadeLances();

        assertEquals(10, qtdLancesDevolvidos);
    }

}