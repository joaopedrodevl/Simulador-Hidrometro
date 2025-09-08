package br.com.hidrometro.config;

import br.com.hidrometro.dto.ConfiguracaoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Configuracao {

    private final ObjectMapper objectMapper;
    private ConfiguracaoDTO configuracaoDTO;

    public Configuracao(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        this.carregaArquivo();
    }

    private void carregaArquivo() throws IOException {
        String ARQUIVO = "config.json";

        this.configuracaoDTO = this.objectMapper.readValue(new File(ARQUIVO), ConfiguracaoDTO.class);
        System.out.println("Arquivo: " + ARQUIVO + " carregado com sucesso!");
    }

    public ConfiguracaoDTO getConfiguracaoDTO() {
        return configuracaoDTO;
    }
}
