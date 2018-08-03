package RemoteClient;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatAppClient extends JFrame
{
	JTextField enterField;
	JTextArea displayArea;
	JTextPane textPane;
	//private String chatName;
	Socket client;
	ObjectOutputStream outputStream;
	ObjectInputStream inputStream;

	public ChatAppClient(Socket client) throws IOException
	{
		super("Remote Chat (Client)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);

		this.client = client;

		//chatName=JOptionPane.showInputDialog("Enter chat name:");

		enterField = new JTextField();
		enterField.setEditable(true);
		enterField.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				sendMessage(e.getActionCommand());
				enterField.setText("");
			}
		});

		add(enterField, BorderLayout.SOUTH);

		/*displayArea=new JTextArea();
		displayArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret) displayArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		add(new JScrollPane(displayArea), BorderLayout.CENTER);*/

		textPane = new JTextPane();
		textPane.setEditable(false);
		add(new JScrollPane(textPane), BorderLayout.CENTER);

		setVisible(true);
		getStreams();
	}

	public void getStreams() throws IOException
	{
		outputStream = new ObjectOutputStream(client.getOutputStream());
		outputStream.flush();

		inputStream = new ObjectInputStream(client.getInputStream());

		//displayArea.append("Start chatting\n");
		appendToPane(textPane, "Start chatting:", Color.DARK_GRAY);

		String message = "";
		do
		{
			try
			{
				message = (String) inputStream.readObject();
				displayMessageServer("\n" + message);
			}
			catch (ClassNotFoundException e)
			{
				appendToPane(textPane, "\nUnknown object type received",
						Color.RED);
			}
		}
		while (!message.equals("SERVER>>> TERMINATE"));
	}

	private void appendToPane(JTextPane tp, String msg, Color c)
	{
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily,
				"Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment,
				StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		//tp.replaceSelection(msg);

		StyledDocument doc = textPane.getStyledDocument();
		try
		{
			doc.insertString(doc.getLength(), msg, aset);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	private void sendMessage(String message)
	{
		try
		{
			outputStream.writeObject("CLIENT>>> " + message);
			outputStream.flush();
			displayMessage("\nCLIENT>>> " + message);
		}
		catch (IOException io)
		{
			//displayArea.append("\nError writing object");
			appendToPane(textPane, "\nError writing object", Color.RED);
		}
	}

	private void displayMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override public void run()
			{
				//displayArea.append(message);
				appendToPane(textPane, message, Color.GREEN);
			}
		});
	}

	private void displayMessageServer(final String message)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override public void run()
			{
				//displayArea.append(message);
				appendToPane(textPane, message, Color.BLUE);
			}
		});
	}

}
