package macro1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

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


public class Pass1 {
	//calls , MDT , MNT, KPDTAB are stored in different files 
	public static void main(String[] args) throws IOException {
		int mntptr=1,mdtptr=1,kpptr=1;
		MNT mnt[] = new MNT[6];
		String mdt[] = new String[50];
		HashMap<String,String> kptab = new HashMap<>();
		OutputStream os1 = new FileOutputStream(new File("calls.txt"));
		FileReader fr = new FileReader("code2.txt");
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine())!= null){
			if(line.equals("MACRO")){
				HashMap<String,Integer> pntab = new HashMap<>();
				line = br.readLine();
				String params[] = line.split(",");
				String s1[] = params[0].split(" ");//params[0] contains name and 1st parameter
				int k=0,p=0;
				//keyword parameters contain '='
				if(s1[1].contains("=")) {
					k =1;
					//name of parameter will be before =
					int equalto_index = s1[1].indexOf('=');
					pntab.put(s1[1].substring(0,equalto_index),1);
				}
				else {
					p =1;
					pntab.put(s1[1],1);
				}
				
				for(int i =1;i<params.length;i++){
					if(params[i].contains("=")){
						k++;
						int equalto_index = params[i].indexOf('=');
						pntab.put(params[i].substring(0,equalto_index),i+1);
						//add keyword parameters in kptab
						String s2[] = params[i].split("=");
						//separating name and value of keyword parameters
						String v;//default value
						if(s2.length==1){
							v = "-";
						}else{
							v = s2[1];
						}
						kptab.put(s2[0].substring(1)+"",v);//store without '&' in kptab
					}
					else{
						p++;
						pntab.put(params[i],i+1);
					}
				}
				System.out.println(Arrays.asList(pntab));
				
				mnt[mntptr] = new MNT(s1[0],p,k,mdtptr,kpptr);
				kpptr +=k;
				mntptr++;
				while(line.equals("MEND")==false){
        			line = br.readLine();
					String write ="";
					String s3[] = line.split(" ");//replace all parameters with (P,n) 
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
        OutputStream os2 = new FileOutputStream(new File("MDT.txt"));
        OutputStream os3 = new FileOutputStream(new File("MNT.txt"));
        OutputStream os4 = new FileOutputStream(new File("KPDTAB.txt"));
		int count =1;
		for (Entry<String,String> entry : kptab.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  line = (count + " " +key +" "+ value)+"\n";
			  os4.write(line.getBytes(),0,line.length());
			  count++;
			}
		
		for(int i =1;i<mntptr;i++){
			line = (mnt[i].name + " "+ mnt[i].pp + " " + mnt[i].kp + " " + mnt[i].mdtp + " "+ mnt[i].kpdtp)+"\n";
			os3.write(line.getBytes(),0,line.length());
		}
		for(int i =1;i<mdtptr;i++){
			line = (mdt[i])+"\n";
			os2.write(line.getBytes(),0,line.length());
		}
		br.close();
		os1.close();
		os2.close();
		os3.close();
		os4.close();
	}
}
