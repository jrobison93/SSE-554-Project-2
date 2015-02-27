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
		try(Socket s = new Socket(InetAddress.getLocalHost().getHostName(), 8189))
		{
			InputStream inStream = s.getInputStream();
			OutputStream outStream = s.getOutputStream();
			
			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream, true);
			Scanner scanner = new Scanner(System.in);
			thread = new Thread(this);
			thread.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
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
			

			String response = in.nextLine();
			
			String username;
			
			if(response.trim().equals("Hello! What is your username?"));
			{
				System.out.println("What is your username?");
				username = scanner.nextLine();
				out.println(username);
			}
			
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
			

			client = new MessageClientThread(this, s);
			
			System.out.println("Fire\n");
			String recipient = scanner.nextLine();
			System.out.println(recipient);
			out.println(recipient);
			response = in.nextLine().trim();
			while(!response.equals("The user does not exist."))
			{
				System.out.println(response + ": The user does not exist.\n");
				recipient = scanner.nextLine();
				out.println(recipient);
				response = in.nextLine().trim();
				
			}

			
			while(thread != null)
			{
				out.println(scanner.nextLine());
			}
			
			
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
