package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exception lançada quando um usuário não está cadastrado no sistema.
 * Utilizada para validações onde a existência do usuário é esperada.
 *
 */
public class UsuarioNaoCadastradoException extends RuntimeException {
    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param mensagem Detalhes sobre o usuário não cadastrado.
     */
    public UsuarioNaoCadastradoException(String mensagem) {
        super(mensagem);
    }
}
