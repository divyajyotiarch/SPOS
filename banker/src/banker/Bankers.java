package banker;

import java.util.Scanner;
import java.util.ArrayList;
public class Bankers{
    private int need[][],allocated[][],max[][],resource[],np,nr,work[];
    private boolean finish[];    
     
    private void init(){
     Scanner sc=new Scanner(System.in);
     
     System.out.print("Enter no. of processes and resources : ");
     
     np=sc.nextInt();  //no. of process
     nr=sc.nextInt();  //no. of resources
     
     need=new int[np][nr];  //initializing arrays
     max=new int[np][nr];
     
     allocated=new int[np][nr];
     resource=new int[nr];
     work = new int[nr];
     finish=new boolean[np];
     
     for(int j=0;j<np;j++)
     {
    	finish[j]=false;  //initialize finish vector with false
     }
      
     System.out.println("Enter allocation matrix: ");
     for(int i=0;i<np;i++)
     {
    	 for(int j=0;j<nr;j++)
    	 {
    		 allocated[i][j]=sc.nextInt();  //allocation matrix
    	 }
     }
          
       
     System.out.println("Enter max matrix: ");
     for(int i=0;i<np;i++)
     {
    	 for(int j=0;j<nr;j++)
    	 {
             max[i][j]=sc.nextInt();  //max matrix
    	 }
     }
          
        System.out.println("Enter initial resource vector: ");
        for(int j=0;j<nr;j++)
         {
        	resource[j]=sc.nextInt();  //initial resource matrix
         }
         
        	
        	for(int i=0;i<nr;i++)
        	{	int sum=0;
        		for(int k=0;k<np;k++)
        		{
        			sum=sum + allocated[k][i];
        		}
        		work[i]=resource[i]-sum;
        	}
        	  //initialize work vector
        
        
        sc.close();
    }
    
     
    private int[][] calc_need(){
    	
       for(int i=0;i<np;i++)
       {
         for(int j=0;j<nr;j++)
         {											//calculating need matrix
        	 need[i][j]=max[i][j]-allocated[i][j];
         }
       }
       return need;
    }
    
  
    private boolean check(int i){
       //checking if all resources for ith process can be allocated
       for(int j=0;j<nr;j++) 
       {
    	   if(need[i][j] < work[j])
    		   return true;
       }
    
    return false;
    }
    
    private boolean checkFinish(){
    	for(int i=0;i<np;i++){
    		if(finish[i]==false){
    			return false;
    		}
    	}
    	return true;
    }
 
    public void isSafe(){
       init();
       calc_need();
       
       while(!checkFinish())
       {
       for(int i=0;i<np;i++){
    	   
    	   if(!finish[i] && check(i)){
    		   
    		   for(int j=0;j<nr;j++){
    			   work[j] = work[j]+allocated[i][j];
    		   }
    		   finish[i]=true;
    	   }
    	   
        }
	       if(checkFinish())
		   {
			   System.out.println("Machine is in stable state");
			   break;
		   }
       }
      
    }
     
    public static void main(String[] args) {
       new Bankers().isSafe();
    }
}