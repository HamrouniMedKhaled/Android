package pfe.mobilebanking.atbmobile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ParametreActivity extends Activity {
	
	private Button boutonPwd;
	private Button boutonMail;
	private Button boutonMailSupp;
	
	String cin;
	String pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametre);
		
		boutonPwd  = (Button)findViewById(R.id.bModifierMdp);
		boutonMail = (Button)findViewById(R.id.bAjouterEmail);
		boutonMailSupp = (Button)findViewById(R.id.bSupprimerEmail);
		
		Intent chargeNum = getIntent();
		cin = chargeNum.getStringExtra("cin");
		pwd = chargeNum.getStringExtra("pwd");
		
		boutonPwd.setOnClickListener(modifierPwd);
		boutonMail.setOnClickListener(ajoutMail);
		boutonMailSupp.setOnClickListener(supprimerMail);
		
	}
	
	OnClickListener modifierPwd = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            Intent secondeActivity = new Intent(ParametreActivity.this, PasswordActivity.class);
            
            secondeActivity.putExtra("cin", cin);
            secondeActivity.putExtra("pwd", pwd);
			
			startActivity(secondeActivity);
			
		}
	};
	
OnClickListener ajoutMail = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            Intent secondeActivity = new Intent(ParametreActivity.this, AddMailActivity.class);
            
            secondeActivity.putExtra("cin", cin);
            secondeActivity.putExtra("pwd", pwd);
			
			startActivity(secondeActivity);
			
		}
	};
	
OnClickListener supprimerMail = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            Intent secondeActivity = new Intent(ParametreActivity.this, DeleteMailActivity.class);
            
            secondeActivity.putExtra("cin", cin);
            secondeActivity.putExtra("pwd", pwd);
			
			startActivity(secondeActivity);
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parametre, menu);
		return true;
	}
	
	/*@Override
	public void onUserLeaveHint()
	{
		
    		Intent secondeActivity = new Intent(ParametreActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	
	}*/

}
