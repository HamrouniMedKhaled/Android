package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pfe.mobilebanking.atbmobile.AddMailActivity.UserAddMailTask;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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

public class DeleteMailActivity extends Activity {
	
	public Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	private UserDeleteMailTask mAuthTask = null;
	
	private EditText editPwdPersonnel;
	private EditText editMailsupp;
	private Button   boutonSupprimer;
	
	private View mRegisterFormView;
	private View mRegisterStatusView;
	private TextView mRegisterStatusMessageView;
	
	private String cin;
	private String pwd;
	
	private String ePwd;
	private String eMailSupp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_mail);
		
		editPwdPersonnel = (EditText)findViewById(R.id.editPwdPersonnelMailsupprime);
		editMailsupp   = (EditText)findViewById(R.id.editMailsupprime);
		boutonSupprimer   = (Button)findViewById(R.id.button_Delete_mail);
		
		Intent chargeNum = getIntent();
		cin = chargeNum.getStringExtra("cin");
		pwd = chargeNum.getStringExtra("pwd");
		
		boutonSupprimer.setOnClickListener(supprimerMail);
		
		editPwdPersonnel.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.SuppMail || id == EditorInfo.IME_NULL) {
					validation();
					return true;
				}
				return false;
			}
		});
		
		editMailsupp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.SuppMail || id == EditorInfo.IME_NULL) {
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
		getMenuInflater().inflate(R.menu.delete_mail, menu);
		return true;
	}
	
OnClickListener supprimerMail = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if((isHardwareConnectionOn(DeleteMailActivity.this)==true))
				validation();
			else if((isHardwareConnectionOn(DeleteMailActivity.this)==false))
					{
						AlertDialog.Builder ad = new AlertDialog.Builder(DeleteMailActivity.this);
					    ad.setTitle("Probl�me connexion");
					    ad.setMessage("SVP! activez le wifi ou le 3G!");
					    
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
					    
						//Toast cnx = Toast.makeText(LoginActivity.this, "SVP! v�rifiez votre connexion!", Toast.LENGTH_LONG);
						//cnx.show();
					}
			
		}
	};
	
	private void validation()
	{
		if (mAuthTask != null) {
			return;
		}
		
		        // Reset errors.
				editPwdPersonnel.setError(null);
				editMailsupp.setError(null);
				
				

				// Store values at the time of the login attempt.
				ePwd = editPwdPersonnel.getText().toString();
				eMailSupp = editMailsupp.getText().toString();

				boolean cancel = false;
				View focusView = null;
				
				
				
				// Check for a valid password.
				if (TextUtils.isEmpty(ePwd)) {
					editPwdPersonnel.setError(getString(R.string.error_field_required));
					focusView = editPwdPersonnel;
					cancel = true;
				} else if (!ePwd.equals(pwd)) {
					editPwdPersonnel.setError(getString(R.string.error_invalid_password));
					focusView = editPwdPersonnel;
					cancel = true;
				}
				
				
				// Check for a valid email address.
				if (TextUtils.isEmpty(eMailSupp)) {
					editMailsupp.setError(getString(R.string.error_field_required));
					focusView = editMailsupp;
					cancel = true;
				} else if (!eMailSupp.contains("@")) {
					editMailsupp.setError(getString(R.string.error_invalid_email));
					focusView = editMailsupp;
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
					mAuthTask = new UserDeleteMailTask(cin,editPwdPersonnel.getText().toString(),editMailsupp.getText().toString());
					mAuthTask.execute((Void) null);
				}
		
	}
	
public class UserDeleteMailTask extends AsyncTask<Void, Void, Boolean> {
		
		String cin = null;
		String pass = null;
		String mailSupp = null;
		
		String reponse = null;
		String repPing = null;
		
		public UserDeleteMailTask(String cin, String pass, String mailSupp)
		{
			this.cin = cin;
			this.pass = pass;
			this.mailSupp = mailSupp;
			
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
				
				repPing = ping("172.21.0.1");
				System.out.println(repPing);
				System.out.println(repPing.substring(repPing.length()-31, repPing.length()-27));
				repPing = repPing.substring(repPing.length()-31, repPing.length()-27);
				
			} catch (InterruptedException e) {
				return false;
			}
			
			if(repPing.equals("100%"))
			{
				return false;
			}
			else
			{
				try {
					clientSocket = new Socket("172.21.0.1", 2014);
					in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					out = new PrintWriter(clientSocket.getOutputStream());
					out.println("supp_mail");
					out.println(cin);
					out.println(pass);
					out.println(mailSupp);
					
					out.flush();
					reponse = in.readLine();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(reponse.equals("supprime mail ok"))
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
				
				
				AlertDialog.Builder ad = new AlertDialog.Builder(DeleteMailActivity.this);
			    ad.setTitle("Succ�s");
			    ad.setMessage("Votre deuxi�me e-mail \'"+ editMailsupp.getText().toString() +"\' est supprim� avec succ�s");
			    
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
			    
				//Toast cnx = Toast.makeText(LoginActivity.this, "SVP! v�rifiez votre connexion!", Toast.LENGTH_LONG);
				//cnx.show();
			}
				
			
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			//showProgress(false);
			
		}
	}
	
	public static boolean isHardwareConnectionOn(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		
		// ARE WE CONNECTED TO THE NET
		if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
			// MESSAGE TO SCREEN FOR TESTING (IF REQ)
			 
			return true;
		} else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			
			return false;
		}
		
		return false;
	}
	
	public String ping(String url) {

	    int count = 0;
	    String str = "";
	    try {
	        Process process = Runtime.getRuntime().exec(
	                "/system/bin/ping -c 4 " + url);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                process.getInputStream()));
	        int i;
	        char[] buffer = new char[4096];
	        StringBuffer output = new StringBuffer();
	        while ((i = reader.read(buffer)) > 0)
	            output.append(buffer, 0, i);
	        reader.close();

	        // body.append(output.toString()+"\n");
	        str = output.toString();
	        // Log.d(TAG, str);
	    } catch (IOException e) {
	        // body.append("Error\n");
	        e.printStackTrace();
	    }
	    return str;
	}

}
