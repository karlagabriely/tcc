package cadastros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import dominio.Usuario;

@ManagedBean
public class ListaUsuario {
	

	//usar esse método no XHTML, no atributo value do datatable do primefaces (sem o get)
	public List<Usuario> getUsuariosCadastrados() throws SQLException, ClassNotFoundException {
		Connection conexao = ConnectionFactory.criarConexao();

		String sql = "SELECT * FROM usuario;";

		PreparedStatement comando = conexao.prepareStatement(sql);

		System.out.println("Executando comando...");
		ResultSet resultado = comando.executeQuery();

		List<Usuario> lista = new ArrayList<Usuario>();
		while (resultado.next()) {
			Usuario a = new Usuario();
			
			a.setId(resultado.getLong("id_usuario"));
			a.setNome(resultado.getString("nome"));
			a.setMatricula(resultado.getString("matricula"));
			a.setCidade(resultado.getString("cidade"));
			a.setRg(resultado.getString("RG"));
			a.setCpf(resultado.getString("cpf"));
			a.setData_nasc(resultado.getDate("data_nasc"));
			a.setEmail(resultado.getString("email"));
			a.setCelular(resultado.getString("celular"));
			a.setConta(resultado.getString("conta"));
			a.setAgencia(resultado.getString("agencia"));
			a.setOperacao(resultado.getString("operacao"));
			a.setSenha(resultado.getString("senha"));
			lista.add(a);
		}

		System.out.println("\nFechando conexão...");
		conexao.close();

		return lista;
	}
}

