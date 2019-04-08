package pass;


import java.util.*;
import java.io.*;

class Lit
{
	String literal;
	int addr;
	Lit(String l)
	{
		literal=l;
	}

}
class Sym
{
	String sname;
	int addr;
	int len;
	Sym(String s1)
	{
			sname=s1;
		
	}
}

class Pass1
{
	Sym[] s = new Sym[10];
	Lit[] l = new Lit[10];
	
	Pass1(Sym[] s,Lit[] l){
		this.s=s;
		this.l=l;
		
	}
	
	public static boolean checkLabel(String s1)
	{
		if(s1.matches("[A-Za-z0-9]+"))		//check if alphanumeric
		{
			return true;
		}
		return false;
	}
	
	public Sym[] getSArray(){
		
		return s;
	}
	
	public Lit[] getLArray(){
		return l;
	}
	
	public static void main(String args[])throws Exception
	{
		int loc=0,sindex=0,lindex=0,pool_ptr=0;
		
		boolean flag=false;
		int flagbit=0;
		Lit LArray[]=new Lit[10];
		Sym SArray[]=new Sym[10];
		int Pooltab[] = new int[10];
		Pooltab[0]=0;
		
		HashMap<String, Integer> POT = new HashMap<>();
 		POT.put("START",1);
        POT.put("END",2);
        POT.put("ORIGIN",3);
        POT.put("EQU",4);
        POT.put("LTORG",5);

        
        HashMap<String, Integer> DL = new HashMap<>();
        DL.put("DC", 1);
        DL.put("DS", 2);


        HashMap<String, Integer> MOT = new HashMap<>();
        MOT.put("STOP", 0);
 		MOT.put("ADD",1);
        MOT.put("SUB",2);
        MOT.put("MULT",3);
        MOT.put("MOVER",4);
        MOT.put("MOVEM",5);
        MOT.put("COMP",6);
        MOT.put("BC",7);
        MOT.put("DIV",8);
        MOT.put("READ",9);
        MOT.put("PRINT",10);

        HashMap<String, Integer> REG = new HashMap<>();
 		REG.put("AREG,",1);
        REG.put("BREG,",2);
        REG.put("CREG,",3);
        REG.put("DREG,",4);

        HashMap<String, Integer> COT = new HashMap<>();
        COT.put("LT,",1);
        COT.put("LE,",2);
        COT.put("EQ,",3);
        COT.put("GT,",4);
        COT.put("GE,",5);
        COT.put("ANY,",6);

        FileReader fr=new FileReader("asm.txt");
        BufferedReader br=new BufferedReader(fr);
        String s1[]=new String[4];
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("asm11.txt"));
        
        BufferedWriter sym = new BufferedWriter(new FileWriter("sym.txt"));
        BufferedWriter lit = new BufferedWriter(new FileWriter("lit.txt"));
        
        BufferedWriter ptab = new BufferedWriter(new FileWriter("ptab.txt"));
        
