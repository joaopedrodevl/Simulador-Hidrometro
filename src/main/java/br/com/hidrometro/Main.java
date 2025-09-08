package br.com.hidrometro;

import br.com.hidrometro.config.Configuracao;
import br.com.hidrometro.core.*;
import br.com.hidrometro.core.components.*;
import br.com.hidrometro.core.ui.Display;
import br.com.hidrometro.utils.TipoFluido;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        Configuracao configuracao = new Configuracao(new ObjectMapper());
        Simulador simulador = getSimulador(configuracao);
        simulador.execute();
    }

    private static Simulador getSimulador(Configuracao configuracao) throws Exception {
        CanoEntrada canoEntrada = new CanoEntrada(TipoFluido.AGUA, configuracao.getConfiguracaoDTO().diametroCanoEntradaEmMilimetros(), configuracao.getConfiguracaoDTO().vazaoEmMilimetrosPorSegundoEntrada());
        CanoSaida canoSaida = new CanoSaida(TipoFluido.AGUA, configuracao.getConfiguracaoDTO().diametroCanoSaidaEmMilimetros(), configuracao.getConfiguracaoDTO().vazaoEmMilimetrosPorSegundoSaida());
        Medidor medidor = new Medidor(configuracao.getConfiguracaoDTO().fatorPorcentagemArAConsiderar());
        Display display = new Display();
        Hidrometro hidrometro = new Hidrometro(canoEntrada, canoSaida, medidor, display);

        return new Simulador(configuracao, hidrometro);
    }
}