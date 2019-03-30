package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

import pfe.mobilebanking.atbmobile.CompteActivity.UserCompteTask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class HistoriqueActivity extends Activity {
	
	private UserHistoriqueTask mHistoTask;
	private BufferedReader in = null;
	private PrintWriter out = null;
	String reponse = null;
	Socket clientSocket;
	
	private ListView mListNumCompte=null;
	private String [] mNumCompte = null;
	
	private Button boutonAfficher = null;
	
	private Spinner dateDebutJour = null;
	private TextView dateDebutMois = null;
	private TextView dateDebutAnnee = null;
	
	private Spinner dateFinJour = null;
	private TextView dateFinMois = null;
	private TextView dateFinAnnee = null;
	
	String jourDebutSelection = new String();
	String jourFinSelection = new String();
	String compteSelected = null;
	
	private String [] array_spinner_date_debut;
	private String [] array_spinner_date_fin;
	
	Calendar c = Calendar.getInstance();
	
	String jD;
	String mD;
	String mF;
	String aD;
	int compteurJour;
	int jourDuMois;
	int jourDuFevrier;
	
	boolean homeButton = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historique);
		
        mListNumCompte = (ListView)findViewById(R.id.list_num_compte);
        
        
        boutonAfficher = (Button)findViewById(R.id.button_statistique);
        
        
        boutonAfficher.setOnClickListener(afficher);
        
        Intent chargeNum = getIntent();
		mNumCompte = chargeNum.getStringArrayExtra("liste_compte");
        
		
        mListNumCompte.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, mNumCompte));
		mListNumCompte.setItemChecked( 0, true);
		
		dateDebutJour = (Spinner)findViewById(R.id.date_debut_jour);
		dateDebutMois = (TextView)findViewById(R.id.date_debut_mois);
		dateDebutAnnee = (TextView)findViewById(R.id.date_debut_annee);
		
		dateFinJour = (Spinner)findViewById(R.id.date_fin_jour);
		dateFinMois = (TextView)findViewById(R.id.date_fin_mois);
		dateFinAnnee = (TextView)findViewById(R.id.date_fin_annee);
	    
		SimpleDateFormat jourDebut = new SimpleDateFormat("dd");
		jD = jourDebut.format(c.getTime());
		
		SimpleDateFormat moisDebut = new SimpleDateFormat("MM");
		mD = moisDebut.format(c.getTime());
		
		jourDuMois = c.get(Calendar.DAY_OF_MONTH);
		
		
		dateFinMois.setText("   -   " + mD +"   -   ");
		
		SimpleDateFormat anneeDebut = new SimpleDateFormat("yyyy");
		aD = anneeDebut.format(c.getTime());
		
		Calendar mycal = new GregorianCalendar(Integer.parseInt(aD), Calendar.FEBRUARY, 1);
		
		jourDuFevrier = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(jourDuFevrier);
		
		
		dateDebutAnnee.setText(aD);
		dateFinAnnee.setText(aD);
		
		int jour = Integer.parseInt(jD);
		array_spinner_date_debut =   new String[6];
		array_spinner_date_fin   =   new String[6];
		
		int testMois = (Integer.parseInt(mD))-1;
		System.out.println(testMois);
		
		if(testMois == 0)
			testMois = 12;
		
		for(int i= 0;i<6;i++)
		{
			compteurJour = jour - i;
			if(((testMois == 1)||(testMois == 3)||(testMois == 5)||(testMois == 7)||(testMois == 8)||(testMois == 10)||(testMois == 12)))
			{
				if(compteurJour < 1)
				{
					array_spinner_date_debut[i] = String.valueOf(compteurJour + 31) ;
					array_spinner_date_fin[i] = String.valueOf(compteurJour + 31) ;
				}else
				{
					array_spinner_date_debut[i] = String.valueOf(compteurJour) ;
					array_spinner_date_fin[i] = String.valueOf(compteurJour) ;
				}
			}
			else if(testMois == 2)
			{
				if((jourDuFevrier == 28)&&(compteurJour < 1))
				{
					array_spinner_date_debut[i] = String.valueOf(compteurJour + 28) ;
					array_spinner_date_fin[i] = String.valueOf(compteurJour + 28) ;
				}
				else if((jourDuFevrier == 29)&&(compteurJour == 0))
				{
					array_spinner_date_debut[i] = String.valueOf(compteurJour + 29) ;
					array_spinner_date_fin[i] = String.valueOf(compteurJour + 29) ;
				}
			}
			else
			{
				if(compteurJour < 1)
				{
					array_spinner_date_debut[i] = String.valueOf(compteurJour + 30) ;
					array_spinner_date_fin[i] = String.valueOf(compteurJour + 30) ;
				}
				else
				{
					array_spinner_date_debut[i] = "0"+String.valueOf(compteurJour) ;
					array_spinner_date_fin[i] = "0"+String.valueOf(compteurJour) ;
				}
				
			}
			
			
		}
		
		if((compteurJour<30)&&(compteurJour>25))
		{
			int m = Integer.parseInt(mD)-1;
			if(String.valueOf(m).length()==1)
				mF = "0"+m;
			else
				mF = String.valueOf(m);
			
			System.out.println("mois... "+mF);
			
			dateDebutMois.setText("   -   " + mF +"   -   ");
		}else
		{
			
			System.out.println("mois mD  "+mD);
			dateDebutMois.setText("   -   " + mD +"   -   ");
		}
		
		ArrayAdapter adapterDJ = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner_date_debut);
		        dateDebutJour.setAdapter(adapterDJ);
		
		ArrayAdapter adapterFJ = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner_date_fin);
		        dateFinJour.setAdapter(adapterFJ);
		        
		        
		        
		        dateDebutJour.setOnItemSelectedListener(changerMois);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historique, menu);
		return true;
	}
	
	OnItemSelectedListener changerMois = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if((Integer.parseInt((String) dateFinJour.getSelectedItem())<Integer.parseInt((String) dateDebutJour.getSelectedItem())))
				mF="0"+String.valueOf(Integer.parseInt(mD)-1);
			else
				mF=mD;
				
			System.out.println("mois  "+mF);
				dateDebutMois.setText("   -   " + mF +"   -   ");
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	OnClickListener afficher = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			homeButton = false;
			
			jourDebutSelection = dateDebutJour.getSelectedItem().toString() + "" + dateDebutMois.getText().toString() + "" + dateDebutAnnee.getText().toString();
			System.out.println(jourDebutSelection );
			
			jourFinSelection = dateFinJour.getSelectedItem().toString() + ""  + dateFinMois.getText().toString() + "" + dateFinAnnee.getText().toString();
			System.out.println(jourFinSelection);
			
			mHistoTask = new UserHistoriqueTask();
    		mHistoTask.execute((Void) null);
			
		}
	};
	
	
	public class UserHistoriqueTask extends AsyncTask<Void, Void, Boolean>
	{
		int select = 0;
		
		private String [] mTransCompte = null;
		String [] infoTrans;

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try {
				// Simulate network access.
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				return false;
			}
			
			select = Integer.parseInt(dateFinJour.getSelectedItem().toString()) - Integer.parseInt(dateDebutJour.getSelectedItem().toString());
			System.out.println(select);
			
			for(int i = 0;i<mListNumCompte.getCount();i++)
			{
				if(mListNumCompte.isItemChecked(i))
					compteSelected = mListNumCompte.getItemAtPosition(i).toString();
			}
			
			try
			{
				System.out.println("lancement de socket");
				clientSocket  = new Socket("172.21.0.1", 2014);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream());
				out.println("historique");
				
				out.println(compteSelected);
				System.out.println(aD+"-"+mF+"-"+dateDebutJour.getSelectedItem().toString());
				out.println(aD+"-"+mF+"-"+dateDebutJour.getSelectedItem().toString());
				System.out.println(aD+"-"+mD+"-"+dateFinJour.getSelectedItem().toString());
				out.println(aD+"-"+mD+"-"+dateFinJour.getSelectedItem().toString());
				
				out.flush();
				
				mTransCompte = new String[50];
				String [] line3 = new String [3];
				
				for(int i=0;i<mTransCompte.length;i++)
				{
					System.out.println(" data received: "+i);
					
					for(int r=0;r<3;r++)
					{
						reponse = in.readLine();
						if (reponse != null)
							line3[r] = reponse;
					}
					if (reponse != null)
						mTransCompte[i] = line3[0]+"\n"+line3[1]+"\n"+line3[2];
					else
						mTransCompte[i] = " ";
					
					System.out.println(mTransCompte[i]);
				}
				
				clientSocket.close();
				in.close();
				out.close();
			}catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}
		
		@Override
		protected void onPostExecute(final Boolean success) {
			
			mHistoTask = null;
			String s;
			
			if (success) {
				int lesTransactions = 0;
				for(int i = 0;i<mTransCompte.length;i++)
				{
					s = mTransCompte[i];
					if(s != " ")
					{
						System.out.println(mTransCompte[i]);
						lesTransactions++;
					}
						
				}
				
				System.out.println("length in HA:  "+lesTransactions);
				Intent iStatics = new Intent(HistoriqueActivity.this, StatistiqueActivity.class);
				iStatics.putExtra("date_debut_periode", jourDebutSelection);
				iStatics.putExtra("date_fin_periode", jourFinSelection);
				iStatics.putExtra("Rip_selected",compteSelected);
				iStatics.putExtra("les_transactions", lesTransactions);
				//iStatics.putExtra("periode_selection", infoTrans);
				iStatics.putExtra("liste_trans", mTransCompte);
				
				startActivity(iStatics);
			}
		}
		
		@Override
		protected void onCancelled() {
			mHistoTask = null;
			
		}
	}
	
	@Override
    protected void onResume() {
      super.onResume();
      homeButton = true;
    }
	
	@Override
	public void onUserLeaveHint()
	{
		if(homeButton == true)
		{
            Intent secondeActivity = new Intent(HistoriqueActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
		}
    	
	}
	
}
