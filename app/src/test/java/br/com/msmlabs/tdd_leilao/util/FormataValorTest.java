package br.com.msmlabs.tdd_leilao.util;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class FormataValorTest {

    @Test
    public void deve_FormatarValorParaMoedaLocal_QuandoRecebeDouble() {
        String valorDevolvido = FormataValor.valorFormatado(1000.0);

        assertThat(valorDevolvido, is("R$ 1.000,00"));
    }

}