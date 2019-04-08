package macro;


import java.util.*;
import java.io.*;


class MNT
{
	String mname;
	int MDTindex,param;
	
	MNT(String m,int i,int j)
	{
		mname=m;
		MDTindex=i;
		param = j;
	}
}

class ALA
{
	String formal, pos;
	
	ALA(String f,String p){
		formal = f;
		pos = p;
	}
	
}


public class Macro {
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		MNT MNTArray[]=new MNT[10];
		String ALA[]=new String[10];
		ALA PArray[] = new ALA[10];
		String MDT[]=new String[30];
		int mntp=0,mdtp=0,alap=0;
		String s2[]=new String[10];
		BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
       	BufferedReader br=new BufferedReader(new FileReader("input1.txt"));
       	BufferedWriter ic = new BufferedWriter(new FileWriter("ic.txt"));
       	BufferedWriter mnt = new BufferedWriter(new FileWriter("mnt.txt"));
       	
        	String CurrentLine,l1;
		while ((CurrentLine = br.readLine()) != null)
		{
			
			String s[]=CurrentLine.split(" ");

			if(s[0].equals("MACRO"))
			{
				String nl=br.readLine();
				String tkn[]=nl.split("	");

				StringTokenizer st = new StringTokenizer(nl);
				 int m=0;
				while(st.hasMoreTokens())
				{
					s2[m] = st.nextToken();		//newline
				//	System.out.println(s2[m]);
					m++;
					
				}
				
				MNTArray[mntp]=new MNT(tkn[0],mdtp,m-1);
				mntp++;
				//StringBuilder str = new StringBuilder(tkn[0]);
				alap=0;
				for(int i=1;i<tkn.length;i++)
				{
					ALA[alap]=tkn[i];
					String pos1="#" + Integer.toString(alap+1);
					PArray[alap] = new ALA(tkn[i],pos1);
					
					//str.append(" #"+alap);
					alap++;
				}
				//MDT[mdtp]=str.toString();
				//mdtp++;
				while (!(l1= br.readLine()).equals("MEND"))
				{
					int k=-1;
					String tkn1[]=l1.split("	");
					StringBuilder s1=new StringBuilder(tkn1[0]);
					for(int i=0;i<tkn1.length;i++)
					{

						if(tkn1[i].charAt(0)=='&')
						{
							for(int j=0;j<alap;j++)
							{
								if(tkn1[i].equals(PArray[j].formal))
								{
									k=j+1;
									break;
								}
							}
						}


					}
					if(k==-1)
					{
						s1.append("	"+tkn1[1]);
					}
					else
					{s1.append("	#"+k);}
					MDT[mdtp]=s1.toString();
					mdtp++;
				}
				
			//	flushALA(PArray);
				
				MDT[mdtp]="MEND";
					mdtp++;

			}
			else
			{
				ic.write(CurrentLine);
				ic.newLine();
			//	System.out.println(CurrentLine);
				
			}
		}
		System.out.println();
		System.out.println("MDT TABLE.......................");
		System.out.println("MDTIndex\tInstruction");
		for(int i=0;i<mdtp;i++)
		{
			System.out.println(i+"\t\t"+MDT[i]);
			bw.write(i +"\t"+ MDT[i]);
			bw.newLine();
			

		}

				System.out.println();
				System.out.println("ALA TABLE.......................");
				System.out.println("ALAIndex\tArgument\t PositionalParam");
				for(int i=0;i<alap;i++)
				{
					System.out.println(i+"\t\t"+PArray[i].formal+"\t\t" + PArray[i].pos);
					

		}

				System.out.println();

				System.out.println("MNT TABLE.........................");
				System.out.println("Index\tMNTName\t #Parameters\tMDTIndex");
				for(int i=0;i<mntp;i++)
				{
					System.out.println(i+"\t"+MNTArray[i].mname+"\t"+ MNTArray[i].param +"\t"+MNTArray[i].MDTindex+"\n");
					mnt.write(i+"\t"+MNTArray[i].mname+"\t"+ MNTArray[i].param +"\t"+MNTArray[i].MDTindex+"\n");
					mnt.newLine();

				}
				
		bw.close();
		br.close();
		ic.close();
	}
	}


