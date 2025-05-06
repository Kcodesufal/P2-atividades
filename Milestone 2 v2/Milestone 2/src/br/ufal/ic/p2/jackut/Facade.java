package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;

/**
 * Classe Facade, que serve de fachada para sistema.
 * <p>
 * Esta classe implementa o padr�o Facade, fornecendo uma interface simplificada
 * para todas as opera��es do sistema Jackut, delegando as chamadas para a classe
 * Sistema que cont�m a implementa��o real.
 * </p>
 * <p>
 * A Facade serve como um ponto �nico de acesso para o sistema, escondendo a complexidade
 * das opera��es e fornecendo uma API mais limpa para os clientes.
 * </p>
 */
public class Facade {
    private Sistema sistema;

    /**
     * Constr�i uma nova Facade inicializando o sistema Jackut.
     * <p>
     * O construtor inicializa uma nova inst�ncia do Sistema, que carrega
     * os dados persistentes dos arquivos "usuarios.ser" e "comunidades.ser".
     * </p>
     */
    public Facade() {
        this.sistema = new Sistema();
    }

    /**
     * Remove todos os usu�rios e comunidades cadastradas, zerando o sistema.
     */
    public void zerarSistema() {
        sistema.zerarSistema();
    }

    /**
     * Cria um novo usu�rio na plataforma.
     *
     * @param login Login do usu�rio (deve ser �nico)
     * @param senha Senha do usu�rio
     * @param nome  Nome completo do usu�rio
     * @throws InformacaoInvalidaException Se alguma informa��o for inv�lida
     * @throws UsuarioJaExistenteException Se o login j� estiver cadastrado
     * @throws ConflitoInformacaoException Se a senha for igual ao nome ou login
     */
    public void criarUsuario(String login, String senha, String nome) {
        sistema.criarUsuario(login, senha, nome);
    }

    /**
     * Obt�m um atributo espec�fico de um usu�rio.
     *
     * @param login    Login do usu�rio
     * @param atributo Nome do atributo a ser recuperado
     * @return Valor do atributo solicitado
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado
     */
    public String getAtributoUsuario(String login, String atributo) {
        return sistema.getAtributoUsuario(login, atributo);
    }

    /**
     * Autentica um usu�rio e abre uma nova sess�o.
     *
     * @param login Login do usu�rio
     * @param senha Senha do usu�rio
     * @return ID da sess�o criada
     * @throws InformacaoInvalidaException Se o login ou senha forem inv�lidos
     */
    public String abrirSessao(String login, String senha) {
        return sistema.abrirSessao(login, senha);
    }

