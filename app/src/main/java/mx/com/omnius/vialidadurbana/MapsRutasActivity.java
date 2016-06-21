package mx.com.omnius.vialidadurbana;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.omnius.vialidadurbana.mapas.DirectionsJSONParser;
import mx.com.omnius.vialidadurbana.pojos.Paradas;
import mx.com.omnius.vialidadurbana.pojos.Rutas;
import mx.com.omnius.vialidadurbana.ws.Constante;

public class MapsRutasActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ProgressDialog progressDialog;
    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_rutas);
        setupActionBar();
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pos = new LatLng(Constante.latitud, Constante.longitud);
        //mMap.addMarker(new MarkerOptions().position(pos).title("Tu posición actual"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,14));
        cargaRutas();
        //cargaParadas();
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

    public void cargaRutas(){
        //ArrayList<Rutas> rutasAux = Constante.auxRutas;
        //if (rutasAux != null){
        for (int i=0; i<1; i++){
            cont ++;
            switch (i){
                case 0:
                    if(Constante.auxParadaUno != null){
                        comoLlegar(1, new LatLng(Constante.auxParadaUno.get(0).getLatitud(), Constante.auxParadaUno.get(0).getLongitud()), new LatLng(Constante.auxParadaUno.get(Constante.auxParadaUno.size()-1).getLatitud(), Constante.auxParadaUno.get(Constante.auxParadaUno.size()-1).getLongitud()), 0);
                    }
                    break;
                case 1:
                    if(Constante.auxParadaDos != null){
                        comoLlegar(1, new LatLng(Constante.auxParadaDos.get(0).getLatitud(), Constante.auxParadaDos.get(0).getLongitud()), new LatLng(Constante.auxParadaDos.get(Constante.auxParadaDos.size()-1).getLatitud(), Constante.auxParadaDos.get(Constante.auxParadaDos.size()-1).getLongitud()), 1);
                    }
                    break;
                case 2:
                    if(Constante.auxParadaTres != null){
                        comoLlegar(1, new LatLng(Constante.auxParadaTres.get(0).getLatitud(), Constante.auxParadaTres.get(0).getLongitud()), new LatLng(Constante.auxParadaTres.get(Constante.auxParadaTres.size()-1).getLatitud(), Constante.auxParadaTres.get(Constante.auxParadaTres.size()-1).getLongitud()), 2);
                    }
                    break;
            }
               // LatLng ltaux = new LatLng(rutasAux.get(i).getLatitud(), rutasAux.get(i).getLongitud());
               // mMap.addMarker(new MarkerOptions().position(ltaux).title(rutasAux.get(i).getNombre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop)));
        }
        //}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /// Trazo de rutas con WayPoints

    //Metodos Para dibujar ruta en el mapa


    public void comoLlegar(int tiporuta, LatLng origen, LatLng destino, int pos){
        // Tipo ruta 1=Vehiculo, 2=caminando
        String url;
        url = getDirectionsUrl(origen, destino, 1, pos);
        DownloadTask downloadtask = new DownloadTask();
        downloadtask.execute(url);
    }


    private String getDirectionsUrl(LatLng origin,LatLng dest,int tiporut, int posicion){

        // Origin of travel mode
        String mode="";


        mode = "mode=driving";
        //imagenLlegada.setBackground(null);



        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";



        // Output format
        String output = "json";
        String waypoints = "";

        switch (posicion){
            case 0:
                for (int i = 1; i < (Constante.auxParadaUno.size()-1); i++){
                    waypoints += (i == Constante.auxParadaUno.size()-2) ? Constante.auxParadaUno.get(i).getLatitud() + "," + Constante.auxParadaUno.get(i).getLongitud() : Constante.auxParadaUno.get(i).getLatitud() + "," + Constante.auxParadaUno.get(i).getLongitud()+"|";
                }
                break;
            case 1:
                for (int i = 1; i < (Constante.auxParadaDos.size() -1); i++){
                    waypoints += (i == Constante.auxParadaDos.size()-2) ? Constante.auxParadaDos.get(i).getLatitud() + "," + Constante.auxParadaDos.get(i).getLongitud() : Constante.auxParadaDos.get(i).getLatitud() + "," + Constante.auxParadaDos.get(i).getLongitud()+"|";
                }
                break;
            case 2:
                for (int i = 1; i < (Constante.auxParadaTres.size() -1); i++){
                    waypoints += (i == Constante.auxParadaTres.size()-2) ? Constante.auxParadaTres.get(i).getLatitud() + "," + Constante.auxParadaTres.get(i).getLongitud() : Constante.auxParadaTres.get(i).getLatitud() + "," + Constante.auxParadaTres.get(i).getLongitud()+"|";
                }
                break;
        }

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+waypoints+"&"+sensor+"&"+mode;
        //String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        System.out.println("Imprime URL de servicio  "+ url);
        return url;
    }


    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){

        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(MapsRutasActivity.this);
            progressDialog.setMessage("Trazando Ruta...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service

            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){

            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String tiempo = tiempoLlegada(result);
            //tiempoLlegada.setText("Tiempo estimado de llegada "+tiempo);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    public String tiempoLlegada(String data){
        JSONObject jObject, jDuracion=null;
        JSONArray jRoutes;
        JSONArray jLegs=null;
        try {
            jObject = new JSONObject(data);
            jRoutes = jObject.getJSONArray("routes");
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
            }

            for(int i=0;i<jLegs.length();i++){
                jDuracion = jLegs.getJSONObject(i);
            }

            JSONObject obj = jDuracion.getJSONObject("duration");
            String t = obj.getString("text");
            System.out.println(t);
            return t;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "";
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);


                }

                switch (cont){
                    case 1:
                        Polyline line = mMap.addPolyline(new PolylineOptions()
                                .addAll(points)
                                //.add(new LatLng(20.9893276, -86.8316037), new LatLng(21.0254455, -86.8588391))
                                .width(5)
                                .color(Color.RED));
                        break;
                    case 2:
                        Polyline lineUno = mMap.addPolyline(new PolylineOptions()
                                .addAll(points)
                                //.add(new LatLng(20.9893276, -86.8316037), new LatLng(21.0254455, -86.8588391))
                                .width(5)
                                .color(Color.BLUE));
                        break;
                    default:
                        Polyline lineDos = mMap.addPolyline(new PolylineOptions()
                                .addAll(points)
                                //.add(new LatLng(20.9893276, -86.8316037), new LatLng(21.0254455, -86.8588391))
                                .width(5)
                                .color(Color.GREEN));
                        break;
                }

                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .addAll(points)
                        //.add(new LatLng(20.9893276, -86.8316037), new LatLng(21.0254455, -86.8588391))
                        .width(5)
                        .color(Color.RED));


                // Adding all the points in the route to LineOptions
               /* lineOptions.addAll(points);
                lineOptions.width(2);
                if(cont==1){
                    lineOptions.color(Color.RED);
                }else{if (cont ==2){
                    lineOptions.color(Color.BLUE);
                }else{
                    lineOptions.color(Color.GREEN);
                }

                }*/

            }

            // Drawing polyline in the Google Map for the i-th route
           // mMap.addPolyline(lineOptions);
        }
    }

}
