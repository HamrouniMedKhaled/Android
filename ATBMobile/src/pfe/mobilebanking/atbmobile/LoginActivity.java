package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 * @param <TCPServerATBMobile>
 */
public class LoginActivity<TCPServerATBMobile> extends Activity {
	
	public Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	private Button  boutonInscription = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		
		boutonInscription = (Button)findViewById(R.id.button_inscription);
		boutonInscription.setOnClickListener(inscription);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_connexion).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if((isHardwareConnectionOn(LoginActivity.this)==true))
							attemptLogin();
						else if((isHardwareConnectionOn(LoginActivity.this)==false))
								{
									AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
								    ad.setTitle("Problème connexion");
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
								    
									//Toast cnx = Toast.makeText(LoginActivity.this, "SVP! vérifiez votre connexion!", Toast.LENGTH_LONG);
									//cnx.show();
								}
							
							
					}
				});
	}
	
	OnClickListener inscription = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
            Intent secondeActivity = new Intent(LoginActivity.this, RegisterActivity.class);
			
			startActivity(secondeActivity);
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (mEmail.length() < 8) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(mEmailView.getText().toString(),mPasswordView.getText().toString());
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
							
							
							
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			
			
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
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
			showProgress(false);

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
								
                Intent secondeActivity = new Intent(LoginActivity.this, EspaceClientActivity.class);
                
                secondeActivity.putExtra("numero_compte", numCompte);
                secondeActivity.putExtra("cin", mEmailView.getText().toString());
                secondeActivity.putExtra("pwd", mPasswordView.getText().toString());
				
				startActivity(secondeActivity);
				
			}else if((success)&&(reponse.equals("first ok")))
			{
                Intent secondeActivity = new Intent(LoginActivity.this, CodeActivity.class);
                
                secondeActivity.putExtra("login", cin);
				
				startActivity(secondeActivity);
			}else if(repPing.equals("100%"))
			{
				AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
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
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
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
