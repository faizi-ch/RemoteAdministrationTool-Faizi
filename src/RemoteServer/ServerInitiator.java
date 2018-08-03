package RemoteServer;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInitiator extends JFrame
{
	private int port;
	private JTextArea displayArea;
	private ServerSocket server;
	private Socket connection;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	ServerInitiator()
	{
		super("Server Connection");

		String p = JOptionPane.showInputDialog("Enter port to listen:");
		port = Integer.parseInt(p);

		displayArea = new JTextArea();
		displayArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret) displayArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		add(new JScrollPane(displayArea), BorderLayout.CENTER);

		setSize(300, 150);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void runServer()
	{
		try
		{
			server = new ServerSocket(port);

			while (true)
			{
				try
				{
					waitForConnection();
					//getStreams();
					new ChatAppServer(connection);
				}
				catch (EOFException e)
				{
					displayMessage("\nServer terminated connection");
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void waitForConnection() throws IOException
	{
		displayMessage("Listening port: "+port+"\n");
		displayMessage("Waiting for connection\n");
		connection = server.accept();

		displayMessage("Connection received from: " + connection
				.getInetAddress().getHostName() + "\n");
		//new RemoteToolsWindow(connection);
	}
	/*private void getStreams() throws IOException
	{
		outputStream=new ObjectOutputStream(connection.getOutputStream());
		outputStream.flush();

		inputStream=new ObjectInputStream(connection.getInputStream());

		displayArea.append("\nGot I/O streams\n");


	}*/

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
		ServerInitiator serverInitiator = new ServerInitiator();
		serverInitiator.runServer();
	}

}