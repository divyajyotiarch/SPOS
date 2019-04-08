package Schedule;

import java.util.*;

public class JobScheduling {
	
	final static int max =100;
	private int bt, at, tt=0, wt, st,priority; // bt = burst time, at = arrival time, tt= turn around time, wait time 
											// st = array of start time
	private String p;  //p = process id	
	
	protected void getProcessId(int i)
	{
		p = "P" + i;
	}
	
	protected String setP()
	{
		return p;
	}
	
	protected void getBt()
	{
		Scanner read = new Scanner(System.in);
		bt = read.nextInt();
		
	}
	protected void chBt(int k){
		bt = k;
	}
	
	protected int setStart()
	{
		return tt;
		
	}

	protected void getStart(int i)
	{

		st= i;
		
	}
	
	protected int setTt()
	{
		return tt;
		
	}
	
	protected void getTt(int i)
	{
		tt= i;
		
	}
	
	protected int setBt()
	{
		return bt;
	}
	
	protected void getAt()
	{
		Scanner read = new Scanner(System.in);
		at = read.nextInt();
		
	}
	
	protected int setAt()
	{	
		return at;
	}
	
	protected int calcTT(int i1, int i2)
	{
		tt = i1 + i2;
		return tt;
	}

	protected void getTA(int k1)
	{
		tt = k1;
	}
	
	protected int setTA()
	{
		return tt;
	}
	
	protected int calcWT(int i, int at)
	{
		wt = i - at; // at = arrival time
		return wt;
	}
	

	protected void getWt(int i) {
		
		wt = i;
	}
	
	protected int setWt()
	{	
		return wt;
	}
	
	public static void main(String[] args) {
		
		Scanner read = new Scanner(System.in);
		int c=0;
		

		JobScheduling[] j = new JobScheduling[max];
		
		
		
		System.out.println("Enter number of processes ");
		int n = read.nextInt();
	
		
		
		do{
		System.out.println("1. FCFS");
		System.out.println("2. SJF (Pre-emptive)");
		System.out.println("3. Round Robin");
		System.out.println("4. Priority Scheduling");
		System.out.println("Choose");
		c = read.nextInt();
	
		
		switch(c)
		{
		case 1: 
			System.out.println("Turnaround Time");
			
			for(int i=1;i<=n;i++)
			{
				j[i] = new JobScheduling();
			System.out.println("Enter burst time for process P"+i);
			j[i].getProcessId(i);
			j[i].getBt();
			
			System.out.println("Enter arrival time for process P"+i);
			j[i].getAt();

			}
			
			for(int i = 1; i<=n;i++)
			{
				j[i].getTt(j[i].setBt()); 	//copying burst time into turnaround time
			}
			System.out.println(j[1].setP() + " " + j[1].setTt() ); 
			for(int i = 1; i<n;i++)
			{
				System.out.println(j[i+1].setP() + " " + j[i+1].calcTT( j[i].setTt(),j[i+1].setTt() ) );
			}
			System.out.println("Waiting Time");
			//copying turnaround time in waiting time
			j[1].getWt(0);
			for(int i = 1; i<n;i++)
			{
				j[i+1].getWt(j[i].setTt()); 
			}
			for(int i = 1; i<=n;i++)
			{
				System.out.println(j[i].setP() + " " + j[i].calcWT( j[i].setWt(),j[i].setAt() ) );
			}
			double avgWt = 0;
			for(int i=1; i<=n;i++)
			{
				
				avgWt = j[i].setWt() + avgWt;
			}
			avgWt  = avgWt/n; 
			System.out.println("Average waiting time " + avgWt);
			double avgTt = 0;
			for(int i=1; i<=n;i++)
			{
				
				avgTt = j[i].setTt() + avgTt;
			}
			avgTt= avgTt/n;
			System.out.println("Average turnaround time " + avgTt);	
			
			System.out.println("Gantt Chart");
			for(int i=1;i<=n;i++)
			{
				System.out.print(" "+j[i].setP() + "  ");
			}
			System.out.println(); System.out.print("0 ");
			for(int i=1;i<=n;i++)
			{
				System.out.print(j[i].setTt() + "  ");
			}
			System.out.println(); 		
			break;
			
		case 2:
			int k[]= new int[max];   // it is also stores brust time
			
			for(int i=1;i<=n;i++)
			{
				j[i] = new JobScheduling();
			System.out.println("Enter burst time for process P"+i);
			j[i].getProcessId(i);
			j[i].getBt();
			k[i]=j[i].setBt();
			
			System.out.println("Enter arrival time for process P"+i);
			j[i].getAt();

			}
			
			Queue<JobScheduling> q1 = new LinkedList();
			
			//made start time as complete time
			int f[] = new int[max];  // f means it is flag it checks process is completed or not
			
		    int i, st=0, tot=0;
		    float avgwt=0, avgta=0;
	 
		    int min=99,c1=n;
		    while(true){
		    	
		    	if (tot==n)
		    	{
		    		break;
		    	}
		    	
		    	for ( i=1;i<=n;i++)
		    	{
		    		
		    		if ((j[i].setAt()<=st) && (f[i]==0) && (j[i].setBt()<min))
		    		{	
		    			min=j[i].setBt();
		    			c1=i;
		    			
		    		}
		    	}
		    	
		    	if (c1==n)
		    	{
		    		st++;
		    	}
		    	else
		    	{
		    		System.out.println("hello");
		    		j[c1].chBt(j[c1].setBt()-1);
		    		st++;
		    		if (j[c1].setBt()==0)
		    		{
		    			j[c1].getStart(st);
		    			f[c1]=1; //after process is complete
		    			tot++;
		    		}
		    	}
		    }
		    
		    for(i=1;i<=n;i++)
		    {
		    	j[i].getTA(j[i].setStart()-j[i].setAt());
		    	j[i].getWt(j[i].setWt() - k[i]);
		  
		    	avgwt+= j[i].setWt();
		    	avgta+= j[i].setTA();
		    }
			
		    System.out.println("Gantt Chart");
			for(int i1=1;i1<=n;i1++)
			{
				System.out.print(" "+j[i1].setP() + "  ");
			}
			System.out.println(); System.out.print("0 ");
			for(int i1=1;i1<=n;i1++)
			{
				System.out.print(j[i1].setStart() + "  ");
			}
			System.out.println(); 
					
			
			break;
		case 3:
			break;
		case 4:

			for(int i1=1;i1<=n;i1++)
			{
				j[i1] = new JobScheduling();
			System.out.println("Enter burst time for process P"+i1);
			j[i1].getProcessId(i1);
			j[i1].getBt();
			
			System.out.println("Enter priority for process P"+i1);
			j[i1].setPriority();

			}
			
			PriorityQueue<JobScheduling> pq = new PriorityQueue();
			
			
			
			break;
		
		}
		
		}while(c<5);
		
		
		
		// TODO Auto-generated method stub

	}

	public int getPriority() {
		
		return priority;
	}

	public void setPriority() {
		
		Scanner read = new Scanner(System.in);
		priority = read.nextInt();
	}


}