package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.Relacionamentos.*;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usu�rio do Jackut, com todos seus atributos e relacionamentos.
 * <p>
 * Armazena informa��es b�sicas do usu�rio (nome, login, senha), atributos adicionais,
 * mensagens, recados e todos os tipos de relacionamentos com outros usu�rios (amigos, �dolos, f�s...).
 * </p>
 * <p>
 * Implementa Serializable para permitir a persist�ncia dos objetos.
 * Precisou de uma imensa dose de caf� para ser feita.
 * </p>
 */
public class Usuario implements Serializable {

    private String nome;
    private String login;
    private String senha;
    private final Map<String, String> atributosMap = new HashMap<>();
    private final Queue<Comunicado> comunicados = new LinkedList<>();
    private final Queue<Comunicado> mensagens = new LinkedList<>();
    private final Set<String> comunidades = new LinkedHashSet<>();
    /**
     * "destinat�rios" � um caso um pouco especial. N�o � necess�rio para passar nos casos testes, mas foi implementado pois pode ser �til no futuro.
     */
    private final Set<String> destinatarios = new LinkedHashSet<>();

    // Relacionamentos
    private final Convites convites;
    private final Amigos amigos;
    private final Idolos idolos;
    private final Fas fas;
    private final Paqueras paqueras;
    private final Inimigos inimigos;

    /**
     * Constr�i um novo usu�rio com informa��es b�sicas.
     *
     * @param login Login �nico do usu�rio
     * @param senha Senha do usu�rio
     * @param nome  Nome completo do usu�rio
     */
    public Usuario(String login, String senha, String nome) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;

