package chat;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/** Klasa reprezentująca pokój - jest singletonem, gdyż pokój jest jeden, 
 * a jest to najprostszy sposób synchronizacji wszystkich połaczeń */
public class Room {
	
	private ArrayList<Session> connectedUsers = new ArrayList<Session>();
	private static Room instance = null;
	
	/**
	 * Pobranie instancji pokoju.
	 * @return
	 */
	public synchronized static Room getRoom() {
		if (instance == null) {
			instance = new Room(); 
		}
		return instance;
	}
	
	/**
	 *  Wysłanie wiadomości do wszystkich użytkowników podłączonych do pokoju
	 * @param message obiekt wiadomości do przesłania wszystkim użytkownikom
	 */
	public synchronized void sendMessage(final ChatMessage msg) {
		for (Session cuser: connectedUsers) {
			if (cuser.isOpen()) {
				try { 
					cuser.getBasicRemote().sendObject(msg); 
				}
				catch (IOException | EncodeException e) { 
					e.printStackTrace(); 
				}
			}
		}
	}
	
	/**
	 * Dodanie użytkownika do pokoju
	 * @param session sesja połączenia danego użytkownika
	 */
	public synchronized void join(Session s) { 
		connectedUsers.add(s); 
		ChatMessage chatMsg = new ChatMessage();
		chatMsg.setSender("System");
		chatMsg.setMessageType(mType.login);
		chatMsg.setMessage("Witaj " + s.getUserProperties().get("nick"));
		try { 
			connectedUsers.get(connectedUsers.size()-1).getBasicRemote().sendObject(chatMsg); 
		}
		catch (IOException| EncodeException e) { 
			e.printStackTrace(); 
		}
	}
	/**
	 * Usunięcie użytkownika z pokoju
	 * @param session
	 */
	public synchronized void leave(Session s) { 
		connectedUsers.remove(s); 
	}
}
