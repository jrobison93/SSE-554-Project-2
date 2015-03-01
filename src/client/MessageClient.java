package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class MessageClient implements Runnable
{
	
	private InputStream inStream;
	private OutputStream outStream;
	
	private Scanner in;
	private PrintWriter out;
	private Scanner scanner;
	
	private Thread thread;
	private MessageClientThread client;
	
	
	public MessageClient()
	{
		thread = new Thread(this);
		thread.start();
		
	}
	
	public static void main(String[] args)
	{
		MessageClient client = new MessageClient();
		
	}

	@Override
	public void run() 
	{
		try(Socket s = new Socket(InetAddress.getLocalHost().getHostName(), 8189))
		{
			InputStream inStream = s.getInputStream();
			OutputStream outStream = s.getOutputStream();
			
			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream, true);
			Scanner scanner = new Scanner(System.in);
			out.flush();
			
			int pubNums[] = new int[2];
			
			pubNums[0] = in.nextInt();
			pubNums[1] = in.nextInt();
			
			int secretInt = Diffie.Secret(pubNums[0]);
			int secretNum = Diffie.GenMessage(pubNums, secretInt);
			
			out.println(secretNum);
			
			int serverSecret = in.nextInt();
			
			int secretKey = Diffie.GenKey(pubNums, serverSecret, secretInt);
			
			System.out.println("The secret key is " + secretKey);
			out.println(secretKey);
			
			in.nextLine();
			String response = in.nextLine();
			System.out.println(response);
			
			String username;
			
			username = scanner.nextLine();
			out.println(username);
			
			response = in.nextLine();
			System.out.println(response);
			
			
			while(!(response.trim().equals("Welcome, " + username + "!")))
			{
				System.out.println(response);
				System.out.println("Invalid username! Try again!");
				username = scanner.nextLine();
				out.println(username);
				response = in.nextLine();
			}
			
			int users = in.nextInt();
			System.out.println(users);
			for(int x = 0; x < users + 3; x++)
				System.out.println(in.nextLine());
			

			
			//System.out.println("Who would you like to message?\n");
			String recipient = scanner.nextLine();
			System.out.println(recipient);
			out.println(recipient);
			response = in.nextLine();
			while(response.equals("User not found."))
			{
				System.out.println(response + ": The user does not exist.\n");
				recipient = scanner.nextLine();
				out.println(recipient);
				response = in.nextLine().trim();
				
			}
			

			client = new MessageClientThread(this, s);
			
			while(thread != null)
			{
				out.println(scanner.nextLine());
			}
			
			System.out.println("done");
			
			
		} catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void handle(String message)
	{
		System.out.println(message);
	}
	
	public void stop()
	{
		if(thread != null)
		{
			thread.stop();
			thread = null;
		}
		
		if(in != null)
			in.close();
		if(out != null)
			out.close();
		
		client.close();
		client.stop();
		
	}

}
