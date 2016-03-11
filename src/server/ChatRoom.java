package server;

import java.util.ArrayList;

public class ChatRoom {
	private String name;
	private ArrayList<User> users;
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}
	

	
}
