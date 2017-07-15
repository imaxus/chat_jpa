package chat;

/**
 * Zawiera rodzaje wiadomości. Dostępne dwie : wiadomość tekstowa i wiadomość 
 * o zalogowaniu innego użytkownika.
 * @author mina
 *
 */
public enum mType { 
    login("login"),
    msg("msg"),
    logout("logout");
	
	private final String text;
	
    private mType(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
    }
