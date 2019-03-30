package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pfe.mobilebanking.atbmobile.LoginActivity.UserLoginTask;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CodeActivity extends Activity {
	
	public Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	private EditText passPersonnel;
	private Button bouton_continue;
	
	private View mCodeFormView;
	private View mCodeStatusView;
	private TextView mCodeStatusMessageView;
	
	private UserLoginTask mAuthTask = null;
	private String mPassword;
	private String login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code);
		
		passPersonnel = (EditText)findViewById(R.id.password_personnel);
		bouton_continue = (Button)findViewById(R.id.continue_connexion);
		
		bouton_continue.setOnClickListener(continue_connexion);
		
		Intent loginCin = getIntent();
		login = loginCin.getStringExtra("login");
		
		mCodeFormView = findViewById(R.id.from_code);
		mCodeStatusView = findViewById(R.id.code_status);
		mCodeStatusMessageView = (TextView) findViewById(R.id.code_status_message);
	}
	
	OnClickListener continue_connexion = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			attemptLogin();
			
		}
	};
	
public void attemptLogin() {
		
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		
		passPersonnel.setError(null);

		// Store values at the time of the login attempt.
		
		mPassword = passPersonnel.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			passPersonnel.setError(getString(R.string.error_field_required));
			focusView = passPersonnel;
			cancel = true;
		} else if (mPassword.length() < 4) {
			passPersonnel.setError(getString(R.string.error_invalid_password));
			focusView = passPersonnel;
			cancel = true;
		}
		

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mCodeStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(login,passPersonnel.getText().toString());
			mAuthTask.execute((Void) null);
		}
	}

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
private void showProgress(final boolean show) {
	// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
	// for very easy animations. If available, use these APIs to fade-in
	// the progress spinner.
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
		int shortAnimTime = getResources().getInteger(
				android.R.integer.config_shortAnimTime);

		mCodeStatusView.setVisibility(View.VISIBLE);
		mCodeStatusView.animate().setDuration(shortAnimTime)
				.alpha(show ? 1 : 0)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						mCodeStatusView.setVisibility(show ? View.VISIBLE
								: View.GONE);
					}
				});

		mCodeFormView.setVisibility(View.VISIBLE);
		mCodeFormView.animate().setDuration(shortAnimTime)
				.alpha(show ? 0 : 1)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						mCodeFormView.setVisibility(show ? View.GONE
								: View.VISIBLE);
						
						
						
					}
				});
	} else {
		// The ViewPropertyAnimator APIs are not available, so simply show
		// and hide the relevant UI components.
		mCodeStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
		mCodeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		
		
	}
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.code, menu);
		return true;
	}
	
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		
		String cin = null;
		String pass = null;
		String reponse = null;
		String repPing = null;
		
		public UserLoginTask(String cin, String pass) {
			// TODO Auto-generated constructor stub
			this.cin = cin;
			this.pass = pass;
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(100);
				
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
					out.println("connexion");
					out.println(cin);
					out.println(pass);
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
				
			if((reponse.equals("ok"))||(reponse.equals("first ok")))
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

			if ((success)&&(reponse.equals("ok"))) {
				finish();
				
				String [] numCompte = new String [10];
				int i=0;
				try {
					System.out.println("envoie de INtent");
					while(in.ready())
					{
						numCompte [i] = in.readLine();
						System.out.println(numCompte[i]);
						i++;
					}
					clientSocket.close();
					in.close();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
                Intent secondeActivity = new Intent(CodeActivity.this, EspaceClientActivity.class);
                
                secondeActivity.putExtra("numero_compte", numCompte);
				
				startActivity(secondeActivity);
				
			}else if(repPing.equals("100%"))
			{
				AlertDialog.Builder ad = new AlertDialog.Builder(CodeActivity.this);
			    ad.setTitle("Problème connexion");
			    ad.setMessage("SVP! vérifiez votre connexion Internet!");
			    
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
				
			} else {
				passPersonnel
						.setError(getString(R.string.error_incorrect_password));
				passPersonnel.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
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
