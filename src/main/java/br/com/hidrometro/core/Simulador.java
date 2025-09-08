package br.com.hidrometro.core;

import br.com.hidrometro.config.Configuracao;
import br.com.hidrometro.core.components.Hidrometro;
import br.com.hidrometro.core.events.FaltaAgua;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulador {
    private final Configuracao configuracao;
    private final Hidrometro hidrometro;
    private final FaltaAgua eventoFaltaAgua;

    private final JFrame frame;

    public Simulador(Configuracao configuracao, Hidrometro hidrometro) {
        this.configuracao = configuracao;
        this.hidrometro = hidrometro;
        this.eventoFaltaAgua = new FaltaAgua(this.configuracao.getConfiguracaoDTO().duracaoMinimaEmSegundosDaFaltaDeAgua(), this.configuracao.getConfiguracaoDTO().duracaoMaximaEmSegundosDaFaltaDeAgua());

        frame = new JFrame("Simulador de Hidrômetro " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this.hidrometro.getDisplay());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void execute() {
        try {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            int tempoDisplay = this.configuracao.getConfiguracaoDTO().atualizarDisplayHidrometroEmSegundos();
            int tempoSimulacao = this.configuracao.getConfiguracaoDTO().tempoSimulacaoEmSegundos();

            this.eventoFaltaAgua.gerarFaltaDeAgua(this.configuracao.getConfiguracaoDTO().quantasVezesDeveFaltarAguaPorDia(), this.configuracao.getConfiguracaoDTO().duracaoMinimaEmSegundosDaFaltaDeAgua(), this.configuracao.getConfiguracaoDTO().duracaoMaximaEmSegundosDaFaltaDeAgua());

            scheduler.scheduleAtFixedRate(() -> {
                System.out.println("Atualizando display a cada 1s");
                hidrometro.medir(this.eventoFaltaAgua.getFaltasDeAgua());
            }, 0, 1, TimeUnit.SECONDS);

            scheduler.scheduleAtFixedRate(() -> {
                System.out.println("Mostrando Display!");
                SwingUtilities.invokeLater(() -> this.hidrometro.getDisplay().atualizarDados());
                frame.setTitle("Simulador de Hidrômetro " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            }, 0, tempoDisplay, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                System.out.println("Encerrando simulação...");
                scheduler.shutdown();

                if (!scheduler.isTerminated()) {
                    frame.setTitle("Simulador de Hidrômetro " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - Simulação Encerrada");
                }
            }, tempoSimulacao, TimeUnit.SECONDS);
        } catch (IllegalArgumentException ignored) {}

    }
}
