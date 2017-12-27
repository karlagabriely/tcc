package editar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import Dao.Database;
import dominio.Livro;

/**Classe de requisitos necessários para cadastrar e/ou editar um livro.
 * @author karla
 * @version 1.0
 * @since Release 01 da aplicação
 */

@ManagedBean
@ViewScoped
public class Editar_Livro {
	private String editar_Livro;
	private List<Livro> livros = new ArrayList();
	private Livro livro = new Livro();
	
	 /**Método para pesquisa um novo livro
     * @author Karla       
      */
	public void pesquisarLivro() {
		EntityManager em = Database.getInstance().getEntityManager();
		
		Query q = em.createQuery("SELECT x FROM Livro x WHERE x.nome LIKE :nome OR x.descricao LIKE :descricao OR x.autor LIKE :autor");
		q.setParameter("nome", "%"+editar_Livro+"%");
		q.setParameter("descricao", "%"+editar_Livro+"%");
		q.setParameter("autor", "%"+editar_Livro+"%");
		
		livros = (List<Livro>) q.getResultList();
		em.close();
	}
	
	
	
	
}
