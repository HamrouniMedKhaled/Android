package classesTraitment;

import classesDB.ChDemandeDB;
import classesDB.ChOppositionDB;

public class ActionOppositionCh {
	
	String num_ch;
	String raison;
	
	public ActionOppositionCh(String num_ch, String raison) {
		
		this.num_ch = num_ch;
		this.raison = raison;
	}
	
	public String autoOppositionCh()
	{
		String lot = null;
		
		
		System.out.println("mettre en opposiotn chequier...");
		
		
		ChOppositionDB chOpposition = new ChOppositionDB(num_ch,raison);
		chOpposition.oppositionCh();
		
		return "opposition ok";
	}

}
