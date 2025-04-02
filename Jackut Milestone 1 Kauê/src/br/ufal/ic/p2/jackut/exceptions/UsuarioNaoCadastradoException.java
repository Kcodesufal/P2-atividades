package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lan�ada quando um usu�rio n�o est� cadastrado no sistema.
 * Utilizada para valida��es onde a exist�ncia do usu�rio � esperada.
 *
 */
public class UsuarioNaoCadastradoException extends RuntimeException {
    /**
     * Constr�i uma nova exce��o com a mensagem especificada.
     *
     * @param mensagem Detalhes sobre o usu�rio n�o cadastrado.
     */
    public UsuarioNaoCadastradoException(String mensagem) {
        super(mensagem);
    }
}
