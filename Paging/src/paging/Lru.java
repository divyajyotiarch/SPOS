package paging;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lru {
	
	protected static int minimum(Map<Integer,Integer>map){
		
		int val = 99999999,key=0;
		
		for(Map.Entry<Integer, Integer> i : map.entrySet())
		{
			if(val>i.getValue()){
				val = i.getValue();
				key = i.getKey();
			}
		}
		
		return key;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		// TODO Auto-generated method stub
		
		int pageReq[] = new int[50];
		//Deque<Integer> lru = new ArrayDeque<>();
		
		System.out.println("Enter the number of page requests: ");
		int n = sc.nextInt();
		System.out.println("Enter the page requests: ");
		for(int i=0;i<n;i++){
			pageReq[i] = sc.nextInt();
		}
		System.out.println("Enter the number of page frames: ");
		int f = sc.nextInt();
		
		Map<Integer,Integer> frames = new HashMap<>();
		
		int hit=0;
		int clock=0; 
		
		for(int i=0;i<n;i++){
			clock++;
				if(frames.size()<f)
				{
					if(!frames.containsKey(pageReq[i]))
					{
						frames.put(pageReq[i], clock);
					}
					else
					{
						hit++;
						frames.put(pageReq[i], clock);
					}
				}
				else if(frames.size()==f)
				{
					if(!frames.containsKey(pageReq[i])) //change here
					{
						//find the min value
						int key = minimum(frames);
						frames.remove(key);
						frames.put(pageReq[i],clock);	
						
						
					}
					else
					{
						hit++;
						frames.put(pageReq[i], clock);
					}
				}
				
				for(Map.Entry<Integer, Integer> k : frames.entrySet()){
					System.out.print(" " + k.getKey() + " " + k.getValue());
				}
				System.out.println();
		}
		
		System.out.println("Page Hits: "+ hit);
		System.out.println("Page Faults: "+ (n-hit));

	}

}
