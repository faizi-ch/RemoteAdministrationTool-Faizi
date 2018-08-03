package RemoteServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RemoteToolsWindow extends JFrame implements ActionListener
{
	private JButton chatButton;
	private JButton remoteDesktopButton;
	private JButton tictactoeButton;
	private JButton fileSendingButton;
	private Socket connection;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	RemoteToolsWindow(Socket connection)
	{
		super("Remote Administration Tools");
		setSize(150, 300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		this.connection = connection;
		/*try
		{
			getStreams();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}*/

		chatButton = new JButton("Chat");
		chatButton.addActionListener(this);
		add(chatButton);

		fileSendingButton=new JButton("Send File");
		fileSendingButton.addActionListener(this);
		add(fileSendingButton);

		remoteDesktopButton = new JButton("Remote Desktop");
		add(remoteDesktopButton);

		tictactoeButton = new JButton("Tic-Tac-Toe");
		add(tictactoeButton);


		setVisible(true);
	}

	/*private void getStreams() throws IOException
	{
		outputStream=new ObjectOutputStream(connection.getOutputStream());
		outputStream.flush();

		inputStream=new ObjectInputStream(connection.getInputStream());

		//displayArea.append("\nGot I/O streams\n");


	}*/

	@Override public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==chatButton)
		{
			try
			{
				new ChatAppServer(connection);
				outputStream.writeObject("chat");

			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}

		else if(e.getSource()==fileSendingButton)
		{
			try
			{
				//outputStream.writeObject("file");
				new FileSenderServer(connection);

			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}

		}
	}
}
