package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MessageClientThread extends Thread
{
	private Socket socket;
	private MessageClient client;
	private Scanner in;
	
	public MessageClientThread(MessageClient _client, Socket _socket)
	{
		client = _client;
		socket = _socket;
		
		try
		{
			in = new Scanner(socket.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			client.stop();
		}
		
		start();
		
	}
	
	public void close()
	{
		if(in != null)
			in.close();
		
	}
	
	public void run()
	{
		while (true)
		{
			client.handle(in.nextLine());
		}
	}

}
