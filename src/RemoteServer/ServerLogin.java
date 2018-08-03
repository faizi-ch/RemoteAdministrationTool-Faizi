package RemoteServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

public class ServerLogin extends JFrame implements ActionListener
{
	JLabel userNameLabel, passwordLabel;
	JTextField userNameField;
	JPasswordField passwordField;
	JButton signIn, signUp, forgetPassword;
	private Scanner read;
	BufferedReader in;

	ServerLogin()
	{
		super("Sign In");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(400, 200);
		setLayout(new FlowLayout());

		userNameLabel = new JLabel("Username:");
		add(userNameLabel);
		userNameField = new JTextField(12);
		add(userNameField);

		passwordLabel = new JLabel("Password:");
		add(passwordLabel);
		passwordField = new JPasswordField(12);
		passwordField.addActionListener(this);
		add(passwordField);

		signIn = new JButton("Sign In");
		signIn.setToolTipText("Log In");
		signIn.addActionListener(this);
		add(signIn);

		signUp = new JButton("Sign Up");
		signUp.setToolTipText("Create new account");
		signUp.addActionListener(this);
		add(signUp);

		forgetPassword = new JButton("Forget password?");
		forgetPassword.setToolTipText("Recover password");
		forgetPassword.addActionListener(this);
		add(forgetPassword);

		setVisible(true);
	}

	@Override public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == signIn || e.getSource() == passwordField)
		{
			String user = userNameField.getText();
			String pass = new String(passwordField.getPassword());
			String userAndPass = user + ":" + pass;
			read = new Scanner("C:\\Users\\Faizi\\Desktop\\users.txt");
			String line;
			boolean check = false;
			try
			{
				in = new BufferedReader(
						new FileReader("C:\\Users\\Faizi\\Desktop\\users.txt"));
			}
			catch (FileNotFoundException e1)
			{
				JOptionPane.showMessageDialog(null,
						"Error opening or creating file");
				System.exit(1);
			}
			try
			{
				while ((line = in.readLine()) != null)
				{
					if (line.equals(userAndPass))
					{
						check = true;
						break; //break out of loop now
					}
					else
					{
						check = false;
						//break;
					}

				}
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}

			if (check)
			{
				check = false;
				ServerInitiator serverInitiator = new ServerInitiator();
				serverInitiator.runServer();
			}

			else
			{
				JOptionPane.showMessageDialog(null, "not found");
				check = false;
			}

		}

		else if (e.getSource() == signUp)
			new SignUp();

		else if (e.getSource() == forgetPassword)
			new ForgetPassword();

	}

	public static void main(String[] args)
	{
		new ServerLogin();
	}

	class ForgetPassword extends JFrame implements ActionListener
	{
		private JComboBox questionsBox;
		private String[] questions = { "Where your mother born?",
				"What was the name of your elementary / primary school?",
				"In what year was your father born?",
				"What is the last name of the teacher who gave you your first failing grade?",
				"What was your childhood nickname?",
				"What is the name of your favorite childhood friend?",
				"What was your favorite place to visit as a child?",
				"What is your favorite movie?", "What is your favorite color?",
				"Which is your favorite web browser?",
				"What is the name of your first boyfriend or girlfriend?" };
		private JLabel questionLabel, answerLabel;
		private JTextField answerField;
		private int selectedQuestionIndex;
		private JButton findPassword;

		ForgetPassword()
		{
			super("Sign Up");
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setSize(500, 400);
			setLayout(new FlowLayout());

			questionLabel = new JLabel("Select security question:");
			add(questionLabel);

			questionsBox = new JComboBox(questions);
			add(questionsBox);
			questionsBox.addItemListener(new ItemListener()
			{
				@Override public void itemStateChanged(ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
						selectedQuestionIndex = questionsBox.getSelectedIndex();
				}
			});

			answerLabel = new JLabel("Answer:");
			add(answerLabel);
			answerField = new JTextField(12);
			answerField.addActionListener(this);
			add(answerField);

			findPassword = new JButton("Find Password");
			findPassword.addActionListener(this);
			add(findPassword);

			setVisible(true);
		}

		@Override public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == findPassword || e.getSource() == answerField)
			{
				selectedQuestionIndex = questionsBox.getSelectedIndex();
				String answer = answerField.getText();
				selectedQuestionIndex = questionsBox.getSelectedIndex();
				String questionIndex = Integer.toString(selectedQuestionIndex);
				String qIndexAndAnswer = questionIndex + ":" + answer;
				read = new Scanner("C:\\Users\\Faizi\\Desktop\\users.txt");
				String line;
				boolean check = false;
				int count=0;
				try
				{
					in = new BufferedReader(new FileReader(
							"C:\\Users\\Faizi\\Desktop\\users.txt"));
				}
				catch (FileNotFoundException e1)
				{
					JOptionPane.showMessageDialog(null,
							"Error opening or creating file");
					System.exit(1);
				}
				try
				{
					while ((line = in.readLine()) != null)
					{

						if (line.equals(qIndexAndAnswer))
						{
							System.out.println(count);
							check = true;
							break; //break out of loop now
						}
						else
						{
							count++;
							check = false;
							//break;
						}

					}
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				int counter=0;
				int temp=count-3;
				String t="";
				if (check)
				{
					for(int i = 0; i < count; i++){
						try
						{
							t=in.readLine();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}}
					System.out.println(t);
					/*try
					{
						while((line = in.readLine()) != null) {
							counter++;
							if(!line.equals(qIndexAndAnswer))
							{
								t=line;
								break;
							}
						}
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					System.out.println(t);*/
							//String usernameAndPassword = line;

				}

				else
				{
					JOptionPane.showMessageDialog(null,"not found");
					check=false;
				}
			}
		}
	}

}
class SignUp extends JFrame implements ActionListener
{
	private JLabel userNameLabel, passwordLabel1, passwordLabel2, confirmPasswordLabel, questionLabel, answerLabel;
	private JTextField userNameField, answerField;
	JPasswordField passwordField1, passwordField2;
	private String password1, password2;
	private JComboBox questionsBox;
	private String[] questions = { "Where your mother born?",
			"What was the name of your elementary / primary school?",
			"In what year was your father born?",
			"What is the last name of the teacher who gave you your first failing grade?",
			"What was your childhood nickname?",
			"What is the name of your favorite childhood friend?",
			"What was your favorite place to visit as a child?",
			"What is your favorite movie?", "What is your favorite color?",
			"Which is your favorite web browser?",
			"What is the name of your first boyfriend or girlfriend?" };

