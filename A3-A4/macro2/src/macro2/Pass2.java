package macro2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

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
	static int Search(MNT mnt[],int n,String key) {
		int index =-1;
		for(int i =1;i<n;i++) {
			if(mnt[i].name.equals(key)){
				return i;
			}
		}
		return index;
	}
	
}

class KPTAB{
	int index;
	String name;
	String value;
	
	public KPTAB(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	static int searchByName(KPTAB kptab[],int n,String key) {
		int index=-1;
		for(int i =0;i<n;i++) {
			if(kptab[i].name.equals(key)) {
				return i;
			}
		}
		return index;
	}
	
	
}

public class Pass2 {	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String mdt[] = new String[100];
		MNT mnt[] = new MNT[100];
		String calls[] = new String[100];
		KPTAB kptab[] = new KPTAB[20];
		String line;
		int mntptr =1,mdtptr =1, kpptr = 1,call=1;
		
		
		FileReader fr = new FileReader("MNT.txt");
		BufferedReader br = new BufferedReader(fr);
		
		FileReader fr1 = new FileReader("MDT.txt");
		BufferedReader br1 = new BufferedReader(fr1);
		
		FileReader fr2 = new FileReader("KPDTAB.txt");
		BufferedReader br2 = new BufferedReader(fr2);
		
		FileReader fr3 = new FileReader("calls.txt");
		BufferedReader br3 = new BufferedReader(fr3);
		
		OutputStream os = new FileOutputStream(new File("final.txt"));
		
		while((line = br.readLine())!=null){
			String s[] = line.split(" ");
			mnt[mntptr] = new MNT(s[0],Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]),Integer.parseInt(s[4]));
			//System.out.println(mnt[mntptr].name + " "+ mnt[mntptr].pp + " " + mnt[mntptr].kp + " " + mnt[mntptr].mdtp + " "+ mnt[mntptr].kpdtp);
			mntptr++;
		}
	    while((line = br1.readLine()) != null){
	    	//String s[] = line.split(" ");
	    	mdt[mdtptr] = line;
	    	mdtptr++;
	    }
	    
	    while((line = br3.readLine()) != null){
	    	//String s[] = line.split(" ");
	    	calls[call] = line;
	    	call++;
	    }
	    
	    while((line = br2.readLine()) != null){
	    	String s[] = line.split(" ");
	    	kptab[kpptr] = new KPTAB(s[1],s[2]);
	    	//System.out.println(kptab[kpptr].name +" "+ kptab[kpptr].value );
	    	kpptr++;
	    }
	    //process every call;
	    for(int i =1;i<call;i++){
	    	String s[] = calls[i].split(" ");//current call
	    	//search for name in MNT and get pp and kp to form APTAB
	    	int mnt_index = MNT.Search(mnt,mntptr,s[0]);
	    	String[] params = s[1].split(",");
	    	int kp = mnt[mnt_index].kp;
	    	int pp = mnt[mnt_index].pp;
	    	String aptab[] = new String[kp+pp+1];
	    	//copy value of positional parameters from call
	    	for(int j=0;j<pp;j++) {
	    		aptab[j+1] = params[j];
	    	}
	    	
	    	//copy value of default keyword parameters from kptab
	    	int kp_ptr = mnt[mnt_index].kpdtp;
	    	for(int j=0;j<kp;j++) {
	    		aptab[pp+j+1]=kptab[kp_ptr+j].value;
	    	}
	    	
	    	//search for keyword parameters in call, if present, replace their values in aptab
	    	if(params.length>pp) {
	    		for(int j=kp_ptr;j<kp_ptr+kp;j++) {
	    			String sub = "&" + kptab[j].name;
	    			//search sub in params
	    			for(int k =pp;k<params.length;k++) {
	    				if(params[k].contains(sub)) {
	    					//
	    					int aptab_ptr = j+1 - kp_ptr +pp;
	    					aptab[aptab_ptr]=params[k].split("=")[1];
	    				}
	    			}
	    		}
	    	}	
	    	//now replace (P,n) with aptab[n] in MDT till MEND and write it to "finalcode.txt"
	    	int j = mnt[mnt_index].mdtp;
	    	while(mdt[j].equals("MEND")==false) {
	    		for(int k =1;k<aptab.length;k++) {
	    			mdt[j] = mdt[j].replace("(P,"+k+")", aptab[k]);
	    		}
	    		line = mdt[j]+'\n';
	    		os.write(line.getBytes(),0,line.length());
	    		j++;
	    	}
	    }
	    os.close();
	    br.close();
	    br1.close();
	    br2.close();
	    br3.close();
	    
	}

}