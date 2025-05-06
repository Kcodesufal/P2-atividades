package br.ufal.ic.p2.jackut.exceptions;

/**
 * Lançada caso tentem criar uma comunidade com nome já existente.
 */
public class ComunidadeJaExistenteException extends RuntimeException {
    public ComunidadeJaExistenteException(String message) {
        super(message);
    }
}
