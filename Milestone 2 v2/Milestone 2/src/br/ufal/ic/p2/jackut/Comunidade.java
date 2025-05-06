package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.MembroNaoExistenteException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaExistenteException;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.io.Serializable;

/**
 * Classe de comunidade. Irá indicar os donos, membros, seu nome e também possuirá uma descrição.
 */
public class Comunidade implements Serializable {
    private String dono;
    private String nome;
    private String descricao;
    private Set<String> membros = new LinkedHashSet<>();

    /**
     * Construtora de comunidade.
     * @param dono Dono da comunidade.
     * @param nome Nome da comunidade.
     * @param descricao Descrição da comunidade.
     */
    public Comunidade(String dono, String nome, String descricao) {
        this.dono = dono;
        this.nome = nome;
        this.descricao = descricao;
        membros.add(dono);

    }

    /**
     * Retorna a descrição da comunidade.
     * @return Descrição.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o nome da comunidade.
     * @return nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o dono da comunidade.
     * @return dono.
     */
    public String getDono() {
        return dono;
    }

    /**
     * Retorna os membros da comunidade, no formato de String.
     * @return String: Lista de membros.
     */
    public String getMembros() {
        return "{" + String.join(",", membros) + "}";
    }

    /**
     * Retorna os membros em seu formato original (Set).
     * @return Set da lista de membros.
     */
    public Set<String> getMembrosSet() {
        return membros;
    }

    /**
     * Adiciona um membro.
     * @param membro nome do membro.
     */
    public void adicionarMembro(String membro) {
        if (membros.contains(membro)) {
            throw new UsuarioJaExistenteException("Usuario já faz parte dessa comunidade.");
        }
        membros.add(membro);
    }

    /**
     * Remove um membro.
     * @param membro nome do membro.
     */
    public void removerMembro(String membro) {
        if (!membros.contains(membro)) {
            throw new MembroNaoExistenteException("Membro não faz parte da comunidade.");
        }
        membros.remove(membro);
    }

}
