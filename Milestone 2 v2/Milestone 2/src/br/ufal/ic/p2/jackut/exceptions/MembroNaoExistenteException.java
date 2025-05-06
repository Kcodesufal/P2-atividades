package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lançada caso tente-se interagir com um membro não existente de comunidade.
 */
public class MembroNaoExistenteException extends RuntimeException {
    public MembroNaoExistenteException(String message) {
        super(message);
    }
}
