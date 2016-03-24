package server;

import java.util.ArrayList;

public class UserDatabase {
	private ArrayList<User> users = new ArrayList<User>();
	
	private SendQueue toSend;
	
	public UserDatabase(SendQueue q) {
		toSend = q;
	}
	
	public void addUser(String name, String password) {
		users.add(new User(name, password));
	}
	
	public User findUser(String name) {
		int i =0;
		while (i < users.size() && !users.get(i).getUsername().equals(name ) ) i++;
		if(i < users.size()) return users.get(i);
		else return null;
	}
	
	public void removeUser(User u) {
		users.remove(u);
	}
	
	public void sendMessage(String message, String room) {
		if(room.equals("")) return;
		int i = 0;
		User user;
		while (i < users.size() ) {
			user = users.get(i);
			if(user.getSubTo().equals(room)) {
				toSend.set(new Message(user, message));
			}
			i++;
		}
	}

	public void sendMessage(String message, User user) {
		//send message to that user
	}
	
}
