package br.com.hidrometro.core.components;

import br.com.hidrometro.core.events.FaltaAgua;
import br.com.hidrometro.core.ui.Display;
import br.com.hidrometro.utils.TipoFluido;

import java.util.List;

public class Hidrometro {
    private final CanoEntrada canoEntrada;
    private final CanoSaida canoSaida;
    private final Medidor medidor;
    private final Display display;

    private double valorPassagemAguaTotalAtual = 0.0;
    private int tempoAtualSegundos = 0;
    private boolean emFaltaAgua = false;
    private int contadorRolado;

    private static final double LIMITE_M3 = 9999999.99;

    public Hidrometro(CanoEntrada canoEntrada, CanoSaida canoSaida, Medidor medidor, Display display) {
        this.canoEntrada = canoEntrada;
        this.canoSaida = canoSaida;
        this.medidor = medidor;
        this.display = display;
    }

    public void medir(List<FaltaAgua> faltaAguaListAgua) {
        tempoAtualSegundos++;

        boolean deveEstarEmFalta = false;

        for (FaltaAgua evento : faltaAguaListAgua) {
            if (tempoAtualSegundos >= evento.getInicioEmSegundos() && tempoAtualSegundos < evento.getFimSegundos()) {
                deveEstarEmFalta = true;
                break;
            }
        }

        if  (deveEstarEmFalta && !emFaltaAgua) {
            canoEntrada.setTipoFluido(TipoFluido.AR);
            canoSaida.setTipoFluido(TipoFluido.AR);
            emFaltaAgua = true;
            System.out.println("⚠️ FALTA DE ÁGUA!");
            this.display.alertaFaltaDeAgua(true);
        } else if (!deveEstarEmFalta && emFaltaAgua) {
            canoEntrada.setTipoFluido(TipoFluido.AGUA);
            canoSaida.setTipoFluido(TipoFluido.AGUA);
            emFaltaAgua = false;
            this.display.alertaFaltaDeAgua(false);
            System.out.println("✅ ÁGUA RESTABELECIDA!");
        }

        valorPassagemAguaTotalAtual += this.medidor.calcularPassagemAgua(canoEntrada.getRaioEmMetros(), canoEntrada.getVazaoEmMetros(), canoEntrada.getTipoFluido());

        if (valorPassagemAguaTotalAtual >= LIMITE_M3) {
            valorPassagemAguaTotalAtual = valorPassagemAguaTotalAtual - LIMITE_M3;
            this.contadorRolado++;
            System.out.println("🔄 CONTADOR ROLADO! Volume excedeu " + LIMITE_M3 + " m³");
        }

        display.atualizarDisplay(valorPassagemAguaTotalAtual);
    }

    public Display getDisplay() {
        return display;
    }
}
