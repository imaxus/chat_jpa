package chat;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
 
/**
 * Klasa koduj¹ca obiekty ChatMessage do json w celu przes³ania 
 * @author mina
 *
 */
public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
	 /**
	  * Funkcja koduj¹ca wiadomoœæ z obiektu ChatMessage do formatu JSON
	  */
	@Override
	public String encode(final ChatMessage chatMessage) throws EncodeException {
		return Json.createObjectBuilder()
				.add("message", chatMessage.getMessage())
				.add("sender", chatMessage.getSender())
				.add("messageType", chatMessage.getMessageType().toString()).build()
				.toString();
	}
}
