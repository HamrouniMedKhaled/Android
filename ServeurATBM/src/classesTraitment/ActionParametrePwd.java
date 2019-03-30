package classesTraitment;

import classesDB.PwdParametreDB;

public class ActionParametrePwd {
	
	String cin;
	String pass;
	String newPass;
	
	public ActionParametrePwd(String cin, String pass, String newPass) {
		// TODO Auto-generated constructor stub
		
		this.cin = cin;
		this.pass = pass;
		this.newPass = newPass;
		
	}
	
	public String changePass()
	{
		PwdParametreDB pwdPara = new PwdParametreDB(cin, pass, newPass);
		pwdPara.changer();
		
		return "modifier pwd ok";
	}

}
