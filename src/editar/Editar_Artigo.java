package editar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import Dao.Database;
import dominio.Artigo;

@ManagedBean
@ViewScoped
public class Editar_Artigo {
	private String editar_Artigo;
	private List<Artigo> artigos = new ArrayList();
	private Artigo artigo = new Artigo();
	
	public void pesquisarArtigo() {
		EntityManager em = Database.getInstance().getEntityManager();
		
		Query q = em.createQuery("SELECT x FROM Artigo x WHERE x.resumo LIKE :resumo OR x.titulo LIKE :titulo OR x.autor LIKE :autor");
		q.setParameter("resumo", "%"+editar_Artigo+"%");
		q.setParameter("titulo", "%"+editar_Artigo+"%");
		q.setParameter("autor", "%"+editar_Artigo+"%");
		
		artigos = (List<Artigo>) q.getResultList();
		em.close();
	}
	
	
	
	
}
