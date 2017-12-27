package cadastros;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

import dominio.Usuario;

/**Classe para cadastrar um novo usuário
 * @author karla
 * @version 1.0
 * @since Release 01 da aplicação
 */
@ManagedBean
public class Cadastrar {
	
	private Usuario aluno = new Usuario();
	
	 /**Esse Método ele cadastra um novo usuário.
     * @author karla
      */
	public  String cadastrarUsuario() throws SQLException, ClassNotFoundException{
		InserirUsuario i = new InserirUsuario();
		
		i.inserirUsuario(aluno.getNome(), aluno.getSenha(), aluno.getCpf(), aluno.getCelular(), aluno.getEmail(),
				aluno.getMatricula(), aluno.getCidade(), aluno.getRg(), aluno.getData_nasc(), aluno.getConta(),  aluno.getAgencia(),
				aluno.getOperacao());
		
		aluno = new Usuario();
		
		return null;
	}

	public Usuario getAluno() {
		return aluno;
	}

	public void setAluno(Usuario aluno) {
		this.aluno = aluno;
	}
	
}
