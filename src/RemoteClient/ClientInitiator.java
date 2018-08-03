package RemoteClient;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientInitiator extends JFrame
{
	private JTextArea displayArea;
	private String host;
	private Socket client;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	ClientInitiator(String host)
	{
		super("Client Connection");

		this.host = host;

		displayArea = new JTextArea();
		displayArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret) displayArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		add(new JScrollPane(displayArea), BorderLayout.CENTER);

		setSize(300, 150);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void runClient()
	{
		{
			try
			{
				connectToServer();
				//getStreams();
				new ChatAppClient(client);
				//chatAppClient.getStreams();
			}
			catch (EOFException e)
			{
				displayMessage("\nServer terminated connection");

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	void connectToServer() throws IOException
	{
		displayMessage("Attempting connection\n");
		//displayMessage("Waiting for connection\n");
		client = new Socket(InetAddress.getByName(host), 1234);

		displayMessage("Connected to: " + client.getInetAddress()
				.getHostName());
	}
	/*private void getStreams() throws IOException
	{
		outputStream=new ObjectOutputStream(client.getOutputStream());
		outputStream.flush();

		inputStream=new ObjectInputStream(client.getInputStream());
		displayArea.append("\nGot I/O streams\n");
		displayArea.append("Connection successful\n");

	}*/

	public void invokeTools() throws IOException, ClassNotFoundException
	{
			if (inputStream.readObject().equals("chat"))
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override public void run()
					{
						try
						{
							new ChatAppClient(client);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				});
			}

			//else if (inputStream.readObject().equals("file"))
				//new FileReceiverClient(client).receiveFile();
	}

	private void displayMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override public void run()
			{
				displayArea.append(message);
			}
		});
	}

	public static void main(String[] args)
	{
		ClientInitiator clientInitiator;
		if (args.length == 0)
			clientInitiator = new ClientInitiator("127.0.0.1");
		else
			clientInitiator = new ClientInitiator(args[0]);

		clientInitiator.runClient();

		try
		{
			clientInitiator.invokeTools();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