	String username, password, answer;
	private int selectedQuestionIndex;
	private JButton register;
	FileWriter f1;

	SignUp()
	{
		super("Sign Up");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(300,400);
		setLayout(new FlowLayout());

		userNameLabel=new JLabel("Enter username:");
		add(userNameLabel);

		userNameField=new JTextField(12);
		add(userNameField);

		passwordLabel1=new JLabel("Enter password:");
		add(passwordLabel1);

		passwordField1=new JPasswordField(12);

		add(passwordField1);

		passwordLabel2=new JLabel("Confirm password:");
		add(passwordLabel2);

		passwordField2=new JPasswordField(12);


		password2=new String(passwordField2.getPassword());
		add(passwordField2);
		confirmPasswordLabel=new JLabel("");
		add(confirmPasswordLabel);
		/*passwordField2.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				char pass1[]=passwordField1.getPassword();
				password1=new String(pass1);
				char pass2[]=passwordField2.getPassword();
				password2=new String(pass2);
					if (password1.equals(password2))
					{
						confirmPasswordLabel.setText("Password Matched!");
						confirmPasswordLabel.setForeground(Color.GREEN);
					}
				}
		});*/


		questionLabel=new JLabel("Select security question:");
		add(questionLabel);

		questionsBox=new JComboBox(questions);
		add(questionsBox);
		questionsBox.addItemListener(new ItemListener()
		{
			@Override public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange()==ItemEvent.SELECTED)
					selectedQuestionIndex=questionsBox.getSelectedIndex();
			}
		});

		answerLabel=new JLabel("Answer:");
		add(answerLabel);
		answerField=new JTextField(12);
		add(answerField);

		register=new JButton("Register");
		add(register);
		register.addActionListener(this);
		/*register.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{

				//System.out.println(username);
				//System.out.println(answer);
				//openFIle();
				//output.format("%s %d %s", username,selectedQuestionIndex,answer);

			}
		});*/

		setVisible(true);

	}

	@Override public void actionPerformed(ActionEvent e)
	{
		username=userNameField.getText();
		char pass1[]=passwordField1.getPassword();
		password1=new String(pass1);
		char pass2[]=passwordField2.getPassword();
		password2=new String(pass2);
		selectedQuestionIndex=questionsBox.getSelectedIndex();
		String questionIndex=Integer.toString(selectedQuestionIndex);
		answer=answerField.getText();

		if(e.getSource()==register)
		{
			if (!password1.equals(password2))
			{
				confirmPasswordLabel.setText("Passwords are different!");
				confirmPasswordLabel.setForeground(Color.RED);
			}
			else
			{
				confirmPasswordLabel.setText("");
				FileWriter fileWriter = null;
				try
				{
					fileWriter = new FileWriter("C:\\Users\\Faizi\\Desktop\\users.txt",true);
					Formatter formatter = new Formatter(fileWriter);
					String usernameAndPassword=username+":"+password1;
					String qIndexAndAnswer=questionIndex+":"+answer;
					formatter.format("%s%n%s%n", usernameAndPassword,qIndexAndAnswer);
					fileWriter.close();
					setVisible(false);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}

		}
	}

	void openFIle()
	{
		FileReader fr = null;
		try
		{
			//output=new Formatter("C:\\Users\\Faizi\\Desktop\\users.txt");
			fr = new FileReader("C:\\Users\\Faizi\\Desktop\\test.txt");

		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"Error opening or creating file");
			System.exit(1);
		}
	}

}
