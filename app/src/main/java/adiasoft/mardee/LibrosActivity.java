package adiasoft.mardee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import adiasoft.mardee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LibrosActivity extends AppCompatActivity {

    ArrayList<String> id;
    ArrayList<String> img;
    ArrayList<String> libro;
    ArrayList<String> autor;
    ArrayList<String> estado;
    ListView lv_libros;

    RequestQueue requestQueue;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ed1b24"));

        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("MARDEE - MATTOS");

        lv_libros = findViewById(R.id.lv_libros);
        //get_libros();

        if(getIntent().getStringExtra("lock").equals("0")){
            get_libros();
        }else{
            verificar();
        }
    }
    public void  verificar(){

        url = "https://adiasis.com//libros_veloz/verificar_pago.php?id_usuario=" + LoginActivity.getIdSession(LibrosActivity.this);
        System.out.println("EL VERIFICA " + url);
        requestQueue = Volley.newRequestQueue(LibrosActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String result = null;
                        try {
                            result = response.getString("Result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(result.equals("OK")){
                            get_libros();
                        }else{
                            Intent intent = new Intent(LibrosActivity.this, LockActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void get_libros(){
        id = new ArrayList<>();
        img = new ArrayList<>();
        libro = new ArrayList<>();
        autor = new ArrayList<>();
        estado = new ArrayList<>();

        lv_libros.setAdapter(null);
        url = "https://adiasis.com//libros_veloz/get_libros.php?id_genero=" + getIntent().getStringExtra("genero")+"&id_usuario=" + LoginActivity.getIdSession(LibrosActivity.this);
        System.out.println("ENLACE " + url);
        requestQueue = Volley.newRequestQueue(LibrosActivity.this);
        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String lock = "";
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject colorObj = response.getJSONObject(i);
                        id.add(colorObj.getString("id"));
                        if(colorObj.getString("img") == null || colorObj.getString("img").equals("") || colorObj.getString("img").equals("null")){
                            img.add("default.jpg");
                        }else{
                            img.add(colorObj.getString("img"));
                        }
                        libro.add(colorObj.getString("titulo"));
                        autor.add(colorObj.getString("autor"));
                        if(colorObj.getString("estado").equals("1") && colorObj.getString("lock").equals("1")){
                            estado.add("0");
                        }else{
                            estado.add(colorObj.getString("estado"));
                        }
                    }
                    lv_libros.setAdapter(new LibrosAdapter(LibrosActivity.this, id, img, libro, autor, getIntent().getStringExtra("id"), estado));
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
