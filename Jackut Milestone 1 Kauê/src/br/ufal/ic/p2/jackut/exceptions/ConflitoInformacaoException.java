package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lan�ada quando h� um conflito de informa��es.
 * Exemplo: senha igual ao nome de usu�rio ou ao login.
 *
 */
public class ConflitoInformacaoException extends RuntimeException {
    /**
     * Constr�i uma nova exce��o com a mensagem especificada.
     *
     * @param mensagem Detalhes sobre o conflito de informa��es.
     */
    public ConflitoInformacaoException(String mensagem) {
        super(mensagem);
    }
}