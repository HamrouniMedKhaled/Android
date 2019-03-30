package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CompteActivity<TCPServerATBMobile> extends Activity{
	
	private UserCompteTask mSoldeTask;
	private BufferedReader in = null;
	private PrintWriter out = null;
	String reponse = null;
	Socket clientSocket;
	
	private ListView mListCompte = null;
	
	private String [] mNumCompte = null;
	
	//private String [] mSoldeCompte = null;
	
	private Button boutonAfficher = null;
	
	boolean homeButton = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compte);
		
        mListCompte = (ListView)findViewById(R.id.list_num_compte_compte);
        
        
        boutonAfficher = (Button)findViewById(R.id.button_compte_affiche);
        
        
        boutonAfficher.setOnClickListener(afficher);
        
        Intent chargeNum = getIntent();
		mNumCompte = chargeNum.getStringArrayExtra("liste_compte");
		
        mListCompte.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mNumCompte));
		mListCompte.setItemChecked( 0, false);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compte, menu);
		return true;
	}
	
    OnClickListener afficher = new OnClickListener() {
		
    	@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
    		homeButton = false;
    		
    		mSoldeTask = new UserCompteTask();
    		mSoldeTask.execute((Void) null);
			
		}

	};
	
	public class UserCompteTask extends AsyncTask<Void, Void, Boolean>
	{
		int select = 0;
		float total = 0;
		private String [] mSoldeCompte = null;
		String [] compteSelected = new String[mNumCompte.length];

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try {
				// Simulate network access.
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				return false;
			}
			
			
			
			try
			{
				System.out.println("lancement de socket");
				clientSocket  = new Socket("172.21.0.1", 2014);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream());
				out.println("compte");
				
				for(int i = 0;i<mListCompte.getCount();i++)
				{
					if(mListCompte.isItemChecked(i))
					{
						compteSelected [select] = mListCompte.getItemAtPosition(i).toString();
						//System.out.println(compteSelected[select]);
						out.println(compteSelected[select]);
						select++;
					}
				}
				
				out.flush();
				
				mSoldeCompte = new String[select+1];
				
				for (int i = 0; i<select;i++)
				{
					reponse = in.readLine();
					mSoldeCompte[i]=reponse;
					//System.out.println(mSoldeCompte[i]);
					total += Float.parseFloat(mSoldeCompte[i]);
				}
				//System.out.println("Float à ajouter..." + total);
				mSoldeCompte[select]= String.valueOf(total).toString();
				//System.out.println("valeur ajoutée..."+mSoldeCompte[select]);
				
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
			
			mSoldeTask = null;
			
			if (success) {
				
				Intent iSolde = new Intent(CompteActivity.this, SoldeActivity.class);
				iSolde.putExtra("Select", select);
				iSolde.putExtra("Rip_selected",compteSelected);
				iSolde.putExtra("solde_selected",mSoldeCompte);
				
				startActivity(iSolde);
			}
		}
		
		@Override
		protected void onCancelled() {
			mSoldeTask = null;
			
		}
	}
	
	@Override
    protected void onResume() {
    	
    	// only when screen turns on
        if (!ScreenReceiver.wasScreenOn) {
            // this is when onResume() is called due to a screen state change
            System.out.println("SCREEN TURNED ON");
            
            Intent secondeActivity = new Intent(CompteActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
            
        } else {
            // this is when onResume() is called when the screen state has not changed
        }
    	
      super.onResume();
      homeButton = true;
    }
		
	@Override
	public void onUserLeaveHint()
	{
		if(homeButton == true)
		{
            Intent secondeActivity = new Intent(CompteActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
		}
    	
	}

}
