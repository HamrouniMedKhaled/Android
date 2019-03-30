package classesTraitment;

import classesDB.ChDemandeDB;

public class ActionDemandeCh {
	
	String type;
	String nbr_ch;
	String num_compt;
	
	
	public ActionDemandeCh(String type, String nbr_ch, String num_compt) {
		
		this.type = type;
		this.nbr_ch = nbr_ch;
		this.num_compt = num_compt;
		
	}
	
	public String autoDemandeCh()
	{
		String lot = null;
		
		char c;
		int j = 0;
		while(j<9)
		{
			c = toChar((int)(Math.random()*(57-48))+48);
			
				
				lot += c;
				j++;
			
			
		}
		
		System.out.println(lot);
		System.out.println(num_compt+lot.substring(4, lot.length()));
		
		ChDemandeDB chDemande = new ChDemandeDB(type, nbr_ch, num_compt);
		chDemande.autorisationCh(lot.substring(4, lot.length()));
		
		return "demande ok"+lot.substring(4, lot.length());
	}
	
	//convert from code ASCII to Char
			public char toChar(int codeASCII)
			{
				//System.out.println("convert from code ASCII to Char");
				//System.out.print(codeASCII+"    ");
				//System.out.println((char)codeASCII);
			       return (char)codeASCII;
			}

}
