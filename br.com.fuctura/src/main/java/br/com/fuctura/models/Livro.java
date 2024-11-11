package br.com.fuctura.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries(@NamedQuery(name = "Livro.consultarPorTitulo",
	query = "select l from Livro l where l.titulo =:titulo"))

public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	private String genero;
	private String editora;
	private String nacionalidade;
	private String email;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Eager: se selecionar livro
																	// ele vai trazer escrito
	private Escritor escritor; // LAZY:Vai trazer os dados só
								// se pedir pra ele mostrar.

	public Livro() {
		super();
	}

	public Livro(int id, String titulo, String genero, String editora, Escritor escritor, String nacionalidade,
			String email) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.editora = editora;
		this.genero = genero;
		this.escritor = escritor;
		this.nacionalidade = nacionalidade;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + "" + titulo + ", genero=" + "" + genero + ", editora=" + editora
				+ ", escritor=" + escritor + "]";
	}

	public void setEscritor(Escritor escritor2) {
		this.escritor = escritor;

	}

	public Escritor getEscritor() {
		return escritor;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
