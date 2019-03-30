package classesTraitment;

import classesDB.AddMailParamatreDB;
import classesDB.DeleteMailParametreDB;

public class ActionParametreDeleteMail {
	
	String cin;
	String pass;
	String mailSupp;
	
	public ActionParametreDeleteMail(String cin, String pass, String mailSupp) {
		// TODO Auto-generated constructor stub
		
		this.cin = cin;
		this.pass = pass;
		this.mailSupp = mailSupp;
		
	}
	
	public String suppMail()
	{
		DeleteMailParametreDB delMailPara = new DeleteMailParametreDB(cin, pass, mailSupp);
		delMailPara.supprimer();
		
		return "supprime mail ok";
	}

}
