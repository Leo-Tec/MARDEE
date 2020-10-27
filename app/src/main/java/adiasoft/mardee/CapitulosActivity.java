package adiasoft.mardee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import adiasoft.mardee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CapitulosActivity extends AppCompatActivity {

    ArrayList<String> id;
    ArrayList<String> capitulo;
    ArrayList<String> n_hojas;
    ListView lv_capitulos;
    TextView txt_titulo;

    RequestQueue requestQueue;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitulos);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ed1b24"));

        txt_titulo = findViewById(R.id.txt_titulo);

        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("MARDEE - MATTOS");

        lv_capitulos = (ListView) findViewById(R.id.lv_capitulos);
        get_capitulos();
    }

    public void get_capitulos(){
        id = new ArrayList<String>();
        capitulo = new ArrayList<String>();
        n_hojas = new ArrayList<String>();

        lv_capitulos.setAdapter(null);

        url = "https://adiasis.com//libros_veloz/get_capitulos.php?id_libro=" + getIntent().getStringExtra("id_libro");
        System.out.println("URL " + url);
        requestQueue = Volley.newRequestQueue(CapitulosActivity.this);
        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject colorObj = response.getJSONObject(i);
                        txt_titulo.setText("Capitulos: "+colorObj.getString("titulo"));
                        id.add(colorObj.getString("id"));
                        capitulo.add(colorObj.getString("capitulo"));
                        //n_hojas.add(colorObj.getString("pagina"));
                        n_hojas.add("");
                    }
                    lv_capitulos.setAdapter(new AdapterCapitulos(CapitulosActivity.this, id, capitulo, n_hojas, getIntent().getStringExtra("id_libro"), getIntent().getStringExtra("id")));
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        requestQueue.add(arrayreq);



    }
}
