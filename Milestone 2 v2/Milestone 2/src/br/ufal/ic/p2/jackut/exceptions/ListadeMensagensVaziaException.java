package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lançada caso não haja mensagens para ser lidas.
 */
public class ListadeMensagensVaziaException extends RuntimeException {
    public ListadeMensagensVaziaException(String message) {
        super(message);
    }
}
