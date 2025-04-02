package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lançada quando uma informação fornecida é inválida.
 * Geralmente utilizada para validações de entradas como login, senha ou nome.
 *

 */
public class InformacaoInvalidaException extends RuntimeException {
    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param mensagem Detalhes sobre a informação inválida.
     */
    public InformacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
