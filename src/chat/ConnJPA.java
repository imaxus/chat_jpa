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
		System.out.println("[DB] user list returned");
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public void AddUser(String name) {
		List<User> q1 = null;
		q1 =(List<User>) entityManager.createQuery("SELECT u FROM User u WHERE u.nick= :name ORDER BY u.nick").setParameter("name", name).getResultList();
		if(q1.isEmpty()) {
			try {
			entityManager.getTransaction().begin();
			System.out.println("test2");
			User us = new User();
			us.setNick(name);
			us.setConnnumber(1);
			us.setIsconnected(true);
			System.out.println("test3");
			entityManager.persist(us);
			System.out.println("test4");
			entityManager.getTransaction().commit();
			System.out.println("[DB] dodano uzytkownika");
			}catch(Exception e) {
				System.out.println("[ERROR DB] wystapil blad przy dodawaniu uzytkownika ");
			}
		}else {
			try {
			int idc = q1.get(0).getId();
			User tempUser = entityManager.find(User.class, idc);
			entityManager.getTransaction().begin();
			tempUser.setConnnumber(q1.get(0).getConnnumber()+1);
			entityManager.getTransaction().commit();
			//Query q2 = entityManager.createQuery("UPDATE User u SET u.connnumber = 2 WHERE u.nick = :name");
			//int rowC = q2.setParameter("name",name).executeUpdate();
			System.out.println("[DB] zaktualizowano uzytkownika");
			}catch(Exception e) {
				System.out.println("[ERROR DB] wystapil blad przy aktualizowaniu uzytkownika");
			}
			
		}
	}
}
