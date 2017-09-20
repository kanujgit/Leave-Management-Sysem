package sathya.com.leavemanagemantsysem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        Thread  myThread=new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(3000);
                    Intent i=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        };
        myThread.start();
    }
    }
