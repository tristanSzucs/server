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
	} //end of function
	
	//adds a new user by username and password
	public synchronized void addUser(String name, String password) {
		users.add(new User(name, password));
	} //end of function
	
	//adds a new user that is passed in
	public synchronized void addUser(User u) {
		users.add(u);
	} //end of function
	
	//returns a user file based on username
	public synchronized User findUser(String name) {
		//start at zero
		int i =0;
		//go through the array until all users are checked or it is found
		while (i < users.size() && !users.get(i).getUsername().equals(name ) ) i++;
		if(i < users.size()) return users.get(i); //if it was found return it
		else return null;	//otherwise return null
	} //end of function
	
	//remove a user based on user object
	public synchronized void removeUser(User u) {
		users.remove(u);
	} //end of function
	
	//send a message to everyone subsribed to a particular room
	public synchronized void  sendMessage(String message, String room) {
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
			} //end of if
			i++;
		} //end of while
	} //end of function
	
	
	//send a message to everyone logged in
		public synchronized void  sendMessage(String message) {
				
			//go through the list
			int i = 0;	
			User user;
			while (i < users.size() ) {
				//get the user at that num
				user = users.get(i);				
				//send them that message if they are logged in
				if (user.LoggedIn()) toSend.set(new Message(user, message));
				i++;
			} //end of while
		} //end of function
		
		//send the user a list of where users are currently located
		public synchronized void startupList(User u) {
			//go through the list
			int i = 0;	
			User user;
			while (i < users.size() ) {
				//get the user at that num
				user = users.get(i);				
				//send them that message if they are logged in
				if(!user.getSubTo().equals(""))
					toSend.set(new Message(u, "1\t" + user.getSubTo()));
				i++;
			} //end of while
		} //end of functino
		
		//prints on the console a list of users that are currently logged in
		public synchronized void activeUsers() {
			//go through the list
			int i = 0;	
			User user;
			while (i < users.size() ) {
				//get the user at that num
				user = users.get(i);				
				//print out the user name if they are logged in
				if(user.LoggedIn())
					System.out.println(user.getUsername()); 
				i++;
			} //end of while
		} //end of functino
		
		
		//prints on the console a list of all users
		public synchronized void allUsers() {
			//go through the list
			int i = 0;	
			while (i < users.size() ) {			
				//print out the user name
				System.out.println( users.get(i).getUsername()); 
				i++;
			} //end of while
		} //end of functino
				
} //end of class
