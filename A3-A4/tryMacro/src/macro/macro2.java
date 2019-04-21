package macro;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class macro2 {
	
	

	public static void main(String[] args) throws IOException {
		
		MNT[] mntab = new MNT[2];
		mntab[0] = new MNT("M1",2,2,1,0);
		mntab[1] = new MNT("M2",2,2,6,2);
		
		KPDTAB[] kpdtab = new KPDTAB[4];
		kpdtab[0] = new KPDTAB("&A", "AREG");
		kpdtab[1] = new KPDTAB("&B", "-");
		kpdtab[2] = new KPDTAB("&U", "CREG");
		kpdtab[3] = new KPDTAB("&V", "DREG");
		
		HashMap<Integer,String>alptab = new HashMap<>();
		int alp=1;
		
		BufferedReader br = new BufferedReader(new FileReader("call.txt"));
		BufferedReader ic = new BufferedReader(new FileReader("ic.txt"));
	
		
		String currBr,currIc;
		String[] mdt = new String[20];
		String[] strArray = new String[6];
		
		while((currBr=br.readLine())!=null){
			
			int m=0;
			StringTokenizer st = new StringTokenizer(currBr);
			while(st.hasMoreTokens()){
				
				strArray[m] = st.nextToken();
				System.out.print(strArray[m]+" ");
				m++;
			}
			System.out.println();
			for(int i=0;i<2;i++){
				if(strArray[0].equals(mntab[i].name))
				{
					if(m-1 < (mntab[i].kp+mntab[i].pp)) //no. of parameters are less
					{
					for(int j=1;j<(mntab[i].kp+mntab[i].pp);j++){
						if(j==3){
							alptab.put(j, kpdtab[0].value);
							if(strArray[j].contains("=")){
								String[] keyp = strArray[j].split("=");
								alptab.put(j+1,keyp[1]);
							}
						}
						else
						{
							alptab.put(j,strArray[j]);
						}
					}
					}
					else
					{
						for(int j=1;j<=(mntab[i].kp+mntab[i].pp);j++){
							if(strArray[j].contains("=")){
								String[] keyp = strArray[j].split("=");
								alptab.put(j,keyp[1]);
							}
							else
							{
								alptab.put(j,strArray[j]);
							}
						}
						
					}
					currIc="";
					while(!currIc.equals("MEND")){
						currIc=ic.readLine();
						int mic=0;
						StringTokenizer stic = new StringTokenizer(currIc);
						while(stic.hasMoreTokens()){
							
							strArray[mic] = stic.nextToken();
						//	System.out.print(strArray[mic]+" ");
							mic++;
						}
					//	System.out.println();
						
						//replace #num with alptab values
						
						if(strArray[1].contains("#") && strArray[2].contains("#") )
						{
							String[] key = strArray[1].split("#");
							String[] key1 = strArray[2].split("#");
							System.out.println(strArray[0]+" "+ alptab.get(Integer.parseInt(key[1])) + " " + alptab.get(Integer.parseInt(key1[1])));
						}
						else if(strArray[1].contains("#") && !strArray[0].equals("MEND")){
							String[] key = strArray[1].split("#");
							//key[1] contains number
							System.out.println(strArray[0]+" "+ alptab.get(Integer.parseInt(key[1])) +" "+strArray[2]);
							
						}
						
					}
					
				}
					
				
			}
				
		
		}
		
		
	}

}
