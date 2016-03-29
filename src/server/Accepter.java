package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;


/*
 * This thread is the first thread that impacts new clients.  It spawns ClientThreads for each client 
 * that connects to it
 */
public class Accepter implements Runnable {
	private ServerSocket incoming;		//the incoming socket is needed to get new clinets
	private ExecutorService executor;	//executor is needed to execute new threads
	private SendQueue sendQueue;		//sendQueue is needed to pass on
	private UserDatabase allUsers;		//the UserDatabase is also passed on
	
	//the constructor gets info needed to perform all of it's duties
	public Accepter(ExecutorService ex, SendQueue sendTo, UserDatabase users) {
		executor = ex;	//copy the needed data into identifiers
		sendQueue = sendTo;
		allUsers = users;
		try {
			incoming = new ServerSocket(4001);		//create the socket needed
		} catch (IOException e) {
						
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				//while it is running
				//gets a client socket, makes a new Client Thread with needed data
				//executes the thread
				executor.execute(  new ClientThread(incoming.accept(), sendQueue, allUsers)  );
			} catch (IOException e) {
								
			}
		}
		
	}

}
