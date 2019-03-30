package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class COppositionDB {
	
	String num_c;
	String raison;
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	
	Calendar c = Calendar.getInstance();
	
	String jour;
	String mois;
	String annee;
	
	public COppositionDB(String num_c,String raison) {
		
		this.num_c = num_c;
		this.raison = raison;
		
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
	
	public Boolean oppositionC()
	{
		String res = new String();
		
		
		SimpleDateFormat jourDebut = new SimpleDateFormat("dd");
		jour = jourDebut.format(c.getTime());
		
		SimpleDateFormat moisDebut = new SimpleDateFormat("MM");
		mois = moisDebut.format(c.getTime());
		
		SimpleDateFormat anneeDebut = new SimpleDateFormat("yyyy");
		annee = anneeDebut.format(c.getTime());
		
		String date = annee+"-"+mois+"-"+jour;
    	
		try {
			
			String sql0 = "SELECT carte.idcarte " +
	    			" FROM atbdb.carte " +
	    			" WHERE carte.cartenum = "+num_c;
			
			leResultat = transmission.executeQuery(sql0);
			
			while(leResultat.next())
			{
				res = leResultat.getString("idcarte");
			}
			
			System.out.println(res);
			
			String sql1 = "INSERT INTO atbdb.oppositioncarte (oppositioncarte.oppositioncartedate,oppositioncarte.oppositioncartenum,oppositioncarte.oppositioncarteraison,oppositioncarte.carte_idcarte)" +
					"VALUES('"+ date +"','"+ num_c +"','"+ raison +"','"+ Integer.parseInt(res) +"')";
			
			transmission.executeUpdate(sql1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
