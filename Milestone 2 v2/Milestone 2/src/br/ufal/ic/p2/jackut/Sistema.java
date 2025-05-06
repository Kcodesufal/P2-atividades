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
 * Sistema representa o sistema de Jackut, realizando as opera��es por tr�s de Facade. Possui diversas fun��es de controle de sess�o,
 * usu�rio, comunidades e intera��es sociais entre usu�rios.
 *
 * O sistema mant�m estados persistentes atrav�s de serializa��o de objetos em arquivos.
 */
public class Sistema {
    /**
     * Mapa de usu�rios cadastrados, onde a chave � o login do usu�rio.
     */
    private Map<String, Usuario> usuarios = new HashMap<>();
    /**
     * Mapa de sess�es ativas, onde a chave � o ID da sess�o e o valor � o login do usu�rio autenticado.
     */
    private Map<String, String> sessoes = new HashMap<>();

    /**
     * Mapa de comunidades. Cada comunidade usa o pr�prio nome como chave prim�ria.
     */
    private Map<String, Comunidade> comunidades = new HashMap<>();

    /**
     * Obt�m um usu�rio pelo seu login.
     *
     * @param login Login do usu�rio a ser obtido
     * @return Objeto Usuario correspondente ao login
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado
     */
    private Usuario getUsuario(String login) {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
        }
        return usuarios.get(login);
    }

    /**
     * Obt�m um usu�rio pelo ID da sess�o.
     *
     * @param idSessao ID da sess�o do usu�rio
     * @return Objeto Usuario correspondente � sess�o
     * @throws UsuarioNaoCadastradoException Se a sess�o n�o existir ou o usu�rio n�o estiver cadastrado
     */
    private Usuario getUsuarioPorSessao(String idSessao) {
        if (!sessoes.containsKey(idSessao)) {
            throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
        }
        String login = sessoes.get(idSessao);
        return getUsuario(login);
    }

    /**
     * Construtor da classe. Carrega os usu�rios e comunidades armazenados nos arquivos
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
     * Remove todos os usu�rios e comunidades cadastradas, zerando o sistema.
     */
    public void zerarSistema() {
        usuarios.clear();
        comunidades.clear();
    }

    /**
     * Cria um novo usu�rio na plataforma.
     *
     * @param login Login do usu�rio
     * @param senha Senha do usu�rio
     * @param nome  Nome do usu�rio
     * @throws InformacaoInvalidaException Se alguma informa��o for inv�lida
     * @throws UsuarioJaExistenteException Se o login j� estiver cadastrado
     * @throws ConflitoInformacaoException Se a senha for igual ao nome ou login
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null) {
            throw new InformacaoInvalidaException("Login inv�lido.");
        } else if (senha == null) {
            throw new InformacaoInvalidaException("Senha inv�lida.");
        } else if (nome == null) {
            throw new InformacaoInvalidaException("Nome inv�lido.");
        } else if (usuarios.containsKey(login)) {
            throw new UsuarioJaExistenteException("Conta com esse nome j� existe.");
        } else if (login.equals(senha) || nome.equals(senha)) {
            throw new ConflitoInformacaoException("A senha deve ser diferente do nome de usu�rio ou do login.");
        }

        Usuario usuario = new Usuario(login, senha, nome);
        usuarios.put(login, usuario);
    }

    /**
     * Retorna um atributo do usu�rio.
     *
     * @param login    Login do usu�rio
     * @param atributo Nome do atributo a ser recuperado
     * @return O valor do atributo solicitado
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado
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
     * Abre uma sess�o para um usu�rio autenticado.
     *
     * @param login Login do usu�rio
     * @param senha Senha do usu�rio
     * @return ID da sess�o aberta
     * @throws InformacaoInvalidaException Se o login ou senha forem inv�lidos
     */
    public String abrirSessao(String login, String senha) {
        if (!usuarios.containsKey(login) || senha == null || getUsuario(login).comparaSenha(senha) == 0) {
            throw new InformacaoInvalidaException("Login ou senha inv�lidos.");
        }

        String id = System.currentTimeMillis() + "-" + login;
        sessoes.put(id, login);
        return id;
    }

    /**
     * Edita um atributo do perfil do usu�rio autenticado.
     *
     * @param id       ID da sess�o
     * @param atributo Nome do atributo a ser editado
     * @param valor    Novo valor do atributo
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public void editarPerfil(String id, String atributo, String valor) {
        Usuario user = getUsuarioPorSessao(id);
        user.editarPerfil(atributo, valor);
    }

    /**
     * Adiciona um amigo � lista de amizades do usu�rio autenticado.
     *
     * @param id    ID da sess�o do usu�rio autenticado
     * @param amigo Login do usu�rio a ser adicionado
     * @throws UsuarioNaoCadastradoException Se o usu�rio ou amigo n�o estiverem cadastrados
     * @throws ConflitoInformacaoException   Se tentar adicionar a si mesmo
     * @throws InimigoException Se o usu�rio ou amigo forem inimigos
     */
    public void adicionarAmigo(String id, String amigo) {
        Usuario user = getUsuarioPorSessao(id);
        Usuario user2 = getUsuario(amigo);

        if (user.getLogin().equals(amigo)) {
            throw new ConflitoInformacaoException("Usu�rio n�o pode adicionar a si mesmo como amigo.");
        }

        if (user.ehInimigo(amigo) || user2.ehInimigo(user.getLogin())) {
            throw new InimigoException(String.format("Fun��o inv�lida: %s � seu inimigo.", user2.getNome()));
        }

        if (user.checaConvite(amigo)) {
            user.adicionarAmigo(amigo);
            user2.adicionarAmigo(user.getLogin());
        } else {
            user2.adicionarConvite(user.getLogin());
        }
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param login Login do primeiro usu�rio
     * @param amigo Login do segundo usu�rio
     * @return true se forem amigos, false caso contr�rio
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o estiver cadastrado
     */
    public boolean ehAmigo(String login, String amigo) {
        Usuario user = getUsuario(login);
        getUsuario(amigo); // apenas para verificar se existe
        return user.ehAmigo(amigo);
    }

    /**
     * Retorna a lista de amigos de um usu�rio.
     *
     * @param login Login do usu�rio
     * @return Lista de amigos do usu�rio no formato "{amigo1,amigo2,...}"
     */
    public String getAmigos(String login) {
        Usuario user = getUsuario(login);
        return "{" + String.join(",", user.getAmigos()) + "}";
    }

    /**
     * Envia um recado para outro usu�rio.
     *
     * @param id           ID da sess�o do remetente
     * @param destinatario Login do destinat�rio
     * @param mensagem     Conte�do do recado
     * @throws UsuarioNaoCadastradoException Se remetente ou destinat�rio n�o estiverem cadastrados
     * @throws ConflitoInformacaoException   Se o usu�rio tentar enviar um recado para si mesmo
     * @throws InimigoException Se o usu�rio ou destinat�rio forem inimigos
     */
    public void enviarRecado(String id, String destinatario, String mensagem) {
        Usuario user = getUsuarioPorSessao(id);
        Usuario user2 = getUsuario(destinatario);

        if (user == user2) {
            throw new ConflitoInformacaoException("Usu�rio n�o pode enviar recado para si mesmo.");
        }
        if (user.ehInimigo(destinatario) || user2.ehInimigo(user.getLogin())) {
            throw new InimigoException(String.format("Fun��o inv�lida: %s � seu inimigo.", user2.getNome()));
        }

        user2.adicionarRecado(mensagem, user.getLogin());
        user.adicionarDestinatario(destinatario);
    }

    /**
     * L� um recado do usu�rio autenticado.
     *
     * @param id ID da sess�o
     * @return O recado recebido
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public String lerRecado(String id) {
        Usuario user = getUsuarioPorSessao(id);
        return user.getRecado();
    }

    /**
     * Cria uma comunidade nova.
     *
     * @param id        ID da sess�o do criador
     * @param nome      Nome da comunidade a ser criada
     * @param descricao Descri��o da comunidade
     * @throws UsuarioNaoCadastradoException  Se a sess�o for inv�lida
     * @throws ComunidadeJaExistenteException Se o nome da comunidade j� existir
     */
    public void criarComunidade(String id, String nome, String descricao) {
        Usuario user = getUsuarioPorSessao(id);

        if (comunidades.containsKey(nome)) {
            throw new ComunidadeJaExistenteException("Comunidade com esse nome j� existe.");
        }
        Comunidade comunidade = new Comunidade(user.getLogin(), nome, descricao);
        comunidades.put(nome, comunidade);
        user.adicionarComunidade(nome);
    }

    /**
     * Mostra a descri��o de uma comunidade.
     *
     * @param nomecomunidade nome da comunidade
     * @return Descri��o da comunidade
     * @throws ComunidadeNaoExistenteException se a comunidade n�o existir
     */
    public String getDescricaoComunidade(String nomecomunidade) {
        if (!comunidades.containsKey(nomecomunidade)) {
            throw new ComunidadeNaoExistenteException("Comunidade n�o existe.");
        }
        Comunidade c = comunidades.get(nomecomunidade);
        return c.getDescricao();
    }

    /**
     * Retorna o nome do dono da comunidade.
     *
     * @param nomecomunidade Nome da comunidade
     * @return nome do dono
     * @throws ComunidadeNaoExistenteException se a comunidade n�o existir
     */
    public String getDonoComunidade(String nomecomunidade) {
        if (!comunidades.containsKey(nomecomunidade)) {
            throw new ComunidadeNaoExistenteException("Comunidade n�o existe.");
        }
        Comunidade c = comunidades.get(nomecomunidade);
        return c.getDono();
    }

    /**
     * Obt�m a lista de membros da comunidade.
     *
     * @param nomecomunidade nome da comunidade
     * @return lista de membros no formato "{membro1,membro2,...}"
     * @throws ComunidadeNaoExistenteException se a comunidade n�o existir
     */
    public String getMembrosComunidade(String nomecomunidade) {
        if (!comunidades.containsKey(nomecomunidade)) {
            throw new ComunidadeNaoExistenteException("Comunidade n�o existe.");
        }
        Comunidade c = comunidades.get(nomecomunidade);
        return c.getMembros();
    }

    /**
     * Obt�m a lista de comunidades da qual o usu�rio participa.
     *
     * @param login nome de usu�rio
     * @return lista de comunidades no formato "{comunidade1,comunidade2,...}"
     * @throws UsuarioNaoCadastradoException se o usu�rio n�o existir
     */
    public String getComunidades(String login) {
        Usuario user = getUsuario(login);
        return "{" + String.join(",", user.getComunidades()) + "}";
    }

    /**
     * Adiciona a comunidade � lista de comunidades do usu�rio. Adiciona o usu�rio � lista de membros da comunidade.
     *
     * @param id   id da sess�o do usu�rio
     * @param nome nome da comunidade
     * @throws UsuarioNaoCadastradoException   se o usu�rio n�o existir
     * @throws ComunidadeNaoExistenteException se a comunidade n�o existir
     */
    public void adicionarComunidade(String id, String nome) {
        Usuario user = getUsuarioPorSessao(id);
        if (!comunidades.containsKey(nome)) {
            throw new ComunidadeNaoExistenteException("Comunidade n�o existe.");
        }
        Comunidade c = comunidades.get(nome);
        c.adicionarMembro(user.getLogin());
        user.adicionarComunidade(nome);
    }

    /**
     * L� uma mensagem do usu�rio autenticado.
     *
     * @param id ID da sess�o
     * @return A mensagem recebida
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public String lerMensagem(String id) {
        Usuario user = getUsuarioPorSessao(id);
        return user.lerMensagem();
    }

    /**
     * Envia uma mensagem para todos os membros de uma comunidade.
     *
     * @param id         ID da sess�o do remetente
     * @param comunidade Nome da comunidade
     * @param mensagem   Conte�do da mensagem
     * @throws UsuarioNaoCadastradoException   Se a sess�o for inv�lida
     * @throws ComunidadeNaoExistenteException Se a comunidade n�o existir
     */
    public void enviarMensagem(String id, String comunidade, String mensagem) {
        Usuario user = getUsuarioPorSessao(id);
        if (!comunidades.containsKey(comunidade)) {
            throw new ComunidadeNaoExistenteException("Comunidade n�o existe.");
        }
        Comunidade c = comunidades.get(comunidade);
        Set<String> m = c.getMembrosSet();

        for (String membro : m) {
            getUsuario(membro).adicionarMensagem(mensagem, user.getLogin());
        }
    }

    /**
     * Verifica se um usu�rio � f� de outro.
     *
     * @param login Login do usu�rio
     * @param idolo Login do �dolo
     * @return true se for f�, false caso contr�rio
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     */
    public boolean ehFa(String login, String idolo) {
        getUsuario(login); // apenas para verificar se existe
        Usuario user2 = getUsuario(idolo);
        return user2.ehFa(login);
    }

    /**
     * Adiciona um �dolo � lista de �dolos do usu�rio autenticado e adiciona o usu�rio � lista de f�s do �dolo.
     *
     * @param id    ID da sess�o do usu�rio
     * @param idolo Login do �dolo
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     * @throws ConflitoInformacaoException   Se o usu�rio tentar adicionar a si mesmo como �dolo
     * @throws InimigoException Se o usu�rio ou �dolo forem inimigos
     */
    public void adicionarIdolo(String id, String idolo) {
        Usuario user = getUsuarioPorSessao(id);
        Usuario user2 = getUsuario(idolo);

        if (user.getLogin().equals(idolo)) {
            throw new ConflitoInformacaoException("Usu�rio n�o pode ser f� de si mesmo.");
        }
        if (user.ehInimigo(idolo) || user2.ehInimigo(user.getLogin())) {
            throw new InimigoException(String.format("Fun��o inv�lida: %s � seu inimigo.", user2.getNome()));
        }

        user.adicionarIdolo(idolo);
        user2.adicionarFa(user.getLogin());
    }

    /**
     * Retorna a lista de f�s de um usu�rio.
     *
     * @param login Login do usu�rio
     * @return Lista de f�s no formato "{fa1,fa2,...}"
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o existir
     */
    public String getFas(String login) {
        Usuario user = getUsuario(login);
        return "{" + String.join(",", user.getFas()) + "}";
    }

    /**
     * Verifica se um usu�rio � paquera do usu�rio autenticado.
     *
     * @param id   ID da sess�o do usu�rio
     * @param nome Login do poss�vel paquera
     * @return true se for paquera, false caso contr�rio
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     */
    public boolean ehPaquera(String id, String nome) {
        Usuario user = getUsuarioPorSessao(id);
        getUsuario(nome); // apenas para verificar se existe
        return user.ehPaquera(nome);
    }

    /**
     * Adiciona um paquera � lista de paqueras do usu�rio autenticado.
     * Se for um paquera m�tuo, envia recados para ambos os usu�rios.
     *
     * @param id   ID da sess�o do usu�rio
     * @param nome Login do paquera
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     * @throws InimigoException Se o usu�rio ou paquera forem inimigos
     */
    public void adicionarPaquera(String id, String nome) {
        Usuario user = getUsuarioPorSessao(id);
        Usuario user2 = getUsuario(nome);

        if (user.ehInimigo(nome) || user2.ehInimigo(user.getLogin())) {
            throw new InimigoException(String.format("Fun��o inv�lida: %s � seu inimigo.", user2.getNome()));
        }

        user.adicionarPaquera(nome);
        if (user2.ehPaquera(user.getLogin())) {
            String msg = String.format("%s � seu paquera - Recado do Jackut.", user2.getNome());
            user.adicionarRecado(msg, "Cupido");
            msg = String.format("%s � seu paquera - Recado do Jackut.", user.getNome());
            user2.adicionarRecado(msg, "Cupido");
        }
    }

    /**
     * Retorna a lista de paqueras do usu�rio autenticado.
     *
     * @param id ID da sess�o do usu�rio
     * @return Lista de paqueras no formato "{paquera1,paquera2,...}"
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public String getPaqueras(String id) {
        Usuario user = getUsuarioPorSessao(id);
        return "{" + String.join(",", user.getPaqueras()) + "}";
    }

    /**
     * Adiciona um inimigo � lista de inimigos do usu�rio autenticado.
     *
     * @param id      ID da sess�o do usu�rio
     * @param inimigo Login do inimigo
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     */
    public void adicionarInimigo(String id, String inimigo) {
        Usuario user = getUsuarioPorSessao(id);
        getUsuario(inimigo); // apenas para verificar se existe
        user.adicionarInimigo(inimigo);
    }

    /**
     * Remove um usu�rio do sistema, limpando todas as suas intera��es com outros usu�rios e comunidades.
     *
     * @param id ID da sess�o do usu�rio a ser removido
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public void removerUsuario(String id) {
        Usuario user = getUsuarioPorSessao(id);
        String login = user.getLogin();

        // Remove intera��es do usu�rio deletado com todos os outros usu�rios
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

        // Remove o usu�rio de todas as comunidades
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

        // Remove o usu�rio do mapa de usu�rios
        usuarios.remove(login);
        // Remove a sess�o associada
        sessoes.remove(id);
    }

    /**
     * Salva os usu�rios cadastrados nos arquivos "usuarios.ser" e "comunidade.ser", ent�o encerra o sistema.
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
     * Retorna uma representa��o textual do sistema.
     *
     * @return String com informa��es dos usu�rios, sess�es ativas e comunidades
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