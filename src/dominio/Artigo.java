package dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Artigo {
	
	@Id
    @GeneratedValue  
	@Column(nullable = false)
	private int id;
	
    @GeneratedValue  
	@Column(nullable = false)
	private String titulo;
	
    @GeneratedValue  
	@Column(nullable = false)
	private String autor;
    
    @GeneratedValue  
	@Column(nullable = false)
	private String resumo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
    

}
