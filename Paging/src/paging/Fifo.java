package paging;

import java.util.*;
import java.util.ArrayDeque;

public class Fifo {
	

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		// TODO Auto-generated method stub
		
		int pageReq[] = new int[50];
		Deque<Integer> fifo = new ArrayDeque<>();
		
		System.out.println("Enter the number of page requests: ");
		int n = sc.nextInt();
		System.out.println("Enter the page requests: ");
		for(int i=0;i<n;i++){
			pageReq[i] = sc.nextInt();
		}
		System.out.println("Enter the number of page frames: ");
		int f = sc.nextInt();
		
		ArrayList<Integer> frames = new ArrayList<>();
		
		int hit=0;
		for(int i=0;i<n;i++){
			
			if(frames.size()<f){
				
				if(!frames.contains(pageReq[i]))
				{
				frames.add(pageReq[i]);
				fifo.addLast(pageReq[i]);
				}
				else
				{
					hit++;
				}
			}
			else if(frames.size()==f){
				if(frames.contains(pageReq[i])){
					hit++;
				}
				else{
					int ind = frames.indexOf(fifo.pop());
					fifo.addLast(pageReq[i]);
					frames.set(ind, pageReq[i]);
				}
			}
			
			for(int j=0;j<frames.size();j++)
			{
				System.out.print(frames.get(j) + " ");
			}
			System.out.println();
		}
		
		
		System.out.println("Page Hits: "+ hit);
		System.out.println("Page Faults: "+ (n-hit));
		

	}
	
	

}
