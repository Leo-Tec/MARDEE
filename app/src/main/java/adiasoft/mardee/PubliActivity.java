package adiasoft.mardee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import adiasoft.mardee.R;

public class PubliActivity extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publi);

        /*ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ed1b24"));

        actionBar.setBackgroundDrawable(colorDrawable);*/

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(PubliActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}
