package chat;

import java.io.StringReader;
import java.util.Date;
 
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
 
/**
 * Klasa dekoduj¹ca obiekty z json do ChatMessage w celu przes³ania i wyœietlenia przy pomocy html/js
 * @author mina
 *
 */
public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
 /**
  * Funkcja dekodujaca wiadomoœæ z formatu JSON do obiektu ChatMessage
  */
	@Override
	public ChatMessage decode(final String textMessage) throws DecodeException {
		ChatMessage chatMessage = new ChatMessage();
		JsonObject obj = Json.createReader(new StringReader(textMessage))
				.readObject();
		chatMessage.setMessage(obj.getString("message"));
		chatMessage.setSender(obj.getString("sender"));
		chatMessage.setSender(obj.getString("messageType"));
		return chatMessage;
	}
 
	@Override
	public boolean willDecode(final String s) {
		return true;
	}
}
