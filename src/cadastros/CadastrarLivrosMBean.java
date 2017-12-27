package cadastros;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import Dao.Database;
import arquitetura.AbstractController;
import dominio.Arquivo;
import dominio.Livro;
import util.ValidatorUtil;


@ManagedBean
@SessionScoped
public class CadastrarLivrosMBean extends AbstractController {

	private Livro livro = new Livro();
	
	/** Tamanho máximo para upload, em bytes. */
	private final Integer TAM_MAXIMO_ARQUIVO = 2097152;
	
	/** Formatos permitidos para o envio da foto do usuário. */
	private final String[] FORMATOS_PERMITIDOS = {"pdf"};

	public String cadastrarLivro() {
		
		boolean validou = validarArquivoCadastro();
		
		if (!validou){
			return null;
		}
			
		//Se o usuário anexou foto
		if (livro.getArquivo() != null && ValidatorUtil.isNotEmpty(livro.getArquivo().getFileName())
				&& livro.getArquivo().getSize() != -1){
			
			//Cria nova entidade arquivo
			Arquivo arq = new Arquivo();
			arq.setNome(livro.getArquivo().getFileName());
			arq.setBytes(livro.getArquivo().getContents());
			
			//Cadastrando a foto no banco
			
			EntityManager em = Database.getInstance().getEntityManager();
			
			try {
				//Iniciando transação com o banco de dados
				em.getTransaction().begin();
				
				em.persist(arq);
				
				//Transação confirmada
				em.getTransaction().commit();
				
				
			} catch (Exception e){
				e.printStackTrace();
				
				if (em.getTransaction().isActive())
					//Como ocorreu erro, a transação não será confirmada
					em.getTransaction().rollback();
			}
			
			/*Informa o ID da foto do usuário, para que possa ser carregada posteriormente
			(como a imagem já foi cadastrada, ela já possui ID) */
			livro.setIdArquivo(arq.getId());
		}

		
		
		
		
		// Cadastrando/editando usuário
		EntityManager em = Database.getInstance().getEntityManager();

		try {
			// Iniciando transação com o banco de dados
			em.getTransaction().begin();

			if (livro.getId() == 0) {
				em.persist(livro); // Cadastro
			} else {
				em.merge(livro); // Edição
			}

			// Transação confirmada
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

			if (em.getTransaction().isActive())
				// Como ocorreu erro, a transação não será confirmada
				em.getTransaction().rollback();
		}

		addMsgInfo("Cadastro realizado com sucesso!");

		return "coordenadores.xhtml";

	}

	public String entrarEdicaoLivros(Livro livro) {
		this.livro = livro;
		return "cad_livros.xhtml";
	}
	
	/** Verifica se a imagem anexada do usuário está de acordo com o esperado */
	private boolean validarArquivoCadastro(){
		if (livro.getArquivo() != null && ValidatorUtil.isNotEmpty(livro.getArquivo().getFileName())
				&& livro.getArquivo().getSize() != -1){
			//Verificando extensão
			
			String[] nomes = livro.getArquivo().getFileName().split("\\.");
			String extensao = nomes[nomes.length - 1].toLowerCase();
			
			List<String> formatos = Arrays.asList(FORMATOS_PERMITIDOS);
			
			if (!formatos.contains(extensao)){
				String msg = "O formato de arquivo que você anexou não é suportado. Por favor, ";
				msg += "envie em um dos seguintes formatos: png ou jpg.";
				
				addMsgError(msg);
				return false;
			} 
			
			//Verificando tamanho arquivo
			if (livro.getArquivo().getSize() > TAM_MAXIMO_ARQUIVO) {
				addMsgError("O tamanho máximo para upload de arquivo é de 2 MB.");
				return false;
			}
		}
		
		return true;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

}
