package adiasoft.mardee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import adiasoft.mardee.R;

public class MainActivity extends AppCompatActivity {

    Button btn_novela, btn_poesia, btn_cuento, btn_opinion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ed1b24"));

        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("MARDEE - MATTOS");

        btn_novela = findViewById(R.id.btn_novela);
        btn_poesia = findViewById(R.id.btn_poesia);
        btn_cuento = findViewById(R.id.btn_cuento);
        btn_opinion = findViewById(R.id.btn_opinion);

        btn_novela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LibrosActivity.class);
                intent.putExtra("genero", "1");
                intent.putExtra("lock", "1");
                intent.putExtra("id", LoginActivity.getIdSession(MainActivity.this));
                startActivity(intent);
            }
        });

        btn_poesia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LibrosActivity.class);
                intent.putExtra("genero", "2");
                intent.putExtra("lock", "1");
                intent.putExtra("id", LoginActivity.getIdSession(MainActivity.this));
                startActivity(intent);
            }
        });

        btn_cuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LibrosActivity.class);
                intent.putExtra("genero", "3");
                intent.putExtra("lock", "0");
                intent.putExtra("id", LoginActivity.getIdSession(MainActivity.this));
                startActivity(intent);
            }
        });

        btn_opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LibrosActivity.class);
                intent.putExtra("genero", "4");
                intent.putExtra("lock", "1");
                //intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("id", LoginActivity.getIdSession(MainActivity.this));
                startActivity(intent);
            }
        });
    }
}
