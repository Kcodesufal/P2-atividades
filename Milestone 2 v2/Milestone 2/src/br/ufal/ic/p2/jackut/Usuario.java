package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.Relacionamentos.*;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuário do Jackut, com todos seus atributos e relacionamentos.
 * <p>
 * Armazena informações básicas do usuário (nome, login, senha), atributos adicionais,
 * mensagens, recados e todos os tipos de relacionamentos com outros usuários (amigos, ídolos, fãs...).
 * </p>
 * <p>
 * Implementa Serializable para permitir a persistência dos objetos.
 * Precisou de uma imensa dose de café para ser feita.
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
     * "destinatários" é um caso um pouco especial. Não é necessário para passar nos casos testes, mas foi implementado pois pode ser útil no futuro.
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
     * Constrói um novo usuário com informações básicas.
     *
     * @param login Login único do usuário
     * @param senha Senha do usuário
     * @param nome  Nome completo do usuário
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
     * Obtém o nome do usuário.
     *
     * @return Nome do usuário
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     *
     * @param nome Novo nome do usuário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o login do usuário.
     *
     * @return Login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o login do usuário.
     *
     * @param login Novo login do usuário
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Compara a senha fornecida com a senha armazenada.
     *
     * @param senha Senha a ser verificada
     * @return 1 se as senhas coincidirem, 0 caso contrário
     */
    public int comparaSenha(String senha) {
        return senha.equals(this.senha) ? 1 : 0;
    }

    /**
     * Define uma nova senha para o usuário.
     *
     * @param senha Nova senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Obtém o valor de um atributo específico do usuário.
     *
     * @param atributo Nome do atributo
     * @return Valor do atributo
     * @throws InformacaoInvalidaException Se o atributo não existir
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
     * Obtém o mapa completo de atributos adicionais do usuário.
     *
     * @return Mapa de atributos adicionais
     */
    public Map<String, String> getAtributosMap() {
        return atributosMap;
    }

    /**
     * Adiciona um convite de amizade de outro usuário.
     *
     * @param user Login do usuário que enviou o convite
     * @throws ConflitoInformacaoException Se o usuário já for amigo
     */
    public void adicionarConvite(String user) {
        if (amigos.contem(user)) {
            throw new ConflitoInformacaoException("Usuário já está adicionado como amigo.");
        }
        convites.adicionar(user);
    }

    /**
     * Verifica se existe um convite pendente de um usuário específico.
     *
     * @param user Login do usuário a verificar
     * @return true se existir convite, false caso contrário
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
     * Verifica se um usuário é amigo.
     *
     * @param user Login do usuário a verificar
     * @return true se for amigo, false caso contrário
     * @throws ConflitoInformacaoException Se tentar verificar amizade consigo mesmo
     */
    public boolean ehAmigo(String user) {
        if (user.equals(login)) {
            throw new ConflitoInformacaoException("Usuário não pode ser amigo de si mesmo.");
        }
        return amigos.contem(user);
    }

    /**
     * Lê o próximo recado não lido.
     *
     * @return Conteúdo do recado
     * @throws InformacaoInvalidaException Se não houver recados
     */
    public String getRecado() {
        if (comunicados.isEmpty()) {
            throw new InformacaoInvalidaException("Não há recados.");
        }
        return comunicados.poll().getMensagem();
    }

    /**
     * Adiciona um novo recado à fila de recados.
     *
     * @param recado    Conteúdo do recado
     * @param remetente Login do remetente
     */
    public void adicionarRecado(String recado, String remetente) {
        Comunicado r = new Comunicado(recado, remetente);
        comunicados.add(r);
    }

    /**
     * Adiciona o usuário a uma comunidade.
     *
     * @param comunidade Nome da comunidade
     */
    public void adicionarComunidade(String comunidade) {
        comunidades.add(comunidade);
    }

    /**
     * Adiciona uma nova mensagem à fila de mensagens.
     *
     * @param mensagem  Conteúdo da mensagem
     * @param remetente Login do remetente
     */
    public void adicionarMensagem(String mensagem, String remetente) {
        Comunicado m = new Comunicado(mensagem, remetente);
        mensagens.add(m);
    }

    /**
     * Lê a próxima mensagem não lida.
     *
     * @return Conteúdo da mensagem
     * @throws ListadeMensagensVaziaException Se não houver mensagens
     */
    public String lerMensagem() {
        if (mensagens.isEmpty()) {
            throw new ListadeMensagensVaziaException("Não há mensagens.");
        }
        return mensagens.poll().getMensagem();
    }

    /**
     * Adiciona um novo ídolo à lista do usuário.
     *
     * @param nome Login do ídolo
     */
    public void adicionarIdolo(String nome) {
        idolos.adicionar(nome);
    }

    /**
     * Verifica se um usuário é fã deste usuário.
     *
     * @param nome Login do possível fã
     * @return true se for fã, false caso contrário
     */
    public boolean ehFa(String nome) {
        return fas.contem(nome);
    }

    /**
     * Adiciona um novo fã à lista do usuário.
     *
     * @param nome Login do fã
     */
    public void adicionarFa(String nome) {
        fas.adicionar(nome);
    }

    /**
     * Verifica se um usuário é paquera deste usuário.
     *
     * @param nome Login do possível paquera
     * @return true se for paquera, false caso contrário
     */
    public boolean ehPaquera(String nome) {
        return paqueras.contem(nome);
    }

    /**
     * Adiciona um novo paquera à lista do usuário.
     *
     * @param paque Login do paquera
     */
    public void adicionarPaquera(String paque) {
        paqueras.adicionar(paque);
    }

    /**
     * Adiciona um novo inimigo à lista do usuário.
     *
     * @param inimigo Login do inimigo
     */
    public void adicionarInimigo(String inimigo) {
        inimigos.adicionar(inimigo);
    }

    /**
     * Verifica se um usuário é inimigo deste usuário.
     *
     * @param inimigo Login do possível inimigo
     * @return true se for inimigo, false caso contrário
     */
    public boolean ehInimigo(String inimigo) {
        return inimigos.contem(inimigo);
    }

    /**
     * Remove todas as referências a um usuário que está sendo deletado.
     *
     * @param deletado Login do usuário sendo deletado
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
     * Remove o usuário de uma comunidade que está sendo deletada.
     *
     * @param deletado Nome da comunidade sendo deletada
     */
    public void deletarComunidade(String deletado) {
        comunidades.remove(deletado);
    }

    /**
     * Adiciona um destinatário à lista de usuários para quem este usuário enviou recados.
     *
     * @param destinatario Login do destinatário
     */
    public void adicionarDestinatario(String destinatario) {
        destinatarios.add(destinatario);
    }

    // Métodos de acesso aos relacionamentos

    /**
     * Obtém a lista de paqueras do usuário.
     *
     * @return Conjunto de logins dos paqueras
     */
    public Set<String> getPaqueras() {
        return paqueras.getRelacionamentos();
    }

    /**
     * Obtém a lista de destinatários de recados.
     *
     * @return Conjunto de logins dos destinatários
     */
    public Set<String> getDestinatarios() {
        return destinatarios;
    }

    /**
     * Obtém a lista de amigos do usuário.
     *
     * @return Conjunto de logins dos amigos
     */
    public Set<String> getAmigos() {
        return amigos.getRelacionamentos();
    }

    /**
     * Obtém a lista de fãs do usuário.
     *
     * @return Conjunto de logins dos fãs
     */
    public Set<String> getFas() {
        return fas.getRelacionamentos();
    }

    /**
     * Obtém a lista de comunidades do usuário.
     *
     * @return Conjunto de nomes das comunidades
     */
    public Set<String> getComunidades() {
        return comunidades;
    }

    /**
     * Obtém a lista de ídolos do usuário.
     *
     * @return Conjunto de logins dos ídolos
     */
    public Set<String> getIdolos() {
        return idolos.getRelacionamentos();
    }

    /**
     * Obtém a lista de convites pendentes.
     *
     * @return Conjunto de logins com convites pendentes
     */
    public Set<String> getConvites() {
        return convites.getRelacionamentos();
    }

    /**
     * Obtém a lista de inimigos do usuário.
     *
     * @return Conjunto de logins dos inimigos
     */
    public Set<String> getInimigos() {
        return inimigos.getRelacionamentos();
    }

    /**
     * Obtém a fila de recados recebidos.
     *
     * @return Fila de recados
     */
    public Queue<Comunicado> getRecados() {
        return comunicados;
    }

    /**
     * Obtém a fila de mensagens recebidas.
     *
     * @return Fila de mensagens
     */
    public Queue<Comunicado> getMensagens() {
        return mensagens;
    }

    /**
     * Retorna uma representação textual do usuário.
     *
     * @return String com informações básicas do usuário
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