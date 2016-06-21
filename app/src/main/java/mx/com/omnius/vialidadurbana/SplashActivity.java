package mx.com.omnius.vialidadurbana;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = (ImageView) findViewById(R.id.imgLogo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animacion);
        splash.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                direccionaPantalla();
            }
        }, 3200);
    }

    public void direccionaPantalla(){
        startActivity(new Intent(this, MainDrawerActivity.class));
        finish();
    }
}
