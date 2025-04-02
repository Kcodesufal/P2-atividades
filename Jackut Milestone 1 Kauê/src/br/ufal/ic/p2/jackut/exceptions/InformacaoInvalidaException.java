package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lan�ada quando uma informa��o fornecida � inv�lida.
 * Geralmente utilizada para valida��es de entradas como login, senha ou nome.
 *

 */
public class InformacaoInvalidaException extends RuntimeException {
    /**
     * Constr�i uma nova exce��o com a mensagem especificada.
     *
     * @param mensagem Detalhes sobre a informa��o inv�lida.
     */
    public InformacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
