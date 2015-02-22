package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class MessageClient 
{
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		try(Socket s = new Socket(InetAddress.getLocalHost().getHostName(), 8189))
		{
			InputStream inStream = s.getInputStream();
			OutputStream outStream = s.getOutputStream();
			
			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream, true);
			Scanner scanner = new Scanner(System.in);
			

			String response = in.nextLine();
			
			if(response.trim().equals("Hello! What is your username?"));
			{
				System.out.println("What is your username?");
				String username = scanner.nextLine();
				out.println(username);
			}
			
			response = in.nextLine();
			System.out.println(response);
			
			System.out.println(response.trim().equals("Welcome!"));
			
			
			while(!(response.trim().equals("Welcome!")))
			{
				System.out.println(response);
				System.out.println("Invalid username! Try again!");
				String username = scanner.nextLine();
				out.println(username);
			}
			
			while(in.hasNextLine())
			{
				response = in.nextLine();
				System.out.println(response);
			}
			
			
		}
		
	}

}
