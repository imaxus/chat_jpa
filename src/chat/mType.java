package chat;

/**
 * Zawiera rodzaje wiadomoœci. Dostêpne dwie : wiadomoœæ tekstowa i wiadomoœæ 
 * o zalogowaniu innego u¿ytkownika.
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
