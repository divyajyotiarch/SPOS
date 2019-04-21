package macro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;


class MNT{
	String name;
	int pp,kp,mntptr,kpdptr;
	
	MNT(String name,int pp,int kp,int mntptr,int kpdptr){
		this.name=name;
		this.pp=pp;
		this.kp=kp;
		this.mntptr=mntptr;
		this.kpdptr=kpdptr;
	}
	
}

class KPDTAB{
	String param,value;
	KPDTAB(String param,String value){
		this.param=param;
		this.value=value;
	}
}

public class macro1 {

	public static void main(String[] args) throws IOException {
		
		MNT [] mntab = new MNT[5];
		KPDTAB[] kpdtab = new KPDTAB[10];
		String[] mdt = new String[20];

		int mnindex=0,kpdptr=0,pnptr=1,mdtptr=1;
		
		HashMap<String,Integer>pntab = new HashMap<>();
		
		String[] strArray = new String[7];
		String currLine;
		
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		
		while((currLine=br.readLine())!=null){
			int m=0;
			StringTokenizer st = new StringTokenizer(currLine);
			while(st.hasMoreTokens()){
				
				strArray[m] = st.nextToken();
				System.out.print(strArray[m]+" ");
				m++;
			}
			System.out.println();
			
			if(strArray[0].equals("MACRO")){
				pntab.clear(); pnptr=1;
				int kp=0,pp=0;
				for(int i=2;i<m;i++){
					if(strArray[i].contains("=")){
						String [] keyp = strArray[i].split("=");
						kpdtab[kpdptr] = new KPDTAB(keyp[0],keyp[1]);
						pntab.put(keyp[0],pnptr);
						kpdptr++;
						pnptr++;
						kp++;
					}else
					{
						pntab.put(strArray[i],pnptr);
						pnptr++;
						pp++;
					}
				}
				mntab[mnindex] = new MNT(strArray[1],pp,kp,mdtptr,kpdptr-kp);
				mnindex++;
			}
			else if(strArray[0].equals("MEND"))
			{
				mdt[mdtptr]="MEND";
				mdtptr++;
			}
			else
			{
				if(strArray[1].contains("&") && strArray[2].contains("&")){
					mdt[mdtptr] = strArray[0] + " #" + pntab.get(strArray[1]) + " #" + pntab.get(strArray[2]);
					mdtptr++;
				}
				else
				{
					mdt[mdtptr] = strArray[0] + " #" + pntab.get(strArray[1]) + " " + strArray[2];
					mdtptr++;
				}
				
			}
			
			
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter("ic.txt"));
		
		for(int j=1;j<=10;j++){
			System.out.println(mdt[j]);
			bw.write(mdt[j]);
			bw.newLine();
		}
		bw.close();
		
		for(int j=0;j<kpdptr;j++){
			System.out.println(kpdtab[j].param + " " + kpdtab[j].value);
		}
		
		for(int j=0;j<mnindex;j++){
			System.out.println(mntab[j].name + " " + mntab[j].pp + " " + mntab[j].kp + " " + mntab[j].mntptr + " " + mntab[j].kpdptr);
		}

	}

}
