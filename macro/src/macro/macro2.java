package macro;

import java.io.*;
import java.util.*;

class macro2
{
	
	public static void main(String [] args) throws IOException{
		String MDT[]=new String[50];
		String s1[] = new String[4];
		HashMap<String,String> PvsA = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader("output.txt"));
		BufferedReader mnt = new BufferedReader(new FileReader("mnt.txt"));
		BufferedReader ir = new BufferedReader(new FileReader("ic.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("finout.txt"));
		
		String icl;
		
		while((icl=ir.readLine())!=null){
			
			
			
		}
		
		String CurrentLine;
		
		while((CurrentLine = br.readLine()) != null){
			
			StringTokenizer st = new StringTokenizer(CurrentLine);
			 int m=0;
			while(st.hasMoreTokens())
			{
				s1[m] = st.nextToken();		//newline
				System.out.println(s1[m]);
				m++;
				
			}
			
			if(s1[1].charAt(0) =='#'){
				
			}
			
		}
		
		
	}
}