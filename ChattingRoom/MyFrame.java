import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class MyFrame extends JFrame{
	// private String userName;
	private JPanel config;
	private JTextField userName;
	private JTextField ipAddress;
	private JTextField portNum;
	private JButton connect;
	private JTextArea dialog;
	private JTextField inputField;
	private JButton send;
	private JPanel input;
	private PrintWriter toOtherSide;
	private Socket socket;
	private Scanner scan;
	private static int num=1;

	public MyFrame(String name){
		if(name.equals("Guest")){
			long timeStamp=System.currentTimeMillis()/1000;
			name=name+"_"+timeStamp;
		}
		// userName=name;
		// setSize(500,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chatting Window");
		setVisible(true);
		setLocation(400, 600);
		//border north
		config=new JPanel();
		config.add(new JLabel("User Name:"));
		userName=new JTextField(name);
		userName.setEditable(true);
		config.add(userName);
		config.add(new JLabel("HOST"));
		ipAddress=new JTextField("127.0.0.1",15);
		config.add(ipAddress);
		config.add(new JLabel("PORT"));
		portNum=new JTextField("4444",5);
		config.add(portNum);
		connect=new JButton("connect");
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(connect.getText().equals("connect")){
					try{
						createClient(ipAddress.getText(),portNum.getText());
						String newUserName=userName.getText();
						userName.setText(newUserName);
						userName.setEditable(false);
					}catch(IOException event){
					}
					connect.setText("disconnect");
				}else if(connect.getText().equals("disconnect")){
					toOtherSide.println(userName.getText()+" left this chatting room!");
					toOtherSide.flush();
					try{
						Server.clientExit(socket);
					}catch(IOException event){
					}
					connect.setText("connect");
					userName.setEditable(true);
				}
			}
		});
		config.add(connect);
		add(config,BorderLayout.NORTH);
		//border center
		dialog=new JTextArea(30,30);
		dialog.setEditable(false);
		JScrollPane scroll=new JScrollPane(dialog);
		add(scroll,BorderLayout.CENTER);
		//border south
		inputField=new JTextField(25);
		inputField.setEditable(true);
		send=new JButton("Send");
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String willSend=inputField.getText();
				Date time=new Date();
				try{
					toOtherSide.println(time);
					toOtherSide.println(userName.getText()+": "+willSend);
					toOtherSide.flush();
				}catch(Exception event){

				}
				inputField.setText("");
			}
		});
		inputField.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){
				if((int)e.getKeyChar()==e.VK_ENTER){
					String willSend=inputField.getText();
					Date time=new Date();
					try{
						toOtherSide.println(time);
						toOtherSide.println(userName.getText()+": "+willSend);
						toOtherSide.flush();
					}catch(Exception event){
					}
					inputField.setText("");
				}
			}
		});
		input=new JPanel();
		input.add(inputField);
		input.add(send);
		add(input,BorderLayout.SOUTH);
		pack(); //adjust the window size automatically
	}
	public void createClient(String ip,String portNum) throws java.io.IOException{
		int port=Integer.parseInt(portNum);
		socket=new Socket(ip,port);
		toOtherSide=new PrintWriter(socket.getOutputStream());
		toOtherSide.println(userName.getText()+" joins this chatting room!");
		toOtherSide.flush();
		scan=new Scanner(socket.getInputStream());
		new Thread(){
			public void run(){
				while(!socket.isClosed() && scan.hasNextLine()){
					dialog.append(scan.nextLine()+"\n");
				}
			}
		}.start();
	}
}
