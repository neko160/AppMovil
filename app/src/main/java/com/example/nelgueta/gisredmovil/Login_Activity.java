package com.example.nelgueta.gisredmovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.popup.ArcGISDescriptionAdapter;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.io.UserCredentials;
import com.esri.core.map.FeatureResult;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;
import com.example.nelgueta.gisredmovil.activity_map;

public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        Button BtnLogin = (Button)findViewById(R.id.btnLogin);
        final EditText Usuario = (EditText)findViewById(R.id.usuario);
        final EditText Password = (EditText)findViewById(R.id.password);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Usuario.getText().toString().equals("admin") && Password.getText().toString().equals("admin"))
                {
                    Intent inten = new Intent(Login_Activity.this,menu_activity.class);
                    startActivity(inten);
                }
                else
                {
                    Toast.makeText(Login_Activity.this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
