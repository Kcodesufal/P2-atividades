package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lan�ada caso tentem interagir com um inimigo.
 */
public class InimigoException extends RuntimeException {
    public InimigoException(String message) {
        super(message);
    }
}
