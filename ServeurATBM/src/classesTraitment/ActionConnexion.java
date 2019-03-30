package classesTraitment;

import classesDB.AuthentificationDB;



public class ActionConnexion {
	
	private String login;
	private String pwd;
	
	public ActionConnexion(String login, String pwd)
	{
		this.login = login;
		this.pwd = pwd;
	}
	
	
	public String verifierConnexion()
	{
		AuthentificationDB c = new AuthentificationDB(login, pwd);
		if(c.existFirst()==true)
			return "first ok";
		else if (c.exist()==true)
			return "ok";
		else
			return "notok";
	}
	
	public String [] donneeChargee()
	{
		AuthentificationDB charge = new AuthentificationDB(login, pwd);
		return charge.chargementNumCompte();
	}

}
