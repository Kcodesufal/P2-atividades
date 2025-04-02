package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lançada quando se tenta cadastrar um usuário já existente.
 * Geralmente utilizada na criação de novos usuários no sistema.
 *
 */
public class UsuarioJaExistenteException extends RuntimeException {
    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param mensagem Detalhes sobre o usuário já cadastrado.
     */
    public UsuarioJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
