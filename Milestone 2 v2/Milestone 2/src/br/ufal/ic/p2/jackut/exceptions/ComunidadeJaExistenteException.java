package br.ufal.ic.p2.jackut.exceptions;

/**
 * Lan�ada caso tentem criar uma comunidade com nome j� existente.
 */
public class ComunidadeJaExistenteException extends RuntimeException {
    public ComunidadeJaExistenteException(String message) {
        super(message);
    }
}
