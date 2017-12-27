package cadastros;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import Dao.UsuarioDAO;
import arquitetura.AbstractController;
import arquitetura.CriptografiaUtils;
import dominio.Usuario;
import util.ValidatorUtil;

@ManagedBean
public class LoginMBean extends AbstractController {

	/** Armazena os dados informados na tela de login. */
	private Usuario usuario;

	@PostConstruct
	private void init() {
		usuario = new Usuario();
	}

	/** Autentica o usuário e faz login no sistema. */
	public String autenticar() {
		if (!validarLogin()) {
			return null;
		}

		try {
			UsuarioDAO dao = new UsuarioDAO();
			usuario = dao.findUsuarioByLoginSenha(usuario.getEmail(),
					CriptografiaUtils.criptografarMD5(usuario.getSenha()));

			// inserir verificação de usuário ativo/inativo aqui
			if (ValidatorUtil.isEmpty(usuario)) {
				init();
				addMsgError("Usuário/Senha incorretos.");
				return null;
			}

			// Salva o usuário permanentemente na memória do sistema
			getCurrentSession().setAttribute("usuarioLogado", usuario);
			return "/restrito_membros.xhtml";

		} catch (Exception e) {
			tratamentoErroPadrao(e);
			return null;
		}
	}

	/** Verifica se os dados para login estão corretos */
	public boolean validarLogin() {
		if (usuario == null
				|| (ValidatorUtil.isEmpty(usuario.getEmail()) && ValidatorUtil.isEmpty(usuario.getSenha()))) {
			addMsgError("Usuário/senha não informados.");
			return false;
		}

		if (ValidatorUtil.isEmpty(usuario.getEmail())) {
			addMsgError("Usuário: campo obrigatório não informado.");
			return false;
		}

		if (ValidatorUtil.isEmpty(usuario.getSenha())) {
			addMsgError("Senha: campo obrigatório não informado.");
			return false;
		}

		return true;
	}

	/** Realiza logoff do sistema. */
	public String logoff() {
		getCurrentSession().invalidate();
		return "/publico/loginmembro.xhtml";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
