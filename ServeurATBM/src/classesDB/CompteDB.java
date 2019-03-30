package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.util.LRUCache;

public class CompteDB {
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	
	String [] compteRecu = new String [20];
	//String [] rechCompte;
	String [] soldeComptClient;
	
	public CompteDB(String [] compteRecu)
	{
		this.compteRecu = compteRecu;
		//rechCompte = new String [compteRecu.length];
		soldeComptClient = new String [compteRecu.length];
		
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
	
	public String [] chargementSoldeCompte()
	{
        try {
			for(int i = 0;i<compteRecu.length;i++)
			{
				String valeur = compteRecu[i];
				String sql = "SELECT solde FROM atbdb.compte WHERE comptenum="+valeur;             
				leResultat = transmission.executeQuery(sql);
				
				System.out.println("chargement...");
			    
				while(leResultat.next())
				{
						String data = leResultat.getString("solde");
						System.out.println(data);
						soldeComptClient[i] = data.toString();
						
						System.out.println("chargement terminé");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return soldeComptClient;
	}

}
