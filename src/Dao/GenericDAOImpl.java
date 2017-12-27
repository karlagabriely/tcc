package Dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import dominio.ObjetoPersistivel;
import util.ValidatorUtil;

/**
 * Esta classe deve ser implementada por todos os DAOs do sistema.
 * Ela contém métodos muito úteis para acesso ao banco de dados,
 * incluindo métodos prontos para acesso a informações de quaisquer
 * tabelas.
 *  
 * @author Renan
 */
public class GenericDAOImpl implements IGenericDAO {
	
	/** Define a operação atual (insert, update ou delete) */
	
	private void change(ObjetoPersistivel c, OperacaoDatabase op){
		switch (op) {
			case INSERIR:
				getEm().persist(c);
				break;
			case ALTERAR:
				getEm().merge(c);
				break;
			case REMOVER:
				getEm().remove(c);
				break;
		}
	}

	/** Obtém o EntityManager para poder fazer operações no banco. */
	@Override
	public EntityManager getEm() {
		return Database.getInstance().getEntityManager();
	}
	
	/** 
	 * Este método pode ser chamado quando for preciso salvar um novo registro
	 * no banco de dados (insert). Funciona para qualquer entidade.
	 * */
	@Override
	public void create(ObjetoPersistivel c){
		change(c, OperacaoDatabase.INSERIR);
	}

	/** 
	 * Este método pode ser chamado quando for preciso atualizar (update) um registro
	 * no banco de dados. Funciona para qualquer entidade.
	 * */
	@Override
	public void update(ObjetoPersistivel c){
		change(c, OperacaoDatabase.ALTERAR);
	}
	
	/** 
	 * Cria ou atualiza um registro do banco, dependendo se ele
	 * já existe ou não. 
	 * */
	@Override
	public void createOrUpdate(ObjetoPersistivel c){
		if (c.getId() == 0)
			change(c, OperacaoDatabase.INSERIR);
		else
			change(c, OperacaoDatabase.ALTERAR);
	}
	
	/**
	 * Remove um registro do banco de dados (delete). Funciona
	 * para qualquer entidade.
	 */
	@Override
	public void delete(ObjetoPersistivel c){
		change(c, OperacaoDatabase.REMOVER);
	}
	
	/** Força a sincronização de suas entidades com o banco de dados. */
	@Override
	public void flush(){
		getEm().flush();
	}
	
	/** Obtém qualquer entidade através do ID dela. */
	@Override
	public <T> T findByPrimaryKey(int id, Class<T> classe){
		EntityManager em = getEm();
		T c = em.find(classe, id);
		return c;
	}
	
	/** Obtém todos os registros de uma tabela no banco de dados. Basta informar o .class da entidade. */
	@Override
	public <T extends ObjetoPersistivel> List<T> findAll(Class<T> classe){
		EntityManager em = getEm();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(classe);
		TypedQuery<T> typedQuery = em.createQuery(query.select(query.from(classe)));
		List<T> c = typedQuery.getResultList();
		return c;
	}
	
	/** Retorna todos os registros de uma entidade que estão ativos, ou seja, que
	 * possuem a coluna "ativo" igual a true. */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ObjetoPersistivel> List<T> findAllAtivos(Class<T> classe) {
		String tabela = classe.getSimpleName();
		String jpql = "from "+tabela+ " where ativo = :ativo ";
		EntityManager em = getEm();
		Query q = em.createQuery(jpql);
		q.setParameter("ativo", true);
		List<T> retorno = q.getResultList();
		return retorno;
	}
	
	/** Retorna todos os registros de uma entidade que estão ativos, ou seja, que
	 * possuem a coluna "ativo" igual a true. Adiciona possibilidade de ordenação
	 * dos resultados. */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ObjetoPersistivel> List<T> findAllAtivos(Class<T> classe, String orderBy) {
		String tabela = classe.getSimpleName();
		String jpql = "from "+tabela+ " where ativo = :ativo order by " + orderBy;
		EntityManager em = getEm();
		Query q = em.createQuery(jpql);
		q.setParameter("ativo", true);
		
		List<T> retorno = q.getResultList();
		return retorno;
	}
	
	/** 
	 * Retorna os registros de qualquer entidade do banco que possuam
	 * um determinado valor de coluna. Não precisa ser valor exato. */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ObjetoPersistivel> List<T> findAllLike(String coluna,String valor, String orderby, Class<T> classe){
		String tabela = classe.getSimpleName();
		String jpql = "from "+tabela+ " where upper("+coluna+") like upper(:valor)";
		
		if (ValidatorUtil.isNotEmpty(orderby)){
			jpql += " order by " + orderby;
		}
		
		EntityManager em = getEm();
		Query q = em.createQuery(jpql);
		q.setParameter("valor", "%"+valor+"%");
		List<T> retorno = q.getResultList();
		return retorno;
	}

