package cadastros;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import Dao.Database;
import arquitetura.AbstractController;
import dominio.Artigo;
import dominio.Usuario;

/**Classe de requisitos necess�rios para cadastrar um novo artigo.
 * @author alice
 * @version 1.0
 * @since Release 01 da aplica��o
 */

@ManagedBean
public class CadastrarArtigosMBean extends AbstractController {

	private Artigo artigo = new Artigo();

	 /**M�todo para cadastrar ou editar um artigo
     * @author alice         
     * @return String - retorna o endere�o d� p�gina onde ele ser� redirecionado.
      */
	public String cadastrarArtigo() {

		// Cadastrando/editando usu�rio
		EntityManager em = Database.getInstance().getEntityManager();

		try {
			// Iniciando transa��o com o banco de dados
			em.getTransaction().begin();

			if (artigo.getId() == 0) {
				em.persist(artigo); // Cadastro
			} else {
				em.merge(artigo); // Edi��o
			}

			// Transa��o confirmada
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

			if (em.getTransaction().isActive())
				// Como ocorreu erro, a transa��o n�o ser� confirmada
				em.getTransaction().rollback();
		}

		addMsgInfo("Cadastro realizado com sucesso!");

		return "coordenadores.xhtml";

	}
	
	 /**Esse M�todo ele chama a p�gina cadastrar artigo, para que o usu�rio cadastre um novo artigo.
     * @author Alice         
     * @return String que redimensiona para outra p�gina.
      */
	public String entrarEdicaoArtigos(Artigo artigo){
		this.artigo = artigo;
		return "cad_artigos.xhtml";
	}

	public Artigo getArtigo() {
		return artigo;
	}

	public void setArtigo(Artigo artigo) {
		this.artigo = artigo;
	}

	
}
