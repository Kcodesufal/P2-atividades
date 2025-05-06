package br.ufal.ic.p2.jackut.Relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.ConflitoInformacaoException;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe abstrata para os diversos tipos de relacionamento. Implementa o design pattern de template method.
 * (Seu uso diminuiu o tamanho do código de Usuario pela metade, então foi o mais viável).
 */
public abstract class Relacionamento implements Serializable {
    // Nunca antes fiquei tão traumatizado editando meu código antigo...
    protected Set<String> relacionamentos = new LinkedHashSet<>();
    protected String usuarioLogado; // login do usuário atual

    /**
     * Construtora.
     * @param usuarioLogado login do usuário que chamou a construtora.
     */
    public Relacionamento(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    /**
     * Adiciona alguém no relacionamento.
     * @param nome Usuário a ser adicionado.
     * @throws ConflitoInformacaoException Caso haja algum tipo de conflito.
     */
    public final void adicionar(String nome) throws ConflitoInformacaoException {
        validarAutoRelacionamento(nome);
        validarRelacionamentoExistente(nome);
        relacionamentos.add(nome);
    }

    // Métodos abstratos que as subclasses devem implementar

    /**
     *  Realiza a validação do relacionamento.
     * @param nome login do usuário relacionado.
     * @throws ConflitoInformacaoException caso haja algum problema.
     */
    protected abstract void validarRelacionamentoExistente(String nome) throws ConflitoInformacaoException;

    // LEMBRETE - Preciso sobrescrever se necessário com @overrides

    /**
     * Checagem do relacionamento. Evita que o usuário tente se relacionar consigo mesmo.
     * @param nome Nome do usuário.
     * @throws ConflitoInformacaoException se o usuário tentar se relacionar consigo mesmo.
     */
    protected void validarAutoRelacionamento(String nome) throws ConflitoInformacaoException {
        if (nome.equals(usuarioLogado)) {
            throw new ConflitoInformacaoException("Usuário não pode ter relacionamento consigo mesmo.");
        }
    }

    /**
     * Obtém os relacionamentos.
     * @return lista dos relacionamentos.
     */
    public Set<String> getRelacionamentos() {
        return relacionamentos;
    }

    /**
     * Remove alguém do relacionamento.
     * @param nome Usuário a ser removido.
     */
    public void remover(String nome) {
        relacionamentos.remove(nome);
    }

    /**
     * Checa se o usuário está relacionado com outro usuário.
     * @param nome usuário a ser buscado na lista.
     * @return true ou false, de acordo com o resultado.
     */
    public boolean contem(String nome) {
        return relacionamentos.contains(nome);
    }
}
