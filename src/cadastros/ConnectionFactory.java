package cadastros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public static Connection criarConexao() throws SQLException, ClassNotFoundException{
		
		 String stringDeConexao = "jdbc:mysql://localhost:3306/tcc"; 
		 String usuario = "root"; 
		 String senha = "root"; 

		 System.out.println("Abrindo conexão..."); 
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection conexao = 
				 DriverManager.getConnection(stringDeConexao , usuario , senha);
		 return conexao;
	}

}
