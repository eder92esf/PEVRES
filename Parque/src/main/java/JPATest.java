import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.vilarica.jpa.JpaUtil;
import br.com.vilarica.model.Contato;
import br.com.vilarica.model.Endereco;
import br.com.vilarica.model.EstadoEnum;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.Grupo;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.PaisEnum;
import br.com.vilarica.model.Usuario;

public class JPATest {

	EntityManager manager;

	public static void main(String[] args) {
		// new JPATest().createTables();
		// new JPATest().insertInit();
		// new JPATest().multiInsert();
		// new JPATest().updateInstituicao();
		// new JPATest().search("", EstadoEnum.PR);
		// new JPATest().findAll();
		// new JPATest().listExcursoes();
		// new JPATest().hashMap();
		// new JPATest().insertUser();
		// new JPATest().getGrupo();
		new JPATest().readProperties();
		System.exit(0);
	}
	
	private void readProperties(){
		try {
			File f = new File("Parque/data/Modelo_Lista.xlsx");
			System.out.println(f.getAbsolutePath());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void getGrupo(){
		manager = JpaUtil.getEntityManager();
		Usuario u = this.manager.find(Usuario.class, 2L);
		System.out.println(u);
		
		EntityTransaction t = manager.getTransaction();
		t.begin();
		u.setGrupo(null);
		//manager.merge(u);
		manager.remove(u);
		t.commit();
	}
	
	private void insertUser(){
		Grupo g = new Grupo();
		g.setNome("USERS");
		g.setDescricao("Usuáiros do Sistema");
		
		Usuario u = new Usuario();
		u.setEmail("joao@email.com");
		u.setNome("Joao");
		u.setSenha("123456");
		u.setGrupo(g);
		
		manager = JpaUtil.getEntityManager();
		EntityTransaction t = manager.getTransaction();
		t.begin();
		manager.persist(g);
		manager.persist(u);
		t.commit();
	}
	
	private void hashMap(){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("Fênix", 10);
		map.put("Quinta do Sol", 7);
		map.put("Londrina", 8);
		
		Set s = map.keySet();
		
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			String aux = (String) iterator.next();
			System.out.println(map.get(aux));
		}
	}

	private boolean agendavel(Date agendadaInicio, Date novaInicio) {
		Date agendadaFim = new Date();
		agendadaFim.setHours(agendadaInicio.getHours() + 2);
		agendadaFim.setMinutes(agendadaInicio.getMinutes());

		Date novaFim = new Date();
		novaFim.setHours(novaInicio.getHours() + 2);
		novaFim.setMinutes(novaInicio.getMinutes());

		if ((novaInicio.getHours() >= agendadaInicio.getHours())
				&& (novaInicio.getHours() < agendadaFim.getHours())) {
			return false;
		} else if (novaInicio.getHours() == agendadaFim.getHours()) {
			if (novaInicio.getMinutes() < agendadaFim.getMinutes()) 
				return false;
		} else if (novaFim.getHours() >= agendadaInicio.getHours()
				&& novaFim.getHours() < agendadaFim.getHours()) {
			if (novaFim.getMinutes() != agendadaInicio.getMinutes())
				return false;
		} else if (novaFim.getHours() == agendadaInicio.getHours()
				&& novaFim.getMinutes() > agendadaInicio.getMinutes()) {
			return false;
		} 
		return true;
	}
	
	private void listExcursoes() {
		manager = JpaUtil.getEntityManager();
		String sql = "SELECT e FROM ExcursaoEscolar e JOIN e.guia WHERE e.guia.id = :id AND e.dataExcursao BETWEEN :dataInicial AND :dataFinal";
		
		Date inicial = new Date();
		inicial.setHours(8);
		inicial.setMinutes(0);
		Date finall = new Date();
		finall.setHours(17);
		finall.setMinutes(0);
		
		List<ExcursaoEscolar> e = manager.createQuery(sql)
				.setParameter("id", new Long(1))
				.setParameter("dataInicial", inicial)
				.setParameter("dataFinal", finall)
				.getResultList();
		
		for (ExcursaoEscolar excursaoEscolar : e) {
			System.out.println(excursaoEscolar);
		}
	}

	public static void manipulaArquivo() throws Exception {
		String raiz = null;
		if (File.separator.equals("\\"))
			raiz = "C:\\PEVRES";
		else
			raiz = "/home";

		System.out.println(raiz.toString());

		File diretorio = new File(raiz.toString());
		File arquivo = new File(diretorio.getAbsolutePath() + File.separator
				+ "acompanhantes.csv");

		System.out.println(arquivo.getAbsolutePath());
	}

	@SuppressWarnings("deprecation")
	public static void test() {
		Date atual = new Date(115, 5, 30);
		Date dataNascimento = new Date(97, 6, 1);

		System.out.println(atual);
		System.out.println(dataNascimento);

		int ano = atual.getYear() - dataNascimento.getYear();

		if (ano > 18)
			System.out.println("EH MAIOR DE IDADE");
		else if (ano == 18) {
			if (dataNascimento.getMonth() < atual.getMonth())
				System.out.println("EH MAIOR DE IDADE");
			else if (dataNascimento.getMonth() == atual.getMonth()) {
				if (atual.getDay() >= dataNascimento.getDay()) {
					System.out.println("EH MAIOR DE IDADE");
				} else {
					System.out.println("MENOR DE IDADE");
				}
			} else {
				System.out.println("MENOR DE IDADE");
			}
		} else {
			System.out.println("MENOR DE IDADE");
		}
	}

	private void createTables() {
		manager = JpaUtil.getEntityManager();
		EntityTransaction t = manager.getTransaction();
		t.begin();
		t.commit();
	}

	@SuppressWarnings("unchecked")
	private void search(String instituicao, EstadoEnum estado) {

		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		manager = JpaUtil.getEntityManager();
		String sql = "SELECT i FROM Instituicao i JOIN i.municipio m "
				+ "WHERE LOWER(i.nome) like LOWER(:nome) and m.estado = :estado ORDER BY i.nome";
		instituicoes = manager.createQuery(sql)
				.setParameter("nome", '%' + instituicao + '%')
				.setParameter("estado", estado).getResultList();

		System.out.println("");

		for (Instituicao i : instituicoes) {
			System.out.println(i + "\n");
		}
	}

	private void updateInstituicao() {
		manager = JpaUtil.getEntityManager();
		EntityTransaction t = manager.getTransaction();
		t.begin();

		Instituicao i = manager.find(Instituicao.class, new Long(1));

		System.out.println(i);

		i.setNome("TESTE DE ATUALIZAÇÃO");

		manager.merge(i);

		t.commit();
	}

	private void multiInsert() {
		manager = JpaUtil.getEntityManager();

		EntityTransaction t = manager.getTransaction();
		t.begin();

		Municipio m = manager.find(Municipio.class, new Long(1));

		Contato c1 = new Contato();
		c1.setEmail("contato@tan.com");
		c1.setTelefone("44 3272-1121");

		Endereco e1 = new Endereco();
		e1.setBairro("Centro");
		e1.setLogradouro("Av 22 fevereiro");
		e1.setNumero("443");

		Instituicao i1 = new Instituicao();
		i1.setContato(c1);
		i1.setEndereco(e1);
		i1.setMunicipio(m);
		i1.setNome("Escola Tancredo");
		i1.setSigla("TANC");

		manager.persist(c1);
		manager.persist(e1);
		manager.persist(i1);

		Contato c2 = new Contato();
		c2.setEmail("contato@vila.com");
		c2.setTelefone("44 3272-9999");

		Endereco e2 = new Endereco();
		e2.setBairro("Centro");
		e2.setLogradouro("RUA JANGADA");
		e2.setNumero("111");

		Instituicao i2 = new Instituicao();
		i2.setContato(c2);
		i2.setEndereco(e2);
		i2.setMunicipio(m);
		i2.setNome("Escola Estadual Vila Rica");
		i2.setSigla("VRES");

		manager.persist(c2);
		manager.persist(e2);
		manager.persist(i2);

		t.commit();
	}

	private void insertInit() {
		manager = JpaUtil.getEntityManager();

		EntityTransaction t = manager.getTransaction();
		t.begin();

		Municipio m = new Municipio();
		m.setCep("86.950-000");
		m.setEstado(EstadoEnum.PR);
		m.setPais(PaisEnum.BRASIL);
		m.setNome("Fênix");

		Contato c = new Contato();
		c.setEmail("contato@cesil.com");
		c.setTelefone("44 3272-1322");

		Endereco e = new Endereco();
		e.setBairro("Centro");
		e.setLogradouro("Rua das Ruas");
		e.setNumero("123");

		Instituicao i = new Instituicao();
		i.setContato(c);
		i.setEndereco(e);
		i.setMunicipio(m);
		i.setNome("Colégio Estadual Santo Inácio de Loyola");
		i.setSigla("CESIL");

		manager.persist(m);
		manager.persist(c);
		manager.persist(e);
		manager.persist(i);

		t.commit();
	}

	private void findAll() {
		Session s = JpaUtil.getEntityManager().unwrap(Session.class);
		Criteria c = s.createCriteria(Instituicao.class);
		c.addOrder(Order.asc("nome")).list();

	}

}
