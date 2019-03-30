package classesTraitment;

import classesDB.CDemandeDB;


public class ActionDemandeC {
	
	String nom;
	String type;
	String plafond;
	String validite;
	String num_compt;
	
	public ActionDemandeC(String nom, String type, String plafond, String validite, String num_compt) {
		
		this.nom = nom;
		this.type = type;
		this.plafond = plafond;
		this.validite = validite;
		this.num_compt = num_compt;
		
	}
	
	public String autoDemandeC()
	{
		
		CDemandeDB cDemande = new CDemandeDB(nom, type, plafond, validite, num_compt);
		cDemande.autorisationC();
		
		return "demande ok";
	}

}
