package pass1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

class SymLit{
	//class for Symbols and Literals
	int addr;
	String name;
	
	SymLit(String n,int a){
		name = n;
		addr = a;
	}
	SymLit(String n){
		name = n;
	}
	String getName(){
		return name;
	}
	void setAddr(int a){
		addr = a;
	}
	static int Search(SymLit []obj,String key,int ptr){
		for(int i = 1;i<ptr;i++){
			if(obj[i].getName().equals(key))
				return i;
		}
		return 0;
	}
}

public class Pass1 {
	
	public static void main(String args[]) throws IOException{
		SymLit [] SYMTAB = new SymLit[50];//Symbol Table
		SymLit [] LITTAB = new SymLit[50];//Literal Table
		int [] POOLTAB = new int[10]; //Pool Table
		
		
		//Imperative statement 
		HashMap<String , Integer> IS = new HashMap<>();
		IS.put("STOP",0);
		IS.put("ADD",1);
		IS.put("SUB",2);
		IS.put("MULT",3);
		IS.put("MOVER",4);
		IS.put("MOVEM",5);
		IS.put("COMP",6);
		IS.put("BC",7);
		IS.put("DIV",8);
		IS.put("READ",9);
		IS.put("PRINT",10);
		
		//Registers
        HashMap<String, Integer> REG = new HashMap<>();
 		REG.put("AREG",1);
        REG.put("BREG",2);
        REG.put("CREG",3);
        REG.put("DREG",4);
        
        //Conditional Codes
        HashMap<String,Integer> CC = new HashMap<>();
        CC.put("LT", 1);
        CC.put("LE", 2);
        CC.put("EQ", 3);
        CC.put("GT", 4);
        CC.put("GE", 51);
        CC.put("ANY", 6);
        
        //Assembly Directives
		HashMap<String, Integer> AD = new HashMap<>();
 		AD.put("START",1);
        AD.put("END",2);
        AD.put("ORIGIN",3);
        AD.put("EQU",4);
        AD.put("LTORG",5);
        
        //Declarative
		HashMap<String, Integer> DL = new HashMap<>();
		DL.put("DS",2);
		DL.put("DC",1);
		
		//initialize all pointers
		int loc_cntr = 0, pool_ptr = 1,lit_ptr = 1,sym_ptr = 1; 
		POOLTAB[1] = 1;
		int newloc_cntr = 0;
		
        
		//Read code from file
		FileReader fr=new FileReader("code2.txt");
        BufferedReader br=new BufferedReader(fr);
        
        //store intermediate code in ic.txt
        OutputStream os = new FileOutputStream(new File("/home/TE/SPOSL/3171/A1/ic.txt"));
        String CurrentLine;
		outerloop:
        while((CurrentLine = br.readLine()) != null){
        	//read all lines till end of file
        	
        	String line = "";
        	
        	//initialize all values which are a part of every line in intermediate code
        	int val =0 , regval = 0 , cval = 0 , cextra = 0;
        	String key="";
        	char csl='\0',csign = '\0';
        	
        	String s[]=CurrentLine.split(" ");//s[] contains all the words in current line of code
        	
        	/*for(int i = 0 ;i< s.length;i++){
        		System.out.print(s[i] + " ");
        	}
        	System.out.println();*/
        	String temp = s[0];
        	int flag = 0;
        	boolean lflag = true;//flag to check if label is present on current line or not
        while(flag == 0){
        	if(AD.containsKey(temp)){
        		//Assembly Directive is present
        		key = "AD";
        		val = AD.get(temp);
        		if(temp.equals("START")){
        			newloc_cntr = Integer.parseInt(s[1]);
        			cval = Integer.parseInt(s[1]);
        			csl = 'C';
        			if(cextra == 0)
                        line = "("+ key + "," + val + ") (" + csl + "," + cval + ")";
                    else
                    	line = "("+ key + "," + val + ") (" + csl + "," + cval + ")" + csign + cextra + " ";
        		}
        		else if(temp.equals("EQU")){
        			csl = 'S';
        			String sym[];
        			//find symbol in symbol table
        			int index = SymLit.Search(SYMTAB, s[0],sym_ptr);
        			if(s[2].contains("+"))
        				{
        				sym = s[2].split("\\+");
        				csign = '+';
        				cextra = Integer.parseInt(sym[1]);
        				SYMTAB[index].setAddr(SYMTAB[SymLit.Search(SYMTAB, sym[0],sym_ptr)].addr + cextra);
        				}
        			else if(s[2].contains("-"))
        				{
        				sym = s[2].split("\\-");
        				csign = '-';
        				cextra = Integer.parseInt(sym[1]);
        				SYMTAB[index].setAddr(SYMTAB[SymLit.Search(SYMTAB, sym[0],sym_ptr)].addr - cextra);
        				}
        			else{
        				sym = new String[1];
        				sym[0]= s[2];
        				SYMTAB[index].setAddr(SYMTAB[SymLit.Search(SYMTAB, sym[0],sym_ptr)].addr);
        			}
        			
        			
        			cval = SymLit.Search(SYMTAB, sym[0],sym_ptr);
        			//System.out.print(sym[0]+" ");
        			
        			if(cextra == 0)
                        line = ("("+ key + "," + val + ") (" + csl + "," + cval + ")");
                    else
                    	line = ("("+ key + "," + val + ") (" + csl + "," + cval + ")" + csign + cextra + " ");
        		}else if(temp.equals("ORIGIN")){
        			csl = 'S';
        			String sym[];
        			if(lflag == false){
        				s[1]=s[2];
        			}
        			if(s[1].contains("+"))
    				{
    				sym = s[1].split("\\+");
    				csign = '+';
    				cextra = Integer.parseInt(sym[1]);
    				
    				}
    			else if(s[1].contains("-"))
    				{
    				sym = s[1].split("\\-");
    				csign = '-';
    				cextra = Integer.parseInt(sym[1]);
    				}
    			else{
    				sym = new String[1];
    				sym[0] = s[1];
    			}
        			cval = SymLit.Search(SYMTAB, sym[0],sym_ptr);
        			newloc_cntr = SYMTAB[cval].addr ;
        			if(csign == '+'){
        				newloc_cntr +=cextra;
        			}
        			else if(csign == '-'){
        				newloc_cntr -= cextra;
        			}
        	
        			
        			if(cextra == 0)
                      line =  ("("+ key + "," + val + ") (" + csl + "," + cval + ")");
                    else
                    	line = ("("+ key + "," + val + ") (" + csl + "," + cval + ")" + csign + cextra + " ");
        		}else if(temp.equals("LTORG")){
        			line = ("("+ key + "," + val + ")");
        	        for(int i=POOLTAB[pool_ptr];i<lit_ptr;i++){
        	        	LITTAB[i].addr = newloc_cntr;
        	        	newloc_cntr++;
        				}
        	        pool_ptr++;
        	        POOLTAB[pool_ptr] = lit_ptr;
        	        //System.out.println(loc_cntr);
        		}
        		else if(temp.equals("END")){
        			line = ("("+ key + "," + val + ")");
        			break outerloop;
        		}
        		
        		
        		
        		flag = 1;
        	}
        	else if(IS.containsKey(temp)){
        		key = "IS";
        		//Imperative Statement is present
        		String symlit="";
				val=IS.get(temp);
				if(val == 7){
					//BC statement code
					if(lflag){
						regval=CC.get(s[1]);
						}
					else{
						regval=REG.get(s[2]);
						s[2] = s[3];
					}
					symlit = s[2];
					csl='S';
					cval = SymLit.Search(SYMTAB, symlit,sym_ptr);
					if(cval == 0)
					{
					SYMTAB[sym_ptr]=new SymLit(symlit);
					cval=sym_ptr;
					sym_ptr++;
					}
				}
				else if(val == 0){
					//stop statement code do nothing
				}
				else{
						if(lflag){
							if(REG.containsKey(s[1]))
							{
								regval=REG.get(s[1]);
								symlit = s[2];
							}else{
								symlit = s[1];
							}
						}
						else{
							if(REG.containsKey(s[2]))
							{
								regval=REG.get(s[2]);
								s[2] = s[3];
							}
							symlit = s[2];
						}

						if(symlit.charAt(0)=='=')
						{
							csl='L';
							LITTAB[lit_ptr]=new SymLit(symlit.substring(1));
							cval = lit_ptr;
							lit_ptr++;
						}
						else
						{
						csl='S';
						cval = SymLit.Search(SYMTAB, symlit,sym_ptr);
						if(cval == 0)
							{
							SYMTAB[sym_ptr]=new SymLit(symlit);
							cval=sym_ptr;
							sym_ptr++;
							}

						}
						
				}

					newloc_cntr++;
	        		flag = 1;
	        		if(csl != '\0'){
	        			if (regval == 0)
		                    line = ("("+ key + "," + val + ") (" + csl + "," + cval + ") " + loc_cntr);
		                else
		                    line = ("("+ key + "," + val + ") (" + regval + ") (" + csl + "," +cval + ") " + loc_cntr );	
	        		}
	        		else{
	        			line = ("("+ key + "," + val + ") " + loc_cntr );
	        		}
	        		 
	        	}
	        	else if(DL.containsKey(temp)){
					val=DL.get(temp);
					key = "DL";
					csl = 'C';
					if(val == 2){
						int length = Integer.parseInt(s[2]);
						newloc_cntr += length;
						cval = SymLit.Search(SYMTAB, s[0], sym_ptr);
						if(cval == 0)
							{
							SYMTAB[sym_ptr]=new SymLit(s[0]);
							sym_ptr++;
							}
						else
							SYMTAB[cval].setAddr(loc_cntr);
						cval = length;
					}if(val ==1){
						cval = SymLit.Search(SYMTAB, s[0], sym_ptr);
						SYMTAB[cval].addr = loc_cntr;
						newloc_cntr++;
						cval = Integer.parseInt(s[2]);
					}
				
				
				line = ("("+ key + "," + val + ") (" + csl + "," +cval + ") " + loc_cntr );
        		flag = 1;
        	}
        	else{
        		//label is present
        		String lable = temp ;
        		cval = SymLit.Search(SYMTAB, temp, sym_ptr);
        		if(cval == 0){
            		SYMTAB[sym_ptr] = new SymLit(lable,loc_cntr);
            		sym_ptr++;
        		}else if(SYMTAB[cval].addr==0){
        			SYMTAB[cval].setAddr(loc_cntr);
        		}

        		temp = s[1];
        		lflag = false;
        		//System.out.println(SYMTAB[sym_ptr-1].name);
        	}
        }
        loc_cntr = newloc_cntr;
        line = line+'\n';
        os.write(line.getBytes(), 0, line.length());//write line in ic.txt
        }
        //store all literals after end statement just like LTORG
        for(int i=POOLTAB[pool_ptr];i<lit_ptr;i++){
        	LITTAB[i].addr = loc_cntr;
        	loc_cntr++;
			}
        pool_ptr++;
        POOLTAB[pool_ptr] = lit_ptr;
        OutputStream os1 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A1/SymbolTable.txt"));
        OutputStream os2 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A1/LiteralTable.txt"));
        OutputStream os3 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A1/PoolTable.txt"));
		String line = "";
		//Symbol table
		for(int i=1;i < sym_ptr;i++)
		{
					
			line =(i+"\t"+SYMTAB[i].name+"\t"+SYMTAB[i].addr+"\t\n");
			os1.write(line.getBytes(), 0, line.length());

		}
		//Literal table
		for(int i=1;i<lit_ptr;i++){
			line = (i+"\t"+LITTAB[i].name+"\t"+LITTAB[i].addr + "\n");
			os2.write(line.getBytes(), 0, line.length());
			}
		//Pool Table
		for(int i=1;i<pool_ptr;i++){
			line = (i+"\t"+POOLTAB[i] + "\n");
			os3.write(line.getBytes(), 0, line.length());
			}
		
		os1.close();
		os.close();
		os2.close();
		os3.close();

	}

}
