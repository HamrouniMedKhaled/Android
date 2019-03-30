package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PwdParametreDB {
	
	String cin;
	String pass;
	String newPass;
	
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	
	public PwdParametreDB(String cin, String pass, String newPass) {
		// TODO Auto-generated constructor stub
		
		this.cin = cin;
		this.pass = pass;
		this.newPass = newPass;
		
		
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
	
	public Boolean changer()
	{
		
		String res = null;
		
		System.out.println("modifié dans pwdParametre:   "+cin);
		System.out.println("modifié dans pwdParametre:   "+newPass);
    	
		try {
			
			String sql0 = "SELECT client.idclient " +
					"FROM atbdb.client " +
					"WHERE client.clientcin = " +cin;
			
			leResultat = transmission.executeQuery(sql0);
			
			while(leResultat.next())
				res = leResultat.getString("idclient");
			
			String sql = " UPDATE atbdb.client"+
	    			" SET client.clientmdp = '" +newPass+"'"+
	    			" WHERE client.idclient = "+res;
			
			transmission.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
