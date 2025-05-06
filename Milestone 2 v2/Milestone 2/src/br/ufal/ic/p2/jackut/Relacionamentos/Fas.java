package br.ufal.ic.p2.jackut.Relacionamentos;


import br.ufal.ic.p2.jackut.exceptions.ConflitoInformacaoException;

import java.io.Serializable;

/**
 * Representa os f�s.
 */
public class Fas extends Relacionamento implements Serializable {



    /**
     * Construtora.
     * @param usuarioLogado usu�rio atual.
     */
    public Fas(String usuarioLogado) {
        super(usuarioLogado);
    }

    /**
     * Evita conflitos de informa��o.
     */
    @Override
    protected void validarRelacionamentoExistente(String nome) throws ConflitoInformacaoException {

    }

    /**
     * Evita que o usu�rio se relacione consigo mesmo.
     */
    @Override
    protected void validarAutoRelacionamento(String nome) throws ConflitoInformacaoException {

    }
}
