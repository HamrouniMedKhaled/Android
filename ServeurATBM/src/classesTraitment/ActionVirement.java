package classesTraitment;

import classesDB.VirementDB;

public class ActionVirement {
	
	String compteSource;
	String compteDest;
	String solde;
		
	public ActionVirement(String compteSource , String compteDest, String solde)
	{
		this.compteSource = compteSource;
		this.compteDest = compteDest;
		this.solde = solde;
	}
	
	public String virement()
	{
		VirementDB vdb = new VirementDB(compteSource, compteDest, solde);
		
		if((vdb.existCompte() == true)&&(vdb.virementSolde() == true))
			return "vir_ok";
		else
			return "vir_not_ok";
	}

}
