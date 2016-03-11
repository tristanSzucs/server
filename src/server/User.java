package server;

public class User {
	private String userName;
	private String password;
	
	public User(String user, String pass) 
	{
		userName = user;
		password = pass;
	}
	
	public boolean checkPassword(String password)  
	{
	
		return this.password == password;
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
	
}
