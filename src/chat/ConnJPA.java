package chat;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import chat.User;

public class ConnJPA {
	private String punitName;
	private EntityManagerFactory managerFactory; 
	private EntityManager entityManager; 
	private EntityTransaction entityTransaction;
	
	public ConnJPA(String persistenceUnitName) {
		System.out.println("[DB] przygotowywanie po³¹czenia ... ");
		punitName = persistenceUnitName;
		managerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		entityManager = managerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction(); 
		System.out.println("[DB] utworzono po³¹czenie z baz¹ danych! ");
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getPersonList() {
		List<User> users = null;
		try {
			users = (List<User>) entityManager.createNamedQuery("findAll").getResultList();
			// manager.close();
		} catch (Exception e) {
			System.out.println("Failed !!! " + e.getMessage());
		}
		return users;
	}
	
	public void AddUser(String name) {
		System.out.println("test0");
		Query q1 = entityManager.createQuery("select u from User u;");//User where nick=':name';").setParameter("name", name).getSingleResult();
		System.out.println("test1");
		/*if(q1 ==null) {
			try {
			entityManager.getTransaction().begin();

			User us = new User();
			us.setNick(name);
			us.setConnnumber(0);
			us.setIsconnected(true);

			entityManager.persist(us);

			entityManager.getTransaction().commit();
			System.out.println("[DB] dodano uzytkownika");
			}catch(Exception e) {
				System.out.println("[ERROR DB] wystapil blad przy dodawaniu uzytkownika ");
			}
		}else {
			try {
			User tempU = (User)q1;
			Query query = entityManager.createQuery("UPDATE users u SET u.connnumber = "+(tempU.getConnnumber()+1)+" WHERE e.nick = ':name'").setParameter("name",name);
			int rowCount = query.executeUpdate();
			System.out.println("[DB] zaktualizowano uzytkownika");
			}catch(Exception e) {
				System.out.println("[ERROR DB] wystapil blad przy aktualizowaniu uzytkownika");
			}
			
		}*/
		entityManager.close();
	}
}
