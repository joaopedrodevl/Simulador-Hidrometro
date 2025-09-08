package br.com.hidrometro.core.abstracts;

import br.com.hidrometro.utils.TipoFluido;

public abstract class Cano {
    private TipoFluido tipoFluido;
    private final double diametroMM;
    private final double vazao;

    public Cano(TipoFluido tipoFluido, double diametroMM, double vazao) throws Exception {
        this.tipoFluido = tipoFluido;
        this.diametroMM = validaInput(diametroMM, "Diâmetro MM");
        this.vazao = validaInput(vazao, "Vazão");
    }

    private double validaInput(double input, String fieldName) throws Exception {
        if  (input < 0) {
            throw new Exception("Valor do campo: " + fieldName + " não pode ser menor que 0.");
        }

        return input;
    }

    public double getVazaoEmMetros() {
        return vazao / 1000.0;
    }

    public double getRaioEmMetros() {
        return this.getDiametroCanoEmMetros() / 2.0;
    }

    public double getDiametroCanoEmMetros() {
        return this.diametroMM / 1000.0;
    }

    public TipoFluido getTipoFluido() {
        return tipoFluido;
    }

    public void setTipoFluido(TipoFluido tipoFluido) {
        this.tipoFluido = tipoFluido;
    }
}
