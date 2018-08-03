package RemoteClient;

import java.io.*;
import java.net.Socket;

public class FileReceiverClient
{
	Socket client;
	ObjectInputStream inputStream;
	File receivedFile;
	FileInputStream fileIn = null;
	FileOutputStream fileOut = null;

	FileReceiverClient(Socket client)
	{
		this.client=client;
	}

	public void receiveFile() throws IOException, ClassNotFoundException
	{
		try
		{
			inputStream=new ObjectInputStream(client.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		receivedFile=(File)inputStream.readObject();

		String fileName=receivedFile.getName();
		String saveDirectory="C:\\"+fileName;

		fileIn=new FileInputStream(receivedFile);
		fileOut=new FileOutputStream(saveDirectory);

		while (fileIn.read()!=-1)
		{
			fileOut.write(fileIn.read());
		}

		fileOut.close();
		fileIn.close();

	}
}
