package trySchedule;

import java.util.Scanner;

public class robin {

	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter no of processes");
		int n = sc.nextInt();
		
		Process[] pr = new Process[n];
		
		System.out.println("Enter burst time");
		for(int i=0;i<n;i++){
			pr[i] = new Process();
			pr[i].bt = sc.nextInt();
			pr[i].at = 0;
		}
		
		System.out.println("enter quantum time");
		int quant = sc.nextInt();
		
		int[] rt = new int[n];
		for(int i=0;i<n;i++){	
			rt[i]= pr[i].bt;
		}
		
		int complete=0,timer=0;
		boolean flag=false;
		
		while(complete!=n){
			
			for(int j=0;j<n;j++){
				flag = false;
				if(rt[j]>quant){
					
					rt[j]=rt[j]-quant;
					timer = timer + quant;
					
				}
				else	//last cycle of a process
				{
					if(rt[j]!=0){
						flag=true;
					}
					timer = timer + rt[j];
					rt[j]=0;
				}
				
				if(flag==true){
					complete++;
					pr[j].ct = timer;
					pr[j].tat = pr[j].ct;
					pr[j].wt = pr[j].tat - pr[j].bt;
				}
				
				
			}
			
		}
		
		for(int i=0;i<n;i++){
			System.out.println(pr[i].wt +" "+ pr[i].ct +" "+ pr[i].at +" "+ pr[i].bt + " " + pr[i].tat);
		}	
		
		
	}

}
