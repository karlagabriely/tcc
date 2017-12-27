package cadastros;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import Dao.Database;
import arquitetura.AbstractController;
import dominio.Artigo;
import dominio.Usuario;

/**Classe de requisitos necessários para cadastrar um novo artigo.
 * @author alice
 * @version 1.0
 * @since Release 01 da aplicação
 */

@ManagedBean
public class CadastrarArtigosMBean extends AbstractController {

	private Artigo artigo = new Artigo();

	 /**Método para cadastrar ou editar um artigo
     * @author alice         
     * @return String - retorna o endereço dá página onde ele será redirecionado.
      */
	public String cadastrarArtigo() {

		// Cadastrando/editando usuário
		EntityManager em = Database.getInstance().getEntityManager();

		try {
			// Iniciando transação com o banco de dados
			em.getTransaction().begin();

			if (artigo.getId() == 0) {
				em.persist(artigo); // Cadastro
			} else {
				em.merge(artigo); // Edição
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
	
	 /**Esse Método ele chama a página cadastrar artigo, para que o usuário cadastre um novo artigo.
     * @author Alice         
     * @return String que redimensiona para outra página.
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