        String CurrentLine;
		try
		{
		while ((CurrentLine = br.readLine()) != null)
		{
				int val,ind=-1,regval=0;
// ****************************
				StringTokenizer st = new StringTokenizer(CurrentLine);
				 int m=0;
				while(st.hasMoreTokens())
				{
					s1[m] = st.nextToken();		//newline
					System.out.println(s1[m]);
					m++;
					
				}
			

				if(POT.containsKey(s1[0]))
				{

					val=POT.get(s1[0]);    //gets code
					if(s1[0].equals("START"))
					{
						if(s1[1]!=null)
						{
							loc=Integer.parseInt(s1[1]);
						}
						System.out.println("( AD , "+val+" )  ( C , " +loc+" )");
						bw.write("( AD , "+val+" )  ( C , " +loc+" )");
						bw.newLine();
					}
					else if(s1[0].equals("END"))
					{
						
						bw.write("( AD , "+val+" )");
						bw.newLine();
						System.out.println("(AD, "+val+")");
						
						pool_ptr++;
						if(flagbit>=1)
						{
							
							for(int i=pool_ptr;i<lindex;i++)
							{
								LArray[i].addr=loc;
								loc++;
							
							}
						}
					}
					else if(s1[0].equals("ORIGIN"))
					{
						int i1=0;
						for(i1=0;i1<sindex;i1++)
						{
							if(SArray[i1].sname.equals(s1[1]))
							{
								if(s1[2].charAt(0)=='+')
								{
									
									loc = SArray[i1].addr + (s1[2].charAt(1) - '0');
									break;
									
								}
								else if(s1[2].charAt(0)=='-')
								{
									loc = SArray[i1].addr - (s1[2].charAt(1) - '0');
									break;
								}
								
							}
						//compare symbol table entry with symbol encountered 
							//in this case ORIGIN (A)+1
						}
						bw.write("( AD , "+val+" ) ( S , "+ i1+ " ) " + s1[2]);
						bw.newLine();
						System.out.println("(AD, "+val+") (S, "+ i1+ ") " + s1[2] );
					}
					else if(s1[0].equals("LTORG"))
					{	
						val = POT.get(s1[0]);
														
						flagbit=1;
						System.out.println("(AD, "+val+")");
						bw.write("( AD , "+val+" )");
						bw.newLine();
						pool_ptr++;
						Pooltab[pool_ptr] = lindex;
						
									
						for(int i=0;i<lindex;i++)
						{
							LArray[i].addr=loc;
							loc++;
						
						}
						flagbit++;
					}

				}
				else if(s1[0].equals("BC"))
				{
					char ch='n';
				
					val = COT.get(s1[1]);
					int val1 = MOT.get("BC");				
					
					if(checkLabel(s1[2])){
						ch='S';

					for(int i1=0;i1<sindex;i1++)
					{
						if(s1[2].equals(SArray[i1].sname))
						{
							flag=true;
							break;
						}

					}
					if(flag==false)
					{
					SArray[sindex]=new Sym(s1[2]);
					ind=sindex;
					sindex++;
					}
					}
					loc++;
					
					bw.write("( IS , "+val1+" ) ( CC , "+val+" )  ( "+ch+" , "+ind+" )");
					bw.newLine();
					System.out.println("(IS, "+val1+") (CC, "+val+")  ("+ch+", "+ind+")");
					
				}
				else if(POT.containsKey(s1[1]))
				{
					val = POT.get(s1[1]);	//for EQU
						
						int i1=0,k=0;//print entry of symbol from symbol table
						for( k=0;k<sindex;k++)
						{

							if(SArray[k].sname.equals(s1[0]))
							{
								SArray[k].len=1;
								break;
							}
						}
						for(i1=0;i1<sindex;i1++)
						{
							if(SArray[i1].sname.equals(s1[2]))
							{
								if(s1[3].charAt(0)=='+')
								{
									
									SArray[k].addr = SArray[i1].addr + (s1[3].charAt(1) - '0');
									break;
									
								}
								else if(s1[3].charAt(0)=='-')
								{
									SArray[k].addr = SArray[i1].addr - (s1[3].charAt(1) - '0');
									break;
								}
								
							}
						
					}
						System.out.println("( AD, "+val+") (S, "+ i1+ ") " + s1[3] );
					bw.write("( AD , "+val+" ) ( S , "+ i1+ " ) " + s1[3] );
					bw.newLine();
				}
				else if(MOT.containsKey(s1[1])) //when 2nd string is IS
				{
					//adding label to symbol table
					int i;
					flag=false;
					if(checkLabel(s1[0]))
					{
						
					
					for( i=0;i<sindex;i++)
					{
						if(s1[0].equals(SArray[i].sname))
						{
							flag=true;
							break;
						}

					}
					if(flag==false)
					{
						
						SArray[sindex]=new Sym(s1[0]);
						ind=sindex;
						SArray[sindex].addr=loc;
						SArray[sindex].len=1;
						sindex++;
					}
					else
					{
						SArray[i].addr=loc;
						SArray[i].len=1;
					}
					}
					//********************************

					char ch='n';
					val=MOT.get(s1[1]);
					if(REG.containsKey(s1[2]))
					{
						regval=REG.get(s1[2]);
					}
					if(s1[3].charAt(0)=='=')		//*****literal address  def
					{
						ch='L';
						//int l1=Integer.parseInt(s1[2].charAt(1));
						LArray[lindex]=new Lit(s1[3]);
						ind=lindex;
						lindex++;
					}
					else if(s1[2].charAt(0)=='=')		//*****literal address  def
					{
						ch='L';
						//int l1=Integer.parseInt(s1[2].charAt(1));
						LArray[lindex]=new Lit(s1[3]);
						ind=lindex;
						lindex++;
					}
					else
					{
						if(checkLabel(s1[2])){
							ch='S';

						for(int i1=0;i1<sindex;i1++)
						{
							if(s1[2].equals(SArray[i1].sname))
							{
								flag=true;
								ind = i1;
								break;
							}

						}
						if(flag==false)
						{
						SArray[sindex]=new Sym(s1[2]);
						ind=sindex;
						sindex++;
						}
					}

						if(checkLabel(s1[3])){
							ch='S';

						for(int i1=0;i1<sindex;i1++)
						{
							if(s1[3].equals(SArray[i1].sname))
							{
								flag=true;
								ind = i1;
								break;
							}

						}
						if(flag==false)
						{
						SArray[sindex]=new Sym(s1[3]);
						ind=sindex;
						sindex++;
						}
					}

					}

					bw.write("( IS , "+val+" ) ( "+regval+" ) ( "+ch+" , "+ind+" )");
					bw.newLine();
					System.out.println("(IS, "+val+") ("+regval+") ("+ch+", "+ind+")");
					loc++;
					
				}
				
				else if(MOT.containsKey(s1[0]))	//first string is IS
				{
					if(s1[0].equals("STOP"))
					{
						System.out.println("(IS, "+ MOT.get(s1[0])+ ")");
						
						bw.write("( IS , "+ MOT.get(s1[0])+ " )");
						bw.newLine();
					
					}
					else
					{	
					
					char ch = 'n';	
					val=MOT.get(s1[0]);
					if(REG.containsKey(s1[1]))
					{
						regval=REG.get(s1[1]);
					}
					if(s1[2].charAt(0)=='=')
					{
						ch='L';
						//int l1=Integer.parseInt(s1[2].charAt(1));
						LArray[lindex]=new Lit(s1[2]);
						ind=lindex;
						lindex++;
					}
					else
					{
						if(checkLabel(s1[0])){
					ch='S';
						for(int i=0;i<sindex;i++)
						{
							if(s1[2].equals(SArray[i].sname))
							{
								flag=true;
								break;
							}

						}
						if(flag==false)
						{
						SArray[sindex]=new Sym(s1[2]);
						ind=sindex;
						sindex++;
						}

					}
					}
					bw.write("( IS , "+val+" ) ( "+regval+" ) ( "+ch+" , "+ind+" )");
					bw.newLine();
					System.out.println("(IS, "+val+") ("+regval+") ("+ch+", "+ind+")");
					}
					
					loc++;
				}
				else if(DL.containsKey(s1[1]))
				{
					int c = DL.get(s1[1]);
					
					if(s1[1].equals("DS"))
					{
						if(checkLabel(s1[0])){
						int l=Integer.parseInt(s1[2]); //adding label to symbol table
						int i;
						flag=false;
						for( i=0;i<sindex;i++)
						{
							if(s1[0].equals(SArray[i].sname))
							{
								flag=true;
								break;
							}
	
						}
						if(flag==false)
						{
							SArray[sindex]=new Sym(s1[0]);
							ind=sindex;
							SArray[sindex].addr=loc;
							SArray[sindex].len=l;
							sindex++;
						}
						else
						{
							SArray[i].addr=loc;
							SArray[i].len=l;
						}
						loc+=l;
					}
					}
					
					else	//for DC
					{
						int l=1; //adding label to symbol table
						int i;
						flag=false;
						if(checkLabel(s1[0])){
							
						for( i=0;i<sindex;i++)
						{
							if(s1[0].equals(SArray[i].sname))
							{
								flag=true;
								break;
							}
	
						}
						if(flag==false)
						{
							SArray[sindex]=new Sym(s1[0]);
							ind=sindex;
							SArray[sindex].addr=loc;
							SArray[sindex].len=l;
							sindex++;
						}
						else
						{
							SArray[i].addr=loc;
							SArray[i].len=l;
						}
						loc+=l;
						}
					}
					bw.write("( DL , "+c + " ) ( C , "+s1[2] + " )");
					bw.newLine();
					System.out.println("(DL, "+c + ") (C, "+s1[2] + " )");
				}
				else if(DL.containsKey(s1[0]))  //when no label
				{
					int c = DL.get(s1[0]);
					bw.write("( DL , "+c + " ) ( C , "+s1[1] + " )");
					bw.newLine();
					System.out.println("(DL, "+c + ") (C, "+s1[1] + " )");
				}
				else
				{
						System.out.println("Label");
						flag=false;
						for(int i=0;i<sindex;i++)	//checks for duplicate Label
						{
							if(s1[0].equals(SArray[i].sname))
							{
								flag=true;
								break;
							}

						}
						if(flag==false)
						{

						SArray[sindex]=new Sym(s1[0]);
						ind=sindex;
						SArray[sindex].addr=loc;
						sindex++;
						System.out.println(SArray[sindex].sname);
						}
						else
						{
							System.out.println("Duplicate Label");
							System.exit(0);
						}
					
				} //end else
     			 System.out.println();

		} //end while
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		bw.close();
		System.out.println("**************Symbol Table*******");
		System.out.println("sindex\tsname\taddress\tlength");
		for(int i=0;i<sindex;i++)
		{
					
						System.out.println(i+"\t"+SArray[i].sname+"\t"+SArray[i].addr+"\t"+SArray[i].len);
						sym.write(i+"\t"+SArray[i].sname+"\t"+SArray[i].addr+"\t"+SArray[i].len);
						sym.newLine();

		}
		sym.close();
		System.out.println("**************Literal Table*******");
		System.out.println("lindex\tlname\tAddress");
		for(int i=0;i<lindex;i++)
						{
					
						System.out.println(i+"\t"+LArray[i].literal+"\t"+LArray[i].addr);
						lit.write(i+"\t"+LArray[i].literal+"\t"+LArray[i].addr);
						lit.newLine();
						}
		
		lit.close();

		
		System.out.println("**************Literal Pool Table*******");
		System.out.println("literal no");
		for(int i=0;i<pool_ptr;i++)
		{

		System.out.println(i + " " +Pooltab[i]);
		ptab.write(i + " " +Pooltab[i]);
		ptab.newLine();

		}
		ptab.close();
    }
}

