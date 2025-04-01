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
 * Facade representa o Sistema. Possui diversas fun��es de controle de sess�o e usu�rio.
 */
public class Facade {
  /**
   * Mapa de usu�rios cadastrados, onde a chave � o login do usu�rio.
   */
  private Map<String, Usuario> usuarios = new HashMap<>();
  /**
   * Mapa de sess�es ativas, onde a chave � o ID da sess�o e o valor � o login do usu�rio autenticado.
   */
  private Map<String, String> sessoes = new HashMap<>();

  /**
   * Construtor da classe. Carrega os usu�rios armazenados em "usuarios.ser".
   */
  public Facade() {
    try {
      FileInputStream fileIn = new FileInputStream("usuarios.ser");
      ObjectInputStream objIn = new ObjectInputStream(fileIn);
      usuarios = (HashMap<String, Usuario>) objIn.readObject();
      objIn.close();
      fileIn.close();
      System.out.println("Usu�rios carregados com sucesso.");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Ocorreu um erro ao carregar os usu�rios.");
    }
  }

  /**
   * Remove todos os usu�rios cadastrados, zerando o sistema.
   */
  public void zerarSistema() {
    usuarios.clear();
  }

  /**
   * Cria um novo usu�rio na plataforma.
   *
   * @param login Login do usu�rio.
   * @param senha Senha do usu�rio.
   * @param nome Nome do usu�rio.
   * @throws InformacaoInvalidaException Se alguma informa��o for inv�lida.
   * @throws UsuarioJaExistenteException Se o login j� estiver cadastrado.
   * @throws ConflitoInformacaoException Se a senha for igual ao nome ou login.
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
   * @param login Login do usu�rio.
   * @param atributo Nome do atributo a ser recuperado.
   * @return O valor do atributo solicitado.
   * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
   */
  public String getAtributoUsuario(String login, String atributo) {
    if (!usuarios.containsKey(login)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
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
   * Abre uma sess�o para um usu�rio autenticado.
   *
   * @param login Login do usu�rio.
   * @param senha Senha do usu�rio.
   * @return ID da sess�o aberta.
   * @throws InformacaoInvalidaException Se o login ou senha forem inv�lidos.
   */
  public String abrirSessao(String login, String senha) {
    if (!usuarios.containsKey(login) || senha == null || usuarios.get(login).comparaSenha(senha) == 0) {
      throw new InformacaoInvalidaException("Login ou senha inv�lidos.");
    }

    String id = System.currentTimeMillis() + "-" + login;
    sessoes.put(id, login);
    return id;
  }

  /**
   * Edita um atributo do perfil do usu�rio autenticado.
   *
   * @param id ID da sess�o.
   * @param atributo Nome do atributo a ser editado.
   * @param valor Novo valor do atributo.
   * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida.
   */
  public void editarPerfil(String id, String atributo, String valor) {
    if (!sessoes.containsKey(id)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }

    String auxLogin = sessoes.get(id);
    if (!usuarios.containsKey(auxLogin)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }

    Usuario user = usuarios.get(auxLogin);
    user.editarPerfil(atributo, valor);
  }

  /**
   * Adiciona um amigo � lista de amizades do usu�rio autenticado.
   *
   * @param id ID da sess�o do usu�rio autenticado.
   * @param amigo Login do usu�rio a ser adicionado.
   * @throws UsuarioNaoCadastradoException Se o usu�rio ou amigo n�o estiverem cadastrados.
   * @throws ConflitoInformacaoException Se tentar adicionar a si mesmo.
   */
  public void adicionarAmigo(String id, String amigo) {
    if (!sessoes.containsKey(id)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }
    if (!usuarios.containsKey(amigo)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }

    String auxLogin = sessoes.get(id);

    if (auxLogin.equals(amigo)) {
      throw new ConflitoInformacaoException("Usu�rio n�o pode adicionar a si mesmo como amigo.");
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
   * Verifica se dois usu�rios s�o amigos.
   *
   * @param login Login do primeiro usu�rio.
   * @param amigo Login do segundo usu�rio.
   * @return true se forem amigos, false caso contr�rio.
   * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o estiver cadastrado.
   */
  public boolean ehAmigo(String login, String amigo) {
    if (!usuarios.containsKey(login)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }
    if (!usuarios.containsKey(amigo)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }

    return usuarios.get(login).ehAmigo(amigo);
  }

  /**
   * Retorna a lista de amigos de um usu�rio.
   *
   * @param login Login do usu�rio.
   * @return Lista de amigos do usu�rio.
   */
  public String getAmigos(String login) {
    return usuarios.get(login).getAmigos();
  }

  /**
   * Envia um recado para outro usu�rio.
   *
   * @param id ID da sess�o do remetente.
   * @param destinatario Login do destinat�rio.
   * @param mensagem Conte�do do recado.
   * @throws UsuarioNaoCadastradoException Se remetente ou destinat�rio n�o estiverem cadastrados.
   * @throws ConflitoInformacaoException Se o usu�rio tentar enviar um recado para si mesmo.
   */
  public void enviarRecado(String id, String destinatario, String mensagem) {
    if (!sessoes.containsKey(id)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }

    String auxLogin = sessoes.get(id);

    if (!usuarios.containsKey(destinatario)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }

    Usuario user = usuarios.get(auxLogin);
    Usuario user2 = usuarios.get(destinatario);

    if (user == user2) {
      throw new ConflitoInformacaoException("Usu�rio n�o pode enviar recado para si mesmo.");
    }

    user2.adicionarRecado(mensagem,auxLogin);
  }

  /**
   * L� um recado do usu�rio autenticado.
   *
   * @param id ID da sess�o.
   * @return O recado recebido.
   * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida.
   */
  public String lerRecado(String id) {
    if (!sessoes.containsKey(id)) {
      throw new UsuarioNaoCadastradoException("Usu�rio n�o cadastrado.");
    }

    String auxLogin = sessoes.get(id);
    return usuarios.get(auxLogin).getRecado();
  }

  /**
   * Salva os usu�rios cadastrados no arquivo "usuarios.ser" e encerra o sistema.
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
      System.err.println("Ocorreu um erro ao salvar os usu�rios.");
    }
  }

  /**
   * Retorna uma representa��o textual da Facade.
   *
   * @return String com informa��es dos usu�rios e sess�es ativas.
   */
  @Override
  public String toString() {
    return "Facade{" +
            "usuarios=" + usuarios.keySet() +
            ", sessoes=" + sessoes.keySet() +
            '}';
  }
}
