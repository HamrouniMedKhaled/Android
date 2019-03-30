package classesTraitment;

import java.util.ArrayList;

import classesDB.mapDB;

public class ActionMap {
	
	ArrayList<String> infoMap =  new ArrayList<String>();
	
	public ActionMap()
	{
		System.out.println("tentation de connexion");
		mapDB mdb = new mapDB();
		infoMap = mdb.chargementCoordonnee();
	}
	
	public ArrayList<String> infoMap()
	{
		
		
		
		return infoMap;
	}

}
