package cadastros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

import dominio.Livro;

@ManagedBean
public class ListaLivros {
	
	public List<Livro> getLivrosCadastrados() throws SQLException, ClassNotFoundException {
		Connection conexao = ConnectionFactory.criarConexao();

		String sql = "SELECT * FROM livro;";

		PreparedStatement comando = conexao.prepareStatement(sql);

		System.out.println("Executando comando...");
		ResultSet resultado = comando.executeQuery();

		List<Livro> lista = new ArrayList<Livro>();
		while (resultado.next()) {
			
			Livro l = new Livro();			
			
			l.setNome(resultado.getString("nome"));
			l.setDescricao(resultado.getString("descricao"));
			l.setId(resultado.getInt("id_livro"));
			lista.add(l);
		}

		System.out.println("\nFechando conexão...");
		conexao.close();

		return lista;
	}
}
