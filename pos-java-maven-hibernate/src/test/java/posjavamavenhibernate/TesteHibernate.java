package posjavamavenhibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {
	
	
	/*Teste para ver se o hibernate ta iniciando*/
	@Test
	public void testeHibernateUtil() {
		HibernateUtil.getEntityManager();
		
	}
	
	
	
	/* Teste para ver se ta criando certo no banco de dados*/
	 @Test
	public void testeSalvar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setIdade(30);
		pessoa.setLogin("teste");
		pessoa.setNome("Beatriz");
		pessoa.setSobrenome("Landgraf");
		pessoa.setSenha("teste");
		pessoa.setEmail("matheus_godoyland@hotmail.com");
		daoGeneric.salvar(pessoa);
	}
	
	/* Teste para ver se ta pesquisando certo no banco de dados*/
	@Test
	public void testeBuscar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(2L);
		pessoa = daoGeneric.pesquisar(pessoa);
		System.out.println(pessoa);
	}
	
	
	/* Teste para ver se ta atualizando certo no banco de dados*/
	@Test
	public void testeUpdate() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);
		pessoa = daoGeneric.pesquisar(pessoa);
		pessoa.setIdade(80);
		pessoa.setNome("Maria");
		pessoa = daoGeneric.updateMerge(pessoa);
		System.out.println(pessoa);
	}
	
	/* Teste para ver se ta deletando certo no banco de dados*/
	@Test
	public void testeDelete() throws Exception {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(2L);
		pessoa = daoGeneric.pesquisar(pessoa);
		daoGeneric.deletarPorId(pessoa);

	}
	
	
	/* Teste para ver se ta criando lista de usuários certo no banco de dados*/
	@Test
	public void testeConsultar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.listar(UsuarioPessoa.class);
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("------------------------------------");
		}
	}
	
	
	/* Teste mais específico, utilizando getEntityManager para pesquisar algo específico no banco de dados*/
	@Test
	public void testeQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa where nome = 'Maria'").getResultList();
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);	
		}

	}
	
	/* Teste mais específico, utilizando getEntityManager para pesquisar o Max de resultados por ordem de nomes*/
	@Test
	public void testeQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa order by nome").setMaxResults(5).getResultList();
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);	
		}
	}
	
	
	/* Teste de parâmetros e querys condicionais, utilizando getEntityManager para pesquisar*/
	@Test
	public void testeQueryListParameter() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa where nome = :nome").setParameter("nome", "Fernando").getResultList();
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);	
		}

	}
	
	/* Teste efetuando operações matemáticas  com o banco de dados, utilizando getEntityManager para pesquisar*/
	@Test
	public void testeQuerySomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		Long somaIdade = (Long) daoGeneric.getEntityManager().createQuery("select sum(u.idade) from UsuarioPessoa u ").getSingleResult();
		System.out.println("A soma de todas as idades é : " + somaIdade);
		
	}
	
	/* Teste passando NamedQuery para fazer uma lista com todos*/
	@Test
	public void testeNameQuery1(){
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.findAll").getResultList();
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);	
		}

	}
	
	
	/* Teste passando NamedQuery para fazer uma lista com parâmetros*/

	@Test
	public void testeNameQuery2(){
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.findByName").setParameter("nome", "Beatriz").getResultList();
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);	
		}

	}
	
	
	/*Teste adicionando um telefone para um userPessoa no banco de dados*/

	@Test
	public void testeGravaTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(5L);
		UsuarioPessoa usuarioPessoa = (UsuarioPessoa) daoGeneric.pesquisar(pessoa);
		TelefoneUser telefoneUser = new TelefoneUser();
		telefoneUser.setTipo("Casa");
		telefoneUser.setNumero("4335245366");
		telefoneUser.setUsuarioPessoa(pessoa);
		daoGeneric.salvar(telefoneUser);
	}
	
	/*Teste Pesquisando os telefones para um userPessoa em específico no banco de dados*/

	@Test
	public void testeConsultaTelefones() {
		DaoGeneric daoGeneric = new DaoGeneric();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(5L);
		UsuarioPessoa usuarioPessoa = (UsuarioPessoa) daoGeneric.pesquisar(pessoa);
		for (TelefoneUser fone : usuarioPessoa.getTelefoneUsers()) {
			System.out.println(fone.getNumero());
			System.out.println(fone.getTipo());
			System.out.println(fone.getUsuarioPessoa().getNome());
			System.out.println("--------------------------");
			
		}
	}

}
