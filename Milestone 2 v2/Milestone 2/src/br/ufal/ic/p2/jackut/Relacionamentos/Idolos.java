package br.ufal.ic.p2.jackut.Relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.ConflitoInformacaoException;

import java.io.Serializable;

/**
 * Representa os �dolos de um usu�rio.
 */
public class Idolos extends Relacionamento implements Serializable {

    /**
     * Construtora.
     * @param usuarioLogado usu�rio atual.
     */
    public Idolos(String usuarioLogado) {
        super(usuarioLogado);
    }

    /**
     * Evita conflitos de informa��o.
     */
    @Override
    protected void validarRelacionamentoExistente(String nome) throws ConflitoInformacaoException {
        if (relacionamentos.contains(nome)) {
            throw new ConflitoInformacaoException("Usu�rio j� est� adicionado como �dolo.");
        }
    }

    /**
     * Evita que o usu�rio se relacione consigo mesmo.
     */
    @Override
    protected void validarAutoRelacionamento(String nome) throws ConflitoInformacaoException {
        if (nome.equals(usuarioLogado)) {
            throw new ConflitoInformacaoException("Usu�rio n�o pode adicionar a si mesmo como �dolo.");
        }
    }
}
