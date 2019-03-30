package pfe.mobilebanking.atbmobile;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EspaceClientActivity extends Activity{
	
	private Button boutonParametre = null;
	private Button boutonChequier = null;
	private Button boutonCarte = null;
	private Button boutonCompte = null;
	private Button boutonVirement = null;
	private Button boutonHistorique = null;
	
	private String [] mNumCompte = new String[20];// {"012345678910","118957123879"};
	String [] listCompte;
	int taille =0;
	private String cin;
	private String pwd;
	
	boolean homeButton = true;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_espace_client);
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
		
		homeButton = true;
		
		boutonParametre = (Button)findViewById(R.id.bParametre);
		boutonParametre.setOnClickListener(parametrePersonnel);
		
		boutonChequier = (Button)findViewById(R.id.bChequier);
		boutonChequier.setOnClickListener(chequierActivite);
		
		boutonCarte = (Button)findViewById(R.id.bCarte);
		boutonCarte.setOnClickListener(carteActivite);
		
		boutonCompte = (Button)findViewById(R.id.bGestionCompte);
		boutonCompte.setOnClickListener(compteActivite);
		
		boutonVirement = (Button)findViewById(R.id.bVirement);
		boutonVirement.setOnClickListener(virementActivite);
		
		boutonHistorique = (Button)findViewById(R.id.bHistorique);
		boutonHistorique.setOnClickListener(historiqueActivite);
		
		Intent chargeNum = getIntent();
		mNumCompte = chargeNum.getStringArrayExtra("numero_compte");
		cin = chargeNum.getStringExtra("cin");
		pwd = chargeNum.getStringExtra("pwd");
		
		while(mNumCompte[taille] != null)
		{
			taille++;
		}
		
		//listCompte = new String[taille+1];
		
		listCompte = new String[taille];
		for(int i = 0;i<taille;i++)
		{
			listCompte[i] = mNumCompte[i];
		}
		
	}
	
	OnClickListener compteActivite = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			homeButton = false;
			
			Intent secondeActivity = new Intent(EspaceClientActivity.this, CompteActivity.class);
            
            secondeActivity.putExtra("liste_compte", listCompte);
			
			startActivity(secondeActivity);
			
		}
	};
	
    OnClickListener historiqueActivite = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			homeButton = false;
			
            Intent secondeActivity = new Intent(EspaceClientActivity.this, HistoriqueActivity.class);
            
            secondeActivity.putExtra("liste_compte", listCompte);
			
			startActivity(secondeActivity);
			
		}
	};
	
    OnClickListener virementActivite = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			homeButton = false;
			
			numCompteDialog();
			
		}
	};
	
	OnClickListener parametrePersonnel = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			homeButton = false;
			
            Intent secondeActivity = new Intent(EspaceClientActivity.this, ParametreActivity.class);
            
            secondeActivity.putExtra("cin", cin);
            secondeActivity.putExtra("pwd", pwd);
			
			startActivity(secondeActivity);
			
		}
	};
	
    OnClickListener chequierActivite = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			homeButton = false;
			
            Intent secondeActivity = new Intent(EspaceClientActivity.this, ChequierActivity.class);
            
            secondeActivity.putExtra("liste_compte", listCompte);
			
			startActivity(secondeActivity);
			
		}
	};
	
    OnClickListener carteActivite = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			homeButton = false;
			
            Intent secondeActivity = new Intent(EspaceClientActivity.this, CarteActivity.class);
            
            secondeActivity.putExtra("liste_compte", listCompte);
			
			startActivity(secondeActivity);
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.espace_client, menu);
		return true;
	}
	
	String compteSelectionne = new String();
	
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
                            Intent secondeActivity = new Intent(EspaceClientActivity.this, VirementActivity.class);
				            
				            secondeActivity.putExtra("compte_selectionne", compteSelectionne);
							
							startActivity(secondeActivity);
						}
						else
						{
							Toast t = Toast.makeText(EspaceClientActivity.this, "SVP! choisissez un compte avant d'effectuer un virement", Toast.LENGTH_LONG);
							t.show();
						}
						
					}
				}
			);
		
		ad.show();
	}
    
    @Override
    protected void onResume() {
    	
    	// only when screen turns on
        if (!ScreenReceiver.wasScreenOn) {
            // this is when onResume() is called due to a screen state change
            System.out.println("SCREEN TURNED ON");
            
            Intent secondeActivity = new Intent(EspaceClientActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
            
        } else {
            // this is when onResume() is called when the screen state has not changed
        }
    	
      super.onResume();
      homeButton = true;
    }
    
    @Override
	public void onUserLeaveHint()
	{
    	if(homeButton == true)
    	{
    		Intent secondeActivity = new Intent(EspaceClientActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	}
    	
	}
    
    
    /*@Override
	public void onPause()
	{
    	// when the screen is about to turn off
        if (ScreenReceiver.wasScreenOn) {
            // this is the case when onPause() is called by the system due to a screen state change
        	
            System.out.println("SCREEN TURNED OFF");
        } else {
            // this is when onPause() is called when the screen state has not changed
        	
        	System.out.println("SCREEN TURNED ON");
        }
    	
	}*/

    

}