	/** Retorna todos os registros de uma entidade que possuam um valor exato de coluna. */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ObjetoPersistivel> List<T> findByExactField(String coluna, Object valor, Class<T> classe) {
		String tabela = classe.getSimpleName();
		String jpql = "from "+tabela+ " where "+coluna+" = :valor";
		EntityManager em = getEm();
		Query q = em.createQuery(jpql);
		q.setParameter("valor", valor);
		List<T> retorno = q.getResultList();
		return retorno;
	}
	
	/** Retorna todos os registros de uma entidade que possuam um valor exato de coluna. */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ObjetoPersistivel> List<T> findByExactField(String coluna, Object valor, String orderBy, Class<T> classe) {
		String tabela = classe.getSimpleName();
		String jpql = "from "+tabela+ " where "+coluna+" = :valor";
		
		if (ValidatorUtil.isNotEmpty(orderBy)){
			jpql += " order by " + orderBy;
		}
		
		EntityManager em = getEm();
		Query q = em.createQuery(jpql);
		q.setParameter("valor", valor);
		List<T> retorno = q.getResultList();
		return retorno;
	}
	
	/** Retorna todos os registros de uma entidade que possuam valores específicos de algumas colunas. */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ObjetoPersistivel> List<T> findByExactFields(String[] colunas, Object[] valores, Class<T> classe) {
		String tabela = classe.getSimpleName();
		String jpql = "from "+tabela+ " where 1=1 ";
		
		for (int i = 0; i < colunas.length; i++){
			String coluna = colunas[i];
			jpql += " and " + coluna + " = :valor" + i + " ";
		}
		
		EntityManager em = getEm();
		Query q = em.createQuery(jpql);
		
		for (int i = 0; i < valores.length; i++){
			q.setParameter("valor"+i, valores[i]);
		}
		
		List<T> retorno = q.getResultList();
		return retorno;
	}
	
	/** Retorna todos os registros de uma entidade que possuam valores específicos de algumas colunas. */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ObjetoPersistivel> T findByExactFields(String[] colunas, Object[] valores, 
			boolean limit, Class<T> classe) {
		String tabela = classe.getSimpleName();
		String jpql = "from "+tabela+ " where 1=1 ";
		
		for (int i = 0; i < colunas.length; i++){
			String coluna = colunas[i];
			jpql += " and " + coluna + " = :valor" + i + " ";
		}
		
		EntityManager em = getEm();
		Query q = em.createQuery(jpql);
		
		if (limit)
			q.setMaxResults(1);
		
		for (int i = 0; i < valores.length; i++){
			q.setParameter("valor"+i, valores[i]);
		}
		
		List<T> results = q.getResultList();
		
		return ValidatorUtil.isNotEmpty(results) ? results.get(0) : null;
	}
	
	/** Atualiza uma coluna de uma tabela de qualquer entidade. */
	@Override
	public <T extends ObjetoPersistivel> void updateField(Class<T> classe, int id, String coluna, Object valor) {
		String tabela = classe.getSimpleName();
		String jpql = "update "+tabela+ " set " + coluna + " = :valor where id = :id ";
		EntityManager em = getEm();
		Query q = em.createQuery(jpql);
		q.setParameter("valor", valor);
		q.setParameter("id", id);
		q.executeUpdate();
	}

	/** 
	 * Limpa o cache do Hibernate. Força o recarregamento de todas as entidades, quando necessário.
	 * Todas as alterações não salvas serão perdidas. */
	@Override
	public void clear() {
		getEm().clear();	
	}
	
	/** Limpa uma determinada entidade da memória do Hibernate. */
	@Override
	public void detach(ObjetoPersistivel p) {
		getEm().detach(p);
	}
	
	/** Atualiza as informações de um determinado registro (objeto) de entidade.
	 * Sincroniza com o banco. */
	@Override
	public void refresh(ObjetoPersistivel p) {
		getEm().refresh(p);
	}
	
	/** Realiza um update através de um SQL simples. */
	@Override
	public void update(String sql) {
		Session session = (Session) Database.getInstance().getEntityManager().getDelegate();
		SQLQuery q = session.createSQLQuery(sql);
		q.executeUpdate();
	}

}

enum OperacaoDatabase {
	INSERIR,ALTERAR,REMOVER;
}

