package chat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jcabi.aspects.Loggable;

import chat.User;
import logger.LoggerSetup;
/**
 * Klasa odpowiedzialna za obs³ugê ¿¹dañ wysy³anych do bazy danych
 * @author mina
 *
 */
public class ConnJPA {
	private String punitName;
	private EntityManagerFactory managerFactory; 
	private EntityManager entityManager; 
	private EntityTransaction entityTransaction;
	/**
	 * Konstruktor 
	 * @param persistenceUnitName nazwa jednostki z pliku konfiguracyjnego persistance.xml
	 */
	public ConnJPA(String persistenceUnitName) {
		//ustawienie loggera
		try {
			LoggerSetup.setup();
		} catch (IOException e) {
		}
		System.out.println("[DB] przygotowywanie po³¹czenia ... ");
		punitName = persistenceUnitName;
		managerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		entityManager = managerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction(); 
		System.out.println("[DB] utworzono po³¹czenie z baz¹ danych! ");
	}
	
	/**
	 * Funkcja pobieraj¹ca pe³n¹ listê u¿ytkowników z bazy danych
	 * @return lista u¿ytkowników
	 */
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
	/**
	 * Funkcja loguj¹ca u¿ytkownika do bazy danych, jeœli u¿ytkownika nie ma w bazie to dostaje dodany,
	 * jeœli istnieje w bazie danych to jest zwiêkszany licznik zalogowañ i wartoœæ logiczna okreœlaj¹ca
	 * czy dany u¿ytkownik, jest aktualnie zalogowany.
	 * @param name Nazwa u¿ytkownika
	 * @return zwraca true jeœli pomyœlnie zalogowano u¿ytkownika, w przeciwnym razie false
	 */
	@SuppressWarnings("unchecked")
	public boolean AddUser(String name) {
		List<User> q1 = null;
		q1 =(List<User>) entityManager.createQuery("SELECT u FROM User u WHERE u.nick= :name ORDER BY u.nick").setParameter("name", name).getResultList();
		if(q1.isEmpty()) {
			try {
			entityManager.getTransaction().begin();
			User us = new User();
			us.setNick(name);
			us.setConnnumber(1);
			us.setIsconnected(true);
			entityManager.persist(us);
			entityManager.getTransaction().commit();
			System.out.println("[DB] dodano uzytkownika");
			}catch(Exception e) {
				System.out.println("[ERROR DB] wystapil blad przy dodawaniu uzytkownika ");
			}
		}else if(!q1.get(0).getIsconnected()){
			try {
			int idc = q1.get(0).getId();
			User tempUser = entityManager.find(User.class, idc);
			entityManager.getTransaction().begin();
			tempUser.setConnnumber(q1.get(0).getConnnumber()+1);
			tempUser.setIsconnected(true);
			entityManager.getTransaction().commit();
			//Query q2 = entityManager.createQuery("UPDATE User u SET u.connnumber = 2 WHERE u.nick = :name");
			//int rowC = q2.setParameter("name",name).executeUpdate();
			System.out.println("[DB] zaktualizowano uzytkownika");
			}catch(Exception e) {
				System.out.println("[ERROR DB] wystapil blad przy aktualizowaniu uzytkownika");
			}
			
		}else {
			
			System.out.println("[DB] uzytkownik o tej nazwie jest juz zalogowany");
			return false;
		}
		return true;
	}
	/**
	 * Funkcja wylogowuj¹ca u¿ytkownika, zmienia wartoœæ logiczna w bazie okreœlaj¹c¹ czy u¿ytkownik jest 
	 * aktualnie zalogowany
	 * @param name Nazwa u¿ytkownika
	 */
	@SuppressWarnings("unchecked")
	public void LogoutUser(String name) {
		try {
		List<User> q1 = null;
		q1 =(List<User>) entityManager.createQuery("SELECT u FROM User u WHERE u.nick= :name ORDER BY u.nick").setParameter("name", name).getResultList();
		int idc = q1.get(0).getId();
		User tempUser = entityManager.find(User.class, idc);
		entityManager.getTransaction().begin();
		tempUser.setIsconnected(false);
		entityManager.getTransaction().commit();
		System.out.println("[DB] wylogowano uzytkownika");
		}catch(Exception e) {
			System.out.println("[ERROR DB] wystapil blad przy wylogowywaniu uzytkownika");
		}
		CloseConnection();
	}
	/**
	 * Zamkniêcie po³¹czenia
	 */
	public void CloseConnection() {
		entityManager.close();
		managerFactory.close();
	}
}
