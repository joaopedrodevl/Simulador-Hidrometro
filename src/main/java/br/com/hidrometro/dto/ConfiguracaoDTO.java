package br.com.hidrometro.dto;

public record ConfiguracaoDTO (
        double diametroCanoEntradaEmMilimetros,
        double diametroCanoSaidaEmMilimetros,
        int fatorPorcentagemArAConsiderar,
        double vazaoEmMilimetrosPorSegundoEntrada,
        double vazaoEmMilimetrosPorSegundoSaida,
        int quantasVezesDeveFaltarAguaPorDia,
        int duracaoMinimaEmSegundosDaFaltaDeAgua,
        int duracaoMaximaEmSegundosDaFaltaDeAgua,
        int tempoSimulacaoEmSegundos,
        int atualizarDisplayHidrometroEmSegundos
) { }
