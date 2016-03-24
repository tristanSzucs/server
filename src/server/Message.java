package server;

public class Message {
	private User user;
	private String message;
	
	public Message(User u, String mes) {
		user = u;
		message = mes;
	}
	
	public User getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}
}
