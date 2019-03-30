package classesTraitment;

import classesDB.ConversionDB;;

public class ActionConversion {
	
	String devisesource;
	String deviseetranger;
		
		public ActionConversion(String devisesource, String deviseetranger)
		{
			this.devisesource = devisesource;
			this.deviseetranger = deviseetranger;
		}
		
		public String conversionMonnaie()
		{
			ConversionDB convert = new ConversionDB(devisesource, deviseetranger);
			System.out.println("devise:   "+convert.monnaieDevise());
			return convert.monnaieDevise();
		}

}
