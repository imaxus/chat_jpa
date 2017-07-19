package chat;

/**
 * Zawiera rodzaje wiadomo�ci. Dost�pne cztery :
 * - wiadomo�� tekstowa
 * - wiadomo�� o zalogowaniu innego u�ytkownika.
 * - wiadomo�� o wylogowaniu innego u�ytkownika
 * - wiadomo�� o b��dzie
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
