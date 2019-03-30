package classesTraitment;

import classesDB.AddMailParamatreDB;


public class ActionParametreAddMail {
	
	String cin;
	String pass;
	String newMail;
	
	public ActionParametreAddMail(String cin, String pass, String newMail) {
		// TODO Auto-generated constructor stub
		
		this.cin = cin;
		this.pass = pass;
		this.newMail = newMail;
		
	}
	
	public String ajoutMail()
	{
		AddMailParamatreDB addMailPara = new AddMailParamatreDB(cin, pass, newMail);
		addMailPara.ajouter();
		
		return "ajout mail ok";
	}

}
