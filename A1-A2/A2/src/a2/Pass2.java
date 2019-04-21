package a2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;


class SymLit{
	int addr;
	String name;
	
	SymLit(String n,int a){
		name = n;
		addr = a;
	}
}

public class Pass2 {
	
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		SymLit [] SYMTAB = new SymLit[50];
		SymLit [] LITTAB = new SymLit[50];
		//int [] POOLTAB = new int[10];
		
		//storing data in symbol table
		
		FileReader fr=new FileReader("SymbolTable.txt");
	    BufferedReader br=new BufferedReader(fr);
	    String line;
	    int sym_ptr = 1;
	    while((line = br.readLine()) != null){
	    	String s[] = line.split("\t");
	    	SYMTAB[sym_ptr] = new SymLit(s[1],Integer.parseInt(s[2]));
	    	sym_ptr++;
	    }
	    
	    //storing data in literal table
	    FileReader fr1=new FileReader("LiteralTable.txt");
	    BufferedReader br1=new BufferedReader(fr1);
	    int lit_ptr = 1;
	    while((line = br1.readLine()) != null){
	    	String s[] = line.split("\t");
	    	LITTAB[lit_ptr] = new SymLit(s[1],Integer.parseInt(s[2]));
	    	lit_ptr++;
	    }
	    
	    //read intermediate code line by line
	    FileReader fr2=new FileReader("ic.txt");
	    BufferedReader br2=new BufferedReader(fr2);
	    OutputStream os = new FileOutputStream(new File("/home/TE/SPOSL/3171/A2/FinalCode.txt"));
	    while((line = br2.readLine()) != null){
	    	String loc_cntr = "";
	    	String s[] = line.split(" ");
	    	loc_cntr = s[s.length -1];//get location counter
	    	if(s[0].charAt(1) == 'I'){
	    		//Imperative Statement
	    		int insNo = Integer.parseInt(s[0].charAt(4)+"");
	    		if(s[0].charAt(5) != ')'){
	    			insNo = 10;
	    		}if(s.length == 3){
	    			char regval = '0';
	    			char sl = s[1].charAt(1);
	    			int index = Integer.parseInt(s[1].charAt(3)+"");
	    			int addr = 0;
	    			if(sl == 'L'){
	    				addr = LITTAB[index].addr;
	    			}
	    			else if (sl == 'S'){
	    				addr = SYMTAB[index].addr;
	    			}
	    			line = (loc_cntr + ") " + insNo + " " + regval + " " + addr + "\n" );
	    		}
	    		else if(s.length > 3){
	    			char regval = s[1].charAt(1);//register is present
	    			char sl = s[2].charAt(1);
	    			int index = Integer.parseInt(s[2].charAt(3)+"");
	    			int addr = 0;
	    			if(sl == 'L'){
	    				addr = LITTAB[index].addr;
	    			}
	    			else if (sl == 'S'){
	    				addr = SYMTAB[index].addr;
	    			}
	    			line = (loc_cntr + ") " + insNo + " " + regval + " " + addr + "\n" );
	    		}else{
	    			line = ( loc_cntr + ") " + insNo + " 0 000" + "\n");
	    		}
	    	}
	    	else if(s[0].charAt(1) == 'D'){
	    		//Declarative Statement 
	    		int insNo = Integer.parseInt(s[0].charAt(4)+"");
	    		if(insNo == 1){
	    			//only DC statements
	    			String constant ="";
	    			for(int i =3;i< s[1].length() ;i++){
	    				if(s[1].charAt(i)== ')')
	    					break;
	    				constant = constant + s[1].charAt(i);
	    				
	    			}
	    			
	    			int cons = Integer.parseInt(constant);
	    			line = (loc_cntr + ") 0 0 " + cons + "\n");
	    		}
	    		else{
	    			//for DS statements
	    			line = "\n";
	    		}
	    	}
	    	else{
	    		//for AD statements
	    		line = "\n";
	    	}
		   os.write(line.getBytes(), 0, line.length());
	    }
	    

	    
		
	    
	    br.close();
	    br1.close();
		br2.close();
		os.close();
	}

}
