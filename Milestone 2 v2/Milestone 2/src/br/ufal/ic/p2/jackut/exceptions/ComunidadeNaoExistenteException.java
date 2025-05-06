package br.ufal.ic.p2.jackut.exceptions;

/**
 * Lançada caso busquem uma comunidade que não existe.
 */
public class ComunidadeNaoExistenteException extends RuntimeException {
    public ComunidadeNaoExistenteException(String message) {
        super(message);
    }
}
