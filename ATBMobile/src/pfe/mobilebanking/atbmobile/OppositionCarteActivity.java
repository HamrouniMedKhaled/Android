package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pfe.mobilebanking.atbmobile.OppositionChequierActivity.UserOppoChTask;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class OppositionCarteActivity extends Activity {
	
	private EditText editNumCarte;
	private Spinner spinnerRaison;
	private Button boutonValider;
	
	public Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	private UserOppoCTask mAuthTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opposition_carte);
		
		editNumCarte = (EditText)findViewById(R.id.editnumcarte);
		spinnerRaison = (Spinner)findViewById(R.id.raisons_oppostions);
		boutonValider = (Button)findViewById(R.id.button_oppo_c);
		boutonValider.setOnClickListener(validerCarte);
		
		ArrayAdapter adapterRaison = ArrayAdapter.createFromResource(this, R.array.les_diff_raison_c, android.R.layout.simple_spinner_item);
        adapterRaison.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerRaison.setAdapter(adapterRaison);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.opposition_carte, menu);
		return true;
	}
	
OnClickListener validerCarte = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			mAuthTask = new UserOppoCTask(editNumCarte.getText().toString(),spinnerRaison.getSelectedItem().toString());
			mAuthTask.execute((Void) null);
			
		}
	};
	
public class UserOppoCTask extends AsyncTask<Void, Void, Boolean> {
		
		String num_c = null;
		String raison = null;
		
		String reponse = null;
		
		public UserOppoCTask(String num_c, String raison)
		{
			this.num_c = num_c;
			this.raison = raison;
			
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
				
			} catch (InterruptedException e) {
				return false;
			}
			
			
				try {
					clientSocket = new Socket("172.21.0.1", 2014);
					in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					out = new PrintWriter(clientSocket.getOutputStream());
					out.println("o_carte");
					out.println(num_c);
					out.println(raison);
					
					
					out.flush();
					reponse = in.readLine();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			if(reponse.equals("opposition ok"))
				return true;
			else
				return false;

			// TODO: register the new account here.
			//return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			//showProgress(false);

			if (success) {
				
				
				infoOppoChDialog("Votre demande d'opposition chéquier est diposée avec succés\n" +
						"numéro de chequier: "+editNumCarte.getText().toString());
				
			} else {
				infoOppoChDialog("Votre demande d'opposition chéquier n'est pas diposée");
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			//showProgress(false);
			
		}
	}

public void infoOppoChDialog(String msg){
	
    AlertDialog.Builder ad = new AlertDialog.Builder(this);
    ad.setTitle("Mes comptes");
    //ad.setView(LayoutInflater.from(this).inflate(R.layout.activity_dialogue_numero_compte,null));
    ad.setMessage(msg);

	ad.setOnCancelListener(new DialogInterface.OnCancelListener(){
		public void onCancel(DialogInterface dialog) {
			// OK				
		}}
	
	);
	
	
	ad.setPositiveButton("Continuer", 
			new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int arg1) {
					//OK
					
				}
			}
		);
	
	ad.show();
}
	
	@Override
	public void onUserLeaveHint()
	{
		
    		Intent secondeActivity = new Intent(OppositionCarteActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	
	}

}
