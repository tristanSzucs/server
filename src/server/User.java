package server;

import java.net.Socket;

public class User {
	private String userName;
	private String password;
	private String subTo = "";
	private Socket socket = null;
	private Boolean loggedIn;
	
	public User(String user, String pass) 
	{
		userName = user;
		password = pass;
	}
	
	public User(String user, String pass, Socket sock) 
	{
		userName = user;
		password = pass;
		socket = sock;
	}
	
	public boolean checkPassword(String password)  
	{
	
		return this.password.equals( password );
	}
	
	public boolean changePassword(String oldPass, String newPass) 
	{
		if ( oldPass == this.password ) 
		{
			password = newPass;
			return true		;
		}
		else return false;
		
	}
	
	public String getUsername() {
		return userName;
	}
	
	public String getSubTo() {
		return subTo;
	}
	
	public void setSubTo(String sub) {
		subTo = sub;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket s) {
		socket = s;
	}
	
	public void setLoggedIn(Boolean h) {
		loggedIn = h;
	}
	
	public boolean LoggedIn() {
		return loggedIn;
	}
}
