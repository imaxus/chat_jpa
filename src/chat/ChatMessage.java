package chat;

/**
 * Obiekt wiadomoœci, zawiera typ oraz tekst wiadomoœci
 * @author mina
 *
 */
public class ChatMessage {
	private String message;
    private mType messageType;
    private String sender;
    
	/**
	 * Ustawia nadawcê wiadomoœci
	 * @param str - tekst wiadomosci
	 */
    public void setSender(String str) {
    	this.sender = str;
    }
    /**
     * Zwraca nazwê nadawcy wiadomoœci
     * @return nazwa nadawcy
     */
    public String getSender() {
    	return this.sender;
    }
	/**
	 * Ustawia tekst wiadomoœci
	 * @param str - tekst wiadomosci
	 */
    public void setMessage(String str) { 
    	this.message = str; 
    }
    /**
     * Zwraca tekst wiadomoœci
     */
    public String getMessage() { 
    	return this.message;
    }
    /**
     *  Ustawia typ wiadomoœci
     * @param v
     */
    public void setMessageType(mType m) {
    	this.messageType = m; 
    	}
    /**
     *  Zwraca typ wiadomoœci
     */
    public mType getMessageType() { 
    	return messageType; 
    }
}