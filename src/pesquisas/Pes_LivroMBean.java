package pesquisas;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import Dao.Database;
import Dao.UsuarioDAO;
import arquitetura.CriptografiaUtils;
import dominio.Arquivo;
import dominio.Artigo;
import dominio.Livro;

@ManagedBean
@SessionScoped
public class Pes_LivroMBean {
	private String pes_Livro;
	private List<Livro> livro = new ArrayList();

	public void pesquisarLivro() {
		EntityManager em = Database.getInstance().getEntityManager();

		Query q = em.createQuery(
				"SELECT x FROM Livro x WHERE x.nome LIKE :nome OR x.descricao LIKE :descricao OR x.autor LIKE :autor");
		q.setParameter("nome", "%" + pes_Livro + "%");
		q.setParameter("descricao", "%" + pes_Livro + "%");
		q.setParameter("autor", "%" + pes_Livro + "%");

		livro = (List<Livro>) q.getResultList();
		em.close();
	}

	public void removerLivro(Livro l) {
		EntityManager em = Database.getInstance().getEntityManager();

		try {
			em.getTransaction().begin();

			Livro livroParaRemover = em.find(Livro.class, l.getId());
			em.remove(livroParaRemover);

			em.getTransaction().commit();

			pesquisarLivro();

		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
		}
	}

	public void urlLivro(Livro l) {
		UsuarioDAO dao = new UsuarioDAO();
		Arquivo arq = dao.findByPrimaryKey(l.getId(), Arquivo.class);
		
		HttpServletResponse response;
		try {
			response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/download");
			response.addHeader("Content-Disposition", "attachment; filename=" + arq.getNome());
			//response.setContentLength(arq.length);
			response.getOutputStream().write(arq.getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPes_Livro() {
		return pes_Livro;
	}

	public void setPes_Livro(String pes_Livro) {
		this.pes_Livro = pes_Livro;
	}

	public List<Livro> getLivro() {
		return livro;
	}

	public void setLivro(List<Livro> livro) {
		this.livro = livro;
	}

}
