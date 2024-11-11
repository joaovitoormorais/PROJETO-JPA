package br.com.fuctura.interfacedao;

import java.util.List;
import br.com.fuctura.models.Livro;

public interface InterfaceDAO {
	
	public void inserir(Livro livro);
	public Livro listar(Integer id);
	public void atualizar(Livro livro);
	public Livro listarPorTitulo(String titulo);
	public void excluir(Livro livro);
	public List<Livro> listarTudo();
	public void excluir(Integer id);
}
