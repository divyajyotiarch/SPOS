package saPackage;
import java.util.Scanner;
import java.util.Arrays;

class ps implements Comparable<ps>{
	String processId;
	int burstTime;
	int priority;
	public ps(String pid,int bt,int p){
		this.processId = pid;
		this.priority = p;
		this.burstTime = bt;
	}
	public int compareTo(ps other) {
	    if(this.priority > other.priority)
	        return 1;
	    else if (this.priority == other.priority)
	        return 0 ;
	    return -1 ;
	}
}

public class SAclass implements Comparable<SAclass>{
	String processId;
	int burstTime;
	int arrivalTime;
	

public int compareTo(SAclass other) {
    if(this.arrivalTime > other.arrivalTime)
        return 1;
    else if (this.arrivalTime == other.arrivalTime)
        return 0 ;
    return -1 ;
}
	
public static void fcfs(SAclass[] obj,int n){
	System.out.println("Gantt Chart for FCFS");
	System.out.println("ProcessId" + " " + "StartTime");
	
	int startTime[] = new int[n +1];
	int waitTime[] = new int[n];
	int taTime[] = new int[n];
	Arrays.fill(startTime,0);
	for(int i=0;i<n;i++){
		System.out.println(obj[i].processId + '\t'+'\t' + startTime[i]);
		startTime[i+1] = startTime[i] + obj[i].burstTime;	
	}
	System.out.println("Gantt Chart end time - " + startTime[n]);
	System.out.println("ProcessId" + " " + "WaitTime" + " " + "TurnaroundTime" );
	int avgWT=0,avgTAT=0;
	for(int i =0;i<n;i++){
		waitTime[i] = startTime[i] - obj[i].arrivalTime;
		taTime[i] = waitTime[i] + obj[i].burstTime;
		System.out.println(obj[i].processId + '\t'+'\t' + waitTime[i]+ '\t'+'\t' + taTime[i]);
		avgWT = avgWT + waitTime[i];
		avgTAT = avgTAT + taTime[i];
	}
	System.out.println("Average wait time = " + (avgWT/n));
	System.out.println("Average turnaround time = " + (avgTAT/n));
	
}


public static int FindSmallestBT (SAclass [] arr1,int length){//start method

       int index = 0;
       int min = arr1[index].burstTime;
       for (int i=1; i<length; i++){

           if (arr1[i].burstTime < min ){
               min = arr1[i].burstTime;
               index = i;
           }


       }
       return index ;

}



public static void sjfp(SAclass[] proc,int n){
	int wt[] = new int[n];
    int rt[] = new int[n]; 
    
    // Copy the burst time into rt[] 
    for (int i = 0; i < n; i++) 
        rt[i] = proc[i].burstTime; 
   
    int complete = 0, t = 0, minm = Integer.MAX_VALUE; 
    int shortest = 0, finish_time; 
    boolean check = false; 
   
    // Process until all processes gets 
    // completed 
    while (complete != n) { 
   
        // Find process with minimum 
        // remaining time among the 
        // processes that arrives till the 
        // current time` 
        for (int j = 0; j < n; j++)  
        { 
            if ((proc[j].arrivalTime <= t) && 
              (rt[j] < minm) && rt[j] > 0) { 
                minm = rt[j]; 
                shortest = j; 
                check = true; 
            } 
        } 
   
        if (check == false) { 
            t++; 
            continue; 
        } 
   
        // Reduce remaining time by one 
        rt[shortest]--; 
   
        // Update minimum 
        minm = rt[shortest]; 
        if (minm == 0) 
            minm = Integer.MAX_VALUE; 
   
        // If a process gets completely 
        // executed 
        if (rt[shortest] == 0) { 
   
            // Increment complete 
            complete++; 
            check = false; 
   
            // Find finish time of current 
            // process 
            finish_time = t + 1; 
   
            // Calculate waiting time 
            wt[shortest] = finish_time - 
                         proc[shortest].burstTime - 
                         proc[shortest].arrivalTime; 
   
            if (wt[shortest] < 0) 
                wt[shortest] = 0; 
        } 
        // Increment time 
        t++; 
    } 
    
    for(int i =0;i<n;i++) {
    	System.out.println(proc[i].processId + '\t'+'\t'+proc[i].burstTime + '\t'+'\t' + wt[i]+ '\t'+'\t' );
    }
} 
	/*System.out.println("Gantt Chart for SJF(Preemptive)");
	System.out.println("ProcessId" + " " + "StartTime");
	int startTime[] = new int[n +1];
	int waitTime[] = new int[n];
	int taTime[] = new int[n];
	Arrays.fill(startTime,0);
	
	int m = 0;
	int globalTime = 0;
	int prevmin = 0;
	for(int i =1;;i++) {
		int flag = 0;
		if(i<n)
			flag =1;
		if(flag == 1) {
		obj[m].burstTime = obj[m].burstTime - obj[i].arrivalTime ;
		globalTime = globalTime +obj[i].arrivalTime;
		prevmin = m;
		m = SAclass.FindSmallestBT(obj,i+1);
		System.out.println("m=" + m + " pm=" + prevmin + " " + obj[prevmin].burstTime);
		if(prevmin!=m) {
			System.out.println(""+ obj[m].processId + "  " + globalTime);
		}
		}
		else{
			break;
		}
	}*/
	
