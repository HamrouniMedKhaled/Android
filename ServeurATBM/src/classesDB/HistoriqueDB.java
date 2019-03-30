package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class HistoriqueDB {
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	
	String compteRecu;
	String dateDebut;
	String dateFin;
	String [] transComptClient;
	
	
	public HistoriqueDB(String compteRecu , String dateDebut, String dateFin)
	{
		this.compteRecu = compteRecu;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		
		transComptClient = new String [50];
		
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
	
	public String [] chargementTransCompte()
	{
        try {
        	
        	String compte = compteRecu;
        	String sql1 = "SELECT transactiondate , transactiondescription , transactionsolde " +
        			" FROM atbdb.transaction " +
        			" WHERE compte_idcompte = (SELECT idcompte FROM atbdb.compte WHERE compte.comptenum = "+ compte +")AND " +
							"(transaction.transactiondate BETWEEN '"+ dateDebut +"' and '"+ dateFin+"')";
			String sql = "SELECT transactiondescription " +
					"FROM atbdb.transaction join atbdb.compte " +
					"WHERE idcompte = '(SELECT compte.idcompte FROM atbdb.compte WHERE compte.comptenum = "+ compte +")' AND " +
							"(transaction.transactiondate BETWEEN '"+ dateDebut +"' and '"+ dateFin+"')";             
			leResultat = transmission.executeQuery(sql1);
        	
				
				System.out.println("chargement...");
			    int i = 0;
				while(leResultat.next())
				{
						String data = leResultat.getString("transactiondate")+"\n"+leResultat.getString("transactiondescription")+"\n"+leResultat.getString("transactionsolde");
						System.out.println(data);
						transComptClient[i] = data.toString();
						i++;
						System.out.println("chargement terminé");
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return transComptClient;
	}

}
