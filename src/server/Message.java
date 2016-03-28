package server;
/*
 * The message class is used to pass the user along with what the message is they need to send.  
 * This class is used primaraly to send information to the send Queue to send to the users socket.
 */
public class Message {
	private User user;	//a user to send the message to
	private String message;	//the formatted string on what to say
	
	
	//a constructor to make this class
	public Message(User u, String mes) {
		user = u;
		message = mes;
	}
	
	//a function to get the user
	public User getUser() {
		return user;
	}

	//a function to get the message to be send
	public String getMessage() {
		return message;
	}
}
