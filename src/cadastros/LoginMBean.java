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

	/** Autentica o usu�rio e faz login no sistema. */
	public String autenticar() {
		if (!validarLogin()) {
			return null;
		}

		try {
			UsuarioDAO dao = new UsuarioDAO();
			usuario = dao.findUsuarioByLoginSenha(usuario.getEmail(),
					CriptografiaUtils.criptografarMD5(usuario.getSenha()));

			// inserir verifica��o de usu�rio ativo/inativo aqui
			if (ValidatorUtil.isEmpty(usuario)) {
				init();
				addMsgError("Usu�rio/Senha incorretos.");
				return null;
			}

			// Salva o usu�rio permanentemente na mem�ria do sistema
			getCurrentSession().setAttribute("usuarioLogado", usuario);
			return "/restrito_membros.xhtml";

		} catch (Exception e) {
			tratamentoErroPadrao(e);
			return null;
		}
	}

	/** Verifica se os dados para login est�o corretos */
	public boolean validarLogin() {
		if (usuario == null
				|| (ValidatorUtil.isEmpty(usuario.getEmail()) && ValidatorUtil.isEmpty(usuario.getSenha()))) {
			addMsgError("Usu�rio/senha n�o informados.");
			return false;
		}

		if (ValidatorUtil.isEmpty(usuario.getEmail())) {
			addMsgError("Usu�rio: campo obrigat�rio n�o informado.");
			return false;
		}

		if (ValidatorUtil.isEmpty(usuario.getSenha())) {
			addMsgError("Senha: campo obrigat�rio n�o informado.");
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
