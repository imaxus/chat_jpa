package chat;

/**
 * Zawiera rodzaje wiadomoœci. Dostêpne cztery :
 * - wiadomoœæ tekstowa
 * - wiadomoœæ o zalogowaniu innego u¿ytkownika.
 * - wiadomoœæ o wylogowaniu innego u¿ytkownika
 * - wiadomoœæ o b³êdzie
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
