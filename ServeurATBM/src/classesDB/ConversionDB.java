package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConversionDB {
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	
	String deviseSource;
	String deviseEtranger;
	
	public ConversionDB(String deviseSource , String deviseEtranger)
	{
		this.deviseSource = deviseSource;
		this.deviseEtranger = deviseEtranger;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			laConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/devisedb","root","2014Pfe2014");
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
	
	public String monnaieDevise()
	{
		String res = null;
		
		System.out.println(deviseEtranger);
		
		try {
		String sql = "SELECT devisemonnaie " +
				"FROM devisedb.devise " +
				"WHERE source_idsource = (SELECT idsource FROM devisedb.source WHERE sourcenom = '"+deviseSource+"') " +
						"AND deviseetranger = '"+deviseEtranger.toString()+"'";            
		
			leResultat = transmission.executeQuery(sql);
			
			while (leResultat.next())
				res = leResultat.getString("devisemonnaie").toString();
			
			System.out.println("devise:   "+res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}

}
