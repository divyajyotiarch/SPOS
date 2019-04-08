import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

//generates intermediate code . MDT and MNT
class MNTClass{
	String mname;
	int params;
	int sindex;
	MNTClass(String n,int p,int s){
		mname = n;
		params = p;
		sindex = s;
		
	}
}

public class Pass1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String MDT[] = new String[100];
		MNTClass MNT[] = new MNTClass[100];
		
		FileReader fr=new FileReader("SourceCode.txt");
        BufferedReader br=new BufferedReader(fr);
        
        OutputStream os = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/ic.txt"));

        
        String line;
        int mdt_pntr = 1;
        int mnt_pntr = 1;
        while((line = br.readLine()) != null){
        	String s[]= line.split(" ");
        	if(s[0].equals("Macro")){
        		int p = s.length -1 ;
        		MNT[mnt_pntr]= new MNTClass(s[1],p,mdt_pntr);
        		mnt_pntr++;
        		while(line.equals("MEND")==false){
        			line = br.readLine();
        			MDT[mdt_pntr] = line;
        			mdt_pntr++;
        		}
        		//MDT[mdt_pntr]="MEND";
        		//mdt_pntr++;
        	}else if(s[0].equals("START")){
        		
        		while(line.equals("END")==false){
        			System.out.println(line);
        			//os.write(line.getBytes(), 0, line.length());
        			line = br.readLine();
        		}
        		//os.write(line.getBytes(), 0, line.length());
        		System.out.println(line);
        	}
        
        }
        
        OutputStream os1 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/MDT.txt"));
        OutputStream os2 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/MNT.txt"));
        
        System.out.println("MDT");
        System.out.println("Index MacroDefinition");
        for(int i =1;i<mdt_pntr ;i++){
        	System.out.println(i + " " + MDT[i]);
			//os1.write(line.getBytes(), 0, line.length());

        }
        
        System.out.println("MNT");
        System.out.println("Index MacroName Parameters StartIndex");
        for(int i =1;i<mnt_pntr ;i++){
        	System.out.println(i + " " + MNT[i].mname + " " + MNT[i].params +" " + MNT[i].sindex);
			//os2.write(line.getBytes(), 0, line.length());

        }
        
        os.close();os1.close();os2.close();br.close();

	}

}
