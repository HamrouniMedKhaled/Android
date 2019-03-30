package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CDemandeDB {
	
	String nom;
	String type;
	String plafond;
	String validite;
	String num_compt;
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	ResultSet rsData = null;
	
	public CDemandeDB(String nom, String type, String plafond, String validite, String num_compt) {
		
		this.nom = nom;
		this.type = type;
		this.plafond = plafond;
		this.validite = validite;
		this.num_compt = num_compt;
		
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
	
	public Boolean autorisationC()
	{
		String res = new String();
		String res1 = new String();
		String res2 = new String();
		String res3 = new String();
    	
		try {
			String sql0 = "SELECT compte.client_idclient , compte.client_clientcin , compte.idcompte , compte.solde " +
	    			" FROM atbdb.compte " +
	    			" WHERE compte.comptenum = "+num_compt;
			
			leResultat = transmission.executeQuery(sql0);
			
			while(leResultat.next())
			{
				res = leResultat.getString("client_idclient");
				res1 = leResultat.getString("client_clientcin");
				res2 = leResultat.getString("idcompte");
				res3 = leResultat.getString("solde");
			}
			
			System.out.println(res);
			System.out.println(res1);
			
			String sql1 = "INSERT INTO atbdb.carte (carte.carteporteur,carte.cartetype,carte.cartesolde,carte.carteplafondsolde,carte.cartenum,carte.client_idclient,carte.client_clientcin,carte.compte_idcompte)" +
					"VALUES('"+ nom +"','"+ type +"','"+ res3 +"','"+ plafond +"','demande','"+ res +"','"+ res1 +"','"+ res2 +"')";
			
			transmission.executeUpdate(sql1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
