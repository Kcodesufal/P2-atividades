package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;

/**
 * Classe Facade, que serve de fachada para sistema.
 * <p>
 * Esta classe implementa o padrão Facade, fornecendo uma interface simplificada
 * para todas as operações do sistema Jackut, delegando as chamadas para a classe
 * Sistema que contém a implementação real.
 * </p>
 * <p>
 * A Facade serve como um ponto único de acesso para o sistema, escondendo a complexidade
 * das operações e fornecendo uma API mais limpa para os clientes.
 * </p>
 */
public class Facade {
    private Sistema sistema;

    /**
     * Constrói uma nova Facade inicializando o sistema Jackut.
     * <p>
     * O construtor inicializa uma nova instância do Sistema, que carrega
     * os dados persistentes dos arquivos "usuarios.ser" e "comunidades.ser".
     * </p>
     */
    public Facade() {
        this.sistema = new Sistema();
    }

    /**
     * Remove todos os usuários e comunidades cadastradas, zerando o sistema.
     */
    public void zerarSistema() {
        sistema.zerarSistema();
    }

    /**
     * Cria um novo usuário na plataforma.
     *
     * @param login Login do usuário (deve ser único)
     * @param senha Senha do usuário
     * @param nome  Nome completo do usuário
     * @throws InformacaoInvalidaException Se alguma informação for inválida
     * @throws UsuarioJaExistenteException Se o login já estiver cadastrado
     * @throws ConflitoInformacaoException Se a senha for igual ao nome ou login
     */
    public void criarUsuario(String login, String senha, String nome) {
        sistema.criarUsuario(login, senha, nome);
    }

    /**
     * Obtém um atributo específico de um usuário.
     *
     * @param login    Login do usuário
     * @param atributo Nome do atributo a ser recuperado
     * @return Valor do atributo solicitado
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado
     */
    public String getAtributoUsuario(String login, String atributo) {
        return sistema.getAtributoUsuario(login, atributo);
    }

    /**
     * Autentica um usuário e abre uma nova sessão.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @return ID da sessão criada
     * @throws InformacaoInvalidaException Se o login ou senha forem inválidos
     */
    public String abrirSessao(String login, String senha) {
        return sistema.abrirSessao(login, senha);
    }

