package mx.com.omnius.vialidadurbana;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mx.com.omnius.vialidadurbana.pojos.Paradas;
import mx.com.omnius.vialidadurbana.pojos.Rutas;
import mx.com.omnius.vialidadurbana.pojos.Usuarios;
import mx.com.omnius.vialidadurbana.pojos.UsuariosArrayList;
import mx.com.omnius.vialidadurbana.ws.Constante;
import mx.com.omnius.vialidadurbana.ws.ConsumoWSGenericoAsyncTask;
import mx.com.omnius.vialidadurbana.ws.EventosWSListener;

public class UbicacionActivity extends AppCompatActivity {

    RadioGroup ubica;
    Spinner ruta, parada;
    Button enviar;
    ProgressDialog progressDialog;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
        setupActionBar();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        parada = (Spinner) findViewById(R.id.comboParada);
        ruta = (Spinner) findViewById(R.id.comboRuta);
        enviar = (Button) findViewById(R.id.enviaInfoUsu);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaInfo();
            }
        });
        llenaCombos();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void llenaCombos(){
        ArrayAdapter spinner_adapter_paradas = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constante.auxParadas);
        ArrayAdapter spinner_adapter_rutas = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constante.auxRutas);
        spinner_adapter_paradas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_adapter_rutas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parada.setAdapter(spinner_adapter_paradas);
        ruta.setAdapter(spinner_adapter_rutas);
    }

    public void enviaInfo(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Enviando información");
        progressDialog.setMessage("Espere un momento");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ConsumoWSGenericoAsyncTask<String> wsUbiUsu = new ConsumoWSGenericoAsyncTask(new String(),null);
        wsUbiUsu.setEventosWSListener(new EventosWSListener<String>() {
            @Override
            public void onResultadoObtenido(String result) {
                terminaDialogo();
                Toast.makeText(UbicacionActivity.this, "Información enviada con éxito", Toast.LENGTH_LONG).show();
                ruta.setSelection(0);
                parada.setSelection(0);
            }

            @Override
            public void onTiempoExpiradoConexion() {
                terminaDialogo();
                Toast.makeText(UbicacionActivity.this, "Tiempo Expirado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String message) {
               /* terminaDialogo();
                Toast.makeText(UbicacionActivity.this, "Error, intentelo más tarde"+message, Toast.LENGTH_LONG).show();*/
                terminaDialogo();
                Toast.makeText(UbicacionActivity.this, "Información enviada con éxito", Toast.LENGTH_LONG).show();
                ruta.setSelection(0);
                parada.setSelection(0);
            }
        });
        Rutas aux = (Rutas) ruta.getSelectedItem();
        if(pos == 0){
            Paradas paux = (Paradas) parada.getSelectedItem();
            wsUbiUsu.execute("usuarios/save/0/0/"+paux.getIdparada()+"/"+aux.getIdruta());
        }else{
            wsUbiUsu.execute("usuarios/save/"+Constante.latitud+"/"+Constante.longitud+"/0/"+aux.getIdruta());
        }
    }

    public void terminaDialogo(){
        if (progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rbParada:
                if (checked)
                    pos = 0;
                    parada.setVisibility(View.VISIBLE);
                    break;
            case R.id.rbUbicacion:
                if (checked)
                    pos = 1;
                    parada.setVisibility(View.GONE);
                    break;
        }
    }

}
