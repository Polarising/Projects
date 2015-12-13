import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Server{
	private static volatile ArrayList<Socket> clientList=new ArrayList<Socket>();
	private static BlockingQueue<String> messageQueue=new LinkedBlockingQueue<String>();
	private static final int DEFAULT_PORT=4444;
	private static final String DEFAULT_HOST="127.0.0.1";
	private static Socket client;

	public static void main(String[] args) throws java.io.IOException {
		int port=DEFAULT_PORT;
		if(args.length>0){
			port=Integer.parseInt(args[0]);
		}
		ServerSocket s=new ServerSocket(port);
		//create a thread which will send message to all clients
		new Thread(){
			public synchronized void run(){
				while(true){
					try{
						String message=messageQueue.take();
						for(Socket socket : clientList){
							try{
								PrintWriter toClient=new PrintWriter(socket.getOutputStream());
								toClient.println(message);
								toClient.flush();
							}catch(IOException e){
							}
						}
					}catch(InterruptedException e){
					}
				}
			}
		}.start();
		//create a thread for each socket to receive messages
		while(true){
			client=s.accept();
			clientList.add(client);
			new Thread(){
				public synchronized void run(){
					try{
						Scanner scan=new Scanner(client.getInputStream());
						while(scan.hasNextLine()){
							try{
								messageQueue.put(scan.nextLine());
							}catch(InterruptedException e){
							}
						}
					}catch(IOException e){
					}
				}
			}.start();
		}
	}
	public static void clientExit(Socket client) throws java.io.IOException{
		clientList.remove(client);
		client.close();
	}
}
