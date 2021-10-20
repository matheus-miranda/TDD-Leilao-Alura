package br.com.msmlabs.tdd_leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.msmlabs.tdd_leilao.exception.LanceMenorQueMaiorLanceException;
import br.com.msmlabs.tdd_leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.msmlabs.tdd_leilao.exception.UsuarioJaDeuCincoLancesException;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maiorLance = 0.0;
    private double menorLance = 0.0;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public void propoe(Lance lance) {
        if (lanceNaoValido(lance)) return;
        lances.add(lance);
        double valorLance = lance.getValor();
        if (defineMenorEMaiorLanceParaPrimeiroLance(valorLance)) return;
        Collections.sort(lances);
        calculaMaiorLance(valorLance);
        calculaMenorLance(valorLance);
    }

    private boolean defineMenorEMaiorLanceParaPrimeiroLance(double valorLance) {
        if (lances.size() == 1) {
            maiorLance = valorLance;
            menorLance = valorLance;
            return true;
        }
        return false;
    }

    private boolean lanceNaoValido(Lance lance) {
        if (lanceMenorQueMaiorLance(lance))
            throw new LanceMenorQueMaiorLanceException();

        if (temLances()) {
            Usuario usuarioNovo = lance.getUsuario();
            if (usuarioIgualDoUltimoLance(usuarioNovo))
                throw new LanceSeguidoDoMesmoUsuarioException();
            if (usuarioDeuMaisDeCincoLances(usuarioNovo))
                throw new UsuarioJaDeuCincoLancesException();
        }
        return false;
    }

    private boolean temLances() {
        return !lances.isEmpty();
    }

    private boolean usuarioDeuMaisDeCincoLances(Usuario usuarioNovo) {
        int lancesUsuario = 0;
        for (Lance l : lances) {
            if (l.getUsuario().equals(usuarioNovo)) {
                lancesUsuario++;
                if (lancesUsuario == 5) return true;
            }
        }
        return false;
    }

    private boolean usuarioIgualDoUltimoLance(Usuario usuarioNovo) {
        Usuario ultimoUsuario = lances.get(0).getUsuario();
        if (usuarioNovo.equals(ultimoUsuario)) return true;
        return false;
    }

    private boolean lanceMenorQueMaiorLance(Lance lance) {
        if (lance.getValor() <= maiorLance) return true;
        return false;
    }

    private void calculaMenorLance(double valorLance) {
        if (valorLance < menorLance) {
            menorLance = valorLance;
        }
    }

    private void calculaMaiorLance(double valorLance) {
        if (valorLance > maiorLance) {
            maiorLance = valorLance;
        }
    }

    public double getMaiorLance() {
        return maiorLance;
    }

    public double getMenorLance() {
        return menorLance;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Lance> getTresMaioresLances() {
        int maxSize = Math.min(lances.size(), 3);
        return lances.subList(0, maxSize);
    }

    public int quantidadeLances() {
        return lances.size();
    }
}
