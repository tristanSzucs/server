package server;

import java.net.Socket;

/*
 * This class represents a user complete with all information needed on them
 */
public class User {
	private String userName;	//the username of the user
	private String password;	//the password of the user
	private String subTo = "";	//the room that the user is subbed to
	private Socket socket = null;	//the socket associated with the user
	private Boolean loggedIn;		//if the user is logged in
	
	// a constructor that needs a user and a password
	public User(String user, String pass) 
	{
		userName = user;
		password = pass;
	}
	
	//a constuctor that also contains the socket they are communiating on	
	public User(String user, String pass, Socket sock) 
	{
		userName = user;
		password = pass;
		socket = sock;
	}
	
	//this checks if that is the password
	public boolean checkPassword(String password)  
	{
	
		return this.password.equals( password );
	}
	
	//this requires an old password which if correct sets a new password
	public boolean changePassword(String oldPass, String newPass) 
	{
		if ( oldPass == this.password ) 
		{
			password = newPass;
			return true		;
		}
		else return false;
		
	}
	
	//this function gets the username of the user
	public String getUsername() {
		return userName;
	}
	
	//this function returns the name of the chat room the user is currently subscribed to
	public String getSubTo() {
		return subTo;
	}
	
	
	//this function set the name of the chat room the user is currently subscrubed to
	public void setSubTo(String sub) {
		subTo = sub;
	}
	
	//this function gets the socket currently associated with this user
	public Socket getSocket() {
		return socket;
	}
	
	//the function sets the socket associated with this user
	public void setSocket(Socket s) {
		socket = s;
	}
	
	//this funciton checks if the user is logged in still
	public void setLoggedIn(Boolean h) {
		loggedIn = h;
	}
	
	//the function returns is the user is logged in
	public boolean LoggedIn() {
		return loggedIn;
	}
} //end of class

