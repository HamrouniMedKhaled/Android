package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class mapDB {
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	
	ArrayList<String> listCoordonnees = new ArrayList<String>();
	
	public mapDB(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			laConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atbdb","root","2014Pfe2014");
			System.out.println("connecté");
			transmission = laConnection.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> chargementCoordonnee()
	{
		
       try {
        	
        	
        	String sql1 = "SELECT GichietInfolatitude , GichietInfolongitude , GichietInfoadresse " +
        			" FROM gapinformations.gichietinfo " +
        			" WHERE Gichiet_idGichiet = 1";        
			leResultat = transmission.executeQuery(sql1);
        	
				
				System.out.println("chargement coordonnées...");
			    int i = 0;
				while(leResultat.next())
				{
						String data = leResultat.getString("GichietInfolatitude")+" "+leResultat.getString("GichietInfolongitude")+" "+leResultat.getString("GichietInfoadresse");
						System.out.println(data);
						listCoordonnees.add(data);
						i++;
						System.out.println("chargement terminé");
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCoordonnees;
	}

}
