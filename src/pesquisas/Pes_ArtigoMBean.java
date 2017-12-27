package pesquisas;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import Dao.Database;
import dominio.Artigo;

@ManagedBean
@SessionScoped
public class Pes_ArtigoMBean {
	private String pes_Artigo;
	private List<Artigo> artigo = new ArrayList();
	
	
	public void pesquisarArtigo() {
		EntityManager em = Database.getInstance().getEntityManager();
		
		Query q = em.createQuery("SELECT x FROM Artigo x WHERE x.resumo LIKE :resumo OR x.titulo LIKE :titulo OR x.autor LIKE :autor");
		q.setParameter("resumo", "%"+pes_Artigo+"%");
		q.setParameter("titulo", "%"+pes_Artigo+"%");
		q.setParameter("autor", "%"+pes_Artigo+"%");
		
		artigo = (List<Artigo>) q.getResultList();
		em.close();
	}
	
	public void removerArtigo(Artigo a){
		EntityManager em = Database.getInstance().getEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Artigo artigoParaRemover = em.find(Artigo.class, a.getId());
			em.remove(artigoParaRemover);
			
			em.getTransaction().commit();
			
			pesquisarArtigo();
			
		} catch (Exception e){
			em.getTransaction().rollback();
			em.close();
		} 
	}


	public String getPes_Artigo() {
		return pes_Artigo;
	}


	public void setPes_Artigo(String pes_Artigo) {
		this.pes_Artigo = pes_Artigo;
	}


	public List<Artigo> getArtigo() {
		return artigo;
	}


	public void setArtigo(List<Artigo> artigo) {
		this.artigo = artigo;
	}
	
}
