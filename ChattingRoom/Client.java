import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends JFrame{
	private static final String DEFAULT_NAME="Guest";
	private static String name="";
	public static void main(String[] args) throws java.io.IOException {
		name=DEFAULT_NAME;
		if(args.length>0){
			name=args[0];
		}
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				MyFrame client=new MyFrame(name);
			}
		});
	}
}
