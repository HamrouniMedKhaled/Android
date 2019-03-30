package classesTraitment;
import java.io.BufferedReader;
import java.io.PrintWriter;

import classesDB.AuthentificationDB;
import classesDB.CompteDB;


public class ActionCompte {
	
	String [] transCompte;
	
	public ActionCompte(String [] transcompte)
	{
		this.transCompte = transcompte;
	}
	
	public String [] soldeChargee()
	{
		CompteDB charge = new CompteDB(transCompte);
		return charge.chargementSoldeCompte();
	}

}
