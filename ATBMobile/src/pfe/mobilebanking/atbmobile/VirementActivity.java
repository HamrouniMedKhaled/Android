package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pfe.mobilebanking.atbmobile.HistoriqueActivity.UserHistoriqueTask;
import pfe.mobilebanking.atbmobile.RegisterActivity.UserRegisterTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VirementActivity extends Activity {
	
	private UserVirementTask mVirementTask;
	private BufferedReader in = null;
	private PrintWriter out = null;
	String reponse = null;
	Socket clientSocket;
	
	private TextView textCompteSelectionne = null;
	private EditText editNumDest;
	private EditText editSolde;
	private Button bEffectuer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_virement);
		
		textCompteSelectionne = (TextView)findViewById(R.id.vir_compte_selectionne);
		editNumDest = (EditText)findViewById(R.id.edit_vir_compte_dest);
		editSolde = (EditText)findViewById(R.id.edit_vir_solde);
		
		bEffectuer = (Button)findViewById(R.id.button_virement_effectuer);
		bEffectuer.setOnClickListener(effectuer);
		
		Intent iVir = getIntent();
		textCompteSelectionne.setText(iVir.getStringExtra("compte_selectionne"));
		
		editNumDest.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.Register || id == EditorInfo.IME_NULL) {
					validation();
					return true;
				}
				return false;
			}
		});
		
		editSolde.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.Register || id == EditorInfo.IME_NULL) {
					validation();
					return true;
				}
				return false;
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.virement, menu);
		return true;
	}
	
	OnClickListener effectuer = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			validation();
		}
	};
	
	public class UserVirementTask extends AsyncTask<Void, Void, Boolean>
	{

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
				out.println("virement");
				
				out.println(textCompteSelectionne.getText());
				out.println(editNumDest.getText());
				out.println(editSolde.getText());
				
				out.flush();
				
				
				reponse = in.readLine();
				
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
			
			mVirementTask = null;
			
			if (success) {
				
				if (reponse.equals("vir_ok"))
					infoVirementDialog("Virement effectué avec succé");
				else
					infoVirementDialog("Virement n'est pas effectué");
					
			}
		}
		
		@Override
		protected void onCancelled() {
			mVirementTask = null;
			
		}
	}
	
	private void validation()
	{
		if (mVirementTask != null) {
			return;
		}
		
		        // Reset errors.
				editNumDest.setError(null);
				editSolde.setError(null);
				
				// Store values at the time of the login attempt.
				String eNumCompte = editNumDest.getText().toString();
				String eSolde = editSolde.getText().toString();

				boolean cancel = false;
				View focusView = null;
				
				
				if (TextUtils.isEmpty(eNumCompte)) {
					editNumDest.setError(getString(R.string.error_field_required));
					focusView = editNumDest;
					cancel = true;
				}/*else if(eNumCompte.length() < 8)
				{
					editCin.setError(getString(R.string.error_cin));
					focusView = editCin;
					cancel = true;
				}*/
				
				if (TextUtils.isEmpty(eSolde)) {
					editSolde.setError(getString(R.string.error_field_required));
					focusView = editSolde;
					cancel = true;
				}else if(Float.parseFloat(eSolde)>=300)
				{
					editSolde.setError(getString(R.string.error_solde));
					focusView = editSolde;
					cancel = true;
				}
				
				if (cancel) {
					// There was an error; don't attempt login and focus the first
					// form field with an error.
					focusView.requestFocus();
				} else {
					// Show a progress spinner, and kick off a background task to
					// perform the user login attempt.
					//mRegisterStatusMessageView.setText(R.string.login_progress_register);
					//showProgress(true);
					mVirementTask = new UserVirementTask();
		    		mVirementTask.execute((Void) null);
				}
		
	}
	
	public void infoVirementDialog(String msg){
		
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
		
    		Intent secondeActivity = new Intent(VirementActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	
	}

}
