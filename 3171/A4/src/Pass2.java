import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


class MNTClass{
	String mname;
	int params;
	int sindex;
	MNTClass(String n,int p,int s){
		mname = n;
		params = p;
		sindex = s;
		
	}
	static int searchMacro(MNTClass obj[],int n,String key){
	
		for(int i = 1;i<n;i++){
			if(key.equals(obj[i].mname)== true)
				return i;
		}
		return 0;
	}
}
public class Pass2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String MDT[] = new String[100];
		MNTClass MNT[] = new MNTClass[100];
		
		String line;
		//fill MDT
	    //storing data in literal table
	    FileReader fr1=new FileReader("MDT.txt");
	    BufferedReader br1=new BufferedReader(fr1);
	    int mdt_ptr = 1;
	    while((line = br1.readLine()) != null){
	    	//String s[] = line.split(" ");
	    	MDT[mdt_ptr] = line;
	    	mdt_ptr++;
	    }
	    
	    FileReader fr=new FileReader("MNT.txt");
	    BufferedReader br=new BufferedReader(fr);
	    int mnt_ptr = 1;
	    while((line = br.readLine()) != null){
	    	String s[] = line.split(" ");
	    	MNT[mnt_ptr] = new MNTClass(s[1],Integer.parseInt(s[2]),Integer.parseInt(s[3]));
	    	mnt_ptr++;
	    }
		
		FileReader fr2=new FileReader("ic.txt");
        BufferedReader br2=new BufferedReader(fr2);
        
        while((line = br2.readLine()) != null){
        	String s[] = line.split(" ");
        	
        	int index;
        	
        	
        	if((index = MNTClass.searchMacro(MNT,mnt_ptr,s[0]))!=0){
        		//Macro Call
        		//form Positional vs Actual parameters table
        		int params = MNT[index].params;
        		HashMap<String,String> PvsA = new HashMap<>();

        		for(int  i =1;i<=params ;i++){
        			String key = "#"+ i;
        			PvsA.put(key,s[i]);
        		}
        		//start reading lines from MDT till MEND from start index
        		int start = MNT[index].sindex;
        		line = MDT[start];
        		start++;
        		while(line.equals("MEND")==false){
        			
        			if(params != 0){
        				String s1[] = line.split(" ");
        				if(s1[1].charAt(0)== '#')
        				{
        					//replace with actual parameters if position present in MDT
        					String arg = PvsA.get(s1[1]) ;
        					s1[1] = arg;
        				}
        				line = s1[0] +" " +s1[1];
        			}
        			System.out.println(line);
        			line = MDT[start];
        			start++;
        		}
        		
        	}else{
        		//normal statement
        		System.out.println(line);
        	}
        }
        br1.close();
        br.close();
        br2.close();
        fr.close();
        fr1.close();
        fr2.close();
	}

}
