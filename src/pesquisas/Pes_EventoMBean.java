package pesquisas;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import Dao.Database;
import dominio.Evento;

@ManagedBean
@ViewScoped
public class Pes_EventoMBean {
	private String pes_Evento;
	private List<Evento> evento = new ArrayList();
	
	
	public void pesquisarEvento() {
		EntityManager em = Database.getInstance().getEntityManager();
		
		Query q = em.createQuery("SELECT x FROM Evento x WHERE x.nome LIKE :nome OR x.local LIKE :local");
		q.setParameter("nome", "%"+pes_Evento+"%");
		q.setParameter("local", "%"+pes_Evento+"%");
		
		evento = (List<Evento>) q.getResultList();
		em.close();
	}


	public String getPes_Evento() {
		return pes_Evento;
	}


	public void setPes_Evento(String pes_Evento) {
		this.pes_Evento = pes_Evento;
	}


	public List<Evento> getEvento() {
		return evento;
	}


	public void setEvento(List<Evento> evento) {
		this.evento = evento;
	}
	
}
