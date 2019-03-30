package classesTraitment;

public class Cryptonite {
	
	String superMan;
	String superManDied;
	char [] word = new char[100];
	
	public Cryptonite()
	{
		
	}
	
	
	int [] wordASCII = new int[word.length];
	
	int [] wordASCIIFinal = new int[word.length];
	String [] cWordB = new String[word.length];
	
	//String theKey = "IBY";
	char [] key = {'I','B','Y','&','H','M','K'};
	int [] keyASCII = new int[key.length];
	String [] cKeyB = new String[7];
	
	int taille=0;
	int crypt = 0;
	
	public String greenCryptonite(String superMan)
	{
		
        this.superMan = superMan;
        
        System.out.println(superMan);
		
		for(int i=0;i<superMan.length();i++)
		{
			word[i] = this.superMan.charAt(i);
		}
		
		for(int i = 0;i<word.length;i++)
		{
			if(word[i]!= 0)
			{
				wordASCII[i] = toASCII(word[i]);
				//System.out.println(wordASCII[i]);
				cWordB[i] = toBinaire(wordASCII[i]);
				//System.out.println(cWordB[i]);
				taille++;
			}
				
		}
		
		String [] binaireCrypt = new String[taille];
		String [] wordFinal = new String[taille];
		
		for(int i = 0;i<key.length;i++)
		{
			keyASCII[i] = toASCII(key[i]);
			//System.out.println(keyASCII[i]);
			cKeyB[i] = toBinaire(keyASCII[i]).toString();
			//System.out.println(cKeyB[i]);
		}
		
		for(int i = 0;i<wordASCII.length;i++)
		{
			if(cWordB[i]!= null)
			{
				if(crypt == 2)
					crypt = 0;
				//System.out.println("char binaire "+i+"  "+cWordB[i]);
				String [] w = cWordB[i].split("");
				String [] w1 = new String[9];
				String [] k = cKeyB[crypt].split("");
				crypt++;
					
				for(int j = 0;j<9;j++)
				{	
					//System.out.println("iteration "+i+"   "+w[j]);
					if(w[j].equals(k[j]))
						w1[j] = "0";
					else
						w1[j] = "1";
					//System.out.println(w[j]);
					
					binaireCrypt[i] = binaireCrypt[i] + w1[j];
				}
				
				binaireCrypt[i] = binaireCrypt[i].substring(5, binaireCrypt[i].length());
				//System.out.println("char "+i+"   "+binaireCrypt[i]);
				
				wordASCIIFinal[i]= toDecimal(binaireCrypt[i]);
				wordFinal[i] = String.valueOf(toChar(wordASCIIFinal[i]));
				superManDied += wordFinal[i];
			}
			
		}
		superManDied = superManDied.substring(4, superManDied.length());
		System.out.println(superManDied);
		return superManDied;
	}
	
	//convert from code ASCII to Char
	public char toChar(int codeASCII)
	{
		//System.out.println("convert from code ASCII to Char");
		//System.out.print(codeASCII+"    ");
		//System.out.println((char)codeASCII);
	       return (char)codeASCII;
	}

	//convert from char to code ASCII
	public int toASCII(char lettre)
	{
		//System.out.println("convert from char to code ASCII");
		//System.out.print(lettre+"    ");
		//System.out.println((int)lettre);
	       return (int)lettre;
	}
	
	//convert from code ASCII to Binaire
	public String toBinaire(int a)
	{
		String [] st = new String [8];
		String binaire;
		//System.out.println("convert from code ASCII to Binaire");
		//System.out.print(a+"    ");
		//String st=Integer.toBinaryString(a);
		
		for(int bit = 0;bit<8;bit++)
		{
			
			st[bit]= String.valueOf(a%2);
			a = a/2;
			
		}
		//System.out.println(st[7]+""+st[6]+""+st[5]+""+st[4]+""+st[3]+""+st[2]+""+st[1]+""+st[0]);
		binaire = st[7]+""+st[6]+""+st[5]+""+st[4]+""+st[3]+""+st[2]+""+st[1]+""+st[0];
		return binaire;
	}
	
	//convert from Binaire to code ASCII
	public int toDecimal(String d)
	{
		double monDecimal = 0;
		String [] octet = d.split("");
		int x = 8;
		for(int n = 1;n<d.length()+1;n++)
		{
			//System.out.println("bit "+n+"   "+octet[n]);
			if(octet[n].equals("1"))
			{
				monDecimal += (Math.pow(2, x-1));
				//System.out.println("power  "+Math.pow(2, x-1));
			}
			x--;
			
		}
		//System.out.println(monDecimal);
		return (int)monDecimal;
	}
	
	public String blueCryptonite(String superMan)
	{
		
        this.superMan = superMan;
        
        System.out.println(superMan);
		
		for(int i=0;i<superMan.length();i++)
		{
			word[i] = this.superMan.charAt(i);
		}
		
		for(int i = 0;i<word.length;i++)
		{
			if(word[i]!= 0)
			{
				wordASCII[i] = toASCII(word[i]);
				//System.out.println(wordASCII[i]);
				
					cWordB[i] = toBinaire(wordASCII[i]).toString();
				
					cWordB[i] = toBinaire(wordASCII[i]).toString();
				//System.out.println(cWordB[i]);
				taille++;
			}
				
		}
		
		String [] binaireCrypt = new String[taille];
		String [] wordFinal = new String[taille];
		
		for(int i = 0;i<key.length;i++)
		{
			keyASCII[i] = toASCII(key[i]);
			//System.out.println(keyASCII[i]);
			cKeyB[i] = toBinaire(keyASCII[i]);
			//System.out.println(cKeyB[i]);
		}
		
		for(int i = 0;i<wordASCII.length;i++)
		{
			if(cWordB[i]!= null)
			{
				if(crypt == 2)
					crypt = 0;
				//System.out.println("char binaire "+i+"  "+cWordB[i]);
				String [] w = cWordB[i].split("");
				String [] w1 = new String[9];
				String [] k = cKeyB[crypt].split("");
				crypt++;
					
				for(int j = 0;j<9;j++)
				{	
					//System.out.println("iteration "+i+"   "+w[j]);
					if(w[j].equals(k[j]))
						w1[j] = "0";
					else
						w1[j] = "1";
					//System.out.println(w[j]);
					
					binaireCrypt[i] = binaireCrypt[i] + w1[j];
				}
				//System.out.println("char "+i+"   "+binaireCrypt[i]);
				binaireCrypt[i] = binaireCrypt[i].substring(5, binaireCrypt[i].length());
				//System.out.println("char "+i+"   "+binaireCrypt[i]);
				
				wordASCIIFinal[i]= toDecimal(binaireCrypt[i]);
				wordFinal[i] = String.valueOf(toChar(wordASCIIFinal[i]));
				superManDied += wordFinal[i];
			}
			
		}
		superManDied = superManDied.substring(4, superManDied.length());
		
		System.out.println(superManDied);
		
		return superManDied;
	}
}
