package cadastros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**Classe para inserir um novo livro
 * @author alice
 * @version 1.0
 * @since Release 01 da aplicação
 */
public class InserirLivros {
	
	 /**Método para inserir um novo livro
     * @author alice         
     */
	public void inserirLivros(String nome, String descricao)
			throws SQLException, ClassNotFoundException{
		 
		String stringDeConexao = "jdbc:mysql://localhost:3306/tcc"; 
		 String usuario = "root"; 
		 String senha = "root";

		 System.out.println("Abrindo conexão..."); 
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection conexao = 
		
				 DriverManager.getConnection(stringDeConexao, usuario, senha);
		 
		 
		 String sql = "INSERT INTO livro (nome, descricao) " + 
				 "VALUES (?, ?)"; 
		
		 PreparedStatement comando = conexao.prepareStatement(sql);
		 
		 comando.setString(1, nome);
		 comando.setString(2, descricao);
		 
		 
		 comando.execute(); 
		 
		 comando.close();
		 conexao.close(); 
		
	}

}
