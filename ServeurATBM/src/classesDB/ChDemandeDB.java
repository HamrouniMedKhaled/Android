package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChDemandeDB {
	
	String type;
	String nbr_ch;
	String num_compt;
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	ResultSet rsData = null;
	
	public ChDemandeDB(String type, String nbr_ch, String num_compt) {
		
		this.type = type;
		this.nbr_ch = nbr_ch;
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
	
	public Boolean autorisationCh(String lot)
	{
		String res = new String();
		String res1 = new String();
		String res2 = new String();
    	
		try {
			String sql0 = "SELECT compte.client_idclient , compte.client_clientcin , compte.idcompte " +
	    			" FROM atbdb.compte " +
	    			" WHERE compte.comptenum = "+num_compt;
			
			leResultat = transmission.executeQuery(sql0);
			
			while(leResultat.next())
			{
				res = leResultat.getString("client_idclient");
				res1 = leResultat.getString("client_clientcin");
				res2 = leResultat.getString("idcompte");
			}
			
			System.out.println(res);
			System.out.println(res1);
			
			String sql1 = "INSERT INTO atbdb.chequier (chequier.chequierlot,chequier.chequiertype,chequier.chequiernombre,chequier.client_idclient,chequier.client_clientcin,chequier.compte_idcompte)" +
					"VALUES('"+ lot +"','"+ type +"','"+ nbr_ch +"','"+ res +"','"+ res1 +"','"+ res2 +"')";
			
			transmission.executeUpdate(sql1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