    /**
     * Edita um atributo do perfil do usuário autenticado.
     *
     * @param id       ID da sessão do usuário
     * @param atributo Nome do atributo a ser modificado
     * @param valor    Novo valor para o atributo
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public void editarPerfil(String id, String atributo, String valor) {
        sistema.editarPerfil(id, atributo, valor);
    }

    /**
     * Adiciona um amigo à lista de amizades do usuário autenticado.
     *
     * @param id    ID da sessão do usuário
     * @param amigo Login do usuário a ser adicionado como amigo
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     * @throws ConflitoInformacaoException   Se tentar adicionar a si mesmo
     * @throws InimigoException Se os usuários forem inimigos
     */
    public void adicionarAmigo(String id, String amigo) {
        sistema.adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login Login do primeiro usuário
     * @param amigo Login do segundo usuário
     * @return true se forem amigos, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     */
    public boolean ehAmigo(String login, String amigo) {
        return sistema.ehAmigo(login, amigo);
    }

    /**
     * Obtém a lista de amigos de um usuário.
     *
     * @param login Login do usuário
     * @return String formatada com a lista de amigos no formato "{amigo1,amigo2,...}"
     * @throws UsuarioNaoCadastradoException Se o usuário não existir
     */
    public String getAmigos(String login) {
        return sistema.getAmigos(login);
    }

    /**
     * Envia um recado para outro usuário.
     *
     * @param id           ID da sessão do remetente
     * @param destinatario Login do destinatário
     * @param mensagem     Conteúdo do recado
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     * @throws ConflitoInformacaoException   Se tentar enviar recado para si mesmo
     * @throws InimigoException Se os usuários forem inimigos
     */
    public void enviarRecado(String id, String destinatario, String mensagem) {
        sistema.enviarRecado(id, destinatario, mensagem);
    }

    /**
     * Lê o próximo recado não lido do usuário autenticado.
     *
     * @param id ID da sessão do usuário
     * @return Conteúdo do recado
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public String lerRecado(String id) {
        return sistema.lerRecado(id);
    }

    /**
     * Cria uma nova comunidade.
     *
     * @param id        ID da sessão do criador
     * @param nome      Nome da comunidade (deve ser único)
     * @param descricao Descrição da comunidade
     * @throws UsuarioNaoCadastradoException  Se a sessão for inválida
     * @throws ComunidadeJaExistenteException Se o nome da comunidade já existir
     */
    public void criarComunidade(String id, String nome, String descricao) {
        sistema.criarComunidade(id, nome, descricao);
    }

    /**
     * Obtém a descrição de uma comunidade.
     *
     * @param nomecomunidade Nome da comunidade
     * @return Descrição da comunidade
     * @throws ComunidadeNaoExistenteException Se a comunidade não existir
     */
    public String getDescricaoComunidade(String nomecomunidade) {
        return sistema.getDescricaoComunidade(nomecomunidade);
    }

    /**
     * Obtém o dono de uma comunidade.
     *
     * @param nomecomunidade Nome da comunidade
     * @return Login do dono da comunidade
     * @throws ComunidadeNaoExistenteException Se a comunidade não existir
     */
    public String getDonoComunidade(String nomecomunidade) {
        return sistema.getDonoComunidade(nomecomunidade);
    }

    /**
     * Obtém a lista de membros de uma comunidade.
     *
     * @param nomecomunidade Nome da comunidade
     * @return String formatada com a lista de membros no formato "{membro1,membro2,...}"
     * @throws ComunidadeNaoExistenteException Se a comunidade não existir
     */
    public String getMembrosComunidade(String nomecomunidade) {
        return sistema.getMembrosComunidade(nomecomunidade);
    }

    /**
     * Obtém a lista de comunidades que um usuário participa.
     *
     * @param login Login do usuário
     * @return String formatada com a lista de comunidades no formato "{comunidade1,comunidade2,...}"
     * @throws UsuarioNaoCadastradoException Se o usuário não existir
     */
    public String getComunidades(String login) {
        return sistema.getComunidades(login);
    }

    /**
     * Adiciona o usuário autenticado a uma comunidade.
     *
     * @param id   ID da sessão do usuário
     * @param nome Nome da comunidade
     * @throws UsuarioNaoCadastradoException   Se a sessão for inválida
     * @throws ComunidadeNaoExistenteException Se a comunidade não existir
     */
    public void adicionarComunidade(String id, String nome) {
        sistema.adicionarComunidade(id, nome);
    }

    /**
     * Lê a próxima mensagem não lida do usuário autenticado.
     *
     * @param id ID da sessão do usuário
     * @return Conteúdo da mensagem
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public String lerMensagem(String id) {
        return sistema.lerMensagem(id);
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
        sistema.enviarMensagem(id, comunidade, mensagem);
    }

    /**
     * Verifica se um usuário é fã de outro.
     *
     * @param login Login do possível fã
     * @param idolo Login do possível ídolo
     * @return true se for fã, false caso contrário
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     */
    public boolean ehFa(String login, String idolo) {
        return sistema.ehFa(login, idolo);
    }

    /**
     * Adiciona um ídolo à lista do usuário autenticado e se adiciona como fã do ídolo.
     *
     * @param id    ID da sessão do usuário
     * @param idolo Login do ídolo
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     * @throws ConflitoInformacaoException   Se tentar adicionar a si mesmo como ídolo
     * @throws InimigoException Se os usuários forem inimigos
     */
    public void adicionarIdolo(String id, String idolo) {
        sistema.adicionarIdolo(id, idolo);
    }

    /**
     * Obtém a lista de fãs de um usuário.
     *
     * @param login Login do usuário
     * @return String formatada com a lista de fãs no formato "{fa1,fa2,...}"
     * @throws UsuarioNaoCadastradoException Se o usuário não existir
     */
    public String getFas(String login) {
        return sistema.getFas(login);
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
        return sistema.ehPaquera(id, nome);
    }

    /**
     * Adiciona um paquera à lista do usuário autenticado.
     * Se for um paquera mútuo, envia recados especiais para ambos.
     *
     * @param id   ID da sessão do usuário
     * @param nome Login do paquera
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     * @throws InimigoException Se os usuários forem inimigos
     */
    public void adicionarPaquera(String id, String nome) {
        sistema.adicionarPaquera(id, nome);
    }

    /**
     * Obtém a lista de paqueras do usuário autenticado.
     *
     * @param id ID da sessão do usuário
     * @return String formatada com a lista de paqueras no formato "{paquera1,paquera2,...}"
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public String getPaqueras(String id) {
        return sistema.getPaqueras(id);
    }

    /**
     * Adiciona um inimigo à lista do usuário autenticado.
     *
     * @param id      ID da sessão do usuário
     * @param inimigo Login do inimigo
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não existir
     */
    public void adicionarInimigo(String id, String inimigo) {
        sistema.adicionarInimigo(id, inimigo);
    }

    /**
     * Remove permanentemente o usuário autenticado do sistema.
     * <p>
     * Remove todas as referências ao usuário em amigos, comunidades, etc.
     * </p>
     *
     * @param id ID da sessão do usuário a ser removido
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida
     */
    public void removerUsuario(String id) {
        sistema.removerUsuario(id);
    }

    /**
     * Salva os dados do sistema nos arquivos de persistência e encerra o sistema.
     * <p>
     * Os dados são salvos nos arquivos "usuarios.ser" e "comunidades.ser".
     * </p>
     */
    public void encerrarSistema() {
        sistema.encerrarSistema();
    }

    /**
     * Retorna uma representação textual do estado atual do sistema.
     *
     * @return String contendo informações sobre usuários, sessões e comunidades
     */
    @Override
    public String toString() {
        return sistema.toString();
    }
}