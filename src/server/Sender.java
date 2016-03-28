package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
/*
 * This class is a thread that sends messages as they are added to the queue.
 * It is the only one that performs this action to prevent possible collisions.
 */
public class Sender implements Runnable {
	private SendQueue que;	//the queue it needs to check
	
	//the constructor requires the queue to look at
	public Sender(SendQueue q) {
		que = q;
	}
	
	
	@Override
	public void run() {
		Message m;
		ObjectOutputStream out;
		
		//while running keep doing this
		while (true) {
			try {
				m = que.get(); //get the next message
				//get the output stream		
				out = m.getUser().getOutput();
				//write the message after checking that it is ok
				if(m.getUser().LoggedIn() && out != null) {
					out.writeObject(m.getMessage());
					out.flush();
					System.out.println("Sent " + m.getMessage() );
				}
			} catch (InterruptedException | IOException  e) {

				//e.printStackTrace();
			} finally {
				
			} //end of catch
		} //end of while
		
	} //end of run

} //end of class
 