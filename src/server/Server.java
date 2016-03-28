package server;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		SendQueue sendQueue = new SendQueue();
		UserDatabase userDatabase = new UserDatabase( sendQueue );
		userDatabase.addUser("Ant", "pass");
		userDatabase.addUser("Trs", "pass");
		
		Scanner in = new Scanner(System.in);
		String message;
		String parts[];
		
		ExecutorService executor = Executors.newCachedThreadPool();
		Sender sender = new Sender(sendQueue);
		executor.execute(sender);
		executor.execute(new Accepter(executor, sendQueue, userDatabase));
		
		
		while(true) {
			message = in.nextLine();
			parts = message.split(" ");
			//this adds a user based on the username and password inputed after
			if(parts[0].equals("newUser")) {
				if(parts.length == 3) //if they inputed the correct number of params
					userDatabase.addUser(parts[1],parts[2]);
				else
					System.out.println("Inncorrect number of parameters - 2 needed");
				
			} else if(parts[0].equals("annonce")){
				if(parts.length >= 2) {//if they inputed the correct number of params
					message = "0\t****Sever**** : "; //start of the message to annonce
					for(int i = 1; i < parts.length; i++) message += parts[i] + " ";	//add the other words
					userDatabase.sendMessage(message);	//send the message out
				} else 
					System.out.println("Inncorrect number of parameters - at least 1 needed");				
			}  //end of else structure
		} //end of while loop
	} //end of main
} //end of class