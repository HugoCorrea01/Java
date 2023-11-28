package entidades.ingresso;

import entidades.evento.Evento;

public class IngExposicao extends Ingresso {
    private boolean descontoSocial;

    public IngExposicao(Evento evento, TipoIngresso tipo, boolean descontoSocial) {
        super(evento, tipo);
        this.descontoSocial = descontoSocial;
    }

    @Override
    public double getPreco() {
        if (this.descontoSocial) {
            return 0;
        }

        return super.getPreco();
    }
}
