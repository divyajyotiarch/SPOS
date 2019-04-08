package paging;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Optimal {
	
protected static int maximum(Map<Integer,Integer>map){
		
		int val = -1,key=0;
		
		for(Map.Entry<Integer, Integer> i : map.entrySet())
		{
			if(val<i.getValue()){
				val = i.getValue();
				key = i.getKey();
			}
		}
		
		return key;
	}

protected static int search(int [] pages, int target, int k)
{
	int count=0;
	
	for(int i=k;i<pages.length;i++){
		count++;
		if(pages[i]==target)
		{
			break;
		}
	}
	
	return count;
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
		int iter=0;
		
		for(int i=0;i<n;i++){
			
			iter = search(pageReq,pageReq[i],i+1);
			if(frames.size()<f)
			{
				if(frames.containsKey(pageReq[i]))
				{
					
					hit++;
					frames.put(pageReq[i], iter);
					
				}
				else
				{
					frames.put(pageReq[i], iter);
				}
			}
			else if(frames.size()==f)
			{
				if(!frames.containsKey(pageReq[i])) //change here
				{
					//find the max value 
					int key = maximum(frames);
					frames.remove(key);
					frames.put(pageReq[i],iter);	
					
					
				}
				else
				{
					hit++;
					frames.put(pageReq[i], iter);
				}
			}
			

			for(Map.Entry<Integer, Integer> k : frames.entrySet()){
				System.out.print(" " + k.getKey() + " ");
			}
			System.out.println();
	}
	
	System.out.println("Page Hits: "+ hit);
	System.out.println("Page Faults: "+ (n -hit));
	}

}
