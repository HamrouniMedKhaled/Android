package classesTraitment;

import classesDB.HistoriqueDB;

public class ActionHistorique {
	
String transCompte;
String dateDebut;
String dateFin;
	
	public ActionHistorique(String transcompte, String dateDebut, String dateFin)
	{
		this.transCompte = transcompte;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	
	public String [] transChargee()
	{
		HistoriqueDB chargeHisto = new HistoriqueDB(transCompte, dateDebut, dateFin);
		return chargeHisto.chargementTransCompte();
	}

}
