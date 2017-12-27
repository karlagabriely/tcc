package cadastros;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import Dao.Database;
import arquitetura.AbstractController;
import dominio.Evento;

@ManagedBean
public class CadastrarEventoMBean extends AbstractController {
	
	private Evento evento = new Evento(); 
	
	public String cadastrarEvento() {

		// Cadastrando/editando usuário
		EntityManager em = Database.getInstance().getEntityManager();

		try {
			// Iniciando transação com o banco de dados
			em.getTransaction().begin();

			if (evento.getId() == 0) {
				em.persist(evento); // Cadastro
			} else {
				em.merge(evento); // Edição
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

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	

}
