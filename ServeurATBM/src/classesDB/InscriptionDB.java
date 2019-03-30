package classesDB;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InscriptionDB {
	
	String lg_compte;
	String pwd_compte;
	String email;
	boolean test = false;
	
	String [] numComptClient;
    Array a = null;
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	ResultSet rsData = null;
	
	public InscriptionDB(String lg_compte, String pwd_compte, String email)
	{
		this.lg_compte = lg_compte;
		this.pwd_compte = pwd_compte;
		this.email = email;
		
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
	
	public Boolean existCompte()
	{	
		try {
			
			String sql = "SELECT * FROM atbdb.client WHERE clientcin="+lg_compte;             
			leResultat = transmission.executeQuery(sql);
			System.out.println("test");
			if (!leResultat.isFirst())
			{
				System.out.println("cin correct");
				while(leResultat.next())
				{
					if(("0"+leResultat.getString("clientcin").toString()).equals(lg_compte))
					{
						if((leResultat.getString("clientmail")!=null))
						{
							if(leResultat.getString("clientmail").toString().equals(email))
							test = false;
						}else {
							System.out.println("exist...");
							test = true;
						}
						
					
					}else {
						System.out.println("not exist...");
						test = false;
					}
				}
			}	
			else
				test = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;
	}
	
	public void enregistrerClient(String passGenere)
	{
		
		String sql1 = "UPDATE atbdb.client " +
    			" SET client.clientmail = '"+ email +"', client.clientmdp = '"+pwd_compte+"'"+
    			" WHERE client.clientcin = "+lg_compte;
		
		String sql2 = "SELECT client.idclient FROM atbdb.client WHERE clientcin="+lg_compte;
		
		
		String res = null;
		try {
			leResultat = transmission.executeQuery(sql2);
			while(leResultat.next())
				res = leResultat.getString(1);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		String sql3 = null;
		sql3 = "INSERT INTO atbdb.firsttime (firsttime.firsttimeenter,firsttime.firsttimemdpg,firsttime.client_idclient)" +
				"VALUES('1','"+ passGenere +"','"+ res +"')";
		
		try {
			transmission.executeUpdate(sql1);
			transmission.executeUpdate(sql3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
