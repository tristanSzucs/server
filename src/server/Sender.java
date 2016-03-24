package server;

import java.util.concurrent.ArrayBlockingQueue;

public class Sender implements Runnable {
	private SendQueue que;
	
	public Sender(SendQueue q) {
		que = q;
		que.set(new Message(new User("FIVE","0") , "NINE") );
	}
	@Override
	public void run() {
		Message m;
		while (true) {
			try {
				m = que.get();
				System.out.println(m.getUser().getUsername() + " says " + m.getMessage());
				//insert code to send the message
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
			}
		}
		
	}

}
