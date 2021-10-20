package br.com.msmlabs.tdd_leilao.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormataValor {

    public static String valorFormatado(double valor) {
        Locale br = new Locale("pt", "BR");
        NumberFormat formato = NumberFormat.getCurrencyInstance(br);
        String valorFormatado = formato.format(valor);
        return valorFormatado.replace("\u00a0", " ");
    }
}
