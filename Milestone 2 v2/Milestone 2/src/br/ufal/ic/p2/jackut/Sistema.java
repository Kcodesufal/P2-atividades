package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Sistema representa o sistema de Jackut, realizando as operações por trás de Facade. Possui diversas funções de controle de sessão,
 * usuário, comunidades e interações sociais entre usuários.
 *
 * O sistema mantém estados persistentes através de serialização de objetos em arquivos.
 */
public class Sistema {
    /**
     * Mapa de usuários cadastrados, onde a chave é o login do usuário.
     */
    private Map<String, Usuario> usuarios = new HashMap<>();
    /**
     * Mapa de sessões ativas, onde a chave é o ID da sessão e o valor é o login do usuário autenticado.
     */
    private Map<String, String> sessoes = new HashMap<>();

    /**
     * Mapa de comunidades. Cada comunidade usa o próprio nome como chave primária.
     */
    private Map<String, Comunidade> comunidades = new HashMap<>();

    /**
     * Obtém um usuário pelo seu login.
     *
     * @param login Login do usuário a ser obtido
     * @return Objeto Usuario correspondente ao login
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado
     */
    private Usuario getUsuario(String login) {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
        }
        return usuarios.get(login);
    }

    /**
     * Obtém um usuário pelo ID da sessão.
     *
     * @param idSessao ID da sessão do usuário
     * @return Objeto Usuario correspondente à sessão
     * @throws UsuarioNaoCadastradoException Se a sessão não existir ou o usuário não estiver cadastrado
     */
    private Usuario getUsuarioPorSessao(String idSessao) {
        if (!sessoes.containsKey(idSessao)) {
            throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
        }
        String login = sessoes.get(idSessao);
        return getUsuario(login);
    }

    /**
     * Construtor da classe. Carrega os usuários e comunidades armazenados nos arquivos
     * "usuarios.ser" e "comunidades.ser".
     */
    public Sistema() {
        try {
            FileInputStream fileIn = new FileInputStream("usuarios.ser");
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            usuarios = (HashMap<String, Usuario>) objIn.readObject();
            objIn.close();
            fileIn.close();

            fileIn = new FileInputStream("comunidades.ser");
            objIn = new ObjectInputStream(fileIn);
            comunidades = (HashMap<String, Comunidade>) objIn.readObject();
            objIn.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove todos os usuários e comunidades cadastradas, zerando o sistema.
     */
    public void zerarSistema() {
        usuarios.clear();
        comunidades.clear();
    }

    /**
     * Cria um novo usuário na plataforma.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @param nome  Nome do usuário
     * @throws InformacaoInvalidaException Se alguma informação for inválida
     * @throws UsuarioJaExistenteException Se o login já estiver cadastrado
     * @throws ConflitoInformacaoException Se a senha for igual ao nome ou login
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null) {
            throw new InformacaoInvalidaException("Login inválido.");
        } else if (senha == null) {
            throw new InformacaoInvalidaException("Senha inválida.");
        } else if (nome == null) {
            throw new InformacaoInvalidaException("Nome inválido.");
        } else if (usuarios.containsKey(login)) {
            throw new UsuarioJaExistenteException("Conta com esse nome já existe.");
        } else if (login.equals(senha) || nome.equals(senha)) {
            throw new ConflitoInformacaoException("A senha deve ser diferente do nome de usuário ou do login.");
        }

        Usuario usuario = new Usuario(login, senha, nome);
        usuarios.put(login, usuario);
    }

    /**
     * Retorna um atributo do usuário.
     *
     * @param login    Login do usuário
     * @param atributo Nome do atributo a ser recuperado
     * @return O valor do atributo solicitado
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado
     */
    public String getAtributoUsuario(String login, String atributo) {
        Usuario usuario = getUsuario(login);
        if (atributo.equals("nome")) {
            return usuario.getNome();
        } else if (atributo.equals("login")) {
            return usuario.getLogin();
        } else {
            return usuario.getAtributo(atributo);
        }
    }

    /**
     * Abre uma sessão para um usuário autenticado.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @return ID da sessão aberta
     * @throws InformacaoInvalidaException Se o login ou senha forem inválidos
     */
    public String abrirSessao(String login, String senha) {
        if (!usuarios.containsKey(login) || senha == null || getUsuario(login).comparaSenha(senha) == 0) {
            throw new InformacaoInvalidaException("Login ou senha inválidos.");
        }

        String id = System.currentTimeMillis() + "-" + login;
        sessoes.put(id, login);
        return id;
    }

    /**
     * Edita um atributo do perfil do usuário autenticado.
     *
     * @param id       ID da sessão
     * @param atributo Nome do atributo a ser editado
     * @param valor    Novo valor do atributo
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public void editarPerfil(String id, String atributo, String valor) {
        Usuario user = getUsuarioPorSessao(id);
        user.editarPerfil(atributo, valor);
    }

    /**
     * Adiciona um amigo à lista de amizades do usuário autenticado.
     *
     * @param id    ID da sessão do usuário autenticado
     * @param amigo Login do usuário a ser adicionado
     * @throws UsuarioNaoCadastradoException Se o usuário ou amigo não estiverem cadastrados
     * @throws ConflitoInformacaoException   Se tentar adicionar a si mesmo
     * @throws InimigoException Se o usuário ou amigo forem inimigos
     */
    public void adicionarAmigo(String id, String amigo) {
        Usuario user = getUsuarioPorSessao(id);
        Usuario user2 = getUsuario(amigo);

        if (user.getLogin().equals(amigo)) {
            throw new ConflitoInformacaoException("Usuário não pode adicionar a si mesmo como amigo.");
        }

        if (user.ehInimigo(amigo) || user2.ehInimigo(user.getLogin())) {
            throw new InimigoException(String.format("Função inválida: %s é seu inimigo.", user2.getNome()));
        }

        if (user.checaConvite(amigo)) {
            user.adicionarAmigo(amigo);
            user2.adicionarAmigo(user.getLogin());
        } else {
            user2.adicionarConvite(user.getLogin());
        }
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login Login do primeiro usuário
     * @param amigo Login do segundo usuário
     * @return true se forem amigos, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não estiver cadastrado
     */
    public boolean ehAmigo(String login, String amigo) {
        Usuario user = getUsuario(login);
        getUsuario(amigo); // apenas para verificar se existe
        return user.ehAmigo(amigo);
    }

    /**
     * Retorna a lista de amigos de um usuário.
     *
     * @param login Login do usuário
     * @return Lista de amigos do usuário no formato "{amigo1,amigo2,...}"
     */
    public String getAmigos(String login) {
        Usuario user = getUsuario(login);
        return "{" + String.join(",", user.getAmigos()) + "}";
    }

    /**
     * Envia um recado para outro usuário.
     *
     * @param id           ID da sessão do remetente
     * @param destinatario Login do destinatário
     * @param mensagem     Conteúdo do recado
     * @throws UsuarioNaoCadastradoException Se remetente ou destinatário não estiverem cadastrados
     * @throws ConflitoInformacaoException   Se o usuário tentar enviar um recado para si mesmo
     * @throws InimigoException Se o usuário ou destinatário forem inimigos
     */
    public void enviarRecado(String id, String destinatario, String mensagem) {
        Usuario user = getUsuarioPorSessao(id);
        Usuario user2 = getUsuario(destinatario);

        if (user == user2) {
            throw new ConflitoInformacaoException("Usuário não pode enviar recado para si mesmo.");
        }
        if (user.ehInimigo(destinatario) || user2.ehInimigo(user.getLogin())) {
            throw new InimigoException(String.format("Função inválida: %s é seu inimigo.", user2.getNome()));
        }

        user2.adicionarRecado(mensagem, user.getLogin());
        user.adicionarDestinatario(destinatario);
    }

    /**
     * Lê um recado do usuário autenticado.
     *
     * @param id ID da sessão
     * @return O recado recebido
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public String lerRecado(String id) {
        Usuario user = getUsuarioPorSessao(id);
        return user.getRecado();
    }

    /**
     * Cria uma comunidade nova.
     *
     * @param id        ID da sessão do criador
     * @param nome      Nome da comunidade a ser criada
     * @param descricao Descrição da comunidade
     * @throws UsuarioNaoCadastradoException  Se a sessão for inválida
     * @throws ComunidadeJaExistenteException Se o nome da comunidade já existir
     */
    public void criarComunidade(String id, String nome, String descricao) {
        Usuario user = getUsuarioPorSessao(id);

        if (comunidades.containsKey(nome)) {
            throw new ComunidadeJaExistenteException("Comunidade com esse nome já existe.");
        }
        Comunidade comunidade = new Comunidade(user.getLogin(), nome, descricao);
        comunidades.put(nome, comunidade);
        user.adicionarComunidade(nome);
    }

    /**
     * Mostra a descrição de uma comunidade.
     *
     * @param nomecomunidade nome da comunidade
     * @return Descrição da comunidade
     * @throws ComunidadeNaoExistenteException se a comunidade não existir
     */
    public String getDescricaoComunidade(String nomecomunidade) {
        if (!comunidades.containsKey(nomecomunidade)) {
            throw new ComunidadeNaoExistenteException("Comunidade não existe.");
        }
        Comunidade c = comunidades.get(nomecomunidade);
        return c.getDescricao();
    }

    /**
     * Retorna o nome do dono da comunidade.
     *
     * @param nomecomunidade Nome da comunidade
     * @return nome do dono
     * @throws ComunidadeNaoExistenteException se a comunidade não existir
     */
    public String getDonoComunidade(String nomecomunidade) {
        if (!comunidades.containsKey(nomecomunidade)) {
            throw new ComunidadeNaoExistenteException("Comunidade não existe.");
        }
        Comunidade c = comunidades.get(nomecomunidade);
        return c.getDono();
    }

    /**
     * Obtém a lista de membros da comunidade.
     *
     * @param nomecomunidade nome da comunidade
     * @return lista de membros no formato "{membro1,membro2,...}"
     * @throws ComunidadeNaoExistenteException se a comunidade não existir
     */
    public String getMembrosComunidade(String nomecomunidade) {
        if (!comunidades.containsKey(nomecomunidade)) {
            throw new ComunidadeNaoExistenteException("Comunidade não existe.");
        }
        Comunidade c = comunidades.get(nomecomunidade);
        return c.getMembros();
    }

    /**
     * Obtém a lista de comunidades da qual o usuário participa.
     *
     * @param login nome de usuário
     * @return lista de comunidades no formato "{comunidade1,comunidade2,...}"
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getComunidades(String login) {
        Usuario user = getUsuario(login);
        return "{" + String.join(",", user.getComunidades()) + "}";
    }

    /**
     * Adiciona a comunidade à lista de comunidades do usuário. Adiciona o usuário à lista de membros da comunidade.
     *
     * @param id   id da sessão do usuário
     * @param nome nome da comunidade
     * @throws UsuarioNaoCadastradoException   se o usuário não existir
     * @throws ComunidadeNaoExistenteException se a comunidade não existir
     */
    public void adicionarComunidade(String id, String nome) {
        Usuario user = getUsuarioPorSessao(id);
        if (!comunidades.containsKey(nome)) {
            throw new ComunidadeNaoExistenteException("Comunidade não existe.");
        }
        Comunidade c = comunidades.get(nome);
        c.adicionarMembro(user.getLogin());
        user.adicionarComunidade(nome);
    }

    /**
     * Lê uma mensagem do usuário autenticado.
     *
     * @param id ID da sessão
     * @return A mensagem recebida
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public String lerMensagem(String id) {
        Usuario user = getUsuarioPorSessao(id);
        return user.lerMensagem();
    }

    /**
     * Envia uma mensagem para todos os membros de uma comunidade.
     *
     * @param id         ID da sessão do remetente
     * @param comunidade Nome da comunidade
     * @param mensagem   Conteúdo da mensagem
     * @throws UsuarioNaoCadastradoException   Se a sessão for inválida
     * @throws ComunidadeNaoExistenteException Se a comunidade não existir
     */
    public void enviarMensagem(String id, String comunidade, String mensagem) {
        Usuario user = getUsuarioPorSessao(id);
        if (!comunidades.containsKey(comunidade)) {
            throw new ComunidadeNaoExistenteException("Comunidade não existe.");
        }
        Comunidade c = comunidades.get(comunidade);
        Set<String> m = c.getMembrosSet();

        for (String membro : m) {
            getUsuario(membro).adicionarMensagem(mensagem, user.getLogin());
        }
    }

    /**
     * Verifica se um usuário é fã de outro.
     *
     * @param login Login do usuário
     * @param idolo Login do ídolo
     * @return true se for fã, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     */
    public boolean ehFa(String login, String idolo) {
        getUsuario(login); // apenas para verificar se existe
        Usuario user2 = getUsuario(idolo);
        return user2.ehFa(login);
    }

    /**
     * Adiciona um ídolo à lista de ídolos do usuário autenticado e adiciona o usuário à lista de fãs do ídolo.
     *
     * @param id    ID da sessão do usuário
     * @param idolo Login do ídolo
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     * @throws ConflitoInformacaoException   Se o usuário tentar adicionar a si mesmo como ídolo
     * @throws InimigoException Se o usuário ou ídolo forem inimigos
     */
    public void adicionarIdolo(String id, String idolo) {
        Usuario user = getUsuarioPorSessao(id);
        Usuario user2 = getUsuario(idolo);

        if (user.getLogin().equals(idolo)) {
            throw new ConflitoInformacaoException("Usuário não pode ser fã de si mesmo.");
        }
        if (user.ehInimigo(idolo) || user2.ehInimigo(user.getLogin())) {
            throw new InimigoException(String.format("Função inválida: %s é seu inimigo.", user2.getNome()));
        }

        user.adicionarIdolo(idolo);
        user2.adicionarFa(user.getLogin());
    }

    /**
     * Retorna a lista de fãs de um usuário.
     *
     * @param login Login do usuário
     * @return Lista de fãs no formato "{fa1,fa2,...}"
     * @throws UsuarioNaoCadastradoException Se o usuário não existir
     */
    public String getFas(String login) {
        Usuario user = getUsuario(login);
        return "{" + String.join(",", user.getFas()) + "}";
    }

    /**
     * Verifica se um usuário é paquera do usuário autenticado.
     *
     * @param id   ID da sessão do usuário
     * @param nome Login do possível paquera
     * @return true se for paquera, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     */
    public boolean ehPaquera(String id, String nome) {
        Usuario user = getUsuarioPorSessao(id);
        getUsuario(nome); // apenas para verificar se existe
        return user.ehPaquera(nome);
    }

    /**
     * Adiciona um paquera à lista de paqueras do usuário autenticado.
     * Se for um paquera mútuo, envia recados para ambos os usuários.
     *
     * @param id   ID da sessão do usuário
     * @param nome Login do paquera
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     * @throws InimigoException Se o usuário ou paquera forem inimigos
     */
    public void adicionarPaquera(String id, String nome) {
        Usuario user = getUsuarioPorSessao(id);
        Usuario user2 = getUsuario(nome);

        if (user.ehInimigo(nome) || user2.ehInimigo(user.getLogin())) {
            throw new InimigoException(String.format("Função inválida: %s é seu inimigo.", user2.getNome()));
        }

        user.adicionarPaquera(nome);
        if (user2.ehPaquera(user.getLogin())) {
            String msg = String.format("%s é seu paquera - Recado do Jackut.", user2.getNome());
            user.adicionarRecado(msg, "Cupido");
            msg = String.format("%s é seu paquera - Recado do Jackut.", user.getNome());
            user2.adicionarRecado(msg, "Cupido");
        }
    }

    /**
     * Retorna a lista de paqueras do usuário autenticado.
     *
     * @param id ID da sessão do usuário
     * @return Lista de paqueras no formato "{paquera1,paquera2,...}"
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public String getPaqueras(String id) {
        Usuario user = getUsuarioPorSessao(id);
        return "{" + String.join(",", user.getPaqueras()) + "}";
    }

    /**
     * Adiciona um inimigo à lista de inimigos do usuário autenticado.
     *
     * @param id      ID da sessão do usuário
     * @param inimigo Login do inimigo
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     */
    public void adicionarInimigo(String id, String inimigo) {
        Usuario user = getUsuarioPorSessao(id);
        getUsuario(inimigo); // apenas para verificar se existe
        user.adicionarInimigo(inimigo);
    }

    /**
     * Remove um usuário do sistema, limpando todas as suas interações com outros usuários e comunidades.
     *
     * @param id ID da sessão do usuário a ser removido
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public void removerUsuario(String id) {
        Usuario user = getUsuarioPorSessao(id);
        String login = user.getLogin();

        // Remove interações do usuário deletado com todos os outros usuários
        // Para convites
        for (String m : user.getConvites()) {
            Usuario other = usuarios.get(m);
            other.deletarUsuario(login);
        }

        // Para amigos
        for (String m : user.getAmigos()) {
            Usuario other = usuarios.get(m);
            other.deletarUsuario(login);
        }

        // Para idolos
        for (String m : user.getIdolos()) {
            Usuario other = usuarios.get(m);
            other.deletarUsuario(login);
        }

        // Para fas
        for (String m : user.getFas()) {
            Usuario other = usuarios.get(m);
            other.deletarUsuario(login);
        }

        // Para paqueras
        for (String m : user.getPaqueras()) {
            Usuario other = usuarios.get(m);
            other.deletarUsuario(login);
        }

        // Para inimigos
        for (String m : user.getInimigos()) {
            Usuario other = usuarios.get(m);
            other.deletarUsuario(login);
        }

        // Para destinatarios
        for (String m : user.getDestinatarios()) {
            Usuario other = usuarios.get(m);
            other.deletarUsuario(login);
        }

        // Remove o usuário de todas as comunidades
        for (String comunidade : user.getComunidades()) {
            Comunidade c = comunidades.get(comunidade);
            if (c.getDono().equals(login)) {
                Set<String> membros = c.getMembrosSet();
                comunidades.remove(c.getNome());
                for (String membro : membros) {
                    getUsuario(membro).deletarComunidade(c.getNome());
                }
            } else if (c.getMembrosSet().contains(login)) {
                c.removerMembro(login);
            }
        }

        // Remove o usuário do mapa de usuários
        usuarios.remove(login);
        // Remove a sessão associada
        sessoes.remove(id);
    }

    /**
     * Salva os usuários cadastrados nos arquivos "usuarios.ser" e "comunidade.ser", então encerra o sistema.
     */
    public void encerrarSistema() {
        try {
            FileOutputStream fileOut = new FileOutputStream("usuarios.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(usuarios);
            objOut.close();
            fileOut.close();

            fileOut = new FileOutputStream("comunidades.ser");
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(comunidades);
            objOut.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ocorreu um erro ao salvar os dados.");
        }
    }

    /**
     * Retorna uma representação textual do sistema.
     *
     * @return String com informações dos usuários, sessões ativas e comunidades
     */
    @Override
    public String toString() {
        return "Facade{" +
                "usuarios=" + usuarios.keySet() +
                ", sessoes=" + sessoes.keySet() +
                ", comunidades=" + comunidades.keySet() +
                '}';
    }
}