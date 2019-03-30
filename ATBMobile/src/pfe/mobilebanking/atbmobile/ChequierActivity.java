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

public class ChequierActivity extends Activity {
	
	boolean homeButton = true;
	
	private Button boutonDemandeChequier;
	private Button boutonOppoChequier;
	
	String compteSelectionne = new String();
	String [] listCompte = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chequier);
		
		boutonDemandeChequier = (Button)findViewById(R.id.bDemandeChequier);
		boutonOppoChequier    = (Button)findViewById(R.id.bVoirChequier);
		
		boutonDemandeChequier.setOnClickListener(demanderChequier);
		boutonOppoChequier.setOnClickListener(oppoChequier);
		
		Intent chargeNum = getIntent();
		listCompte = chargeNum.getStringArrayExtra("liste_compte");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chequier, menu);
		return true;
	}
	
	OnClickListener demanderChequier = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			homeButton = false;
			
			numCompteDialog();
			
		}
	};
	
	OnClickListener oppoChequier = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            homeButton = false;
			
            Intent secondeActivity = new Intent(ChequierActivity.this, OppositionChequierActivity.class);
    		
    		startActivity(secondeActivity);
			
		}
	};
	
	
	
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
                            Intent secondeActivity = new Intent(ChequierActivity.this, DemandeChequierActivity.class);
				            
				            secondeActivity.putExtra("compte_selectionne", compteSelectionne);
							
							startActivity(secondeActivity);
						}
						else
						{
							Toast t = Toast.makeText(ChequierActivity.this, "SVP! choisissez un compte avant d'effectuer un virement", Toast.LENGTH_LONG);
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
            Intent secondeActivity = new Intent(ChequierActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
		}
    	
	}

}