        this.convites = new Convites(login);
        this.amigos = new Amigos(login);
        this.idolos = new Idolos(login);
        this.fas = new Fas(login);
        this.paqueras = new Paqueras(login);
        this.inimigos = new Inimigos(login);
    }

    /**
     * Obt�m o nome do usu�rio.
     *
     * @return Nome do usu�rio
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usu�rio.
     *
     * @param nome Novo nome do usu�rio
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obt�m o login do usu�rio.
     *
     * @return Login do usu�rio
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o login do usu�rio.
     *
     * @param login Novo login do usu�rio
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Compara a senha fornecida com a senha armazenada.
     *
     * @param senha Senha a ser verificada
     * @return 1 se as senhas coincidirem, 0 caso contr�rio
     */
    public int comparaSenha(String senha) {
        return senha.equals(this.senha) ? 1 : 0;
    }

    /**
     * Define uma nova senha para o usu�rio.
     *
     * @param senha Nova senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Obt�m o valor de um atributo espec�fico do usu�rio.
     *
     * @param atributo Nome do atributo
     * @return Valor do atributo
     * @throws InformacaoInvalidaException Se o atributo n�o existir
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
     * @param atributo Nome do atributo (pode ser "nome" ou "senha" para editar esses campos especiais)
     * @param valor    Novo valor do atributo
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
     * Obt�m o mapa completo de atributos adicionais do usu�rio.
     *
     * @return Mapa de atributos adicionais
     */
    public Map<String, String> getAtributosMap() {
        return atributosMap;
    }

    /**
     * Adiciona um convite de amizade de outro usu�rio.
     *
     * @param user Login do usu�rio que enviou o convite
     * @throws ConflitoInformacaoException Se o usu�rio j� for amigo
     */
    public void adicionarConvite(String user) {
        if (amigos.contem(user)) {
            throw new ConflitoInformacaoException("Usu�rio j� est� adicionado como amigo.");
        }
        convites.adicionar(user);
    }

    /**
     * Verifica se existe um convite pendente de um usu�rio espec�fico.
     *
     * @param user Login do usu�rio a verificar
     * @return true se existir convite, false caso contr�rio
     */
    public boolean checaConvite(String user) {
        return convites.contem(user);
    }

    /**
     * Adiciona um novo amigo, removendo o convite correspondente se existir.
     *
     * @param user Login do novo amigo
     */
    public void adicionarAmigo(String user) {
        convites.remover(user);
        amigos.adicionar(user);
    }

    /**
     * Verifica se um usu�rio � amigo.
     *
     * @param user Login do usu�rio a verificar
     * @return true se for amigo, false caso contr�rio
     * @throws ConflitoInformacaoException Se tentar verificar amizade consigo mesmo
     */
    public boolean ehAmigo(String user) {
        if (user.equals(login)) {
            throw new ConflitoInformacaoException("Usu�rio n�o pode ser amigo de si mesmo.");
        }
        return amigos.contem(user);
    }

    /**
     * L� o pr�ximo recado n�o lido.
     *
     * @return Conte�do do recado
     * @throws InformacaoInvalidaException Se n�o houver recados
     */
    public String getRecado() {
        if (comunicados.isEmpty()) {
            throw new InformacaoInvalidaException("N�o h� recados.");
        }
        return comunicados.poll().getMensagem();
    }

    /**
     * Adiciona um novo recado � fila de recados.
     *
     * @param recado    Conte�do do recado
     * @param remetente Login do remetente
     */
    public void adicionarRecado(String recado, String remetente) {
        Comunicado r = new Comunicado(recado, remetente);
        comunicados.add(r);
    }

    /**
     * Adiciona o usu�rio a uma comunidade.
     *
     * @param comunidade Nome da comunidade
     */
    public void adicionarComunidade(String comunidade) {
        comunidades.add(comunidade);
    }

    /**
     * Adiciona uma nova mensagem � fila de mensagens.
     *
     * @param mensagem  Conte�do da mensagem
     * @param remetente Login do remetente
     */
    public void adicionarMensagem(String mensagem, String remetente) {
        Comunicado m = new Comunicado(mensagem, remetente);
        mensagens.add(m);
    }

    /**
     * L� a pr�xima mensagem n�o lida.
     *
     * @return Conte�do da mensagem
     * @throws ListadeMensagensVaziaException Se n�o houver mensagens
     */
    public String lerMensagem() {
        if (mensagens.isEmpty()) {
            throw new ListadeMensagensVaziaException("N�o h� mensagens.");
        }
        return mensagens.poll().getMensagem();
    }

    /**
     * Adiciona um novo �dolo � lista do usu�rio.
     *
     * @param nome Login do �dolo
     */
    public void adicionarIdolo(String nome) {
        idolos.adicionar(nome);
    }

    /**
     * Verifica se um usu�rio � f� deste usu�rio.
     *
     * @param nome Login do poss�vel f�
     * @return true se for f�, false caso contr�rio
     */
    public boolean ehFa(String nome) {
        return fas.contem(nome);
    }

    /**
     * Adiciona um novo f� � lista do usu�rio.
     *
     * @param nome Login do f�
     */
    public void adicionarFa(String nome) {
        fas.adicionar(nome);
    }

    /**
     * Verifica se um usu�rio � paquera deste usu�rio.
     *
     * @param nome Login do poss�vel paquera
     * @return true se for paquera, false caso contr�rio
     */
    public boolean ehPaquera(String nome) {
        return paqueras.contem(nome);
    }

    /**
     * Adiciona um novo paquera � lista do usu�rio.
     *
     * @param paque Login do paquera
     */
    public void adicionarPaquera(String paque) {
        paqueras.adicionar(paque);
    }

    /**
     * Adiciona um novo inimigo � lista do usu�rio.
     *
     * @param inimigo Login do inimigo
     */
    public void adicionarInimigo(String inimigo) {
        inimigos.adicionar(inimigo);
    }

    /**
     * Verifica se um usu�rio � inimigo deste usu�rio.
     *
     * @param inimigo Login do poss�vel inimigo
     * @return true se for inimigo, false caso contr�rio
     */
    public boolean ehInimigo(String inimigo) {
        return inimigos.contem(inimigo);
    }

    /**
     * Remove todas as refer�ncias a um usu�rio que est� sendo deletado.
     *
     * @param deletado Login do usu�rio sendo deletado
     */
    public void deletarUsuario(String deletado) {
        convites.remover(deletado);
        amigos.remover(deletado);
        idolos.remover(deletado);
        fas.remover(deletado);
        paqueras.remover(deletado);
        inimigos.remover(deletado);

        mensagens.removeIf(m -> m.getRemetente().equals(deletado));
        comunicados.removeIf(r -> r.getRemetente().equals(deletado));
    }

    /**
     * Remove o usu�rio de uma comunidade que est� sendo deletada.
     *
     * @param deletado Nome da comunidade sendo deletada
     */
    public void deletarComunidade(String deletado) {
        comunidades.remove(deletado);
    }

    /**
     * Adiciona um destinat�rio � lista de usu�rios para quem este usu�rio enviou recados.
     *
     * @param destinatario Login do destinat�rio
     */
    public void adicionarDestinatario(String destinatario) {
        destinatarios.add(destinatario);
    }

    // M�todos de acesso aos relacionamentos

    /**
     * Obt�m a lista de paqueras do usu�rio.
     *
     * @return Conjunto de logins dos paqueras
     */
    public Set<String> getPaqueras() {
        return paqueras.getRelacionamentos();
    }

    /**
     * Obt�m a lista de destinat�rios de recados.
     *
     * @return Conjunto de logins dos destinat�rios
     */
    public Set<String> getDestinatarios() {
        return destinatarios;
    }

    /**
     * Obt�m a lista de amigos do usu�rio.
     *
     * @return Conjunto de logins dos amigos
     */
    public Set<String> getAmigos() {
        return amigos.getRelacionamentos();
    }

    /**
     * Obt�m a lista de f�s do usu�rio.
     *
     * @return Conjunto de logins dos f�s
     */
    public Set<String> getFas() {
        return fas.getRelacionamentos();
    }

    /**
     * Obt�m a lista de comunidades do usu�rio.
     *
     * @return Conjunto de nomes das comunidades
     */
    public Set<String> getComunidades() {
        return comunidades;
    }

    /**
     * Obt�m a lista de �dolos do usu�rio.
     *
     * @return Conjunto de logins dos �dolos
     */
    public Set<String> getIdolos() {
        return idolos.getRelacionamentos();
    }

    /**
     * Obt�m a lista de convites pendentes.
     *
     * @return Conjunto de logins com convites pendentes
     */
    public Set<String> getConvites() {
        return convites.getRelacionamentos();
    }

    /**
     * Obt�m a lista de inimigos do usu�rio.
     *
     * @return Conjunto de logins dos inimigos
     */
    public Set<String> getInimigos() {
        return inimigos.getRelacionamentos();
    }

    /**
     * Obt�m a fila de recados recebidos.
     *
     * @return Fila de recados
     */
    public Queue<Comunicado> getRecados() {
        return comunicados;
    }

    /**
     * Obt�m a fila de mensagens recebidas.
     *
     * @return Fila de mensagens
     */
    public Queue<Comunicado> getMensagens() {
        return mensagens;
    }

    /**
     * Retorna uma representa��o textual do usu�rio.
     *
     * @return String com informa��es b�sicas do usu�rio
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", atributosMap=" + atributosMap +
                ", convites=" + convites.getRelacionamentos() +
                ", amigos=" + amigos.getRelacionamentos() +
                ", idolos=" + idolos.getRelacionamentos() +
                ", comunidades=" + comunidades +
                '}';
    }
}