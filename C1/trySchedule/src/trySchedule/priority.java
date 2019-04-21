package schedule;

import java.util.*;

public class priority {

	Scanner sc=new Scanner(System.in);
	int wt[],tat[],ct[];
	int n;
	
	void execute()
	{
		System.out.println("\nEnter number of processes : ");
		n=sc.nextInt();
		process []pr=new process[n];
		wt=new int[n];
		tat=new int[n];
		ct=new int[n];
		System.out.println("\nEnter Burst Time and Priority : ");
		int bt,at,pt;
		for(int i=0;i<n;i++)
		{
			System.out.println("\nBurst Time for P"+ (i+1) + " :");
			bt=sc.nextInt();
			System.out.println("\nArrival Time = 0 ");
			at=0;
			System.out.println("\nPriority for : P"+ (i+1) + " :");
			pt=sc.nextInt();
			pr[i]=new process(i+1,bt,at,pt);
		}
		Arrays.sort(pr,new sortByPriority());
		int sum=0;
		double avgWT=0,avgTAT=0;
		for(int i=0;i<n;i++)
		{
			sum=ct[i]=sum+pr[i].bt;
			tat[i]=ct[i]-pr[i].at;
			wt[i]=tat[i]-pr[i].bt;

			avgWT=avgWT+wt[i];
			avgTAT=avgTAT+tat[i];

		}
		display(pr);
		avgTAT=(double)avgTAT/n;
		avgWT=(double)avgWT/n;
		System.out.println("Average Waiting Time"+avgWT);
		System.out.println("Average TAT="+avgTAT);

	}
	void display(process[] proc)
	{
		System.out.println("\nProcess   BT\tAT\tWT\tTAT\tCT\tPriority");
		for(int i=0;i<n;i++)
		{
			System.out.println(proc[i].processid+"\t"+proc[i].bt+"\t"+proc[i].at+"\t"+wt[i]+"\t"+tat[i]+"\t"+ct[i]+"\t"+proc[i].prio);
		}
	}

	public static void main(String[] args)
	{
		priority p=new priority();
		p.execute();
	}
}
