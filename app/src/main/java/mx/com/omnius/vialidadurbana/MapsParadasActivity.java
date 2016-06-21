package mx.com.omnius.vialidadurbana;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import mx.com.omnius.vialidadurbana.pojos.Paradas;
import mx.com.omnius.vialidadurbana.pojos.ParadasArrayList;
import mx.com.omnius.vialidadurbana.ws.Constante;

public class MapsParadasActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_paradas);
        setupActionBar();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(Constante.latitud, Constante.longitud);
        //mMap.addMarker(new MarkerOptions().position(pos).title("Tu posición actual"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,14));
        cargaParadas();
    }

    public void cargaParadas(){
        ArrayList<Paradas> paradaAux = Constante.auxParadas;
        if (paradaAux != null){
            for (int i=0; i<paradaAux.size(); i++){
                LatLng ltaux = new LatLng(paradaAux.get(i).getLatitud(), paradaAux.get(i).getLongitud());
                mMap.addMarker(new MarkerOptions().position(ltaux).title(paradaAux.get(i).getNombre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop)));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
