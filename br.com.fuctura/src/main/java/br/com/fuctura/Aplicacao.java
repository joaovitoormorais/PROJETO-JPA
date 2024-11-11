package br.com.fuctura;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.fuctura.dao.LivroDAO;
import br.com.fuctura.models.Escritor;
import br.com.fuctura.models.Livro;

public class Aplicacao {

	public static Integer leiaInt(String message, Scanner scan) {
		System.out.println(message);

		try {
			return scan.nextInt();

		} catch (InputMismatchException e) {
			System.out.println("\nEntrada inválida, Digite um número");
			scan.next();
			return null;
		}
	}

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("meu-persistence-unit");
		Scanner scan = new Scanner(System.in);
		Integer opc = null;

		while (opc == null || opc != 7) {
			System.out.println("\nEscolha a opção");
			System.out.println("|---------------------------|");
			System.out.println("|   1 - Inserir Livro       |");
			System.out.println("|   2 - Listar Livro        |");
			System.out.println("|   3 - Atualizar Livro     |");
			System.out.println("|   4 - Excluir Livro       |");
			System.out.println("|   5 - Listar Tudo         |");
			System.out.println("|   6 - Excluir Livro Por Id|");
			System.out.println("|   7 - SAIR                |");

			opc = leiaInt("Opção:", scan);

			if (opc == null) {
				continue;
			}

			LivroDAO livroDAO = new LivroDAO(emf);

			switch (opc) {
			case 1:
				
				Escritor escritor = new Escritor();
				Livro livro = new Livro();
				
				scan.nextLine();

				System.out.println("\nDigite o título do livro ");
				livro.setTitulo(scan.nextLine());

				System.out.println("\nDigite o gênero do livro ");
				livro.setGenero(scan.nextLine());

				System.out.println("\nDigite a editora do livro ");
				livro.setEditora(scan.next());

				System.out.println("\nDigite o nome do escritor ");
				escritor.setNome(scan.nextLine());

				System.out.println("\nDigite a nacionalidade do escritor ");
				escritor.setNacionalidade(scan.nextLine());

				System.out.println("\nDigite o e-mail do escritor ");
				escritor.setEmail(scan.nextLine());

				livro.setEscritor(escritor);

				livroDAO.inserir(livro);
				break;

			case 2:
				Integer id = leiaInt("\nDigite o identificador do livro\n", scan);
				if (id != null) {
					Livro livroEncontrado = livroDAO.listar(id);
					if (livroEncontrado != null) {
						System.out.println("\nLivro encontrado:\n" + livroEncontrado);
					} else {
						System.out.println("\nLivro não encontrado com o ID:" + id);
					}
				}
				break;

			case 3:
				scan.nextLine();
				
				System.out.println("\nDigite o título do livro:");
				String titulo = scan.nextLine();
				
				Livro livroParaAtualizar = livroDAO.listarPorTitulo(titulo);
				if(livroParaAtualizar != null) {
					
					System.out.println("\nDigite o novo título do livro:");
					livroParaAtualizar.setTitulo(scan.nextLine());
					
					System.out.println("\nDigite o novo gênero do livro:");
					livroParaAtualizar.setGenero(scan.nextLine());
					
					System.out.println("\nDigite a nova editora do livro:");
					livroParaAtualizar.setEditora(scan.nextLine());
					
					livroDAO.atualizar(livroParaAtualizar);
					System.out.println("\nLivro atualizado com sucesso!");
					
				}else {
					System.out.println("\nLivro não encontrado!");
				}
				break;

			case 4:
				System.out.println("\nDigite o título do livro\n");
				String tituloExcluir = scan.next();

				Livro livroParaExcluir = livroDAO.listarPorTitulo(tituloExcluir);
				if (livroParaExcluir != null) {
					livroDAO.excluir(livroParaExcluir);
				} else {
					System.out.println("\nLivro não encontrado\n");
				}
				break;

			case 5:
				List<Livro> livros = livroDAO.listarTudo();
				if (livros != null && !livros.isEmpty()) {
					System.out.println("Lista de livros:");
					for (Livro l : livros) {
						System.out.println(l);
					}
				} else {
					System.out.println("\nErro ao tentar listar livros ou nenhum livro encontrado!");
				}
				break;

			case 6:
				Integer idExcluir = leiaInt("\nDigite o id do livro que você quer excluir:\n", scan);
				if (idExcluir != null) {
					livroDAO.excluir(idExcluir);
				}
				break;

			case 7:
				System.out.println("|---------------------------|");
				System.out.println("     SAINDO...");
				System.out.println("|---------------------------|");
				break;

			default:
				System.out.println("\nEscolha uma opção válida!\n");
				break;
			}
		}
		scan.close();
		emf.close();
	}

}