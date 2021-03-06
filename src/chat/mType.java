package chat;

/**
 * Zawiera rodzaje wiadomości. Dostępne cztery :
 * - wiadomość tekstowa
 * - wiadomość o zalogowaniu innego użytkownika.
 * - wiadomość o wylogowaniu innego użytkownika
 * - wiadomość o błędzie
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
