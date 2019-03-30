package classesDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VirementDB {
	
	Calendar c = Calendar.getInstance();
	
	Connection laConnection;
	Statement transmission;
	ResultSet leResultat = null;
	ResultSet leResultat1 = null;
	
	String compteSource;
	String compteDest;
	String solde;
		
	public VirementDB(String compteSource , String compteDest, String solde)
	{
		this.compteSource = compteSource;
		this.compteDest = compteDest;
		this.solde = solde;
		
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
		boolean exist = false;
		String res = new String();
		
        try {
        	
        	String sql1 = "SELECT compte.comptenum " +
        			" FROM atbdb.compte " +
        			" WHERE compte.comptenum = "+compteDest;
        	
			leResultat = transmission.executeQuery(sql1);
				
				System.out.println("verifier l'exitance compte destinataire...");
				
				while(leResultat.next())
					res = leResultat.getString("comptenum");
				
				System.out.println(res);
			    
				if((res).equals(compteDest))
					exist = true;
				else
					exist = false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exist;
	}
	
	public Boolean virementSolde()
	{
		boolean testSolde = false;
		String res = new String();
		String res1 = new String();
		
        try {
        	
        	String sql0 = "SELECT compte.solde " +
        			" FROM atbdb.compte " +
        			" WHERE compte.comptenum = "+compteDest;
        	
			leResultat = transmission.executeQuery(sql0);
			
			while(leResultat.next())
				res = leResultat.getString("solde");
			
			System.out.println(res);
			
			float soldeCompteDest = Float.parseFloat(res);
			System.out.println("Solde compte destinataire:  "+soldeCompteDest);
        	
        	String sql1 = "SELECT compte.solde " +
        			" FROM atbdb.compte " +
        			" WHERE compte.comptenum = "+compteSource;
        	
			leResultat = transmission.executeQuery(sql1);
				
				System.out.println("Verification de solde dans le compte source...");
				
				while(leResultat.next())
					res = leResultat.getString("solde");
				
				System.out.println(res);
				
			    float soldeCompte = Float.parseFloat(res);
			    float soldeSaisi = Float.parseFloat(solde);
			    
			    System.out.println("Solde compte source:  "+soldeCompte);
				
				if((soldeCompte > 0)&&(soldeSaisi < soldeCompte)&&((soldeCompte - soldeSaisi)>0))
				{
					String sql2 = "UPDATE atbdb.compte " +
		        			" SET compte.solde = " +(soldeCompte - soldeSaisi)+
		        			" WHERE compte.comptenum = "+compteSource;
		        	
					transmission.executeUpdate(sql2);
					
					String sql3 = "UPDATE atbdb.compte " +
		        			" SET compte.solde = " +(soldeCompteDest + soldeSaisi)+
		        			" WHERE compte.comptenum = "+compteDest;
		        	
					transmission.executeUpdate(sql3);
					
					//insertion dans la table transaction de transmetteur
					String sql4 = "SELECT compte.idcompte " +
		        			" FROM atbdb.compte " +
		        			" WHERE compte.comptenum = "+compteSource;
					
					leResultat = transmission.executeQuery(sql4);
					
					while(leResultat.next())
						res = leResultat.getString("idcompte");
					
					System.out.println("id compte source: "+ res);
					
					SimpleDateFormat jourDebut = new SimpleDateFormat("yyyy-MM-dd");
					String dateTrans = jourDebut.format(c.getTime());
					
					String sql5 = "INSERT INTO atbdb.transaction (transaction.transactiondate,transaction.transactionsolde,transaction.transactiondescription,transaction.compte_idcompte)" +
							"VALUES('"+ dateTrans +"','"+ soldeSaisi +"','vir vers "+ compteDest +"','"+ res +"')";
					
					transmission.executeUpdate(sql5);
					
					System.out.println("transactions effectuée...");
					
					String sql6="SELECT transactiondate , transactiondescription , transactionsolde " +
        			            " FROM atbdb.transaction " +
        			            " WHERE compte_idcompte = (SELECT idcompte FROM atbdb.compte WHERE compte.comptenum = "+ compteSource +")AND " +
							    "(transaction.transactiondate = "+ dateTrans +")";
					
					leResultat = transmission.executeQuery(sql6);
					
					while(leResultat.next())
					{
						System.out.println(leResultat.getString("transactiondate"));
						System.out.println(leResultat.getString("transactiondescription"));
						System.out.println(leResultat.getString("transactionsolde"));
					}
					
                    //insertion dans la table transaction de bénéficier
					String sql7 = "SELECT compte.idcompte " +
		        			" FROM atbdb.compte " +
		        			" WHERE compte.comptenum = "+compteDest;
					
					leResultat1 = transmission.executeQuery(sql7);
					
					while(leResultat1.next())
						res1 = leResultat1.getString("idcompte");
					
					System.out.println("id compte dest: "+ res1);
					
					String sql8 = "INSERT INTO atbdb.transaction (transaction.transactiondate,transaction.transactionsolde,transaction.transactiondescription,transaction.compte_idcompte)" +
							"VALUES('"+ dateTrans +"','"+ soldeSaisi +"','vir de "+ compteSource +"','"+ res1 +"')";
					
					transmission.executeUpdate(sql8);
					
					System.out.println("transactions effectuée...");
					
					String sql9="SELECT transactiondate , transactiondescription , transactionsolde " +
        			            " FROM atbdb.transaction " +
        			            " WHERE compte_idcompte = (SELECT idcompte FROM atbdb.compte WHERE compte.comptenum = "+ compteSource +")AND " +
							    "(transaction.transactiondate = "+ dateTrans +")";
					
					leResultat = transmission.executeQuery(sql9);
					
					while(leResultat.next())
					{
						System.out.println(leResultat.getString("transactiondate"));
						System.out.println(leResultat.getString("transactiondescription"));
						System.out.println(leResultat.getString("transactionsolde"));
					}
					
					testSolde = true;
				}
				else
					testSolde = false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return testSolde;
	}

}
