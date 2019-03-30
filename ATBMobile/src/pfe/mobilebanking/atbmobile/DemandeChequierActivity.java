package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pfe.mobilebanking.atbmobile.RegisterActivity.UserRegisterTask;

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
import android.widget.Spinner;
import android.widget.TextView;

public class DemandeChequierActivity extends Activity {
	
	private Spinner spinnerNombresCheques;
	private Spinner spinnerTypeChequier;
	private Button boutonValider;
	private TextView textCompteSelectionne = null;
	
	public Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	private UserRequestChTask mAuthTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demande_chequier);
		
		textCompteSelectionne = (TextView)findViewById(R.id.de_compte_selectionne);
		spinnerNombresCheques = (Spinner)findViewById(R.id.cheques_nombres);
		spinnerTypeChequier   = (Spinner)findViewById(R.id.chequier_type);
		
		Intent iDem = getIntent();
		textCompteSelectionne.setText(iDem.getStringExtra("compte_selectionne"));
		
		boutonValider = (Button)findViewById(R.id.button_demande_ch);
		boutonValider.setOnClickListener(validerChequier);
		
		ArrayAdapter adapterType = ArrayAdapter.createFromResource(this, R.array.type_chequier, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerTypeChequier.setAdapter(adapterType);
        
        ArrayAdapter adapterNombres = ArrayAdapter.createFromResource(this, R.array.nombres_cheques, android.R.layout.simple_spinner_item);
        adapterNombres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerNombresCheques.setAdapter(adapterNombres);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.demande_chequier, menu);
		return true;
	}
	
	OnClickListener validerChequier = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			mAuthTask = new UserRequestChTask(spinnerTypeChequier.getSelectedItem().toString(),spinnerNombresCheques.getSelectedItem().toString(),textCompteSelectionne.getText().toString());
			mAuthTask.execute((Void) null);
			
		}
	};
	
	@Override
	public void onUserLeaveHint()
	{
		
    		Intent secondeActivity = new Intent(DemandeChequierActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	
	}
	
public class UserRequestChTask extends AsyncTask<Void, Void, Boolean> {
		
		String type = null;
		String nbr_ch = null;
		String num_compt = null;
		
		String reponse = null;
		
		public UserRequestChTask(String type, String nbr_ch, String num_compt)
		{
			this.type = type;
			this.nbr_ch = nbr_ch;
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
					out.println("d_chequier");
					out.println(type);
					out.println(nbr_ch);
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
			
			
			if(reponse.substring(0, 10).equals("demande ok"))
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
						"numéro de chequier: "+reponse.substring(10, reponse.length())+"\n" +
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
