package vn.coffee.eatclean;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class SplashActivity extends AppCompatActivity {
    private final long SPLASH_TIME = 5000; // s
    ProgressBar splashProgress;
    Animation topAnim, bottomAnim;
    ImageView LoGo;
    TextView MaDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        // Hooks
        LoGo = findViewById(R.id.logo);
        MaDe = findViewById(R.id.made);

        LoGo.setAnimation(topAnim);
        MaDe.setAnimation(bottomAnim);

        splashProgress = findViewById(R.id.splashProgress);
        playProgress();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);

    }

    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(SPLASH_TIME)
                .start();
    }
}