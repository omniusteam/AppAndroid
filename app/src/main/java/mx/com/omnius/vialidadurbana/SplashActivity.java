package mx.com.omnius.vialidadurbana;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import mx.com.omnius.vialidadurbana.pojos.Paradas;
import mx.com.omnius.vialidadurbana.pojos.ParadasArrayList;
import mx.com.omnius.vialidadurbana.pojos.Rutas;
import mx.com.omnius.vialidadurbana.pojos.RutasArrayList;
import mx.com.omnius.vialidadurbana.ws.Constante;
import mx.com.omnius.vialidadurbana.ws.ConsumoWSGenericoAsyncTask;
import mx.com.omnius.vialidadurbana.ws.EventosWSListener;

public class SplashActivity extends AppCompatActivity {

    ImageView splash;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = (ImageView) findViewById(R.id.imgLogo);
        mProgressView = findViewById(R.id.splash_progress);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animacion);
        splash.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cargaParadas();
            }
        }, 3200);
    }

    public void cargaParadas(){
        showProgress(true);
        ConsumoWSGenericoAsyncTask<ParadasArrayList> wsParadas = new ConsumoWSGenericoAsyncTask<>(new ParadasArrayList(), null);
        wsParadas.setEventosWSListener(new EventosWSListener<ParadasArrayList>() {
            @Override
            public void onResultadoObtenido(ParadasArrayList result) {
                guardaParadas(result);
                cargaRutas();
            }

            @Override
            public void onTiempoExpiradoConexion() {
                showProgress(false);
                Toast.makeText(SplashActivity.this, "Tiempo expirado, Favor de intertarlo más tarde", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(String message) {
                showProgress(false);
                Toast.makeText(SplashActivity.this, "Error, Favor de intertarlo más tarde", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        wsParadas.execute("paradas/");

    }

    public void cargaRutas(){
        ConsumoWSGenericoAsyncTask<RutasArrayList> wsParadas = new ConsumoWSGenericoAsyncTask<>(new RutasArrayList(), null);
        wsParadas.setEventosWSListener(new EventosWSListener<RutasArrayList>() {
            @Override
            public void onResultadoObtenido(RutasArrayList result) {
                guardaRutas(result);
                showProgress(false);
                direccionaPantalla();
            }

            @Override
            public void onTiempoExpiradoConexion() {
                showProgress(false);
                Toast.makeText(SplashActivity.this, "Tiempo expirado, Favor de intertarlo más tarde", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(String message) {
                showProgress(false);
                Toast.makeText(SplashActivity.this, "Error, Favor de intertarlo más tarde", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        wsParadas.execute("rutas/");
    }


    public void direccionaPantalla(){
        startActivity(new Intent(this, MainDrawerActivity.class));
        finish();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

           // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
           /* mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });*/

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
           // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void guardaParadas(ParadasArrayList resultado){
        if (resultado!= null && resultado.size()>0){
            for (int i=0; i<resultado.size(); i++){
                Paradas aux = resultado.get(i);
                SharedPreferences sp = getSharedPreferences("Paradas", MODE_PRIVATE);
                SharedPreferences.Editor spe = sp.edit();
                spe.putString(""+aux.getIdparada(), aux.getNombre());
                spe.commit();
            }
            Constante.auxParadas = resultado;
        }
    }


    public void guardaRutas(RutasArrayList resultado){
        if (resultado!= null && resultado.size()>0){
            for (int i=0; i<resultado.size(); i++){
                Rutas aux = resultado.get(i);
                SharedPreferences sp = getSharedPreferences("Rutas", MODE_PRIVATE);
                SharedPreferences.Editor spe = sp.edit();
                spe.putString(""+aux.getIdruta(), aux.getNombre());
                spe.commit();
            }
            Constante.auxRutas = resultado;
        }
    }
}
