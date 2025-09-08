package br.com.hidrometro.core.components;

import br.com.hidrometro.utils.TipoFluido;

public class Medidor {
    private final int FATOR_AR_PORCENTAGEM;

    public Medidor(int FATOR_AR_PORCENTAGEM) {
        this.FATOR_AR_PORCENTAGEM = FATOR_AR_PORCENTAGEM;
    }

    public double calcularPassagemAgua(double raioCanoEntradaEmMetros, double vazaoMetrosPorSegundo, TipoFluido tipoFluido) {
        double areaEntradaM2 = Math.PI * Math.pow(raioCanoEntradaEmMetros, 2); // m²
        double vazaoVolumetrica = areaEntradaM2 * (vazaoMetrosPorSegundo * 100) * 1000; // vazao * 100 - Para a simulação ficar mais rápida - l/s

        return tipoFluido == TipoFluido.AGUA ? vazaoVolumetrica : vazaoVolumetrica * (FATOR_AR_PORCENTAGEM / 100.0);
    }
}
