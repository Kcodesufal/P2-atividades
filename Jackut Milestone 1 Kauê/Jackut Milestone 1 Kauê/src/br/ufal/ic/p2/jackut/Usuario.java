package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuário com múltiplos atributos.
 */
public class Usuario implements Serializable {

    /**
     * Nome do usuário.
     */
    private String nome;

    /**
     * Login do usuário.
     */
    private String login;

    /**
     * Senha do usuário.
     */
    private String senha;

    /**
     * Mapa contendo atributos adicionais do usuário.
     */
    private Map<String, String> atributosMap = new HashMap<>();

    /**
     * Lista de convites pendentes de amizade.
     */
    private Set<String> convites = new LinkedHashSet<>();

    /**
     * Lista de amigos do usuário.
     */
    private Set<String> amigos = new LinkedHashSet<>();

    /**
     * Fila de mensagens recebidas pelo usuário.
     */
    private Queue<Recado> mensagens = new LinkedList<>();

    /**
     * Construtor da classe Usuario.
     *
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     */
    public Usuario(String login, String senha, String nome) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    /**
     * Retorna o nome do usuário.
     *
     * @return Nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define um novo nome para o usuário.
     *
     * @param nome Novo nome do usuário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o login do usuário.
     *
     * @return Login do usuário.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define um novo login para o usuário.
     *
     * @param login Novo login do usuário.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Compara a senha informada com a senha do usuário.
     *
     * @param senha Senha a ser comparada.
     * @return 1 se a senha for correta, 0 caso contrário.
     */
    public int comparaSenha(String senha) {
        return senha.equals(this.senha) ? 1 : 0;
    }

    /**
     * Define uma nova senha para o usuário.
     *
     * @param senha Nova senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna um atributo do usuário.
     *
     * @param atributo Nome do atributo desejado.
     * @return Valor do atributo.
     * @throws InformacaoInvalidaException Se o atributo não estiver preenchido.
     */
    public String getAtributo(String atributo) {
        if (!atributosMap.containsKey(atributo)) {
            throw new InformacaoInvalidaException("Atributo não preenchido.");
        }
        return atributosMap.get(atributo);
    }

    /**
     * Edita um atributo do perfil do usuário.
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
     * Retorna o mapa de atributos do usuário.
     *
     * @return Mapa contendo os atributos do usuário.
     */
    public Map<String, String> getAtributosMap() {
        return atributosMap;
    }

    /**
     * Adiciona um convite de amizade.
     *
     * @param user Login do usuário que enviou o convite.
     * @throws ConflitoInformacaoException Se o usuário já for amigo ou convite já existir.
     */
    public void adicionarConvite(String user) {
        if (convites.contains(user)) {
            throw new ConflitoInformacaoException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }
        if (amigos.contains(user)) {
            throw new ConflitoInformacaoException("Usuário já está adicionado como amigo.");
        }
        convites.add(user);
    }

    /**
     * Verifica se há um convite de amizade pendente.
     *
     * @param user Login do usuário a ser verificado.
     * @return true se houver convite pendente, false caso contrário.
     */
    public boolean checaConvite(String user) {
        return convites.contains(user);
    }

    /**
     * Adiciona um amigo à lista de amigos do usuário.
     *
     * @param user Login do novo amigo.
     */
    public void adicionarAmigo(String user) {
        convites.remove(user);
        amigos.add(user);
    }

    /**
     * Verifica se um usuário é amigo do usuário atual.
     *
     * @param user Login do usuário a ser verificado.
     * @return true se forem amigos, false caso contrário.
     * @throws ConflitoInformacaoException Se tentar adicionar a si mesmo como amigo.
     */

    public boolean ehAmigo(String user) {
        if (user.equals(login)) {
            throw new ConflitoInformacaoException("Usuário não pode ser amigo de si mesmo.");
        }
        return amigos.contains(user);
    }

    /**
     * Retorna a lista de amigos do usuário.
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
     * @throws InformacaoInvalidaException Se não houver recados.
     */
    public String getRecado() {
        if (mensagens.isEmpty()) {
            throw new InformacaoInvalidaException("Não há recados.");
        }
        return mensagens.poll().getMensagem();
    }

    /**
     * Adiciona um recado à fila de mensagens do usuário.
     *
     * @param recado Mensagem a ser adicionada.
     * @param remetente usuário que enviou a Mensagem.
     *
     */
    public void adicionarRecado(String recado, String remetente) {
       Recado mensagem = new Recado(recado, remetente);
        mensagens.add(mensagem);
    }

    /**
     * Retorna uma representação textual do usuário.
     *
     * @return String contendo as informações do usuário.
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
