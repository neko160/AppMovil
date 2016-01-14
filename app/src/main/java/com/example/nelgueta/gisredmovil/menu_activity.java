package com.example.nelgueta.gisredmovil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.nelgueta.gisredmovil.R;

/**
 * Created by nelgueta on 08/01/2016.
 */
public class menu_activity extends AppCompatActivity {

    private ListView maListViewPerso;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        maListViewPerso =(ListView)findViewById(R.id.listviewperso);

        ArrayList<HashMap<String,String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map;


        map = new HashMap<String, String>();
        map.put("titulo", "Estandard");
        map.put("description", "Acceso Usuarios standard");
        map.put("img", String.valueOf(R.drawable.standard));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titulo", "Admin");
        map.put("description", "Acceso Ususrio Administradorn");
        map.put("img", String.valueOf(R.drawable.logo_gisred));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titulo", "Ingreso Clientes");
        map.put("description", "Ingreso de Clientes Externos");
        map.put("img", String.valueOf(R.drawable.logo_gisred));
        listItem.add(map);

        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(),listItem,R.layout.affichageitem,
                new String[]{"img","titulo","description"}, new int[]{R.id.img,R.id.titulo,R.id.description});

        maListViewPerso.setAdapter(mSchedule);

        //Enfin on met un écouteur d'évènement sur notre listView
        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
                //on créer une boite de dialogue
             /*  AlertDialog.Builder adb = new AlertDialog.Builder(menu_activity.this);
                //on attribut un titre à notre boite de dialogue
                adb.setTitle("Sélection Item");
                //on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
                adb.setMessage("Votre choix : " + map.get("titre"));
                //on indique que l'on veut le bouton ok à notre boite de dialogue
                adb.setPositiveButton("Ok", null);
                //on affiche la boite de dialogue
                adb.show();
*/
                if(position == 0)
                {
                 //   Toast.makeText(menu_activity.this, "Posicion " + position, Toast.LENGTH_SHORT).show();
                    Intent inten = new Intent(menu_activity.this,estandard_activity.class);
                    startActivity(inten);
                }
                else if (position == 1)
                {
                    Toast.makeText(menu_activity.this, "Menu Admin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(menu_activity.this, "Menu Clientes", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}

