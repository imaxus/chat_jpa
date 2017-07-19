package chat;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/** Klasa reprezentuj�ca pok�j - jest singletonem, gdy� pok�j jest jeden, 
 * a jest to najprostszy spos�b synchronizacji wszystkich po�acze� */
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
	 *  Wys�anie wiadomo�ci do wszystkich u�ytkownik�w pod��czonych do pokoju
	 * @param message obiekt wiadomo�ci do przes�ania wszystkim u�ytkownikom
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
	 * Dodanie u�ytkownika do pokoju
	 * @param session sesja po��czenia danego u�ytkownika
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
	 * Usuni�cie u�ytkownika z pokoju
	 * @param session
	 */
	public synchronized void leave(Session s) { 
		connectedUsers.remove(s); 
	}
}
