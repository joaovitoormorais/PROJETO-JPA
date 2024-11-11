package br.com.fuctura.dao;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.ArrayList;
import br.com.fuctura.interfacedao.InterfaceDAO;
import br.com.fuctura.models.Escritor;
import br.com.fuctura.models.Livro;

public class LivroDAO implements InterfaceDAO {
	
	private EntityManagerFactory emf;
	private EntityManager em;

	public LivroDAO(EntityManagerFactory emf) {
		super();
		this.emf = emf;
		this.em = emf.createEntityManager();
		
		
	}

	@Override
	public void inserir(Livro livro) {
		
		try {
			em.getTransaction().begin();
			em.persist(livro);
			em.getTransaction().commit();
			System.out.println("\nLivro inserido com sucesso!");
			
		}catch(Exception e) {
			System.out.println("\nErro ao inserir livro:" + e.getMessage());
			em.getTransaction().rollback();
		}
		
	}

	@Override
	public Livro listar(Integer id) {
	
		try {
			Livro livro = em.find(Livro.class, id);
		if(livro != null) {
			return livro;
		}else {
			System.out.println("\nLivro com ID" + id + "não encontrado\n");
			return null;
		}
			
		}catch(Exception e) {
			System.out.println("ERRO" + e.getMessage());
			return null;
		}
	}

	@Override
	public void atualizar(Livro livro) {
		
		try {
			Scanner entrada = new Scanner(System.in);

			Escritor escritor = livro.getEscritor();

			System.out.println("\nDigite a nova editora: ");
			livro.setEditora(entrada.nextLine());

			System.out.println("\nDigite a nova nacionalidade: ");
			escritor.setNacionalidade(entrada.nextLine());

			System.out.println("\nDigite o novo e-mail: ");
			escritor.setEmail(entrada.nextLine());

			livro.setEscritor(escritor);

			em.getTransaction().begin();
			em.merge(livro); // Atualizar o livro
			em.getTransaction().commit();

			System.out.println("\nDados alterados com sucesso!!!\n");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			em.getTransaction().rollback();
		}
	}


	@Override
	public Livro listarPorTitulo(String titulo) {
		try {
			TypedQuery<Livro> query = em.createNamedQuery("Livro.consultarPorTitulo", Livro.class);
			query.setParameter("titulo", titulo);
			return query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Erro ao listar por título: " + e.getMessage());
			return null;
	}
	}

	@Override
	public void excluir(Livro livro) {
		
		try {
			em.getTransaction().begin();
			em.remove(em.contains(livro) ? livro : em.merge(livro)); // Remover ou fazer merge antes
			em.getTransaction().commit();
			System.out.println("\nLivro excluído com sucesso!!!\n");
		} catch (Exception e) {
			System.out.println("Erro ao excluir livro: " + e.getMessage());
			em.getTransaction().rollback();
		}
	}

	@Override
	public List<Livro> listarTudo() {
		
		try {
			TypedQuery<Livro> query = em.createQuery("SELECT l FROM Livro l", Livro.class);
			return query.getResultList();
		} catch (Exception e) {
			System.out.println("Erro ao listar todos os livros: " + e.getMessage());
			return null;
	}
	
	
}

	@Override
	public void excluir(Integer id) {
		
		try {
			
			Livro livro = new Livro(); 
			livro =  em.find(Livro.class, id);
			
			if(livro != null) {
				
				em.getTransaction().begin();
				em.remove(livro); //usado para remover o objeto
				em.getTransaction().commit();
				
				System.out.println("\nLivro excluido com sucesso!\n");
			} else {
				System.out.println("\nLivro não encontrado!\n");
			}
			

		
		
			LivroDAO livroDAO = new LivroDAO(emf);
			
			
			System.out.println("\nLivro excluido com sucesso!\n");
			
		}catch(Exception e) {
			System.out.println("Erro ao excluir livro por ID" + e.getMessage());
			em.close();
		}
		
	}
	
}