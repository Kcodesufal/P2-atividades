package br.ufal.ic.p2.jackut.Relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.ConflitoInformacaoException;

import java.io.Serializable;

/**
 * Representa os inimigos de um usu�rio.
 */
public class Inimigos extends Relacionamento implements Serializable {
    /**
     * Construtora.
     * @param usuarioLogado usu�rio atual.
     */
    public Inimigos(String usuarioLogado) {
        super(usuarioLogado);
    }

    /**
     * Evita conflitos de informa��o.
     */
    @Override
    protected void validarRelacionamentoExistente(String nome) throws ConflitoInformacaoException {
        if (relacionamentos.contains(nome)) {
            throw new ConflitoInformacaoException("Usu�rio j� est� adicionado como inimigo.");
        }
    }

    /**
     * Evita que o usu�rio se relacione consigo mesmo.
     */
    @Override
    protected void validarAutoRelacionamento(String nome) throws ConflitoInformacaoException {
        if (nome.equals(usuarioLogado)) {
            throw new ConflitoInformacaoException("Usu�rio n�o pode ser inimigo de si mesmo.");
        }
    }
}