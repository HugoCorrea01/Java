package entidades.ingresso;

import entidades.evento.Evento;

public class IngJogo extends Ingresso {
    private int percentualDescontoTorcedor;

    public IngJogo(Evento evento, TipoIngresso tipo, int percentual) {
        super(evento, tipo);
        this.percentualDescontoTorcedor = percentual;
    }

    @Override
    public double getPreco() {
        return super.getPreco() * this.percentualDescontoTorcedor / 100;
    }
}
