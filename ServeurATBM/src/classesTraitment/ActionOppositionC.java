package classesTraitment;

import classesDB.COppositionDB;
import classesDB.ChOppositionDB;

public class ActionOppositionC {
	
	String num_c;
	String raison;
	
public ActionOppositionC(String num_c, String raison) {
		
		this.num_c = num_c;
		this.raison = raison;
	}
	
	public String autoOppositionC()
	{
		
		
		
		System.out.println("mettre en opposiotn chequier...");
		
		
		COppositionDB chOpposition = new COppositionDB(num_c,raison);
		chOpposition.oppositionC();
		
		return "opposition ok";
	}

}
