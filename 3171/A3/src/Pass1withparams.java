import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;


public class Pass1withparams {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		
		String MDT[] = new String[100];
		MNTClass MNT[] = new MNTClass[100];
		
		FileReader fr=new FileReader("code1.txt");
        BufferedReader br=new BufferedReader(fr);
        
        OutputStream os = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/ic.txt"));

        
        String line;
        int mdt_pntr = 1;
        int mnt_pntr = 1;
        while((line = br.readLine()) != null){
        	//System.out.println(line);
        	String s[]= line.split(" ");
        	if(s[0].equals("Macro")){
        		int p = s.length -2 ;
        		MNT[mnt_pntr]= new MNTClass(s[1],p,mdt_pntr);
        		mnt_pntr++;
        		HashMap<String ,Integer> FvsP = new HashMap<>();
    			if(p!=0){
    				//make formal vs positional
    				for(int i = 1; i<=p ; i++){
    					FvsP.put(s[i+1], i);
    				}
    				
    			}
        		while(line.equals("MEND")==false){
        			line = br.readLine();
        			if(p!=0){
        				String s1[] = line.split(" ");
        				if(s1.length >1){
            				String parameter = s1[1];
            				if(FvsP.containsKey(s1[1]))
            				parameter = "#" +FvsP.get(s1[1]).toString();
            				line = s1[0] +" " + parameter;
        				}
        			}
        			MDT[mdt_pntr] = line;
        			mdt_pntr++;
        		}
        		//MDT[mdt_pntr]="MEND";
        		//mdt_pntr++;
        	}else if(s[0].equals("START")){
        		
        		while(line.equals("END")==false){
        			//System.out.println(line);
        			line = line +'\n';
        			os.write(line.getBytes(), 0, line.length());
        			line = br.readLine();
        		}
        		line = line +'\n';
        		os.write(line.getBytes(), 0, line.length());
        		//System.out.println(line);
        	}
        
        }
        
        OutputStream os1 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/MDT.txt"));
        OutputStream os2 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/MNT.txt"));
        
        System.out.println("MDT");
        System.out.println("Index MacroDefinition");
        for(int i =1;i<mdt_pntr ;i++){
        	line = (MDT[i]);
        	line = line +'\n';
			os1.write(line.getBytes(), 0, line.length());

        }
        
        System.out.println("MNT");
        System.out.println("Index MacroName Parameters StartIndex");
        for(int i =1;i<mnt_pntr ;i++){
        	line = (i + " " + MNT[i].mname + " " + MNT[i].params +" " + MNT[i].sindex);
        	line = line +'\n';
			os2.write(line.getBytes(), 0, line.length());

        }
        
        os.close();os1.close();os2.close();br.close();

	}

}
