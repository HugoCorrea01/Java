package entidades.ingresso;

import entidades.evento.Evento;

public class IngShow extends Ingresso {
    private String localizacao;

    public IngShow(Evento evento, TipoIngresso tipo, String localizacao) {
        super(evento, tipo);
        this.localizacao = localizacao;
    }
}