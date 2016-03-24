package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Accepter implements Runnable {
	private ServerSocket incoming;
	private ExecutorService executor;
	private SendQueue sendQueue;
	private UserDatabase allUsers;
	
	public Accepter(ExecutorService ex, SendQueue sendTo, UserDatabase users) {
		executor = ex;
		sendQueue = sendTo;
		allUsers = users;
		try {
			incoming = new ServerSocket(4001);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		while(true) {
			try {
				executor.execute(  new ClientThread(incoming.accept(), sendQueue, allUsers)  );
				System.out.println("New User");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
