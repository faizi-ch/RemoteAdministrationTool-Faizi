package RemoteServer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FileSenderServer extends JFrame
{
	JFileChooser fileChooser;
	File selectedFile;
	private Socket server;

	public FileSenderServer(Socket server) throws IOException
	{
		super("File Chooser");

		this.server = server;

		fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile);
			String s=selectedFile.getAbsolutePath();
			String s2=selectedFile.getName();
			//System.out.println(s2);
			String s3=s2.substring(s2.lastIndexOf("."));
			System.out.println(s3);
		}

		add(fileChooser);

		setVisible(true);

		sendFile();
	}

	public void sendFile() throws IOException
	{
		if (selectedFile.exists())
		{
			ObjectOutputStream outputStream = new ObjectOutputStream(server.getOutputStream());

			outputStream.writeObject(selectedFile);
		}

	}

}