    /**
     * Edita um atributo do perfil do usu�rio autenticado.
     *
     * @param id       ID da sess�o do usu�rio
     * @param atributo Nome do atributo a ser modificado
     * @param valor    Novo valor para o atributo
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public void editarPerfil(String id, String atributo, String valor) {
        sistema.editarPerfil(id, atributo, valor);
    }

    /**
     * Adiciona um amigo � lista de amizades do usu�rio autenticado.
     *
     * @param id    ID da sess�o do usu�rio
     * @param amigo Login do usu�rio a ser adicionado como amigo
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     * @throws ConflitoInformacaoException   Se tentar adicionar a si mesmo
     * @throws InimigoException Se os usu�rios forem inimigos
     */
    public void adicionarAmigo(String id, String amigo) {
        sistema.adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param login Login do primeiro usu�rio
     * @param amigo Login do segundo usu�rio
     * @return true se forem amigos, false caso contr�rio
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     */
    public boolean ehAmigo(String login, String amigo) {
        return sistema.ehAmigo(login, amigo);
    }

    /**
     * Obt�m a lista de amigos de um usu�rio.
     *
     * @param login Login do usu�rio
     * @return String formatada com a lista de amigos no formato "{amigo1,amigo2,...}"
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o existir
     */
    public String getAmigos(String login) {
        return sistema.getAmigos(login);
    }

    /**
     * Envia um recado para outro usu�rio.
     *
     * @param id           ID da sess�o do remetente
     * @param destinatario Login do destinat�rio
     * @param mensagem     Conte�do do recado
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     * @throws ConflitoInformacaoException   Se tentar enviar recado para si mesmo
     * @throws InimigoException Se os usu�rios forem inimigos
     */
    public void enviarRecado(String id, String destinatario, String mensagem) {
        sistema.enviarRecado(id, destinatario, mensagem);
    }

    /**
     * L� o pr�ximo recado n�o lido do usu�rio autenticado.
     *
     * @param id ID da sess�o do usu�rio
     * @return Conte�do do recado
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public String lerRecado(String id) {
        return sistema.lerRecado(id);
    }

    /**
     * Cria uma nova comunidade.
     *
     * @param id        ID da sess�o do criador
     * @param nome      Nome da comunidade (deve ser �nico)
     * @param descricao Descri��o da comunidade
     * @throws UsuarioNaoCadastradoException  Se a sess�o for inv�lida
     * @throws ComunidadeJaExistenteException Se o nome da comunidade j� existir
     */
    public void criarComunidade(String id, String nome, String descricao) {
        sistema.criarComunidade(id, nome, descricao);
    }

    /**
     * Obt�m a descri��o de uma comunidade.
     *
     * @param nomecomunidade Nome da comunidade
     * @return Descri��o da comunidade
     * @throws ComunidadeNaoExistenteException Se a comunidade n�o existir
     */
    public String getDescricaoComunidade(String nomecomunidade) {
        return sistema.getDescricaoComunidade(nomecomunidade);
    }

    /**
     * Obt�m o dono de uma comunidade.
     *
     * @param nomecomunidade Nome da comunidade
     * @return Login do dono da comunidade
     * @throws ComunidadeNaoExistenteException Se a comunidade n�o existir
     */
    public String getDonoComunidade(String nomecomunidade) {
        return sistema.getDonoComunidade(nomecomunidade);
    }

    /**
     * Obt�m a lista de membros de uma comunidade.
     *
     * @param nomecomunidade Nome da comunidade
     * @return String formatada com a lista de membros no formato "{membro1,membro2,...}"
     * @throws ComunidadeNaoExistenteException Se a comunidade n�o existir
     */
    public String getMembrosComunidade(String nomecomunidade) {
        return sistema.getMembrosComunidade(nomecomunidade);
    }

    /**
     * Obt�m a lista de comunidades que um usu�rio participa.
     *
     * @param login Login do usu�rio
     * @return String formatada com a lista de comunidades no formato "{comunidade1,comunidade2,...}"
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o existir
     */
    public String getComunidades(String login) {
        return sistema.getComunidades(login);
    }

    /**
     * Adiciona o usu�rio autenticado a uma comunidade.
     *
     * @param id   ID da sess�o do usu�rio
     * @param nome Nome da comunidade
     * @throws UsuarioNaoCadastradoException   Se a sess�o for inv�lida
     * @throws ComunidadeNaoExistenteException Se a comunidade n�o existir
     */
    public void adicionarComunidade(String id, String nome) {
        sistema.adicionarComunidade(id, nome);
    }

    /**
     * L� a pr�xima mensagem n�o lida do usu�rio autenticado.
     *
     * @param id ID da sess�o do usu�rio
     * @return Conte�do da mensagem
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public String lerMensagem(String id) {
        return sistema.lerMensagem(id);
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
        sistema.enviarMensagem(id, comunidade, mensagem);
    }

    /**
     * Verifica se um usu�rio � f� de outro.
     *
     * @param login Login do poss�vel f�
     * @param idolo Login do poss�vel �dolo
     * @return true se for f�, false caso contr�rio
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     */
    public boolean ehFa(String login, String idolo) {
        return sistema.ehFa(login, idolo);
    }

    /**
     * Adiciona um �dolo � lista do usu�rio autenticado e se adiciona como f� do �dolo.
     *
     * @param id    ID da sess�o do usu�rio
     * @param idolo Login do �dolo
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     * @throws ConflitoInformacaoException   Se tentar adicionar a si mesmo como �dolo
     * @throws InimigoException Se os usu�rios forem inimigos
     */
    public void adicionarIdolo(String id, String idolo) {
        sistema.adicionarIdolo(id, idolo);
    }

    /**
     * Obt�m a lista de f�s de um usu�rio.
     *
     * @param login Login do usu�rio
     * @return String formatada com a lista de f�s no formato "{fa1,fa2,...}"
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o existir
     */
    public String getFas(String login) {
        return sistema.getFas(login);
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
        return sistema.ehPaquera(id, nome);
    }

    /**
     * Adiciona um paquera � lista do usu�rio autenticado.
     * Se for um paquera m�tuo, envia recados especiais para ambos.
     *
     * @param id   ID da sess�o do usu�rio
     * @param nome Login do paquera
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     * @throws InimigoException Se os usu�rios forem inimigos
     */
    public void adicionarPaquera(String id, String nome) {
        sistema.adicionarPaquera(id, nome);
    }

    /**
     * Obt�m a lista de paqueras do usu�rio autenticado.
     *
     * @param id ID da sess�o do usu�rio
     * @return String formatada com a lista de paqueras no formato "{paquera1,paquera2,...}"
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public String getPaqueras(String id) {
        return sistema.getPaqueras(id);
    }

    /**
     * Adiciona um inimigo � lista do usu�rio autenticado.
     *
     * @param id      ID da sess�o do usu�rio
     * @param inimigo Login do inimigo
     * @throws UsuarioNaoCadastradoException Se algum dos usu�rios n�o existir
     */
    public void adicionarInimigo(String id, String inimigo) {
        sistema.adicionarInimigo(id, inimigo);
    }

    /**
     * Remove permanentemente o usu�rio autenticado do sistema.
     * <p>
     * Remove todas as refer�ncias ao usu�rio em amigos, comunidades, etc.
     * </p>
     *
     * @param id ID da sess�o do usu�rio a ser removido
     * @throws UsuarioNaoCadastradoException Se a sess�o for inv�lida
     */
    public void removerUsuario(String id) {
        sistema.removerUsuario(id);
    }

    /**
     * Salva os dados do sistema nos arquivos de persist�ncia e encerra o sistema.
     * <p>
     * Os dados s�o salvos nos arquivos "usuarios.ser" e "comunidades.ser".
     * </p>
     */
    public void encerrarSistema() {
        sistema.encerrarSistema();
    }

    /**
     * Retorna uma representa��o textual do estado atual do sistema.
     *
     * @return String contendo informa��es sobre usu�rios, sess�es e comunidades
     */
    @Override
    public String toString() {
        return sistema.toString();
    }
}