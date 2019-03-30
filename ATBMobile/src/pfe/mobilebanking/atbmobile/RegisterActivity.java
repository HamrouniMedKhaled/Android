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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class RegisterActivity extends Activity {
	
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
	private UserRegisterTask mAuthTask = null;
	
	private Button boutonEnregister = null;
	
	
	private EditText editNom = null;
	private EditText editPrenom = null;
	private EditText editCin = null;
	private RadioGroup groupSituation = null;
	private EditText editEmail = null;
	private EditText editMdp = null;
	private EditText editRmdp = null;
	
	private View mRegisterFormView;
	private View mRegisterStatusView;
	private TextView mRegisterStatusMessageView;
	
	private String eNom;
	private String ePrenom;
	private String eCin;
	private String eMdp;
	private String eRMdp;
	private String eEmail;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		editNom        =(EditText)findViewById(R.id.editNom);
		editPrenom     =(EditText)findViewById(R.id.editPrenom);
		editCin        =(EditText)findViewById(R.id.editCin);
		editEmail      =(EditText)findViewById(R.id.editMail);
		editMdp        =(EditText)findViewById(R.id.editMdp);
		editRmdp       =(EditText)findViewById(R.id.editRMdp);
		groupSituation =(RadioGroup)findViewById(R.id.groupSituation);
		
		boutonEnregister =(Button)findViewById(R.id.button_enregistrer);
		
		
		boutonEnregister.setOnClickListener(enregistrer);
		
		
		editNom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		editPrenom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		editCin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		editMdp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		
		editRmdp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		editEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		
		mRegisterFormView = findViewById(R.id.scroll_view_inscription);
		mRegisterStatusView = findViewById(R.id.register_status);
		mRegisterStatusMessageView = (TextView) findViewById(R.id.register_status_message);
	}
	
	OnEditorActionListener champsTest = new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// TODO Auto-generated method stub
			if (actionId == R.id.Register || actionId == EditorInfo.IME_NULL) {
				validation();
				return true;
			}
			return false;
		}
	};
	
	OnClickListener enregistrer = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if((isHardwareConnectionOn(RegisterActivity.this)==true))
				validation();
			else if((isHardwareConnectionOn(RegisterActivity.this)==false))
					{
						AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
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
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	private void validation()
	{
		if (mAuthTask != null) {
			return;
		}
		
		        // Reset errors.
				editNom.setError(null);
				editPrenom.setError(null);
				editCin.setError(null);
				editMdp.setError(null);
				editRmdp.setError(null);
				editEmail.setError(null);
				

				// Store values at the time of the login attempt.
				eNom = editNom.getText().toString();
				ePrenom = editPrenom.getText().toString();
				eCin = editCin.getText().toString();
				eMdp = editMdp.getText().toString();
				eRMdp = editRmdp.getText().toString();
				eEmail = editEmail.getText().toString();

				boolean cancel = false;
				View focusView = null;
				
				
				
				// Check for a valid password.
				if (TextUtils.isEmpty(eMdp)) {
					editMdp.setError(getString(R.string.error_field_required));
					focusView = editMdp;
					cancel = true;
				} else if (eMdp.length() < 4) {
					editMdp.setError(getString(R.string.error_invalid_password));
					focusView = editMdp;
					cancel = true;
				}
				
				// Check for a valid re-password.
				if (TextUtils.isEmpty(eRMdp)) {
					editRmdp.setError(getString(R.string.error_field_required));
					focusView = editRmdp;
					cancel = true;
				} else if (!eMdp.equals(eRMdp)) {
					editRmdp.setError(getString(R.string.error_not_equal_password));
					focusView = editMdp;
					cancel = true;
				}
				
				// Check for a valid email address.
				if (TextUtils.isEmpty(eEmail)) {
					editEmail.setError(getString(R.string.error_field_required));
					focusView = editEmail;
					cancel = true;
				} else if (!eEmail.contains("@")) {
					editEmail.setError(getString(R.string.error_invalid_email));
					focusView = editEmail;
					cancel = true;
				}
				
				if (TextUtils.isEmpty(eCin)) {
					editCin.setError(getString(R.string.error_field_required));
					focusView = editCin;
					cancel = true;
				}else if(eCin.length() < 8)
				{
					editCin.setError(getString(R.string.error_cin));
					focusView = editCin;
					cancel = true;
				}
				
				if (TextUtils.isEmpty(ePrenom)) {
					editPrenom.setError(getString(R.string.error_field_required));
					focusView = editPrenom;
					cancel = true;
				}
				
				if (TextUtils.isEmpty(eNom)) {
					editNom.setError(getString(R.string.error_field_required));
					focusView = editNom;
					cancel = true;
				}
				
				if (cancel) {
					// There was an error; don't attempt login and focus the first
					// form field with an error.
					focusView.requestFocus();
				} else {
					// Show a progress spinner, and kick off a background task to
					// perform the user login attempt.
					mRegisterStatusMessageView.setText(R.string.login_progress_register);
					showProgress(true);
					mAuthTask = new UserRegisterTask(editCin.getText().toString(),editMdp.getText().toString(),editEmail.getText().toString());
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

			mRegisterStatusView.setVisibility(View.VISIBLE);
			mRegisterStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mRegisterFormView.setVisibility(View.VISIBLE);
			mRegisterFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
							
							
							
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mRegisterStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			
			
		}
	}
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
		
		String cin = null;
		String pass = null;
		String email = null;
		String reponse = null;
		String repPing = null;
		
		public UserRegisterTask(String cin, String pass, String email)
		{
			this.cin = cin;
			this.pass = pass;
			this.email = email;
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
					out.println("inscription");
					out.println(cin);
					out.println(pass);
					out.println(email);
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
			
			if(reponse.equals("inscrit ok"))
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

			if (success) {
				finish();
				
				Intent secondeActivity = new Intent(RegisterActivity.this, LoginActivity.class);
				
				startActivity(secondeActivity);
				
			} else {
				editMdp
						.setError(getString(R.string.error_incorrect_password));
				editMdp.requestFocus();
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
