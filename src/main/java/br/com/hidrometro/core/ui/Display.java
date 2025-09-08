package br.com.hidrometro.core.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Display extends JPanel {
    private static BufferedImage imagemHidrometro;
    private String digitos = "000000";
    private boolean emFaltaAgua;

    private int digitoMilharM3;
    private int digitoCentenaM3;
    private int digitoDezenaM3;
    private int digitoUnidadeM3;
    private int digitoCentenaLitros; // 0,1 m³
    private int digitoDezenaLitros; // 0,01 m³

    private int ponteiroLitros; // 0,001 m³
    private int ponteiroDecimosLitro; // 0,0001 m³

    public void atualizarDisplay(double volumeTotalM3) {
        long volumeEmDecimosLitro = (long) Math.floor(volumeTotalM3 * 10);

        ponteiroDecimosLitro = (int) (volumeEmDecimosLitro % 10);
        volumeEmDecimosLitro /= 10;

        ponteiroLitros = (int) (volumeEmDecimosLitro % 10);
        volumeEmDecimosLitro /= 10;

        digitoDezenaLitros = (int) (volumeEmDecimosLitro % 10);
        volumeEmDecimosLitro /= 10;

        digitoCentenaLitros = (int) (volumeEmDecimosLitro % 10);
        volumeEmDecimosLitro /= 10;

        digitoUnidadeM3 = (int) (volumeEmDecimosLitro % 10);
        volumeEmDecimosLitro /= 10;

        digitoDezenaM3 = (int) (volumeEmDecimosLitro % 10);
        volumeEmDecimosLitro /= 10;

        digitoCentenaM3 = (int) (volumeEmDecimosLitro % 10);
        volumeEmDecimosLitro /= 10;

        digitoMilharM3 = (int) (volumeEmDecimosLitro % 10);

        System.out.println(this.getDisplayFormatado());
    }

    public String getDisplayFormatado() {
        return String.format(
                "%d%d%d%d,%d%d %d.%d",
                digitoMilharM3,
                digitoCentenaM3,
                digitoDezenaM3,
                digitoUnidadeM3,
                digitoCentenaLitros,
                digitoDezenaLitros,
                ponteiroLitros,
                ponteiroDecimosLitro);
    }

    public Display() {
        try {
            String IMAGEM_HIDROMETRO = "/images/hidrometro.png";
            imagemHidrometro = ImageIO.read(Objects.requireNonNull(getClass().getResource(IMAGEM_HIDROMETRO)));
        } catch (IOException e) {
            e.printStackTrace();
            imagemHidrometro = null;
        }
        setPreferredSize(new Dimension(862, 660));
    }

    public void atualizarDados() {
        this.digitos = digitoMilharM3 + Integer.toString(digitoCentenaM3)
                + digitoDezenaM3 + digitoUnidadeM3
                + digitoCentenaLitros + digitoDezenaLitros;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagemHidrometro != null) {
            g.drawImage(imagemHidrometro, 0, 0, this);
        }

        // Desenha dígitos
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.setColor(Color.BLACK);

        int xBase = 346;
        int yDigitos = 285;
        int espaco = 34;

        for (int i = 0; i < digitos.length(); i++) {
            g.drawString(String.valueOf(digitos.charAt(i)), xBase + i * espaco, yDigitos);
        }

        if (this.emFaltaAgua) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Faltando água.", 10, 20);
        }

        Graphics2D g2d = (Graphics2D) g.create();

        // Ponteiro água Decimos de Litro (do Meio)
        posicaoPonteiros(g2d, 434, 475, ponteiroDecimosLitro);

        // Ponteiro água Litro (da Direita)
        posicaoPonteiros(g2d, 555, 415, ponteiroLitros);
    }

    public void alertaFaltaDeAgua(boolean emFaltaAgua) {
        this.emFaltaAgua = emFaltaAgua;
    }

    public void posicaoPonteiros(Graphics2D g2d, int centerX, int centerY, int digito) {
        double angulo = Math.toRadians(digito * 36 - 90);
        int TAMANHO_PONTEIRO = 30;
        int pontX = centerX + (int)(TAMANHO_PONTEIRO * Math.cos(angulo));
        int pontY = centerY + (int)(TAMANHO_PONTEIRO * Math.sin(angulo));

        g2d.setColor(new Color(140, 43, 45));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(centerX, centerY, pontX, pontY);
    }
}
