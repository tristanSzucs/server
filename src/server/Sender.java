package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ArrayBlockingQueue;

public class Sender implements Runnable {
	private SendQueue que;
	
	public Sender(SendQueue q) {
		que = q;
		
	}
	@Override
	public void run() {
		Message m;
		ObjectOutputStream out;
		while (true) {
			try {
				m = que.get();
				System.out.println(m.getUser().getUsername() + " says " + m.getMessage());
				
				out = new ObjectOutputStream( m.getUser().getSocket().getOutputStream() );
				out.writeObject(m.getMessage());
				
				
				
				//insert code to send the message
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
			}
		}
		
	}

}
