package br.ufal.ic.p2.jackut.exceptions;

/**
 * Lan�ada caso busquem uma comunidade que n�o existe.
 */
public class ComunidadeNaoExistenteException extends RuntimeException {
    public ComunidadeNaoExistenteException(String message) {
        super(message);
    }
}
