package br.com.hidrometro.core.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FaltaAgua {
    private final int inicioEmSegundos;
    private final int fimSegundos;

    private final List<FaltaAgua>  faltasDeAgua = new ArrayList<>();
    private final Random random = new Random();

    public FaltaAgua(int inicioEmSegundos, int duracaoEmSegundos) {
        this.inicioEmSegundos = inicioEmSegundos;
        this.fimSegundos = inicioEmSegundos + duracaoEmSegundos;
    }

    public void gerarFaltaDeAgua(int vezes, int duracaoMinimaEmSegundos, int duracaoMaximaEmSegundos) {
        for (int i = 0; i < vezes; i++) {
            int SEGUNDOS_POR_DIA = 86400;
            int inicio = random.nextInt(SEGUNDOS_POR_DIA - duracaoMaximaEmSegundos);

            int duracao = duracaoMinimaEmSegundos + random.nextInt(duracaoMaximaEmSegundos - duracaoMinimaEmSegundos + 1);

            FaltaAgua faltaAgua = new FaltaAgua(inicio, duracao);
            faltasDeAgua.add(faltaAgua);
        }
    }

    public List<FaltaAgua> getFaltasDeAgua() {
        return faltasDeAgua;
    }

    public int getInicioEmSegundos() {
        return inicioEmSegundos;
    }

    public int getFimSegundos() {
        return fimSegundos;
    }
}
