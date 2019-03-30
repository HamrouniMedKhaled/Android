package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pfe.mobilebanking.atbmobile.DemandeChequierActivity.UserRequestChTask;

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

public class OppositionChequierActivity extends Activity {
	
	private EditText editeNumChequier;
	private Spinner spinnerRaison;
	private Button boutonValider;
	
	public Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	private UserOppoChTask mAuthTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opposition_chequier);
		
		editeNumChequier = (EditText)findViewById(R.id.editnumchequier);
		spinnerRaison = (Spinner)findViewById(R.id.raisons_oppostions);
		boutonValider = (Button)findViewById(R.id.button_oppo_ch);
		boutonValider.setOnClickListener(validerChequier);
		
		ArrayAdapter adapterRaison = ArrayAdapter.createFromResource(this, R.array.les_diff_raison_ch, android.R.layout.simple_spinner_item);
        adapterRaison.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerRaison.setAdapter(adapterRaison);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.opposition_chequier, menu);
		return true;
	}
	
OnClickListener validerChequier = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			mAuthTask = new UserOppoChTask(editeNumChequier.getText().toString(),spinnerRaison.getSelectedItem().toString());
			mAuthTask.execute((Void) null);
			
		}
	};
	
	@Override
	public void onUserLeaveHint()
	{
		
    		Intent secondeActivity = new Intent(OppositionChequierActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	
	}
	
public class UserOppoChTask extends AsyncTask<Void, Void, Boolean> {
		
		String num_ch = null;
		String raison = null;
		
		String reponse = null;
		
		public UserOppoChTask(String num_ch, String raison)
		{
			this.num_ch = num_ch;
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
					out.println("o_chequier");
					out.println(num_ch);
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
						"numéro de chequier: "+editeNumChequier.getText().toString());
				
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

}
