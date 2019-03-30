package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import pfe.mobilebanking.atbmobile.HistoriqueActivity.UserHistoriqueTask;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ConversionActivity extends Activity implements OnClickListener {
	
	private static String 		URL_WS="http://www.webservicex.com/CurrencyConvertor.asmx/ConversionRate";
	private Spinner             spinnerDevise1 = null;
	private Spinner             spinnerDevise2 = null;
	private EditText            editMonnaie = null;
	private Button              bConvertir;
	private TextView            textMonnaie = null;
	private Handler 			mHandler;
	private ProgressDialog 	    dialog;
	private String 			    resultat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversion);
		
		spinnerDevise1 = (Spinner)findViewById(R.id.choix_devise1);
		spinnerDevise2 = (Spinner)findViewById(R.id.choix_devise2);
		editMonnaie = (EditText)findViewById(R.id.editmonnnaieconversion);
		bConvertir = (Button)findViewById(R.id.button_conversion);
		textMonnaie = (TextView)findViewById(R.id.textMonnaieConverti);
		
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.devises, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerDevise1.setAdapter(adapter);
        spinnerDevise2.setAdapter(adapter);
		
		editMonnaie.setText("1");
		
		bConvertir.setOnClickListener(this);
		
		dialog  = new ProgressDialog(this);

		 dialog.setMessage("Appel au servise de devise en cours...");
		 dialog.setCancelable(true);
		 
		 mHandler = new Handler() {
				public void handleMessage(Message msg) {
					switch(msg.what){
						case 1:
						dialog.dismiss();
						textMonnaie.setText(resultat);
						break;
						
					}
				}
			};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversion, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.button_conversion:
				 dialog.show();
				 new Thread(new Runnable(){
						public void run() {
							resultat=convertViaWebService(spinnerDevise1.getSelectedItem().toString(),spinnerDevise2.getSelectedItem().toString(),Double.parseDouble((editMonnaie).getText().toString()));
							mHandler.sendEmptyMessage(1);
						}; 
					}).start();
			
			break;
		}
	}
	
	private String convertViaWebService(String devise_1, String devise_2, Double montant)
	{
        try {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
    	HttpConnectionParams.setSoTimeout(httpParameters, 5000);
    	
    	HttpClient httpclient = new DefaultHttpClient(httpParameters);
    	
    	HttpPost httppost = new HttpPost(URL_WS);  
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);  
        nameValuePairs.add(new BasicNameValuePair("FromCurrency", devise_1));  
        nameValuePairs.add(new BasicNameValuePair("ToCurrency", devise_2));   
    	
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        
        HttpResponse response = httpclient.execute(httppost); 
        
        InputStream is=response.getEntity().getContent();
        
        InputStreamReader reader = new InputStreamReader(is,HTTP.UTF_8);
        
        char[] buf = new char [4096];
        
        int   count;
		StringBuilder sb=new StringBuilder();
		    		
		while ((count = reader.read (buf, 0, buf.length)) != -1)
			sb.append(buf, 0, count);
		is.close();
		
		String res=sb.toString();
		
		System.out.println(res);
		
		return String.valueOf(((Double.parseDouble(sb.toString().substring(res.indexOf("/\">")+3,res.lastIndexOf("<")))*montant)+ " "+devise_2));
        
		} catch (Exception e) {
			return "Erreur lors de l'appel au service de devise";
		}  
		
	}

}
