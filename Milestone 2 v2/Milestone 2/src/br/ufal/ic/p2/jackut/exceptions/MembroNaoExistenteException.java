package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lan�ada caso tente-se interagir com um membro n�o existente de comunidade.
 */
public class MembroNaoExistenteException extends RuntimeException {
    public MembroNaoExistenteException(String message) {
        super(message);
    }
}
