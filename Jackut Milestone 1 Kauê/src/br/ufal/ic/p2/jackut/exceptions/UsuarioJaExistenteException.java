package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lan�ada quando se tenta cadastrar um usu�rio j� existente.
 * Geralmente utilizada na cria��o de novos usu�rios no sistema.
 *
 */
public class UsuarioJaExistenteException extends RuntimeException {
    /**
     * Constr�i uma nova exce��o com a mensagem especificada.
     *
     * @param mensagem Detalhes sobre o usu�rio j� cadastrado.
     */
    public UsuarioJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
