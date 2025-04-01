
package br.ufal.ic.p2.jackut;

import java.io.Serializable;

/**
 * Classe que representa um Recado.
 * Cada recado possui uma mensagem e um remetente.
 * Implementa a interface Serializable para possibilitar sua serialização.
 */
public class Recado implements Serializable {

    private String remetente;
    private String mensagem;

    /**
     * Construtor da classe Recado.
     *
     * @param mensagem A mensagem do recado.
     * @param remetente O remetente do recado.
     */
    public Recado(String mensagem, String remetente) {
        this.remetente = remetente;
        this.mensagem = mensagem;
    }

    /**
     * Obtém o remetente do recado.
     *
     * @return O remetente do recado.
     */
    public String getRemetente() {
        return remetente;
    }

    /**
     * Altera o remetente do recado.
     *
     * @param remetente O novo remetente do recado.
     */
    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    /**
     * Obtém a mensagem do recado.
     *
     * @return A mensagem do recado.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Altera a mensagem do recado.
     *
     * @param mensagem A nova mensagem do recado.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Retorna uma representação em String do recado.
     *
     * @return Uma String no formato "remetente: mensagem".
     */
    @Override
    public String toString() {
        return remetente + ": " + mensagem;
    }
}
