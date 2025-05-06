package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lançada quando há um conflito de informações.
 * Exemplo: senha igual ao nome de usuário ou ao login.
 *
 */
public class ConflitoInformacaoException extends RuntimeException {
    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param mensagem Detalhes sobre o conflito de informações.
     */
    public ConflitoInformacaoException(String mensagem) {
        super(mensagem);
    }
}