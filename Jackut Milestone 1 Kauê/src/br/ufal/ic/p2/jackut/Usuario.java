package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usu�rio com m�ltiplos atributos.
 */
public class Usuario implements Serializable {

    /**
     * Nome do usu�rio.
     */
    private String nome;

    /**
     * Login do usu�rio.
     */
    private String login;

    /**
     * Senha do usu�rio.
     */
    private String senha;

    /**
     * Mapa contendo atributos adicionais do usu�rio.
     */
    private Map<String, String> atributosMap = new HashMap<>();

    /**
     * Lista de convites pendentes de amizade.
     */
    private Set<String> convites = new LinkedHashSet<>();

    /**
     * Lista de amigos do usu�rio.
     */
    private Set<String> amigos = new LinkedHashSet<>();

    /**
     * Fila de mensagens recebidas pelo usu�rio.
     */
    private Queue<Recado> mensagens = new LinkedList<>();

    /**
     * Construtor da classe Usuario.
     *
     * @param login Login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome Nome do usu�rio.
     */
    public Usuario(String login, String senha, String nome) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    /**
     * Retorna o nome do usu�rio.
     *
     * @return Nome do usu�rio.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define um novo nome para o usu�rio.
     *
     * @param nome Novo nome do usu�rio.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o login do usu�rio.
     *
     * @return Login do usu�rio.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define um novo login para o usu�rio.
     *
     * @param login Novo login do usu�rio.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Compara a senha informada com a senha do usu�rio.
     *
     * @param senha Senha a ser comparada.
     * @return 1 se a senha for correta, 0 caso contr�rio.
     */
    public int comparaSenha(String senha) {
        return senha.equals(this.senha) ? 1 : 0;
    }

    /**
     * Define uma nova senha para o usu�rio.
     *
     * @param senha Nova senha do usu�rio.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna um atributo do usu�rio.
     *
     * @param atributo Nome do atributo desejado.
     * @return Valor do atributo.
     * @throws InformacaoInvalidaException Se o atributo n�o estiver preenchido.
     */
    public String getAtributo(String atributo) {
        if (!atributosMap.containsKey(atributo)) {
            throw new InformacaoInvalidaException("Atributo n�o preenchido.");
        }
        return atributosMap.get(atributo);
    }

    /**
     * Edita um atributo do perfil do usu�rio.
     *
     * @param atributo Nome do atributo a ser editado.
     * @param valor Novo valor do atributo.
     */
    public void editarPerfil(String atributo, String valor) {
        if (atributo.equals(nome)) {
            setNome(valor);
        } else if (atributo.equals(senha)) {
            setSenha(valor);
        } else {
            atributosMap.put(atributo, valor);
        }
    }

    /**
     * Retorna o mapa de atributos do usu�rio.
     *
     * @return Mapa contendo os atributos do usu�rio.
     */
    public Map<String, String> getAtributosMap() {
        return atributosMap;
    }

    /**
     * Adiciona um convite de amizade.
     *
     * @param user Login do usu�rio que enviou o convite.
     * @throws ConflitoInformacaoException Se o usu�rio j� for amigo ou convite j� existir.
     */
    public void adicionarConvite(String user) {
        if (convites.contains(user)) {
            throw new ConflitoInformacaoException("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
        }
        if (amigos.contains(user)) {
            throw new ConflitoInformacaoException("Usu�rio j� est� adicionado como amigo.");
        }
        convites.add(user);
    }

    /**
     * Verifica se h� um convite de amizade pendente.
     *
     * @param user Login do usu�rio a ser verificado.
     * @return true se houver convite pendente, false caso contr�rio.
     */
    public boolean checaConvite(String user) {
        return convites.contains(user);
    }

    /**
     * Adiciona um amigo � lista de amigos do usu�rio.
     *
     * @param user Login do novo amigo.
     */
    public void adicionarAmigo(String user) {
        convites.remove(user);
        amigos.add(user);
    }

    /**
     * Verifica se um usu�rio � amigo do usu�rio atual.
     *
     * @param user Login do usu�rio a ser verificado.
     * @return true se forem amigos, false caso contr�rio.
     * @throws ConflitoInformacaoException Se tentar adicionar a si mesmo como amigo.
     */

    public boolean ehAmigo(String user) {
        if (user.equals(login)) {
            throw new ConflitoInformacaoException("Usu�rio n�o pode ser amigo de si mesmo.");
        }
        return amigos.contains(user);
    }

    /**
     * Retorna a lista de amigos do usu�rio.
     *
     * @return String formatada com a lista de amigos.
     */
    public String getAmigos() {
        return "{" + String.join(",", amigos) + "}";
    }

    /**
     * Retorna um recado da fila de mensagens.
     *
     * @return O recado mais antigo da fila.
     * @throws InformacaoInvalidaException Se n�o houver recados.
     */
    public String getRecado() {
        if (mensagens.isEmpty()) {
            throw new InformacaoInvalidaException("N�o h� recados.");
        }
        return mensagens.poll().getMensagem();
    }

    /**
     * Adiciona um recado � fila de mensagens do usu�rio.
     *
     * @param recado Mensagem a ser adicionada.
     * @param remetente usu�rio que enviou a Mensagem.
     *
     */
    public void adicionarRecado(String recado, String remetente) {
       Recado mensagem = new Recado(recado, remetente);
        mensagens.add(mensagem);
    }

    /**
     * Retorna uma representa��o textual do usu�rio.
     *
     * @return String contendo as informa��es do usu�rio.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", atributos=" + atributosMap +
                ", amigos=" + amigos +
                ", convites=" + convites +
                '}';
    }
}
