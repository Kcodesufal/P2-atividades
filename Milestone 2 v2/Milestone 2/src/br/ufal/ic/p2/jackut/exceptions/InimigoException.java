package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lançada caso tentem interagir com um inimigo.
 */
public class InimigoException extends RuntimeException {
    public InimigoException(String message) {
        super(message);
    }
}
