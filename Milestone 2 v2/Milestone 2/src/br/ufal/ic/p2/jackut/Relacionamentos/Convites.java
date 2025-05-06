package br.ufal.ic.p2.jackut.Relacionamentos;
import br.ufal.ic.p2.jackut.exceptions.ConflitoInformacaoException;

import java.io.Serializable;

/**
 * Representa os convites.
 */
public class Convites extends Relacionamento implements Serializable {

    /**
     * Construtora.
     * @param usuarioLogado usuário atual.
     */
    public Convites(String usuarioLogado) {
        super(usuarioLogado);
    }

    /**
     * Evita conflitos de informação.
     */
    @Override
    protected void validarRelacionamentoExistente(String nome) throws ConflitoInformacaoException {
        if (relacionamentos.contains(nome)) {
            throw new ConflitoInformacaoException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }
    }

    /**
     * Evita que o usuário se relacione consigo mesmo.
     */
    @Override
    protected void validarAutoRelacionamento(String nome) throws ConflitoInformacaoException {
        if (nome.equals(usuarioLogado)) {
            throw new ConflitoInformacaoException("Usuário não pode adicionar a si mesmo como inimigo.");
        }
    }
}
