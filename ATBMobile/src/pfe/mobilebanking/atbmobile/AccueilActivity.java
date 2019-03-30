package pfe.mobilebanking.atbmobile;

import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager.OnCancelListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AccueilActivity extends Activity {
	
	private Button boutonServiceInscription = null;
	private Button boutonCompte = null;
	
	private Button boutonConversion = null;
	private Button boutonProduit = null;
	private Button boutonLocalisation =  null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
		
		
		boutonServiceInscription = (Button)findViewById(R.id.bServiceInscription);
		boutonServiceInscription.setOnClickListener(serviceInscription);
		
		boutonCompte = (Button)findViewById(R.id.bCompte);
		boutonCompte.setOnClickListener(connexion);
		
		boutonConversion = (Button)findViewById(R.id.bServiceConversion);
		boutonConversion.setOnClickListener(serviceConversion);
		
		boutonProduit = (Button)findViewById(R.id.bServiceProduit);
		boutonProduit.setOnClickListener(serviceProduit);
		
		boutonLocalisation = (Button)findViewById(R.id.bServiceLocalisation);
		boutonLocalisation.setOnClickListener(serviceLocalisation);
		
	}
	
	private OnClickListener serviceInscription = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent secondeActivity = new Intent(AccueilActivity.this, RegisterActivity.class);
			
			startActivity(secondeActivity);
			
		}
	};
	
	private OnClickListener connexion = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent secondeActivity = new Intent(AccueilActivity.this, LoginActivity.class);
			
			startActivity(secondeActivity);
		}
	};
	
	OnClickListener serviceConversion = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            Intent secondeActivity = new Intent(AccueilActivity.this, ConversionActivity.class);
			
			startActivity(secondeActivity);
		}
	};
	
	OnClickListener serviceProduit = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            Intent secondeActivity = new Intent(AccueilActivity.this, ProduitActivity.class);
			
			startActivity(secondeActivity);
			
		}
	};
	
	OnClickListener serviceLocalisation = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            Intent secondeActivity = new Intent(AccueilActivity.this, GabLocalisationActivity.class);
			
			startActivity(secondeActivity);
			
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accueil, menu);
		return true;
	}

}
