package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lan�ada caso n�o haja mensagens para ser lidas.
 */
public class ListadeMensagensVaziaException extends RuntimeException {
    public ListadeMensagensVaziaException(String message) {
        super(message);
    }
}
