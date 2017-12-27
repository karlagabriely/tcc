package dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.primefaces.model.UploadedFile;

import arquitetura.CriptografiaUtils;

@Entity
public class Livro {

	@Id
    @GeneratedValue  
	@Column(nullable = false)
	private int id;
	
	@Column(nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private String autor;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false, name="id_arquivo")
	private int idArquivo;
	
	@Transient
	private UploadedFile arquivo;
	
	public int getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(int idArquivo) {
		this.idArquivo = idArquivo;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getNome() {
		return nome;
	}
	
	
}
