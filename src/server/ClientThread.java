package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread implements Runnable {
	Socket client;
	User user;
	SendQueue sendQueue;
	ObjectInputStream in;
	UserDatabase allUsers;
	
	
	public ClientThread(Socket s, SendQueue que, UserDatabase users) {
		client = s;
		allUsers = users;
		user = new User("FALSE", "FALSE", s);
		sendQueue = que;
		try {
			in = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void run() {
		Boolean leaving = false;
		String parts[];
		int id;
		try {
			String message = (String)in.readObject();
			parts = message.split("\t", 2);
			User tryUser = allUsers.findUser(parts[0]);
			if ( !( tryUser != null && tryUser.checkPassword(parts[1]) ) ) {
				sendQueue.set(new Message(tryUser, "FAILED") );
			} else {
				user = tryUser;
				user.setSocket(client);
				user.setLoggedIn(true);
				sendQueue.set(new Message(tryUser, "PASSED") );
				
				while(!leaving) {
					parts = ((String)in.readObject()).split("\t");
					id = Integer.parseInt(parts[0]);
					switch(id) {
					case 0: //send a message to all those in the room
						allUsers.sendMessage(user.getUsername() + " : " + parts[1], user.getSubTo());
						break;
					case 6:
						user.setLoggedIn(false);
						user.setSubTo("");
						leaving = true;
						break;
					case 7:
						user.setSubTo(parts[1]);
						break;
					case 8:
						user.setSubTo("");
						break;
					}	//end of switch
				} //end of while
			} //end of else
			in.close();
			client.close();
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
	}
	

}
