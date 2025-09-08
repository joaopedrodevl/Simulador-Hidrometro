package br.com.hidrometro.core.components;

import br.com.hidrometro.core.abstracts.Cano;
import br.com.hidrometro.utils.TipoFluido;

public class CanoEntrada extends Cano {
    public CanoEntrada(TipoFluido tipoFluido, double diametroMM, double vazao) throws Exception {
        super(tipoFluido, diametroMM, vazao);
    }
}
