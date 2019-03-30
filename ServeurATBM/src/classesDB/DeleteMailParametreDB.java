package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteMailParametreDB {
	
	String cin;
	String pass;
	String mailSupp;
	
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	
	public DeleteMailParametreDB(String cin, String pass, String mailSupp) {
		// TODO Auto-generated constructor stub
		
		this.cin = cin;
		this.pass = pass;
		this.mailSupp = mailSupp;
		
		
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
	
	public Boolean supprimer()
	{
		
		String res = null;
		String resMail1 = null;
		String resMail2 = null;
		String mailNull = null;
		
		System.out.println("modifié dans pwdParametre:   "+cin);
		System.out.println("modifié dans pwdParametre:   "+mailSupp);
    	
		try {
			
			String sql0 = "SELECT client.idclient , client.clientmail , client.clientmail2 " +
					"FROM atbdb.client " +
					"WHERE client.clientcin = " +cin;
			
			leResultat = transmission.executeQuery(sql0);
			
			while(leResultat.next())
			{
				res = leResultat.getString("idclient");
				resMail1 = leResultat.getString("clientmail");
				resMail2 = leResultat.getString("clientmail2");
			}
				
			if(mailSupp.equals(resMail1))
			{
				String sql = " UPDATE atbdb.client"+
		    			" SET client.clientmail = '" +mailNull+"'"+
		    			" WHERE client.idclient = "+res;
				
				transmission.executeUpdate(sql);
			}else if(mailSupp.equals(resMail2))
			{
				String sql = " UPDATE atbdb.client"+
		    			" SET client.clientmail2 = '" +mailNull+"'"+
		    			" WHERE client.idclient = "+res;
				
				transmission.executeUpdate(sql);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
