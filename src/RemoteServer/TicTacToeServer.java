package RemoteServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicTacToeServer extends JFrame
{
	private String[] board=new String[9];
	private JTextArea outputArea;
	private ServerSocket server;
	private int currentPlayer;
	private final static int PLAYER_X=0;
	private final static int PLAYER_O=1;
	private final static String[] MARKS={"X","O"};
	private ExecutorService runGame;
	private Lock gameLock;
	private Condition otherPlayerConnected;
	private Condition otherPlayerTurn;

	TicTacToeServer()
	{
		super("Tic-Tac-Toe Server");

		runGame= Executors.newFixedThreadPool(2);
		gameLock=new ReentrantLock();

		otherPlayerConnected=gameLock.newCondition();

		otherPlayerTurn=gameLock.newCondition();

		for (int i = 0; i < 9; i++)
		{
			board[i]=new String("");
		}

		currentPlayer=PLAYER_X;

		try
		{
			server=new ServerSocket(1234,2);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		outputArea=new JTextArea();
		add(outputArea, BorderLayout.CENTER);
		outputArea.setText("Server awaiting connections\n");

		setSize(300,300);
		setVisible(true);
	}

	void execute()
	{

	}

	private class Player implements Runnable
	{
		private Socket connection;
		private Scanner input;
		private Formatter output;
		private int playerNumber;
		private String mark;
		private boolean suspended=true;

		Player(Socket socket, int number)
		{
			playerNumber=number;
			mark=MARKS[playerNumber];
			connection=socket;

			try
			{
				input=new Scanner(connection.getInputStream());
				output=new Formatter(connection.getOutputStream());
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}

		void otherPlayerMoved(int location)
		{
			output.format("Opponent moved\n");
			output.format("%d\n",location);
			output.flush();
		}

		@Override public void run()
		{

		}
	}
}
