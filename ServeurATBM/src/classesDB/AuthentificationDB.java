package classesDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Array;


public class AuthentificationDB {
	
	String lg_compte;
	String pwd_compte;
	
	boolean test = false;
	boolean testFirst = false;
	
	String [] numComptClient;
    Array a = null;
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	ResultSet leResultat2 = null;
	ResultSet rsData = null;
	
	public AuthentificationDB(String lg_compte, String pwd_compte)
	{
		this.lg_compte = lg_compte;
		this.pwd_compte = pwd_compte;
		
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
	
	public Boolean existFirst()
	{
		boolean test = false;
       
       try {
    	   String sql1 = "SELECT * " +
    	   		"FROM atbdb.firsttime " +
    	   		"WHERE firsttime.client_idclient = (SELECT idclient FROM atbdb.client WHERE clientcin ="+ lg_compte +")";
		leResultat2 = transmission.executeQuery(sql1);
		String pwdFst = null;
		String fstt = null;
		
		if(!leResultat2.isFirst())
		{
			
			while(leResultat2.next())
			{
				
					fstt = leResultat2.getString(2).toString();
					pwdFst = leResultat2.getString(3).toString();
					System.out.println("test first");
				
			}
		}
		else
			test = false;
		
		System.out.println("test first time from DB:   "+fstt);
		System.out.println("test first PWD from DB:   "+pwdFst);
		
		System.out.println("password user:   "+pwd_compte);
		
		if((fstt.equals("0"))&&(pwdFst.equals("vide")))
		{
			test = false;
			System.out.println(test+"  if not first");
		}
		else if((fstt.equals("1"))&&(pwdFst.equals(pwd_compte)))
		{
			test = true;
			System.out.println(test+"  if first");
			

			String sql3 = "UPDATE atbdb.firsttime " +
	    			" SET firsttime.firsttimeenter = '0', firsttime.firsttimemdpg = 'vide'" +
	    			" WHERE firsttime.client_idclient = (SELECT idclient FROM atbdb.client WHERE clientcin ="+ lg_compte +")";
			
				transmission.executeUpdate(sql3);
			


		}
			
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
       System.out.println(test);
       return test;
       
	}
	
	public Boolean exist()
	{	
		try {
			
			if(existFirst() == true)
			{
				System.out.println("pwd securite correct");
				testFirst = true;
			
			}else {
				testFirst = false;
			}
			
			System.out.println("testFirst...  "+testFirst);

			
			String sql = "SELECT * FROM atbdb.client WHERE clientcin="+lg_compte;             
			leResultat = transmission.executeQuery(sql);
			System.out.println("test");
			
			if (!leResultat.isFirst())
			{
				System.out.println("cin correct");
				while(leResultat.next())
				{
					if(testFirst == true)
						test = false;
					else if(leResultat.getString(7).toString().equals(pwd_compte))
					{
						System.out.println("pwd correct");
						test = true;
					
					}else {
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
	
	public String [] chargementNumCompte()
	{
        try {
			int i = 0;
			int taille = 0;
			String sql = "SELECT comptenum FROM atbdb.compte WHERE client_clientcin="+lg_compte;             
			rsData = transmission.executeQuery(sql);
			
			
			while(rsData.next())
			{
				
				taille++;
			}
			numComptClient = new String[taille];
			rsData.absolute(0);
			
			System.out.println("chargement...");
		    while(rsData.next())
			{
					String data = rsData.getString("comptenum");
					System.out.println(data);
					numComptClient[i] = data.toString();
					i++;
					System.out.println("chargement terminé");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return numComptClient;
	}

}
