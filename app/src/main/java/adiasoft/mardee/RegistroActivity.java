package adiasoft.mardee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import adiasoft.mardee.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity {

    EditText ed_nombres, ed_correo, ed_usuario, ed_pass, ed_celular;
    Button btn_registrarme, btn_cancelar;
    RequestQueue requestQueue;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ed1b24"));

        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("MARDEE - MATTOS");

        ed_nombres = findViewById(R.id.ed_nombres);
        ed_correo = findViewById(R.id.ed_correo);
        ed_usuario = findViewById(R.id.ed_usuario);
        ed_pass = findViewById(R.id.ed_pass);
        ed_celular = findViewById(R.id.ed_celular);

        btn_registrarme = (Button) findViewById(R.id.btn_registrarme);
        btn_cancelar = (Button) findViewById(R.id.btn_cancelar);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = ed_usuario.getText().toString();
                String pass = ed_pass.getText().toString();
                String nombres = ed_nombres.getText().toString();
                String correo = ed_correo.getText().toString();
                String celular = ed_celular.getText().toString();

                url = "https://adiasis.com//libros_veloz/registrar.php?usuario=" + usuario + "&pass=" + pass + "&nombres=" + nombres + "&correo=" + correo + "&celular=" + celular;
                requestQueue = Volley.newRequestQueue(RegistroActivity.this);
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
                                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    try {
                                        Toast.makeText(RegistroActivity.this, "" + response.getString("Message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
        });
    }
}
