package ziphome.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread{
        public void run(){
            try {
                sleep(3000 );
            } catch (InterruptedException e){
                e.printStackTrace();
            };

            Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
            startActivity(intent);
            SplashScreenActivity.this.finish();
        }
    }
}
