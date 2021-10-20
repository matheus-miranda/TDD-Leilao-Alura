package br.com.msmlabs.tdd_leilao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.msmlabs.tdd_leilao.R;
import br.com.msmlabs.tdd_leilao.model.Lance;
import br.com.msmlabs.tdd_leilao.model.Leilao;
import br.com.msmlabs.tdd_leilao.util.FormataValor;

public class LancesLeilaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra("leilao")) {
            Leilao leilao = (Leilao) dadosRecebidos.getSerializableExtra("leilao");
            TextView descricao = findViewById(R.id.lances_leilao_descricao);
            descricao.setText(leilao.getDescricao());

            TextView maiorLance = findViewById(R.id.lances_leilao_maior_lance);
            maiorLance.setText(FormataValor.valorFormatado(leilao.getMaiorLance()));

            TextView menorLance = findViewById(R.id.lances_leilao_menor_lance);
            menorLance.setText(FormataValor.valorFormatado(leilao.getMenorLance()));

            TextView maioresLances = findViewById(R.id.lances_leilao_maiores_lances);
            StringBuilder valores = new StringBuilder();
            for (Lance lance : leilao.getTresMaioresLances()) {
                valores.append(FormataValor.valorFormatado(lance.getValor())).append("\n");
            }
            maioresLances.setText(valores.toString());
        }
    }
}