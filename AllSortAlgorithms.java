
import java.util.*;
import java.lang.*;
import java.io.*;

class Solution
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		int[] num={3,2,5,7,6,8,5,10,9,-1,14,1,-3};
		int[] num2={13,21,43,11,22,32,56,99,65,43,21,17};
		Solution test=new Solution();
		test.radixSort(num2);
		System.out.println(Arrays.toString(num2));
	}
	private void bucketSort(int[] num){
		
	}
	
	private void radixSort(int[] num){
		if(num==null || num.length==0){
			return;
		}
		//usually used for nums with same digits and all positive
		//sorted by digit,and from low digit to high digit
		String[] str=new String[num.length];
		for(int i=0;i<num.length;i++){
			str[i]=String.valueOf(num[i]);
		}
		int digits=str[0].length();
		for(int i=digits-1;i>=0;i--){
			ArrayList<String> list=new ArrayList<String>();
			for(int j=0;j<str.length;j++){
				for(int m=0;m<list.size();m++){
					if(str[j].charAt(i) < list.get(m).charAt(i)){
						list.add(m,str[j]);
						break;
					}
				}
				if(list.size()==j){
					list.add(str[j]);
				}
			}
			for(int n=0;n<str.length;n++){
				str[n]=list.get(n);
			}
		}
		for(int i=0;i<num.length;i++){
			num[i]=Integer.parseInt(str[i]);
		}
	}
	
	private void countingSort(int[] num){
		if(num==null || num.length==0){
			return;
		}
		//usually used for range=max-min is small
		int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
		for(int value : num){
			if(value<min){
				min=value;
			}
			if(value>max){
				max=value;
			}
		}
		int[] temp=new int[max-min+1];
		int[] sorted=new int[num.length];
		int index=-1;
		for(int value : num){
			index=value-min;
			temp[index] = temp[index]+1;//temp[i] contains # of elements which value=i+min
		}
		for(int i=1;i<temp.length;i++){
			temp[i]=temp[i]+temp[i-1];//temp[i] contains # of elements which value<=i+min
		}
		for(int i=num.length-1;i>=0;i--){
			index=num[i]-min;//get the index in temp
			sorted[temp[index]-1]=num[i];
			temp[index]=temp[index]-1;
		}
		for(int i=0;i<num.length;i++){
			num[i]=sorted[i];
		}
	}
	
	private void quickSort(int[] num){
		if(num==null || num.length==0){
			return;
		}
		//each time pick up a pivot,so all num[i]<pivot is in left,num[i]>pivot is in right
		quickSortHelper(num,0,num.length-1);
	}
	private void quickSortHelper(int[] num,int start,int end){
		if(start<end){
			int index=partition(num,start,end);
			quickSortHelper(num,start,index-1);
			quickSortHelper(num,index+1,end);
		}
	}
	private int partition(int[] num,int start,int end){
		int pivot=num[end];
		int index=start;
		int temp=0;
		for(int j=start;j<end;j++){
			if(num[j]<=pivot){
				temp=num[index];
				num[index]=num[j];
				num[j]=temp;
				index++;
			}
		}
		num[end]=num[index];
		num[index]=pivot;
		return index;
	}
	
	private void mergeSort(int[] num){
		if(num==null || num.length==0){
			return;
		}
		//by using divide-and-conquer method
		mergeSortHelper(num,0,num.length-1);
	}
	private void mergeSortHelper(int[] num,int start,int end){
		if(start>=end){
			return;
		}
		int mid=start+(end-start)/2;//to avoid large num overflow
		mergeSortHelper(num,start,mid);
		mergeSortHelper(num,mid+1,end);
		merge(num,start,mid,end);
	}
	private void merge(int[] num,int start,int mid,int end){
		int[] sorted=new int[end-start+1];
		int p1=start,p2=mid+1,index=0;
		while(p1<=mid && p2<=end){
			if(num[p1]<num[p2]){
				sorted[index]=num[p1];
				p1++;
			}else{
				sorted[index]=num[p2];
				p2++;
			}
			index++;
		}
		while(p1<=mid){
			sorted[index]=num[p1];
			p1++;
			index++;
		}
		while(p2<=end){
			sorted[index]=num[p2];
			p2++;
			index++;
		}
		index=0;
		for(int i=start;i<=end;i++){
			num[i]=sorted[index];
			index++;
		}
	}
	
	private void insertSort(int[] num){
		if(num==null || num.length==0){
			return;
		}
		//for num[i], insert it into a sorted array
		//Method 1:
		int temp=0;
		for(int i=1;i<num.length;i++){
			for(int j=i;j>0;j--){
				if(num[j]<num[j-1]){
					temp=num[j-1];
					num[j-1]=num[j];
					num[j]=temp;
				}else{
					break;
				}
			}
		}
		// //Method 2:
		// ArrayList<Integer> sorted=new ArrayList<Integer>();
		// for(int i=0;i<num.length;i++){
		// 	for(int j=0;j<sorted.size();j++){
		// 		if(num[i]<sorted.get(j)){
		// 			sorted.add(j,num[i]);
		// 			break;
		// 		}
		// 	}
		// 	if(sorted.size()==i){//means num[i] is largest,so add to the end
		// 		sorted.add(num[i]);
		// 	}
		// }
		// for(int i=0;i<num.length;i++){
		// 	num[i]=sorted.get(i);
		// }
	}
	private void selectSort(int[] num){
		if(num==null || num.length==0){
			return;
		}
		int temp=0;
		int minIndex=0;
		//find the min value from num[i]~num[n-1],then put it in num[i]
		for(int i=0;i<num.length-1;i++){
			minIndex=i;
			for(int j=i;j<num.length;j++){
				if(num[j]<num[minIndex]){
					minIndex=j;
				}
			}
			if(minIndex!=i){
				temp=num[i];
				num[i]=num[minIndex];
				num[minIndex]=temp;
			}
		}
	}
	private void bubbleSort(int[] num){
		if(num==null || num.length==0){
			return;
		}
		int temp=0;
		for(int i=1;i<num.length;i++){ //run n-1 times comparison process
			//each time at the end of this loop, the largest is at num[n-i]
			for(int j=0;j<num.length-i;j++){
				if(num[j]>num[j+1]){
					temp=num[j];
					num[j]=num[j+1];
					num[j+1]=temp;
				}
			}
		}
	}
}
