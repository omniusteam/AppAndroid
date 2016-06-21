package mx.com.omnius.vialidadurbana;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import mx.com.omnius.vialidadurbana.pojos.Usuarios;
import mx.com.omnius.vialidadurbana.pojos.UsuariosArrayList;
import mx.com.omnius.vialidadurbana.ws.ConsumoWSGenericoAsyncTask;
import mx.com.omnius.vialidadurbana.ws.EventosWSListener;

public class UbicacionActivity extends AppCompatActivity {

    TextView prueba;

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
        prueba = (TextView) findViewById(R.id.textViewPrueba);

       pruebaConsumo();
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

    public void pruebaConsumo(){
        ConsumoWSGenericoAsyncTask<UsuariosArrayList> wsUsu = new ConsumoWSGenericoAsyncTask<>(new UsuariosArrayList(), null);
        wsUsu.setEventosWSListener(new EventosWSListener<UsuariosArrayList>() {
            @Override
            public void onResultadoObtenido(UsuariosArrayList result) {
                prueba.setText(""+result.get(0).getIdusuario());
            }

            @Override
            public void onTiempoExpiradoConexion() {
                Toast.makeText(UbicacionActivity.this, "Expirado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(UbicacionActivity.this, "Error "+message, Toast.LENGTH_LONG).show();
            }
        });
        wsUsu.execute("usuarios/");
    }

}
