package trySchedule;

import java.util.Scanner;

class Process{
	
	int bt,at,wt,tat,ct;
	
}

public class sjf {
	
	static int findMin(int[] rt,int n, Process[] pr, int timer){
		int index=0;
		int val=Integer.MAX_VALUE;
		
		for(int i=0;i<n;i++)
		{
			if(val>rt[i] && pr[i].at<=timer && rt[i]!=0){
				val=rt[i];
				index=i;
			}
		}
		
		return index;
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("enter no of processes");
		int n = sc.nextInt();
		
		Process[] pr = new Process[n];
		System.out.println("enter burst time");
		for(int i=0;i<n;i++){
			pr[i] = new Process();
			pr[i].bt = sc.nextInt();
		}
		
		
		System.out.println("enter arrival time");
		for(int i=0;i<n;i++){
			pr[i].at = sc.nextInt();
		}
		
		int[] rt = new int[n];
		
		for(int i=0;i<n;i++){
			rt[i] = pr[i].bt ;
		}
		
		
		int timer=0,complete=0,index=0;
		
		while(complete!=n){
				
			index = findMin(rt,n,pr,timer);

			rt[index]=rt[index]-1;
			
			if(rt[index]==0){
				complete++;
				pr[index].ct = timer+1;
				pr[index].wt = pr[index].ct - pr[index].at - pr[index].bt;
				pr[index].tat = pr[index].wt + pr[index].bt;
			}
			
			timer++;
			
			
		}	
		
		for(int i=0;i<n;i++){
			System.out.println(pr[i].wt +" "+ pr[i].ct +" "+ pr[i].at +" "+ pr[i].bt + " " + pr[i].tat);
		}
		
		
	}

}
