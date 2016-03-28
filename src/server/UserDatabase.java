package server;

import java.util.ArrayList;
/*
 * This is the database used to hold all users.
 * It also provides functions to send out messages to users.
 */
public class UserDatabase {
	private ArrayList<User> users = new ArrayList<User>();	//an array to hold all the users
	
	private SendQueue toSend;	//the queue used to send out messages
	
	//the constructor requires the queue to send out messages
	public UserDatabase(SendQueue q) {
		toSend = q;
	}
	
	//adds a new user by username and password
	public void addUser(String name, String password) {
		users.add(new User(name, password));
	}
	
	//adds a new user that is passed in
	public void addUser(User u) {
		users.add(u);
	}
	
	//returns a user file based on username
	public User findUser(String name) {
		//start at zero
		int i =0;
		//go through the array until all users are checked or it is found
		while (i < users.size() && !users.get(i).getUsername().equals(name ) ) i++;
		if(i < users.size()) return users.get(i); //if it was found return it
		else return null;	//otherwise return null
	}
	
	//remove a user based on user object
	public void removeUser(User u) {
		users.remove(u);
	}
	
	//send a message to everyone subsribed to a particular room
	public void sendMessage(String message, String room) {
		//if they attempted to send to empty return
		if(room.equals("")) return;
		
		//go through the list
		int i = 0;	
		User user;
		while (i < users.size() ) {
			//get the user at that num
			user = users.get(i);
			//if the room is the one they are currently subbed to
			if(user.getSubTo().equals(room)) {
				//send them that message
				toSend.set(new Message(user, message));
			}
			i++;
		}
	}
	
}
