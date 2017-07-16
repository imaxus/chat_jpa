package chat;

/**
 * Zawiera rodzaje wiadomo�ci. Dost�pne dwie : wiadomo�� tekstowa i wiadomo�� 
 * o zalogowaniu innego u�ytkownika.
 * @author mina
 *
 */
public enum mType { 
    login("login"),
    msg("msg"),
    logout("logout"),
	error("error");
	private final String text;
	
    private mType(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
    }
