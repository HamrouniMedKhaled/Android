package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GabLocalisationActivity extends Activity {
	
	private GoogleMap gabMap;
	private ArrayList<String> infoGab = new ArrayList<String>();
	private MapCoordonnees mapCoord;
	String [] lat_lng = new String [3];
	Marker bank = null;
	MarkerOptions options = new MarkerOptions();
	
	
	private Button boutonActualisation;
	private Button boutonLocaliser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gab_localisation);
		
		boutonActualisation = (Button)findViewById(R.id.bActualisationGab);
		boutonLocaliser     = (Button)findViewById(R.id.bLocaliserGab);
		
		boutonActualisation.setOnClickListener(actualiser);
		boutonLocaliser.setOnClickListener(localiser);
		
		mapCoord = new MapCoordonnees();
		mapCoord.execute((Void) null);
		
		gabMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map_gap)).getMap();
			
		gabMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		gabMap.setMyLocationEnabled(true);
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            gabMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
            .zoom(17)                   // Sets the zoom
            .bearing(0)                 // Sets the orientation of the camera to east
            .tilt(0)                    // Sets the tilt of the camera to 30 degrees
            .build();                   // Creates a CameraPosition from the builder
        gabMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
		
		/*LatLng latlng = new LatLng(36.862897,10.194433);
		//LatLng latlng = new LatLng(gabMap.getMyLocation().getLatitude(),gabMap.getMyLocation().getLatitude());
		System.out.println("My location: \n"+gabMap.getMyLocation().getLatitude()+","+gabMap.getMyLocation().getLatitude());
		gabMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17), 2000, null);
		//gabMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng((Double)gabMap.getMyLocation().getLatitude(), (Double)gabMap.getMyLocation().getLatitude()), 17));*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gab_localisation, menu);
		return true;
	}
	
	OnClickListener actualiser = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			mapCoord = new MapCoordonnees();
			mapCoord.execute((Void) null);
			
		}
	};
	
	class MapCoordonnees extends AsyncTask<Void, Void, Boolean>
	{
		Socket clientSocket;
		BufferedReader in;
		PrintWriter out;
		String reponse;
		String repPing = null;

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
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
					out.println("map");
					out.flush();
					
					System.out.println(in.readLine());
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return true;
		}
		
		@Override
		protected void onPostExecute(final Boolean success) {
			
			mapCoord = null;

			if (success)
			{
				try {
					System.out.println("succé");
					//System.out.println(in.readLine());
					
					while(in.ready())
					{
						System.out.println("ecriture des coordonnees");
						reponse = in.readLine();
						System.out.println(reponse);
						infoGab.add(reponse);
					}
					
					clientSocket.close();
					in.close();
					out.close();
					
					
					//Geocoder geocoder = new Geocoder(GabLocalisationActivity.this, new Locale("fr"));
					
					
					for(int i=0;i<infoGab.size();i++)
					{
						lat_lng[0]=infoGab.get(i).substring(0, 10).toString();
						lat_lng[1]=infoGab.get(i).substring(11, 21).toString();
						lat_lng[2]=infoGab.get(i).substring(22, infoGab.get(i).length()).toString();
						
						System.out.println(lat_lng[0]+" , "+lat_lng[1]+" , "+lat_lng[2]);
						
						//List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat_lng[0]), Double.parseDouble(lat_lng[1]), 10);
						/*if (addresses.size() > 0) {
							Address address = addresses.get(0);*/

							
							System.out.println(lat_lng[0]+" , "+lat_lng[1]);
							
							options.position(new LatLng(Double.parseDouble(lat_lng[0]), Double.parseDouble(lat_lng[1])));
							options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_map));
							options.title("ATB");
							options.snippet(lat_lng[2]);
							/*String locality = address.getLocality();
							if(locality!=null)
								options.snippet(address.getCountryCode()+","+address.getLocality()+","+address.getFeatureName());
							else
								options.snippet(address.getCountryCode()+","+address.getFeatureName());*/
							bank = gabMap.addMarker(options);
						//}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(repPing.equals("100%"))
			{
				AlertDialog.Builder ad = new AlertDialog.Builder(GabLocalisationActivity.this);
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
				
			}
		}
		
		@Override
		protected void onCancelled()
		{
			mapCoord = null;
		}
		
	}
	
	OnClickListener localiser = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			double distance;
			double min = 0;
			
			int position = 0;
			
			Location myLocation = new Location("");
			
			myLocation.setLatitude(gabMap.getMyLocation().getLatitude());
			myLocation.setLongitude(gabMap.getMyLocation().getLongitude());
			
			Location gabLocation = new Location("");
			
			for(int i=0;i<infoGab.size();i++)
			{
				
				
				lat_lng[0]=infoGab.get(i).substring(0, 10).toString();
				lat_lng[1]=infoGab.get(i).substring(11, 21).toString();
				//lat_lng[2]=infoGab.get(i).substring(22, infoGab.get(i).length()).toString();
				
				gabLocation.setLatitude(Double.valueOf(lat_lng[0]));
				gabLocation.setLongitude(Double.valueOf(lat_lng[1]));
				
				distance = myLocation.distanceTo(gabLocation);
				
				if(min == 0)
				{
					min = distance;
				}
				
				if(distance < min)
				{
					min = distance;
					position = i;
				}
				
			}
			
			final int pos = position;
			
			AlertDialog.Builder ad = new AlertDialog.Builder(GabLocalisationActivity.this);
		    ad.setTitle("GAB localisé");
		    ad.setMessage(infoGab.get(position).substring(22, infoGab.get(position).length()).toString());
		    
		    ad.setOnCancelListener(new DialogInterface.OnCancelListener(){
				public void onCancel(DialogInterface dialog) {
					// OK				
				}}
			
			);
		    
		    ad.setPositiveButton("Continuer", 
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int arg1) {
				        
				        gabMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
			                    new LatLng(Double.valueOf(infoGab.get(pos).substring(0, 10).toString()), Double.valueOf(infoGab.get(pos).substring(11, 21).toString())), 13));

			            CameraPosition cameraPosition = new CameraPosition.Builder()
			            .target(new LatLng(Double.valueOf(infoGab.get(pos).substring(0, 10).toString()), Double.valueOf(infoGab.get(pos).substring(11, 21).toString())))      // Sets the center of the map to location user
			            .zoom(17)                   // Sets the zoom
			            .bearing(0)                 // Sets the orientation of the camera to east
			            .tilt(0)                    // Sets the tilt of the camera to 30 degrees
			            .build();                   // Creates a CameraPosition from the builder
			        gabMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
							
						}
					}
				);
			
			ad.show();
			
		}
	};

	public String ping(String url) {

	    
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
