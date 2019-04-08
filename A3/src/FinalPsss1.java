import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

class MNT{
	String name;
	int pp;
	int kp;
	int mdtp;
	int kpdtp;
	
	MNT(String n,int p,int kp,int m,int k){
		name = n;
		pp = p;
		this.kp= kp;
		mdtp = m;
		kpdtp = k;
	}
	
}



public class FinalPsss1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int mntptr=1,mdtptr=1,kpptr=1,pnptr=1;
		MNT mnt[] = new MNT[6];
		String mdt[] = new String[50];
		HashMap<String,String> kptab = new HashMap<>();
		OutputStream os1 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/calls.txt"));
		FileReader fr = new FileReader("code2.txt");
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine())!= null){
			
			//System.out.println(line);
			if(line.equals("MACRO")){
				HashMap<String,Integer> pntab = new HashMap<>();
				line = br.readLine();
				String s[] = line.split(",");
				String s1[] = s[0].split(" ");
				int k=0,p=0;
				if(s1[1].contains("="))
					k =1;
				else
					p =1;
					pntab.put(s1[1].substring(0,2),1);
				
					
				
				for(int i =1;i<s.length;i++){
					if(s[i].contains("=")){
						k++;
						//add keyword parameters in kptab
						String s2[] = s[i].split("=");
						String v;
						if(s2.length==1){
							v = "-";
						}else{
							v = s2[1];
						}
						kptab.put(s2[0].charAt(1)+"",v);
					}
					else{
						p++;
					}
					pntab.put(s[i].substring(0,2),i+1);
				}
				mnt[mntptr] = new MNT(s1[0],p,k,mdtptr,kpptr);
				kpptr +=k;
				mntptr++;
				//System.out.println(mnt[1].name +" " +mnt[1].pp);
				while(line.equals("MEND")==false){
        			line = br.readLine();
					String write ="";
					System.out.println(line);
					String s3[] = line.split(" ");
					for(int i =0;i<s3.length;i++){
						if(pntab.containsKey(s3[i])){
							write += " (P,"+pntab.get(s3[i])+") ";
						}
						else{
							write += s3[i];
						}
					}mdt[mdtptr] = write;
					mdtptr++;
				}
				mdt[mdtptr]="MEND";
			}
			else{
				line += "\n";
				os1.write(line.getBytes(), 0, line.length());
			}
		}
        OutputStream os2 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/MDT.txt"));
        OutputStream os3 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/MNT.txt"));
        OutputStream os4 = new FileOutputStream(new File("/home/TE/SPOSL/3171/A3/KPDTAB.txt"));
		
		for (Entry<String,String> entry : kptab.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  line = (key +" "+ value)+"\n";
			  os4.write(line.getBytes(),0,line.length());
			}
		
		for(int i =1;i<mntptr;i++){
			line = (mnt[i].name + " "+ mnt[i].pp + " " + mnt[i].kp + " " + mnt[i].kpdtp + " "+ mnt[i].mdtp)+"\n";
			os3.write(line.getBytes(),0,line.length());
		}
		for(int i =1;i<mdtptr;i++){
			line = (mdt[i])+"\n";
			os2.write(line.getBytes(),0,line.length());
		}
		
	}

}
