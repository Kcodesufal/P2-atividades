package br.ufal.ic.p2.jackut.Relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.ConflitoInformacaoException;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe abstrata para os diversos tipos de relacionamento. Implementa o design pattern de template method.
 * (Seu uso diminuiu o tamanho do c�digo de Usuario pela metade, ent�o foi o mais vi�vel).
 */
public abstract class Relacionamento implements Serializable {
    // Nunca antes fiquei t�o traumatizado editando meu c�digo antigo...
    protected Set<String> relacionamentos = new LinkedHashSet<>();
    protected String usuarioLogado; // login do usu�rio atual

    /**
     * Construtora.
     * @param usuarioLogado login do usu�rio que chamou a construtora.
     */
    public Relacionamento(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    /**
     * Adiciona algu�m no relacionamento.
     * @param nome Usu�rio a ser adicionado.
     * @throws ConflitoInformacaoException Caso haja algum tipo de conflito.
     */
    public final void adicionar(String nome) throws ConflitoInformacaoException {
        validarAutoRelacionamento(nome);
        validarRelacionamentoExistente(nome);
        relacionamentos.add(nome);
    }

    // M�todos abstratos que as subclasses devem implementar

    /**
     *  Realiza a valida��o do relacionamento.
     * @param nome login do usu�rio relacionado.
     * @throws ConflitoInformacaoException caso haja algum problema.
     */
    protected abstract void validarRelacionamentoExistente(String nome) throws ConflitoInformacaoException;

    // LEMBRETE - Preciso sobrescrever se necess�rio com @overrides

    /**
     * Checagem do relacionamento. Evita que o usu�rio tente se relacionar consigo mesmo.
     * @param nome Nome do usu�rio.
     * @throws ConflitoInformacaoException se o usu�rio tentar se relacionar consigo mesmo.
     */
    protected void validarAutoRelacionamento(String nome) throws ConflitoInformacaoException {
        if (nome.equals(usuarioLogado)) {
            throw new ConflitoInformacaoException("Usu�rio n�o pode ter relacionamento consigo mesmo.");
        }
    }

    /**
     * Obt�m os relacionamentos.
     * @return lista dos relacionamentos.
     */
    public Set<String> getRelacionamentos() {
        return relacionamentos;
    }

    /**
     * Remove algu�m do relacionamento.
     * @param nome Usu�rio a ser removido.
     */
    public void remover(String nome) {
        relacionamentos.remove(nome);
    }

    /**
     * Checa se o usu�rio est� relacionado com outro usu�rio.
     * @param nome usu�rio a ser buscado na lista.
     * @return true ou false, de acordo com o resultado.
     */
    public boolean contem(String nome) {
        return relacionamentos.contains(nome);
    }
}
