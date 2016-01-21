package com.example.nelgueta.gisredmovil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.bing.BingMapsLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Polygon;


import com.esri.core.io.EsriSecurityException;
import com.esri.core.io.UserCredentials;

/**
 * Created by nelgueta on 11/01/2016.
 */
public class estandard_activity extends AppCompatActivity {


    MapView myMapView = null;

    //Set bing Maps
    String BingKey = "Asrn2IMtRwnOdIRPf-7q30XVUrZuOK7K2tzhCACMg7QZbJ4EPsOcLk6mE9-sNvUe";
    final BingMapsLayer mAerialBaseMaps = new BingMapsLayer(BingKey, BingMapsLayer.MapStyle.AERIAL);
    final BingMapsLayer mAerialWLabelBaseMaps = new BingMapsLayer(BingKey, BingMapsLayer.MapStyle.AERIAL_WITH_LABELS);
    final BingMapsLayer mRoadBaseMaps = new BingMapsLayer(BingKey, BingMapsLayer.MapStyle.ROAD);

    //set Extent inicial
    Polygon mCurrentMapExtent = null;

    //Declara String de Layer
    String featureServiceURL;
    String LayerTramosURL;
    String LayerBaseChqURL;

    //Declara Tipo de Layer
    ArcGISFeatureLayer LayerAlimentadores;
    ArcGISDynamicMapServiceLayer LayerTramos;
    ArcGISDynamicMapServiceLayer LyerMapaChq;

  //Declara Menu Bar
    android.app.ActionBar mActionBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard);

        myMapView = (MapView) findViewById(R.id.map);
        //Set color de fondo mapa base
        myMapView.setMapBackground(0xffffff, 0xffffff, 10, 10);
        //Set logo Esri
        myMapView.setEsriLogoVisible(true);

        myMapView.enableWrapAround(true);

        //Obtiene menubar desde menu/memu.xml
        mActionBar = getActionBar();

        //Declara Credenciales
        UserCredentials Credenciales = new UserCredentials();

        //Get Credenciales String
        final String user = this.getResources().getString(R.string.user);
        final String pwd = this.getResources().getString(R.string.password);

        //Set Credenciales
        Credenciales.setUserAccount(user, pwd);

        //Get Urls String Urls service
        featureServiceURL = this.getResources().getString(R.string.url_service_1);
        LayerTramosURL = this.getResources().getString(R.string.url_service_tramos);
        LayerBaseChqURL = this.getResources().getString(R.string.LayerBaseChqURL);

        //Crea Layer para carga
        LayerAlimentadores = new ArcGISFeatureLayer(featureServiceURL, ArcGISFeatureLayer.MODE.SNAPSHOT,Credenciales);
        LayerAlimentadores.setVisible(true);

        LayerTramos = new ArcGISDynamicMapServiceLayer(LayerTramosURL,null,Credenciales);
        LyerMapaChq = new ArcGISDynamicMapServiceLayer(LayerBaseChqURL,null, Credenciales);

        //Agrega layers al mapa
        myMapView.addLayer(mRoadBaseMaps, 0);
        myMapView.addLayer(LayerAlimentadores, 1);
        myMapView.addLayer(LayerTramos, 2);

        //Cabios en el mapa
        myMapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (STATUS.LAYER_LOADED == status) {
                    myMapView.setExtent(mCurrentMapExtent);

                }

                if (status == STATUS.LAYER_LOADING_FAILED) {
                    // Check if a layer is failed to be loaded due to security
                    if ((status.getError()) instanceof EsriSecurityException) {
                        EsriSecurityException securityEx = (EsriSecurityException) status
                                .getError();
                        if (securityEx.getCode() == EsriSecurityException.AUTHENTICATION_FAILED)
                            Toast.makeText(myMapView.getContext(),
                                    "Authentication Failed! Resubmit!",
                                    Toast.LENGTH_SHORT).show();
                        else if (securityEx.getCode() == EsriSecurityException.TOKEN_INVALID)
                            Toast.makeText(myMapView.getContext(),
                                    "Invalid Token! Resubmit!",
                                    Toast.LENGTH_SHORT).show();
                        else if (securityEx.getCode() == EsriSecurityException.TOKEN_SERVICE_NOT_FOUND)
                            Toast.makeText(myMapView.getContext(),
                                    "Token Service Not Found! Resubmit!",
                                    Toast.LENGTH_SHORT).show();
                        else if (securityEx.getCode() == EsriSecurityException.UNTRUSTED_SERVER_CERTIFICATE)
                            Toast.makeText(myMapView.getContext(),
                                    "Untrusted Host! Resubmit!",
                                    Toast.LENGTH_SHORT).show();

                        if (o instanceof ArcGISFeatureLayer) {
                            // Set user credential through username and password
                            UserCredentials creds = new UserCredentials();
                            creds.setUserAccount(user, pwd);
                            LayerAlimentadores.reinitializeLayer(creds);
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle user pressing the action bar menu items.
        boolean retVal = false;

        // Get the current map directly from this activity.
        MapView currentMap = (MapView) this.findViewById(R.id.map);

        // Based on the menu item selected, switch the map fragment.
        String mapUrl = null;

        switch (item.getItemId()) {
            case R.id.Road:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                myMapView.removeLayer(0);
                myMapView.addLayer(mRoadBaseMaps,0);
                return true;
            case R.id.Aerial:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                myMapView.removeLayer(0);
                myMapView.addLayer(mAerialBaseMaps, 0);
                return true;
            case R.id.AerialWithLabel:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                myMapView.removeLayer(0);
                myMapView.addLayer(mAerialWLabelBaseMaps, 0);
                return true;
            case R.id.Chilquinta:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                myMapView.removeLayer(0);
                myMapView.addLayer(LyerMapaChq, 0);

                return true;

            default:
                retVal = super.onOptionsItemSelected(item);
                break;
        }

        return retVal;
    }


}