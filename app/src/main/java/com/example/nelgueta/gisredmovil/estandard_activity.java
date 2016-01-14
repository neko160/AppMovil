package com.example.nelgueta.gisredmovil;

import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.esri.android.map.Layer;
import com.esri.android.map.MapOptions;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.bing.BingMapsLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Polygon;
import com.esri.core.io.EsriSecurityException;
import com.esri.core.io.UserCredentials;
import com.esri.core.portal.WebMapLayer;

/**
 * Created by nelgueta on 11/01/2016.
 */
public class estandard_activity extends AppCompatActivity {

    MapView myMapView = null;

    String BingKey = "Asrn2IMtRwnOdIRPf-7q30XVUrZuOK7K2tzhCACMg7QZbJ4EPsOcLk6mE9-sNvUe";
    final BingMapsLayer mAerialBaseMaps = new BingMapsLayer(BingKey, BingMapsLayer.MapStyle.AERIAL);
    final BingMapsLayer mAerialWLabelBaseMaps = new BingMapsLayer(BingKey, BingMapsLayer.MapStyle.AERIAL_WITH_LABELS);
    final BingMapsLayer mRoadBaseMaps = new BingMapsLayer(BingKey, BingMapsLayer.MapStyle.ROAD);




    Polygon mCurrentMapExtent = null;

    String featureServiceURL;
    ArcGISFeatureLayer LayerAlimentadores;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard);

        myMapView = (MapView) findViewById(R.id.map);

        myMapView.addLayer(mRoadBaseMaps);

        myMapView.setEsriLogoVisible(true);
        myMapView.enableWrapAround(true);

        UserCredentials Credenciales = new UserCredentials();



        final String user = this.getResources().getString(R.string.user);
        final String pwd = this.getResources().getString(R.string.password);

        Credenciales.setUserAccount(user, pwd);

        featureServiceURL = this.getResources().getString(R.string.url_service_1);

        LayerAlimentadores = new ArcGISFeatureLayer(featureServiceURL, ArcGISFeatureLayer.MODE.SNAPSHOT,Credenciales);
        LayerAlimentadores.setVisible(true);


        myMapView.addLayer(LayerAlimentadores);

        

        Toast.makeText(estandard_activity.this, LayerAlimentadores.getDrawOrder(1), Toast.LENGTH_LONG).show();


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
                            creds.setUserAccount(user,pwd);
                            LayerAlimentadores.reinitializeLayer(creds);
                        }
                    }
                }
            }



        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menus, menu);

        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Save the current extent of the map before changing the map.
        mCurrentMapExtent = myMapView.getExtent();

        // Handle menu item selection.
        switch (item.getItemId()) {
            case R.id.Road:
          //      myMapView.removeLayer(mAerialWLabelBaseMaps);
          //      myMapView.removeLayer(mAerialBaseMaps);
                myMapView.addLayer(mRoadBaseMaps);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.Aerial:
          //      myMapView.removeLayer(mAerialWLabelBaseMaps);
          //      myMapView.removeLayer(mRoadBaseMaps);
                myMapView.addLayer(mAerialBaseMaps);

                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.AerialWithLabel:
           //     myMapView.removeLayer(mAerialBaseMaps);
           //     myMapView.removeLayer(mRoadBaseMaps);
                myMapView.addLayer(mAerialWLabelBaseMaps);

                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);     ;
                return true;
            /*case R.id.Chilquinta:
                myMapView.addLayer(mAerialBaseMaps);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}