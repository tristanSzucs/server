package server;

/*
 * This class is the holder queue for all data that needs to be sent out.
 */

import java.util.concurrent.ArrayBlockingQueue;

public class SendQueue {
	//the spot to hold all the data
	private ArrayBlockingQueue<Message> q;
	
	//a constructor
	public SendQueue() {
		q = new ArrayBlockingQueue<Message>(100);
	}
	
	//adds the message 
	public void set(Message m) {
		q.add(m);
	}
	
	//gets the next message to send
	public Message get() throws InterruptedException {
		return q.take();
	}
	
	//if it is empty
	public Boolean isEmpty() {
		return q.isEmpty();
	}

}
