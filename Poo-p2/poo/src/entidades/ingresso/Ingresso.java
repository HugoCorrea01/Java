package entidades.ingresso;

import entidades.evento.Evento;

public abstract class Ingresso {
    private Evento evento;
    private TipoIngresso tipo;

    public Ingresso(Evento evento, TipoIngresso tipo) {
        this.evento = evento;
        if (tipo.equals("meia")) {
            this.tipo = TipoIngresso.MEIA;
        } else {
            this.tipo = TipoIngresso.INTEIRA;
        }
    }

    public double getPreco() {
        if (tipo.equals(TipoIngresso.MEIA)) {
            return this.evento.getPrecoCheio() * 0.5;
        }

        return this.evento.getPrecoCheio();
    }

    @Override
    public String toString() {
        return this.evento.getNome() + " - " + this.tipo + " - R$" + this.getPreco();
    }
}
