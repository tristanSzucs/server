package server;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		SendQueue sendQueue = new SendQueue();
		UserDatabase userDatabase = new UserDatabase( sendQueue );
		//Scanner in = new Scanner(System.in);
		ExecutorService executor = Executors.newCachedThreadPool();
		Sender sender = new Sender(sendQueue);
		executor.execute(sender);
		executor.execute(new Accepter(executor, sendQueue, userDatabase));
		
		
		//while(true) {
			//sendQueue.set(new Message(new User("IN", "())("),in.nextLine() ) );
		//}
	}
}