	/*System.out.println("Gantt Chart end time - " + startTime[n]);
	System.out.println("ProcessId" + " " + "WaitTime" + " " + "TurnaroundTime" );
	int avgWT=0,avgTAT=0;
	for(int i =0;i<n;i++){
		waitTime[i] = startTime[i] - obj[i].arrivalTime;
		taTime[i] = waitTime[i] + obj[i].burstTime;
		System.out.println(obj[i].processId + '\t'+'\t' + waitTime[i]+ '\t'+'\t' + taTime[i]);
		avgWT = avgWT + waitTime[i];
		avgTAT = avgTAT + taTime[i];
	}
	System.out.println("Average wait time = " + (avgWT/n));
	System.out.println("Average turnaround time = " + (avgTAT/n));*/



public void initialise(String pId,int bt,int at){
	this.arrivalTime = at;
	this.burstTime = bt;
	this.processId = pId;
	//System.out.println(this.processId + " " + this.burstTime + " " + this.arrivalTime);
	
}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System .out.println("enter no of process");
		int n = sc.nextInt();
		int at,bt;
		String pId;
		SAclass[] obj = new SAclass[n];
		
		//take input

		System.out.println("Enter Process Id , Burst Time , Arrival Time ");
		for(int i = 0 ; i< n ; i++){
			sc.nextLine();
			pId = sc.next();
			sc.nextLine();
			bt = sc.nextInt();
			at = sc.nextInt();
			//System.out.println(at + "" + bt + pId);
			obj[i] = new SAclass();
			obj[i].initialise(pId,bt,at);
		}
		
		Arrays.sort(obj);

	    for(SAclass p: obj) {
	        System.out.println(p.processId +" "+p.burstTime + " " + p.arrivalTime );
	    }
		
		
		SAclass.sjfp(obj, n);
		
		//Priority Schedular
		/*int p;
		PriorityQueue<ps> pq = new PriorityQueue<ps>();
		System.out.println("Enter Process Id , Burst Time , Priority ");
		for(int i = 0 ; i< n ; i++){
			sc.nextLine();
			pId = sc.next();
			sc.nextLine();
			bt = sc.nextInt();
			p = sc.nextInt();
			ps psobj = new ps(pId,bt,p);
			pq.add(psobj);

		}
		//deleting from priority queue and forming Gantt Chart
		System.out.println("Gantt Chart for Priority Scheduling");
		System.out.println("ProcessId" + " " + "WaitTime" + " " + "TurnaroundTime");
		
		int startTime[] = new int[n +1];
		int taTime[] = new int[n];
		Arrays.fill(startTime,0);
		int j=0;
		int avgWT=0,avgTAT=0;
		while(!pq.isEmpty()){
			ps removedPS ;
			removedPS = pq.remove();
			startTime[j+1] = startTime[j] + removedPS.burstTime;
			taTime[j] = startTime[j] + removedPS.burstTime;
			System.out.println(removedPS.processId + '\t'+'\t' + startTime[j] + '\t'+'\t' + taTime[j]);
			avgWT = avgWT + startTime[j];
			avgTAT = avgTAT + taTime[j];
			j++;
			
		}
		System.out.println("Average wait time = " + (avgWT/n));
		System.out.println("Average turnaround time = " + (avgTAT/n));*/
		
		sc.close();

	}

}
