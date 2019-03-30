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

public class DemandeCarteActivity extends Activity {
	
	private EditText editPorteur;
	private Spinner  spinnerType;
	private Spinner  spinnerPlafond;
	private Spinner  spinnerValidity;
	private Button   boutonDemandeC;
	
	public Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	String textCompteSelectionne;
	
	private UserRequestCTask mAuthTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demande_carte);
		
		Intent iDem = getIntent();
		textCompteSelectionne = (iDem.getStringExtra("compte_selectionne"));
		
		editPorteur     =    (EditText)findViewById(R.id.editporteur);
		spinnerType     =    (Spinner)findViewById(R.id.carte_type);
		spinnerPlafond  =    (Spinner)findViewById(R.id.carte_plafond);
		spinnerValidity =    (Spinner)findViewById(R.id.carte_validite);
		boutonDemandeC  =    (Button)findViewById(R.id.button_demande_c);
		
		ArrayAdapter adapterType = ArrayAdapter.createFromResource(this, R.array.les_types, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerType.setAdapter(adapterType);
        
        ArrayAdapter adapterPlafond = ArrayAdapter.createFromResource(this, R.array.les_plafonds, android.R.layout.simple_spinner_item);
        adapterPlafond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerPlafond.setAdapter(adapterPlafond);
        
        ArrayAdapter adapterValidity = ArrayAdapter.createFromResource(this, R.array.les_validites, android.R.layout.simple_spinner_item);
        adapterValidity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerValidity.setAdapter(adapterValidity);
        
        boutonDemandeC.setOnClickListener(validerCarte);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.demande_carte, menu);
		return true;
	}
	
OnClickListener validerCarte = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			mAuthTask = new UserRequestCTask(editPorteur.getText().toString(),spinnerType.getSelectedItem().toString(),spinnerPlafond.getSelectedItem().toString(),spinnerValidity.getSelectedItem().toString(),textCompteSelectionne);
			mAuthTask.execute((Void) null);
			
		}
	};
	
	@Override
	public void onUserLeaveHint()
	{
		
    		Intent secondeActivity = new Intent(DemandeCarteActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	
	}
	
public class UserRequestCTask extends AsyncTask<Void, Void, Boolean> {
		
		String nom = null;
		String type = null;
		String plafond = null;
		String validite = null;
		String num_compt = null;
		
		String reponse = null;
		
		public UserRequestCTask(String nom, String type, String plafond, String validite, String num_compt)
		{
			this.nom = nom;
			this.type = type;
			this.plafond = plafond;
			this.validite = validite;
			this.num_compt = num_compt;
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
					out.println("d_carte");
					out.println(nom);
					out.println(type);
					out.println(plafond);
					out.println(validite);
					out.println(num_compt);
					
					out.flush();
					reponse = in.readLine();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			if(reponse.equals("demande ok"))
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
				
				
				infoDemandeChDialog("Votre demande de chéquier est diposée avec succés\n" +
						"Vous pouvez le récupérer depuis votre agence");
				
			} else {
				infoDemandeChDialog("Votre demande de chéquier n'est pas diposée");
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			//showProgress(false);
			
		}
	}

public void infoDemandeChDialog(String msg){
	
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
