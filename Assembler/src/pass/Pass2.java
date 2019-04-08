package pass;

import java.util.*;
import java.io.*;

public class Pass2{
	
	static String searchSym(String symbol) throws IOException
	{
		 BufferedReader sym = new BufferedReader(new FileReader("sym.txt"));
		String syml="";
		String s[] = new String[4];
	
		while ((syml = sym.readLine()) != null)
		{
				
				StringTokenizer st = new StringTokenizer(syml);
				 int m=0; 
				while(st.hasMoreTokens())
				{
					s[m] = st.nextToken();		//newline
					m++;
					
				}
				if(s[0].equals(symbol))
				{
					return s[2];	//address
				}
		}
		return symbol;
	}
	
	static String searchLit(String symbol) throws IOException
	{
		String litl;
		String s[] = new String[3];
		BufferedReader lit = new BufferedReader(new FileReader("lit.txt"));
		while ((litl = lit.readLine()) != null)
		{
			
				StringTokenizer st = new StringTokenizer(litl);
				 int m=0;
				while(st.hasMoreTokens())
				{
					s[m] = st.nextToken();		//newline
					m++;
					
				}
				if(s[0].equals(symbol))
				{
					return s[2];
				}
		}
		return symbol;
	}
	
	static String searchLitName(int index) throws IOException
	{
		 BufferedReader lit = new BufferedReader(new FileReader("lit.txt"));
		String litl;
		String s[] = new String[3];
		while ((litl = lit.readLine()) != null)
		{
				
				StringTokenizer st = new StringTokenizer(litl);
				 int m=0;
				while(st.hasMoreTokens())
				{
					s[m] = st.nextToken();		//newline
					System.out.println(s[m]);
					m++;
					
				}
				if(s[0].equals(Integer.toString(index)))
				{
					return s[1];
				}
		}
		return Integer.toString(index);
	}
	
	static String retPoolPtr(int pool_ptr) throws IOException
	{
		  BufferedReader ptab = new BufferedReader(new FileReader("ptab.txt"));
		String ptabl;
		String s[] = new String[2];
		int count=0;
		while (count!=2)
		{
				ptabl = ptab.readLine();
				StringTokenizer st = new StringTokenizer(ptabl);
				 int m=0; 
				while(st.hasMoreTokens())
				{
					s[m] = st.nextToken();		//newline
					m++;
				}
				if(s[0].equals(Integer.toString(pool_ptr))){
					count++;
					pool_ptr++;
				}
				
		}
		return s[pool_ptr-1];
	}
	
	static int litEntries() throws IOException{
		 BufferedReader lit = new BufferedReader(new FileReader("lit.txt"));
		 String litl; int count=0;
		 while ((litl = lit.readLine()) != null)
			{
			 count++;
			}
		 return count;
	}


	public static void main(String[] args) throws IOException {
		
		int loc =0, pool_ptr=0;
        BufferedReader br = new BufferedReader(new FileReader("asm11.txt"));
        String addr="";
        String s1[]=new String[15];
        
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("asmF.txt"));
        
        String CurrentLine, ptabLine;
        try{
        	
        	while ((CurrentLine = br.readLine()) != null)
    		{
    				
    				StringTokenizer st = new StringTokenizer(CurrentLine);
    				 int m=0;
    				 Arrays.fill(s1,"0");
    				while(st.hasMoreTokens())
    				{
    					s1[m] = st.nextToken();		//newline
    					System.out.println(s1[m]);
    					m++;
    					
    				}
    				
    				if(s1[1].equals("AD") && s1[3].equals("1"))	//for START
    				{
    					
    					loc = Integer.parseInt(s1[8]);		//assign loc 
    				}
    				else if(s1[1].equals("DL") && s1[3].equals("2"))	//for DS
    				{
    					bw.write(loc + " )");
    					loc = loc + Integer.parseInt(s1[8]);
    					bw.newLine();
    				}
    				else if(s1[1].equals("DL") && s1[3].equals("1"))	//for DC
    				{
    					bw.write(loc + " ) 0 0 " + s1[8]);
    					loc++;
    					bw.newLine();
    				}
    				else if(s1[1].equals("IS") && s1[9].equals("S"))	//for symbol
    				{
    					
    					addr = searchSym(s1[11]);
    					bw.write(loc + " )"+ s1[3] + " "+ s1[6] + " " + addr);
    					bw.newLine();
    					loc++;
    				}
    				else if(s1[1].equals("IS") && s1[9].equals("L"))
    				{
    					
    					addr = searchLit(s1[11]);
    					bw.write(loc + " )"+ s1[3] + " "+ s1[6] + " " + addr);
    					bw.newLine();
    					loc++;
    				}
    				else if(s1[6].equals("CC"))
    				{
    					
    					addr = searchSym(s1[13]);
    					bw.write(loc + " )"+ s1[3] + " "+ s1[8] + " " + addr);
    					bw.newLine();
    					loc++;
    				}
    				else if(s1[1].equals("AD") && s1[3].equals("5")) //LTORG 
    				{
    					
    					pool_ptr = Integer.parseInt(retPoolPtr(pool_ptr));
    					
    					for(int i=0;i<pool_ptr;i++){
    						String lname = searchLitName(i);
    						bw.write(loc + " )"+ "0 0 " + lname.charAt(2));
    						bw.newLine();
    						loc++;
    					}
    					
    				}
    				else if(s1[1].equals("AD") && s1[3].equals("2")){	//END
    					int k = litEntries();
    					
    					for(int i=pool_ptr;i<k;i++){
    						String lname = searchLitName(i);
    						bw.write(loc + " )"+ "0 0 " + lname.charAt(2));
    						bw.newLine();
    						loc++;
    					}
    				}
    				else if(s1[1].equals("AD") && s1[3].equals("3"))	//for ORIGIN
    				{
    					addr = searchSym(s1[8]);
    					if(s1[10].charAt(0)=='-')
    					{
    						loc = Integer.parseInt(addr) - (s1[10].charAt(1)-'0');
    					}
    					else
    					{
    						loc = Integer.parseInt(addr) + (s1[10].charAt(1)-'0');
    					}
    					
    				}
    				else if(s1[1].equals("IS") && s1[3].equals("0")){	//STOP
    					bw.write(loc + " )"+ "0 0 0" );
    					bw.newLine();
    					loc++;
    				}
    				
    		}
        	
        	bw.close();
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }
        

	}

}
