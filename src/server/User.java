package server;

import java.io.ObjectOutputStream;

/*
 * This class represents a user complete with all information needed on them
 */
public class User {
	private String userName;	//the username of the user
	private String password;	//the password of the user
	private String subTo = "";	//the room that the user is subbed to
	private Boolean loggedIn;		//if the user is logged in
	private ObjectOutputStream out;
	
	// a constructor that needs a user and a password
	public User(String user, String pass) 
	{
		loggedIn = false;
		userName = user;
		password = pass;
	}
	
	
	//this checks if that is the password
	public synchronized boolean checkPassword(String password)  
	{
	
		return this.password.equals( password );
	}
	
	//this requires an old password which if correct sets a new password
	public synchronized boolean changePassword(String oldPass, String newPass) 
	{
		if ( oldPass == this.password ) 
		{
			password = newPass;
			return true		;
		}
		else return false;
		
	}
	
	//this function gets the username of the user
	public synchronized String getUsername() {
		return userName;
	}
	
	//this function returns the name of the chat room the user is currently subscribed to
	public synchronized String getSubTo() {
		return subTo;
	}
	
	
	//this function set the name of the chat room the user is currently subscrubed to
	public synchronized void setSubTo(String sub) {
		subTo = sub;
	}
	
	
	
	//this function gets the socket currently associated with this user
		public synchronized ObjectOutputStream getOutput() {
			return out;
		}
		
		//the function sets the socket associated with this user
		public synchronized void setOutput(ObjectOutputStream s) {
			out = s;
		}
	
	//this funciton checks if the user is logged in still
	public synchronized void setLoggedIn(Boolean h) {
		loggedIn = h;
	}
	
	//the function returns is the user is logged in
	public synchronized boolean LoggedIn() {
		return loggedIn;
	}
} //end of class

