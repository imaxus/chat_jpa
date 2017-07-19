package chat;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/** Klasa reprezentuj¹ca pokój - jest singletonem, gdy¿ pokój jest jeden, 
 * a jest to najprostszy sposób synchronizacji wszystkich po³aczeñ */
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
	 *  Wys³anie wiadomoœci do wszystkich u¿ytkowników pod³¹czonych do pokoju
	 * @param message obiekt wiadomoœci do przes³ania wszystkim u¿ytkownikom
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
	 * Dodanie u¿ytkownika do pokoju
	 * @param session sesja po³¹czenia danego u¿ytkownika
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
	 * Usuniêcie u¿ytkownika z pokoju
	 * @param session
	 */
	public synchronized void leave(Session s) { 
		connectedUsers.remove(s); 
	}
}
