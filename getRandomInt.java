import java.util.*;
import java.lang.*;
import java.io.*;

//notice: please change the class name to same with the file XX.java name!
class Solution
{
	public static void main (String[] args) throws java.lang.Exception
	{
		Solution test=new Solution();
		int num=test.getRandomInt(10,20);
		System.out.println(num);
	}
	//generate a random num between min(inclusive) and max(exclusive)
	private int getRandomInt(int min,int max){
	    //Method 1:
		int num=(int)(min+Math.random()*(max-min+1));
		//Method 2:
		// int num=min+new Random().nextInt(max-min+1);
		return num;
	}
}
