package pfe.mobilebanking.atbmobile;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CancellationSignal.OnCancelListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ProduitActivity extends Activity implements OnClickListener{
	
	private ListView listProduitCompte;
	private ListView listProduitCarte;
	private ListView listProduitPack;
	private ListView listProduitService;
	
	private Button bRetourList;
	private Button bOngletCompte;
	private Button bOngletCarte;
	private Button bOngletPack;
	private Button bOngletService;
	
	private boolean boutonCancelCompte = true;
	private boolean boutonCancelCarte = false;
	private boolean boutonCancelpack = false;
	private boolean boutonCancelService = false;
	
	ArrayList<HashMap<String, String>> listItemCompte = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> listItemCarte = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> listItemPack = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> listItemService = new ArrayList<HashMap<String, String>>();

	//On déclare la HashMap qui contiendra les informations pour un item
    HashMap<String, String> mapCarte;
    HashMap<String, String> mapCompte;
    HashMap<String, String> mapPack;
    HashMap<String, String> mapService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produit);
		
		listProduitCompte = (ListView)findViewById(R.id.list_view_produit_compte);
		listProduitCarte = (ListView)findViewById(R.id.list_view_produit_carte);
		listProduitPack = (ListView)findViewById(R.id.list_view_produit_pack);
		listProduitService = (ListView)findViewById(R.id.list_view_produit_service);
		
		bRetourList = (Button)findViewById(R.id.retourListProduit);
		bRetourList.setOnClickListener(afficheListe);
		
		bOngletCompte = (Button)findViewById(R.id.bouton_onglet_compte);
		bOngletCarte = (Button)findViewById(R.id.bouton_onglet_cartes);
		bOngletPack = (Button)findViewById(R.id.bouton_onglet_packs);
		bOngletService = (Button)findViewById(R.id.bouton_onglet_services);
		
		bOngletCompte.setOnClickListener(this);
		bOngletCarte.setOnClickListener(this);
		bOngletPack.setOnClickListener(this);
		bOngletService.setOnClickListener(this);
		
		bOngletCompte.setBackgroundResource(R.drawable.onglet_2);
		bRetourList.setEnabled(false);
		
        remplirListeCarte(getResources().getString(R.string.carte_lella), R.drawable.atblella_carte_icon);
        remplirListeCarte(getResources().getString(R.string.carte_moussafer), R.drawable.atb_moussafer_icon);
        remplirListeCarte(getResources().getString(R.string.carta_atb_visa_et_mastercard), R.drawable.mastercard_visa_icon);
        
        remplirListeCompte(getResources().getString(R.string.compte_Compte_cheques_en_dinars_et_en_dinars_convertibles), R.drawable.banner_dinars_convertible);
        remplirListeCompte(getResources().getString(R.string.compte_Compte_cheques_en_dinars_convertibles_pour_les_TRE), R.drawable.banner_compte_tre);
        remplirListeCompte(getResources().getString(R.string.Compte_professionel_en_dinars_convertibles_ou_en_devises), R.drawable.banner_compte_professionnel);
        remplirListeCompte(getResources().getString(R.string.compte_Jeune), R.drawable.banner_compte_jeune);
        
        remplirListePack(getResources().getString(R.string.Pack_Gisr), R.drawable.atb_pack_gisr);
        remplirListePack(getResources().getString(R.string.Pack_Jeune), R.drawable.packjeune);
        
      //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mScheduleCarte = new SimpleAdapter (this.getBaseContext(), listItemCarte, R.layout.list_produits,
               new String[] {"imgCarte", "titreCarte"}, new int[] {R.id.img_produit, R.id.titre_produit});
        
        SimpleAdapter mScheduleCompte = new SimpleAdapter (this.getBaseContext(), listItemCompte, R.layout.list_produits,
                new String[] {"imgCompte", "titreCompte"}, new int[] {R.id.img_produit, R.id.titre_produit});
        
        SimpleAdapter mSchedulePack = new SimpleAdapter (this.getBaseContext(), listItemPack, R.layout.list_produits,
                new String[] {"imgPack", "titrePack"}, new int[] {R.id.img_produit, R.id.titre_produit});
        
      //On attribut à notre listView l'adapter que l'on vient de créer
        listProduitCarte.setAdapter(mScheduleCarte);
        listProduitCompte.setAdapter(mScheduleCompte);
        listProduitPack.setAdapter(mSchedulePack);
 
        //Enfin on met un écouteur d'évènement sur notre listView
        listProduitCarte.setOnItemClickListener(listClickCarte);
        listProduitCompte.setOnItemClickListener(listClickCompte);
        listProduitPack.setOnItemClickListener(listClickPack);
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.produit, menu);
		return true;
	}
	
	OnItemClickListener listClickCarte = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			// TODO Auto-generated method stub
			
			HashMap<String, String> map = (HashMap<String, String>) listProduitCarte.getItemAtPosition(position);
			bRetourList.setEnabled(true);
			
			if(map.get("titreCarte").equals(getResources().getString(R.string.carte_lella)))
			{
				findViewById(R.id.listView_nos_produits_carte).setVisibility(View.GONE);
				findViewById(R.id.cartalella).setVisibility(View.VISIBLE);
				
			}else if(map.get("titreCarte").equals(getResources().getString(R.string.carte_moussafer)))
			{
				findViewById(R.id.listView_nos_produits_carte).setVisibility(View.GONE);
				findViewById(R.id.cartaMoussafer).setVisibility(View.VISIBLE);
			}else if(map.get("titreCarte").equals(getResources().getString(R.string.carta_atb_visa_et_mastercard)))
			{
				findViewById(R.id.listView_nos_produits_carte).setVisibility(View.GONE);
				findViewById(R.id.cartaVisa_Master).setVisibility(View.VISIBLE);
			}
			
		}
	};
	
	OnItemClickListener listClickCompte = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			// TODO Auto-generated method stub
			
			HashMap<String, String> map = (HashMap<String, String>) listProduitCompte.getItemAtPosition(position);
			bRetourList.setEnabled(true);
			
			if(map.get("titreCompte").equals(getResources().getString(R.string.compte_Compte_cheques_en_dinars_et_en_dinars_convertibles)))
			{
				findViewById(R.id.listView_nos_produits_compte).setVisibility(View.GONE);
				findViewById(R.id.compte_Cheq_d_dc).setVisibility(View.VISIBLE);
				
			}else if(map.get("titreCompte").equals(getResources().getString(R.string.compte_Compte_cheques_en_dinars_convertibles_pour_les_TRE)))
			{
				findViewById(R.id.listView_nos_produits_compte).setVisibility(View.GONE);
				findViewById(R.id.compte_tre).setVisibility(View.VISIBLE);
			}else if(map.get("titreCompte").equals(getResources().getString(R.string.Compte_professionel_en_dinars_convertibles_ou_en_devises)))
			{
				findViewById(R.id.listView_nos_produits_compte).setVisibility(View.GONE);
				findViewById(R.id.compte_pro).setVisibility(View.VISIBLE);
			}else if(map.get("titreCompte").equals(getResources().getString(R.string.compte_Jeune)))
			{
				findViewById(R.id.listView_nos_produits_compte).setVisibility(View.GONE);
				findViewById(R.id.compte_jeune).setVisibility(View.VISIBLE);
			}
			
		}
	};
	
	OnItemClickListener listClickPack = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			// TODO Auto-generated method stub
			
			HashMap<String, String> map = (HashMap<String, String>) listProduitPack.getItemAtPosition(position);
			bRetourList.setEnabled(true);
			
			if(map.get("titrePack").equals(getResources().getString(R.string.Pack_Gisr)))
			{
				findViewById(R.id.listView_nos_produits_pack).setVisibility(View.GONE);
				findViewById(R.id.pack_gisr).setVisibility(View.VISIBLE);
				
			}else if(map.get("titrePack").equals(getResources().getString(R.string.Pack_Jeune)))
			{
				findViewById(R.id.listView_nos_produits_pack).setVisibility(View.GONE);
				findViewById(R.id.pack_jeune).setVisibility(View.VISIBLE);
			}
			
		}
	};
	
	public void remplirListeCarte(String titre, int img)
	{
		//Création d'une HashMap pour insérer les informations du premier item de notre listView
        mapCarte = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        mapCarte.put("titreCarte", titre);
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        mapCarte.put("imgCarte", String.valueOf(img));
        //enfin on ajoute cette hashMap dans la arrayList
        listItemCarte.add(mapCarte);
	}
	
	public void remplirListeCompte(String titre, int img)
	{
		//Création d'une HashMap pour insérer les informations du premier item de notre listView
        mapCompte = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        mapCompte.put("titreCompte", titre);
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        mapCompte.put("imgCompte", String.valueOf(img));
        //enfin on ajoute cette hashMap dans la arrayList
        listItemCompte.add(mapCompte);
	}
	
	public void remplirListePack(String titre, int img)
	{
		//Création d'une HashMap pour insérer les informations du premier item de notre listView
        mapPack = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        mapPack.put("titrePack", titre);
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        mapPack.put("imgPack", String.valueOf(img));
        //enfin on ajoute cette hashMap dans la arrayList
        listItemPack.add(mapPack);
	}
	
	public void remplirListeService(String titre, int img)
	{
		//Création d'une HashMap pour insérer les informations du premier item de notre listView
        mapService = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        mapService.put("titreService", titre);
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        mapService.put("imgService", String.valueOf(img));
        //enfin on ajoute cette hashMap dans la arrayList
        listItemService.add(mapService);
	}
	
	OnClickListener afficheListe = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(boutonCancelCarte == true)
			{
				findViewById(R.id.listView_nos_produits_carte).setVisibility(View.VISIBLE);
				findViewById(R.id.cartalella).setVisibility(View.GONE);
				findViewById(R.id.cartaMoussafer).setVisibility(View.GONE);
				findViewById(R.id.cartaVisa_Master).setVisibility(View.GONE);
			}else if(boutonCancelCompte == true)
			{
				findViewById(R.id.listView_nos_produits_compte).setVisibility(View.VISIBLE);
				findViewById(R.id.compte_Cheq_d_dc).setVisibility(View.GONE);
				findViewById(R.id.compte_tre).setVisibility(View.GONE);
				findViewById(R.id.compte_pro).setVisibility(View.GONE);
				findViewById(R.id.compte_jeune).setVisibility(View.GONE);
			}else if(boutonCancelpack == true)
			{
				findViewById(R.id.listView_nos_produits_pack).setVisibility(View.VISIBLE);
				findViewById(R.id.pack_gisr).setVisibility(View.GONE);
				findViewById(R.id.pack_jeune).setVisibility(View.GONE);
			}else if(boutonCancelService == true)
			{
				findViewById(R.id.listView_nos_produits_service).setVisibility(View.VISIBLE);
				//findViewById(R.id.compte_Cheq_d_dc).setVisibility(View.GONE);
				//findViewById(R.id.compte_tre).setVisibility(View.GONE);
			}	
			
			bRetourList.setEnabled(false);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.bouton_onglet_compte)
		{
			boutonCancelCompte = true;
			boutonCancelCarte = false;
			boutonCancelpack = false;
			boutonCancelService = false;
			bOngletCompte.setBackgroundResource(R.drawable.onglet_2);
			bOngletCarte.setBackgroundResource(R.drawable.onglet_1);
			bOngletPack.setBackgroundResource(R.drawable.onglet_1);
			bOngletService.setBackgroundResource(R.drawable.onglet_1);
			bRetourList.setEnabled(false);
			findViewById(R.id.listView_nos_produits_compte).setVisibility(View.VISIBLE);
			findViewById(R.id.listView_nos_produits_carte).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_pack).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_service).setVisibility(View.GONE);
			findViewById(R.id.cartalella).setVisibility(View.GONE);
			findViewById(R.id.cartaMoussafer).setVisibility(View.GONE);
			findViewById(R.id.cartaVisa_Master).setVisibility(View.GONE);
			findViewById(R.id.compte_Cheq_d_dc).setVisibility(View.GONE);
			findViewById(R.id.compte_tre).setVisibility(View.GONE);
			findViewById(R.id.compte_pro).setVisibility(View.GONE);
			findViewById(R.id.compte_jeune).setVisibility(View.GONE);
			findViewById(R.id.pack_gisr).setVisibility(View.GONE);
			findViewById(R.id.pack_jeune).setVisibility(View.GONE);
			
		}else if(v.getId()==R.id.bouton_onglet_cartes)
		{
			boutonCancelCarte = true;
			boutonCancelCompte = false;
			boutonCancelpack = false;
			boutonCancelService = false;
			bOngletCompte.setBackgroundResource(R.drawable.onglet_1);
			bOngletCarte.setBackgroundResource(R.drawable.onglet_2);
			bOngletPack.setBackgroundResource(R.drawable.onglet_1);
			bOngletService.setBackgroundResource(R.drawable.onglet_1);
			bRetourList.setEnabled(false);
			findViewById(R.id.listView_nos_produits_compte).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_carte).setVisibility(View.VISIBLE);
			findViewById(R.id.listView_nos_produits_pack).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_service).setVisibility(View.GONE);
			findViewById(R.id.cartalella).setVisibility(View.GONE);
			findViewById(R.id.cartaMoussafer).setVisibility(View.GONE);
			findViewById(R.id.cartaVisa_Master).setVisibility(View.GONE);
			findViewById(R.id.compte_Cheq_d_dc).setVisibility(View.GONE);
			findViewById(R.id.compte_tre).setVisibility(View.GONE);
			findViewById(R.id.compte_pro).setVisibility(View.GONE);
			findViewById(R.id.compte_jeune).setVisibility(View.GONE);
			findViewById(R.id.pack_gisr).setVisibility(View.GONE);
			findViewById(R.id.pack_jeune).setVisibility(View.GONE);
			
		}else if(v.getId()==R.id.bouton_onglet_packs)
		{
			boutonCancelpack = true;
			boutonCancelCarte = false;
			boutonCancelCompte = false;
			boutonCancelService = false;
			bOngletCompte.setBackgroundResource(R.drawable.onglet_1);
			bOngletCarte.setBackgroundResource(R.drawable.onglet_1);
			bOngletPack.setBackgroundResource(R.drawable.onglet_2);
			bOngletService.setBackgroundResource(R.drawable.onglet_1);
			bRetourList.setEnabled(false);
			findViewById(R.id.listView_nos_produits_compte).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_carte).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_pack).setVisibility(View.VISIBLE);
			findViewById(R.id.listView_nos_produits_service).setVisibility(View.GONE);
			findViewById(R.id.cartalella).setVisibility(View.GONE);
			findViewById(R.id.cartaMoussafer).setVisibility(View.GONE);
			findViewById(R.id.cartaVisa_Master).setVisibility(View.GONE);
			findViewById(R.id.compte_Cheq_d_dc).setVisibility(View.GONE);
			findViewById(R.id.compte_tre).setVisibility(View.GONE);
			findViewById(R.id.compte_pro).setVisibility(View.GONE);
			findViewById(R.id.compte_jeune).setVisibility(View.GONE);
			
		}else if(v.getId()==R.id.bouton_onglet_services)
		{
			boutonCancelService = true;
			boutonCancelCarte = false;
			boutonCancelpack = false;
			boutonCancelCompte = false;
			bOngletCompte.setBackgroundResource(R.drawable.onglet_1);
			bOngletCarte.setBackgroundResource(R.drawable.onglet_1);
			bOngletPack.setBackgroundResource(R.drawable.onglet_1);
			bOngletService.setBackgroundResource(R.drawable.onglet_2);
			bRetourList.setEnabled(false);
			findViewById(R.id.listView_nos_produits_compte).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_carte).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_pack).setVisibility(View.GONE);
			findViewById(R.id.listView_nos_produits_service).setVisibility(View.VISIBLE);
			findViewById(R.id.cartalella).setVisibility(View.GONE);
			findViewById(R.id.cartaMoussafer).setVisibility(View.GONE);
			findViewById(R.id.cartaVisa_Master).setVisibility(View.GONE);
			findViewById(R.id.compte_Cheq_d_dc).setVisibility(View.GONE);
			findViewById(R.id.compte_tre).setVisibility(View.GONE);
			findViewById(R.id.compte_pro).setVisibility(View.GONE);
			findViewById(R.id.compte_jeune).setVisibility(View.GONE);
			findViewById(R.id.pack_gisr).setVisibility(View.GONE);
			findViewById(R.id.pack_jeune).setVisibility(View.GONE);
			
		}
	}

}
