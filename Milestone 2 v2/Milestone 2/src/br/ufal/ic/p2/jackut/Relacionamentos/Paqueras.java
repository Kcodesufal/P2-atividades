package br.ufal.ic.p2.jackut.Relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.ConflitoInformacaoException;

import java.io.Serializable;

/**
 * Representa as paqueras.
 */
public class Paqueras extends Relacionamento implements Serializable {
    /**
     * Construtora.
     * @param usuarioLogado usuário atual.
     */
    public Paqueras(String usuarioLogado) {
        super(usuarioLogado);
    }

    /**
     * Evita conflitos de informação.
     */
    @Override
    protected void validarRelacionamentoExistente(String nome) throws ConflitoInformacaoException {
        if (relacionamentos.contains(nome)) {
            throw new ConflitoInformacaoException("Usuário já está adicionado como paquera.");
        }
    }


    /**
     * Evita que o usuário se relacione consigo mesmo.
     */
    @Override
    protected void validarAutoRelacionamento(String nome) throws ConflitoInformacaoException {
        if (nome.equals(usuarioLogado)) {
            throw new ConflitoInformacaoException("Usuário não pode ser paquera de si mesmo.");
        }
    }
}