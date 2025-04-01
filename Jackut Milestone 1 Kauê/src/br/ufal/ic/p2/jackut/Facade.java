package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;

import easyaccept.EasyAccept;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Facade representa o Sistema. Possui diversas funções de controle de sessão e usuário.
 */
public class Facade {
  /**
   * Mapa de usuários cadastrados, onde a chave é o login do usuário.
   */
  private Map<String, Usuario> usuarios = new HashMap<>();
  /**
   * Mapa de sessões ativas, onde a chave é o ID da sessão e o valor é o login do usuário autenticado.
   */
  private Map<String, String> sessoes = new HashMap<>();

  /**
   * Construtor da classe. Carrega os usuários armazenados em "usuarios.ser".
   */
  public Facade() {
    try {
      FileInputStream fileIn = new FileInputStream("usuarios.ser");
      ObjectInputStream objIn = new ObjectInputStream(fileIn);
      usuarios = (HashMap<String, Usuario>) objIn.readObject();
      objIn.close();
      fileIn.close();
      System.out.println("Usuários carregados com sucesso.");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Ocorreu um erro ao carregar os usuários.");
    }
  }

  /**
   * Remove todos os usuários cadastrados, zerando o sistema.
   */
  public void zerarSistema() {
    usuarios.clear();
  }

  /**
   * Cria um novo usuário na plataforma.
   *
   * @param login Login do usuário.
   * @param senha Senha do usuário.
   * @param nome Nome do usuário.
   * @throws InformacaoInvalidaException Se alguma informação for inválida.
   * @throws UsuarioJaExistenteException Se o login já estiver cadastrado.
   * @throws ConflitoInformacaoException Se a senha for igual ao nome ou login.
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
   * @param login Login do usuário.
   * @param atributo Nome do atributo a ser recuperado.
   * @return O valor do atributo solicitado.
   * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
   */
  public String getAtributoUsuario(String login, String atributo) {
    if (!usuarios.containsKey(login)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }
    String att;
    if (atributo.equals("nome")) {
      att = usuarios.get(login).getNome();
    } else if (atributo.equals("login")) {
      att = usuarios.get(login).getLogin();
    } else {
      att = usuarios.get(login).getAtributo(atributo);
    }
    return att;
  }

  /**
   * Abre uma sessão para um usuário autenticado.
   *
   * @param login Login do usuário.
   * @param senha Senha do usuário.
   * @return ID da sessão aberta.
   * @throws InformacaoInvalidaException Se o login ou senha forem inválidos.
   */
  public String abrirSessao(String login, String senha) {
    if (!usuarios.containsKey(login) || senha == null || usuarios.get(login).comparaSenha(senha) == 0) {
      throw new InformacaoInvalidaException("Login ou senha inválidos.");
    }

    String id = System.currentTimeMillis() + "-" + login;
    sessoes.put(id, login);
    return id;
  }

  /**
   * Edita um atributo do perfil do usuário autenticado.
   *
   * @param id ID da sessão.
   * @param atributo Nome do atributo a ser editado.
   * @param valor Novo valor do atributo.
   * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
   */
  public void editarPerfil(String id, String atributo, String valor) {
    if (!sessoes.containsKey(id)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }

    String auxLogin = sessoes.get(id);
    if (!usuarios.containsKey(auxLogin)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }

    Usuario user = usuarios.get(auxLogin);
    user.editarPerfil(atributo, valor);
  }

  /**
   * Adiciona um amigo à lista de amizades do usuário autenticado.
   *
   * @param id ID da sessão do usuário autenticado.
   * @param amigo Login do usuário a ser adicionado.
   * @throws UsuarioNaoCadastradoException Se o usuário ou amigo não estiverem cadastrados.
   * @throws ConflitoInformacaoException Se tentar adicionar a si mesmo.
   */
  public void adicionarAmigo(String id, String amigo) {
    if (!sessoes.containsKey(id)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }
    if (!usuarios.containsKey(amigo)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }

    String auxLogin = sessoes.get(id);

    if (auxLogin.equals(amigo)) {
      throw new ConflitoInformacaoException("Usuário não pode adicionar a si mesmo como amigo.");
    }

    Usuario user = usuarios.get(auxLogin);
    Usuario user2 = usuarios.get(amigo);

    if (user.checaConvite(amigo)) {
      user.adicionarAmigo(amigo);
      user2.adicionarAmigo(auxLogin);
    } else {
      user2.adicionarConvite(auxLogin);
    }
  }

  /**
   * Verifica se dois usuários são amigos.
   *
   * @param login Login do primeiro usuário.
   * @param amigo Login do segundo usuário.
   * @return true se forem amigos, false caso contrário.
   * @throws UsuarioNaoCadastradoException Se algum dos usuários não estiver cadastrado.
   */
  public boolean ehAmigo(String login, String amigo) {
    if (!usuarios.containsKey(login)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }
    if (!usuarios.containsKey(amigo)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }

    return usuarios.get(login).ehAmigo(amigo);
  }

  /**
   * Retorna a lista de amigos de um usuário.
   *
   * @param login Login do usuário.
   * @return Lista de amigos do usuário.
   */
  public String getAmigos(String login) {
    return usuarios.get(login).getAmigos();
  }

  /**
   * Envia um recado para outro usuário.
   *
   * @param id ID da sessão do remetente.
   * @param destinatario Login do destinatário.
   * @param mensagem Conteúdo do recado.
   * @throws UsuarioNaoCadastradoException Se remetente ou destinatário não estiverem cadastrados.
   * @throws ConflitoInformacaoException Se o usuário tentar enviar um recado para si mesmo.
   */
  public void enviarRecado(String id, String destinatario, String mensagem) {
    if (!sessoes.containsKey(id)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }

    String auxLogin = sessoes.get(id);

    if (!usuarios.containsKey(destinatario)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }

    Usuario user = usuarios.get(auxLogin);
    Usuario user2 = usuarios.get(destinatario);

    if (user == user2) {
      throw new ConflitoInformacaoException("Usuário não pode enviar recado para si mesmo.");
    }

    user2.adicionarRecado(mensagem,auxLogin);
  }

  /**
   * Lê um recado do usuário autenticado.
   *
   * @param id ID da sessão.
   * @return O recado recebido.
   * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
   */
  public String lerRecado(String id) {
    if (!sessoes.containsKey(id)) {
      throw new UsuarioNaoCadastradoException("Usuário não cadastrado.");
    }

    String auxLogin = sessoes.get(id);
    return usuarios.get(auxLogin).getRecado();
  }

  /**
   * Salva os usuários cadastrados no arquivo "usuarios.ser" e encerra o sistema.
   */
  public void encerrarSistema() {
    try {
      FileOutputStream fileOut = new FileOutputStream("usuarios.ser");
      ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
      objOut.writeObject(usuarios);
      objOut.close();
      fileOut.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Ocorreu um erro ao salvar os usuários.");
    }
  }

  /**
   * Retorna uma representação textual da Facade.
   *
   * @return String com informações dos usuários e sessões ativas.
   */
  @Override
  public String toString() {
    return "Facade{" +
            "usuarios=" + usuarios.keySet() +
            ", sessoes=" + sessoes.keySet() +
            '}';
  }
}
