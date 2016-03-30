package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		SendQueue sendQueue = new SendQueue();
		UserDatabase userDatabase = new UserDatabase( sendQueue );
		userDatabase.addUser("Ant", "pass");
		userDatabase.addUser("Trs", "pass");
		Scanner in;
		String message;
		String parts[];
		
		
		//if a second argument is given it is a file path to a file for starting up the user database
		/*
		 * The file is formatted to be a user password\n and that is it on a line.
		 * The last one does not have a new line.
		 */
		if(args.length == 1) {
			try {
				//create a scanner for the file
				in = new Scanner( new File(args[0]) );
			
				//while there is more lines
				System.out.println("Starting readin");
				while(in.hasNextLine()) {
					message = in.nextLine();
					parts = message.split(" ");
					System.out.println("Adding user: " + parts[0]);
					userDatabase.addUser(parts[0], parts[1]);
				} //end of while
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found");
			} 
		} //end of if
		
		
		in = new Scanner(System.in);
		
		
		
		
		ExecutorService executor = Executors.newCachedThreadPool();
		Sender sender = new Sender(sendQueue);
		executor.execute(sender);
		executor.execute(new Accepter(executor, sendQueue, userDatabase));
		
		User user; //used later in the case a user is specified
		
		
		//a loop to keep getting inputs to the consold and processing them.
		while(true) {
			
			message = in.nextLine();
			parts = message.split(" ");
			//this adds a user based on the username and password inputed after
			if(parts[0].equals("newUser")) {
				if(parts.length == 3) //if they inputed the correct number of params
					userDatabase.addUser(parts[1],parts[2]);
				else
					System.out.println("Inncorrect number of parameters - 2 needed");
				
				
			} else if(parts[0].equals("announce")){
				if(parts.length >= 2) {//if they inputed the correct number of params
					message = "0\t****Sever**** : "; //start of the message to announce
					for(int i = 1; i < parts.length; i++) message += parts[i] + " ";	//add the other words
					userDatabase.sendMessage(message);	//send the message out
				} else 
					System.out.println("Inncorrect number of parameters for announce - at least 1 needed");		
				
				//to display what each thing does - if the user is unsure
			} else if(parts[0].equals("help")){
				System.out.println("use newUser username password   -- to generate a new user");
				System.out.println("use annonce message   -- to send out a message to all rooms");
				System.out.println("use changePassword username password   -- to change a users password");
				System.out.println("allUsers   -- to print out a list of all users on the server");
				System.out.println("activeUsers -- to print a list of all users that are logged in");
				
			}  else if(parts[0].equals("changePassword")){
				if(parts.length == 3) {//if they inputed the correct number of params
					
					
					user = userDatabase.findUser(parts[1]);
					if(user != null) {	//if a user was found
						user.changePassword(parts[2]);	//change the password to what is specified
					} else{ //if no user was found tell the user
						System.out.println("No user " + parts[1]); //let the user know
					} //end of if else found
					
					
				} else 
					System.out.println("Inncorrect number of parameters for changePassword- 2 needed");				
			
				//allUsers prints a list of all users active or not
			}  else if(parts[0].equals("allUsers")){
				userDatabase.allUsers();
				
				
				//activeUsers prints a list of all users currently logged in
			} else if(parts[0].equals("activeUsers")){
				userDatabase.activeUsers();
			}
			else {		//if it was not an option
				System.out.println("Command not recognized - use help to see what commands you can use");
			}//end of else structure
			
			
		} //end of while loop

	} //end of main
} //end of class