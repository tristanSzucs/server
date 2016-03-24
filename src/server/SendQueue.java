package server;

import java.util.concurrent.ArrayBlockingQueue;

public class SendQueue {
	private ArrayBlockingQueue<Message> q;
	
	public SendQueue() {
		q = new ArrayBlockingQueue<Message>(100);
	}
	
	public void set(Message m) {
		q.add(m);
	}
	
	public Message get() throws InterruptedException {
		return q.take();
	}
	
	public Boolean isEmpty() {
		return q.isEmpty();
	}

}
