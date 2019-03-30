package pfe.mobilebanking.atbmobile;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class StatistiqueActivity extends Activity {
	
	private TextView dateD = null;
	private TextView dateA = null;
	private TextView ripS = null;
	private ListView mListRip = null;
	
	int lesTransactions;
	int t;
	String [] dateSelected;
	String [] mTransCompte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistique);
		
		dateD = (TextView)findViewById(R.id.date_periode_de);
		dateA = (TextView)findViewById(R.id.date_periode_a);
		ripS = (TextView)findViewById(R.id.list_rip);
		
		mListRip = (ListView)findViewById(R.id.list_transactions);
		
		
		Intent iStatics = getIntent();
		dateD.setText(iStatics.getStringExtra("date_debut_periode"));
		dateA.setText(iStatics.getStringExtra("date_fin_periode"));
		ripS.setText(iStatics.getStringExtra("Rip_selected"));
		t = iStatics.getIntExtra("les_transactions", lesTransactions);
		//dateSelected = iStatics.getStringArrayExtra("periode_selection");
		mTransCompte = iStatics.getStringArrayExtra("liste_trans");
		
		System.out.println("length in SA:  "+t);
		
		String [][] afficheElementTrans = new String[t+1][t+1];
		for(int i = 0;i<t;i++)
		{
			int tr = i + 1;
			afficheElementTrans[i][0] = "Transaction:   "+tr;
			System.out.println("statistique activite text1:    \n"+afficheElementTrans[i][0]);
		}
		for(int i = 0;i<t;i++)
		{
			afficheElementTrans[i][1] = mTransCompte[i];
			System.out.println("statistique activite text2:    \n"+afficheElementTrans[i][1]);
		}
		
		
		List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> element;
		
		for(int i = 0;i<t;i++)
		{
			element = new HashMap<String, String>();
			element.put("date_trans", afficheElementTrans[i][0]);
			element.put("trans_compte", afficheElementTrans[i][1]);
			liste.add(element);
		}
		
		//System.out.println("affichage de solde");
		ListAdapter adapter = new SimpleAdapter(StatistiqueActivity.this , liste, android.R.layout.simple_list_item_2, new String[] {"date_trans","trans_compte"}, new int[]{android.R.id.text1,android.R.id.text2});
		mListRip.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistique, menu);
		return true;
	}
	
	@Override
	public void onUserLeaveHint()
	{
		
    		Intent secondeActivity = new Intent(StatistiqueActivity.this, AccueilActivity.class);
    		
    		startActivity(secondeActivity);
    	
	}

}
