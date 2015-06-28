import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.vilarica.jpa.JpaUtil;
import br.com.vilarica.model.Contato;
import br.com.vilarica.model.Endereco;
import br.com.vilarica.model.EstadoEnum;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.PaisEnum;

public class JPATest {

	EntityManager manager;

	public static void main(String[] args) {
		//new JPATest().createTables();
		// new JPATest().insertInit();
		// new JPATest().multiInsert();
		// new JPATest().updateInstituicao();
		//new JPATest().search("", EstadoEnum.PR);
		//new JPATest().findAll();
		System.exit(0);
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
				.setParameter("nome", '%'+ instituicao + '%')
				.setParameter("estado", estado)
				.getResultList();

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

		Municipio antigo = i.getMunicipio();

		for (Instituicao aux : antigo.getInstituicoes()) {
			System.out.println("\nTamanho da lista de instituicoes "
					+ antigo.getInstituicoes().size());
			if (aux.getId().equals(i.getId())) {
				antigo.getInstituicoes().remove(i);
				manager.merge(antigo);
				break;
			}
		}

		Municipio novo = manager.find(Municipio.class, new Long(2));

		System.out.println(novo);

		i.setMunicipio(novo);
		novo.getInstituicoes().add(i);

		manager.merge(i);
		manager.merge(novo);

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

		m.getInstituicoes().add(i1);
		m.getInstituicoes().add(i2);

		manager.merge(m);

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

		m.getInstituicoes().add(i);

		manager.merge(m);

		t.commit();

		System.exit(0);
	}
	
	private void findAll(){
		Session s = JpaUtil.getEntityManager().unwrap(Session.class);
		Criteria c = s.createCriteria(Instituicao.class);
		c.addOrder(Order.asc("nome")).list();
		
	}

}
