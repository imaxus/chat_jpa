package chat;

/**
 * Obiekt wiadomo�ci, zawiera typ oraz tekst wiadomo�ci
 * @author mina
 *
 */
public class ChatMessage {
	private String message;
    private mType messageType;
    private String sender;
    
	/**
	 * Ustawia nadawc� wiadomo�ci
	 * @param str - tekst wiadomosci
	 */
    public void setSender(String str) {
    	this.sender = str;
    }
    /**
     * Zwraca nazw� nadawcy wiadomo�ci
     * @return nazwa nadawcy
     */
    public String getSender() {
    	return this.sender;
    }
	/**
	 * Ustawia tekst wiadomo�ci
	 * @param str - tekst wiadomosci
	 */
    public void setMessage(String str) { 
    	this.message = str; 
    }
    /**
     * Zwraca tekst wiadomo�ci
     */
    public String getMessage() { 
    	return this.message;
    }
    /**
     *  Ustawia typ wiadomo�ci
     * @param v
     */
    public void setMessageType(mType m) {
    	this.messageType = m; 
    	}
    /**
     *  Zwraca typ wiadomo�ci
     */
    public mType getMessageType() { 
    	return messageType; 
    }
}