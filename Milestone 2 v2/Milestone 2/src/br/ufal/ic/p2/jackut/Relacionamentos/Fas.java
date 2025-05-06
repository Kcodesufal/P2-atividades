package br.ufal.ic.p2.jackut.Relacionamentos;


import br.ufal.ic.p2.jackut.exceptions.ConflitoInformacaoException;

import java.io.Serializable;

/**
 * Representa os fãs.
 */
public class Fas extends Relacionamento implements Serializable {



    /**
     * Construtora.
     * @param usuarioLogado usuário atual.
     */
    public Fas(String usuarioLogado) {
        super(usuarioLogado);
    }

    /**
     * Evita conflitos de informação.
     */
    @Override
    protected void validarRelacionamentoExistente(String nome) throws ConflitoInformacaoException {

    }

    /**
     * Evita que o usuário se relacione consigo mesmo.
     */
    @Override
    protected void validarAutoRelacionamento(String nome) throws ConflitoInformacaoException {

    }
}
