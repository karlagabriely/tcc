package cadastros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import arquitetura.CriptografiaUtils;


public class InserirUsuario {
	
	public void inserirUsuario(String nome, String senhaUsuario, String cpf, String celular, String email, String matricula, 
			String cidade, String RG, Date data_nasc, String conta, String agencia, String operacao)
			throws SQLException, ClassNotFoundException{
		 
		String stringDeConexao = "jdbc:mysql://localhost:3306/tcc"; 
		 String usuario = "root"; 
		 String senha = "root";

		 System.out.println("Abrindo conexão..."); 
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection conexao = 
		
				 DriverManager.getConnection(stringDeConexao, usuario, senha);
		 
		 
		 String sql = "INSERT INTO usuario (nome, senha, cpf, celular, email, matricula, cidade, RG, data_nasc, conta, agencia, operacao, administrador) " + 
				 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false)"; 
		
		 PreparedStatement comando = conexao.prepareStatement(sql); 
		 comando.setString(1, nome);
		 comando.setString(2, CriptografiaUtils.criptografarMD5(senhaUsuario));
		 comando.setString(3, cpf);
		 comando.setString(4, celular);
		 comando.setString(5, email);
		 comando.setString(6, matricula);
		 comando.setString(7, cidade);
		 comando.setString(8, RG);
		 comando.setDate(9, new java.sql.Date(data_nasc.getTime()));
		 comando.setString(10, conta);
		 comando.setString(11, agencia);
		 comando.setString(12, operacao);
		 comando.execute(); 
		 
		 comando.close();
		 conexao.close(); 
		
	}

}
