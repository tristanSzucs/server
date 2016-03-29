package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
 * ClientThread is responsible for recieveing all messages a particular client sends to the server.
 * It also handles the start up login
 */
public class ClientThread implements Runnable {
	Socket client;	//the client they are using
	User user;	//the user they are
	SendQueue sendQueue;	//the queue to send messages
	ObjectInputStream in;	//the unput stream
	UserDatabase allUsers;	//the database of all users
	ObjectOutputStream out;
	
	//the constructor requires the socket, the send queue and the user database
	public ClientThread(Socket s, SendQueue que, UserDatabase users) {
		client = s;	//the socket
		allUsers = users;	//the users database
		user = new User("FALSE", "FALSE");	//since they are not logged in a false user is made
			//note that it is not added to the database
		
		sendQueue = que;	//setup the sendQueu
		try {
			in = new ObjectInputStream(client.getInputStream() );
			out = new ObjectOutputStream(client.getOutputStream());		//creating an output thread allows an input thread on the client side
		} catch (IOException e) {
			
		} //end of catch
	} //end of constructor
	
	//what happens when executed
	public void run() {
		Boolean leaving = false; //a toogle for when they are going to log out
		String parts[];			//used to break up the section of a message
		String message;			//the entire message
		User tryUser;			//the user that we are going to try to log then in as
		int id;					//the id of the type of message they are sending
		try {
			
			//keep trying to log them in
			while (true) {
				//set up the socket associated with the fake user to send back messages with the queue
				user.setOutput(out);
				user.setLoggedIn(true);
				//read in a messaage form them
				message = (String)in.readObject();
				parts = message.split("\t", 3);
				//break up the message by tabs
				
				//if they send 2 parts then it is a user and password
				if (parts.length == 2) {
					//find the user they are checking
					tryUser = allUsers.findUser(parts[0]);
					//if it exists and the password is correct then break out of the loop
					if ( tryUser != null && tryUser.checkPassword(parts[1]) ) {
						//sends a message saying they passed
						sendQueue.set(new Message(user, "passed") );
						break;
						//otherwise they failed
					} else {
						//send a message that it failed
						sendQueue.set(new Message(user, "failed") );
					}
					
				}  else {
					//if anything else then they failed
					sendQueue.set(new Message(user, "failed") );
					
				} //end of if else structures
			}//end of while
			
			
			//since they go through the user is now the logged in user
			user = tryUser;
			allUsers.startupList(user);
			
			//set up the socket with the logged in user
			user.setOutput(out);	
			user.setLoggedIn(true);	//toggle the logged in value
			
			
			//while they are not leaving keep listening
			while(!leaving) {
				//read in a string and split it up
				parts = ((String)in.readObject()).split("\t");
				
				//a switch case based on the ID that they send us
				id = Integer.parseInt(parts[0]);
				switch(id) {
				case 0: //send a message to all those in the room
					allUsers.sendMessage("0\t" + user.getUsername() + " : " + parts[1], user.getSubTo());
					break;
				case 7:
					//they are requesting to be subbed to a new user
					user.setSubTo(parts[1]); //set subbed to a room
					//sned a message to all logged in that he entered
					//three parts ID, Room, MSG
					allUsers.sendMessage("0\t" + user.getUsername() + " : entered the room", parts[1]);
					allUsers.sendMessage("1\t" + user.getSubTo() );
					break;
				case 8:
					//leaving a room
					//send a message to all that the user left
					//ID, message
					allUsers.sendMessage("0\t" + user.getUsername() + " : left the room", user.getSubTo());
					allUsers.sendMessage("2\t" + user.getSubTo() );
					user.setSubTo(""); //set the subbed to none
					break;
				}	//end of switch
			} //end of while
		//since they left close the things
		in.close();
		client.close();
			
		} catch (ClassNotFoundException | IOException | java.lang.NumberFormatException e) {
			
		} finally {
			//once the user exits a exception is caused in the socket - this loggs them out properly
			if (user != null) {
				user.setLoggedIn(false);
				if(!user.getSubTo().equals("")) {
					//if the user was subbed still then remove that user from other clients in the room
					allUsers.sendMessage("2\t" + user.getSubTo() );
					allUsers.sendMessage("0\t" + user.getUsername() + " : left the room", user.getSubTo());
					user.setSubTo(""); //set the subbed to none
				} //end of if
			} //end of if
			
		}//end of finally
		
	} //end of run
	

} //end of class
