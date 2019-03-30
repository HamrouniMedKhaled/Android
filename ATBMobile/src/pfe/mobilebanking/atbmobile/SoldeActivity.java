package pfe.mobilebanking.atbmobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SoldeActivity extends Activity {
	
	private ListView mListSolde = null;
	String [] compteSelected;
	int selectNombre;
	int select;
	private String [] mSoldeCompte = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solde);
		
		mListSolde = (ListView)findViewById(R.id.info_compte);
		
		Intent iSolde = getIntent();
		selectNombre = iSolde.getIntExtra("Select", select);
		compteSelected = iSolde.getStringArrayExtra("Rip_selected");
		mSoldeCompte = new String[selectNombre];
		mSoldeCompte = new String[selectNombre];
		mSoldeCompte = iSolde.getStringArrayExtra("solde_selected");
		
		String [][] afficheElementSolde = new String[selectNombre+1][selectNombre+1];
		for(int i = 0;i<selectNombre;i++)
		{
			afficheElementSolde[i][0] = "Compte "+compteSelected[i];
		}
		for(int i = 0;i<selectNombre+1;i++)
		{
			afficheElementSolde[i][1] = mSoldeCompte[i];
		}
		afficheElementSolde[selectNombre][0]="Total";
		
		List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> element;
		
		for(int i = 0;i<selectNombre+1;i++)
		{
			element = new HashMap<String, String>();
			element.put("num_compte", afficheElementSolde[i][0]);
			element.put("sol_compte", afficheElementSolde[i][1]);
			liste.add(element);
		}
		
		//System.out.println("affichage de solde");
		ListAdapter adapter = new SimpleAdapter(SoldeActivity.this , liste, android.R.layout.simple_list_item_2, new String[] {"num_compte","sol_compte"}, new int[]{android.R.id.text1,android.R.id.text2});
		mListSolde.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solde, menu);
		return true;
	}
	
	@Override
	public void onUserLeaveHint()
	{
		
    		Intent secondeActivity = new Intent(SoldeActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	
	}

}
