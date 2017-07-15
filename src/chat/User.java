package chat;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Klasa dwzorowuj¹ca encjê z bazy danych
 * 
 */
@Entity
@Table(name = "users")
@NamedQuery(name="findAll", query="SELECT u FROM User u ORDER BY u.nick")

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nick;
	private Integer connnumber;
	private Boolean isconnected;
	
	public User() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Integer getConnnumber() {
		return connnumber;
	}

	public void setConnnumber(Integer connnumber) {
		this.connnumber = connnumber;
	}

	public Boolean getIsconnected() {
		return isconnected;
	}

	public void setIsconnected(Boolean isconnected) {
		this.isconnected = isconnected;
	}
}
