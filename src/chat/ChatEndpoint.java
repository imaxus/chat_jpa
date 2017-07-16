package chat;

import java.io.IOException;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Klasa obs�uguj�ca websocket - endpoint
 * @author mina
 *
 */
@ServerEndpoint(value = "/chatroom",encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
	private Room room = Room.getRoom();
	private ConnJPA cj;

	/**
	 * Funkcja wykonuj�ca si� w momencie otrzymania wiadomo�ci na socketcie, je�li jest to login
	 * to wysy�ana jest do wszystkich pozosta�ych (poza do��czaj�cym) u�ytkownik�w wiadomo�c o tym kto si� zalogowa�, 
	 * je�li jest to normalna wiadomo�� to jest ona wysy�ana do wszystkich
	 * @param s
	 * @param msgJson
	 */
	@OnMessage
	public void onMessage(final Session s, final String msgJson) {
		ChatMessage chatMsg = null;
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> properties = s.getUserProperties();
		
		try {
			chatMsg = om.readValue(msgJson, ChatMessage.class);
			
		} catch (IOException e) {
			try {
				s.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Co� posz�o nie tak, spr�buj za chwil�"));
			} catch (Exception ex) { 
			}
		}
		
		if (chatMsg.getMessageType() == mType.login) {		
			String nick = chatMsg.getMessage();
			properties.put("nick", nick);
			boolean isLogged = cj.AddUser(nick);
			
			if(isLogged) {
				chatMsg.setMessage(nick + " do��czy� do pokoju");
				chatMsg.setMessageType(mType.login);
				chatMsg.setSender("System");
				
				room.sendMessage(chatMsg);
				room.join(s);
				System.out.println("Wys�ano wiadomo�� typu - "+chatMsg.getMessageType()+" sender "+chatMsg.getSender());
			
			}else {
				chatMsg.setMessage("uzytkownik jest juz zalogowany");
				chatMsg.setMessageType(mType.error);
				chatMsg.setSender("System");
				try {
					s.getBasicRemote().sendObject(chatMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Ponowne logowanie ...");
			}
		}
		else if(chatMsg.getMessageType() == mType.msg) {
			chatMsg.setSender((String)properties.get("nick"));
			room.sendMessage(chatMsg);
		}
		else if(chatMsg.getMessageType() == mType.logout) {
			chatMsg.setMessageType(mType.logout);
			chatMsg.setSender("System");
			room.sendMessage(chatMsg);
		}
	}

	@OnOpen
	public void onOpen(final Session s, EndpointConfig c) {
		System.out.println("Utworzono websocket");
		cj = new ConnJPA("PU_dashDB");
	}
	@OnClose
	public void OnClose(Session s, CloseReason r) {
		ChatMessage newMsg = new ChatMessage();
		cj.LogoutUser((String)s.getUserProperties().get("nick"));
		System.out.println("Zamkni�to websocket");
		newMsg.setSender("System");
		newMsg.setMessageType(mType.logout);
		newMsg.setMessage((String)s.getUserProperties().get("nick") + " opu�ci� pok�j ");	
		room.leave(s);
		room.sendMessage(newMsg);
	}

	@OnError
	public void onError(Session s, Throwable ex) {
		cj.LogoutUser((String)s.getUserProperties().get("nick"));
		System.out.println("Wyst�pi� b��d");
	}
}
