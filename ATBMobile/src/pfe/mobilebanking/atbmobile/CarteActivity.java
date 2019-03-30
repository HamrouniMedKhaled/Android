package pfe.mobilebanking.atbmobile;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CarteActivity extends Activity {
	
	private Button boutonDemandeCarte;
	private Button boutonOppoCarte;
	
	String compteSelectionne = new String();
	String [] listCompte = null;
	
	boolean homeButton = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carte);
		
		boutonDemandeCarte = (Button)findViewById(R.id.bDemandeCarte);
		boutonOppoCarte    = (Button)findViewById(R.id.bVoirCarte);
		
		boutonDemandeCarte.setOnClickListener(demandeCarte);
		boutonOppoCarte.setOnClickListener(oppoCarte);
		
		Intent chargeNum = getIntent();
		listCompte = chargeNum.getStringArrayExtra("liste_compte");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.carte, menu);
		return true;
	}
	
	OnClickListener demandeCarte = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			homeButton = false;
			
			numCompteDialog();
			
            
			
		}
	};
	
	OnClickListener oppoCarte = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            homeButton = false;
			
            Intent secondeActivity = new Intent(CarteActivity.this, OppositionCarteActivity.class);
    		
    		startActivity(secondeActivity);
			
		}
	};
	
	@Override
    protected void onResume() {
      super.onResume();
      homeButton = true;
    }
	
public void numCompteDialog(){
		
	    AlertDialog.Builder ad = new AlertDialog.Builder(this);
	    ad.setTitle("Mes comptes");
	    //ad.setView(LayoutInflater.from(this).inflate(R.layout.activity_dialogue_numero_compte,null));
	    //ad.setMessage("Ici le Message");

		ad.setOnCancelListener(new DialogInterface.OnCancelListener(){
			public void onCancel(DialogInterface dialog) {
				// OK				
			}}
		
		);
		
			ad.setSingleChoiceItems(listCompte, -1, new DialogInterface.OnClickListener() {
		
			public void onClick(DialogInterface dialog, int which) {
				
				compteSelectionne = listCompte[which].toString();
				System.out.println(compteSelectionne);
			}
    	});
		
		ad.setPositiveButton("Continuer", 
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int arg1) {
						//OK
						homeButton = false;
						
						if(!compteSelectionne.equals(""))
						{
							
							Intent secondeActivity = new Intent(CarteActivity.this, DemandeCarteActivity.class);
							
							secondeActivity.putExtra("compte_selectionne", compteSelectionne);
				    		
				    		startActivity(secondeActivity);
                       
						}
						else
						{
							Toast t = Toast.makeText(CarteActivity.this, "SVP! choisissez un compte avant d'effectuer un virement", Toast.LENGTH_LONG);
							t.show();
						}
						
					}
				}
			);
		
		ad.show();
	}
	
	@Override
	public void onUserLeaveHint()
	{
		if(homeButton == true)
		{
            Intent secondeActivity = new Intent(CarteActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
		}
    	
	}

}
