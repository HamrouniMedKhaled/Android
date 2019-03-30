package classesTraitment;

import classesDB.InscriptionDB;

public class ActionInscription {
	
	private String login;
	private String pwd;
	private String email;
	
	public ActionInscription(String login, String pwd, String email)
	{
		this.login = login;
		this.pwd = pwd;
		this.email = email;
	}
	
	public String verifierCompte()
	{
		InscriptionDB c = new InscriptionDB(login, pwd, email);
		if(c.existCompte()==true)
			return "ok";
		else
			return "notok";
	}
	
	public void enregistrer(String passGenere)
	{
		InscriptionDB c = new InscriptionDB(login, pwd, email);
		c.enregistrerClient(passGenere);
	}

}
