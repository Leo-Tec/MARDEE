package adiasoft.mardee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class LoginActivity extends AppCompatActivity {

    Button btn_cancelar_sesion, btn_iniciar_sesion;
    EditText ed_usuario;
    EditText ed_pass;
    RequestQueue requestQueue;
    String url;
    String usuario;

    String pass;

    RadioButton RBButton;
    boolean isActivatedRadioButton;
    private static final String STRING_PREFERENCES = "com.example.gas_aplication";
    private static  final String PREFERENCE_ESTATE_BUTTON_SESION = "estado.button.sesion";
    private static  final String PREFERENCE_ESTATE_ID_SESION = "estado.id.sesion";
    private static  final String PREFERENCE_LEVEL_SESSION = "estado.level.sesion";
    private static  final String PREFERENCE_ESTATE_NOMBRE_SESION = "estado.nombre.sesion";

    int tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getEstadoButton(LoginActivity.this)){
            String lvl = getLevelSesion(LoginActivity.this);
            if(lvl.equals("1")){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                intent.putExtra("id", getIdSession(LoginActivity.this));
                intent.putExtra("nombres", getNombreSession(LoginActivity.this));

                startActivity(intent);
                finish();
            }

        }

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ed1b24"));

        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("MARDEE - MATTOS");

        btn_cancelar_sesion = (Button)findViewById(R.id.btn_registro);
        btn_iniciar_sesion = (Button)findViewById(R.id.btn_iniciar_sesion);

        RBButton = (RadioButton)findViewById(R.id.RBButton);

        isActivatedRadioButton = RBButton.isChecked();

        RBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isActivatedRadioButton){
                    RBButton.setChecked(false);
                }
                isActivatedRadioButton = RBButton.isChecked();
            }
        });

        btn_cancelar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        btn_iniciar_sesion = (Button)findViewById(R.id.btn_iniciar_sesion);
        ed_usuario = (EditText)findViewById(R.id.ed_usuario);
        ed_pass = (EditText)findViewById(R.id.ed_pass);

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                usuario = ed_usuario.getText().toString();
                pass = ed_pass.getText().toString();

                url = "https://adiasis.com//libros_veloz/iniciar_sesion.php?usuario=" + usuario + "&pass=" + pass;
                requestQueue = Volley.newRequestQueue(LoginActivity.this);
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
                                    try {
                                        JSONObject values = response.getJSONObject("Values");
                                        try {
                                            tipo = values.getInt("estado");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        guardarEstadoButton();

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("id", values.getString("id"));
                                        intent.putExtra("nombres", values.getString("nombres"));

                                        guardarIdSession(Integer.parseInt(values.getString("id")));
                                        guardarNombreSession(values.getString("nombres"));
                                        setLevelSesion(LoginActivity.this, "1");

                                        startActivity(intent);
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //Toast.makeText(LoginActivity.this, "Registrado Correctamente", Toast.LENGTH_SHORT).show();
                                }else{
                                    /*ly_hijo1.setVisibility(View.VISIBLE);
                                    ly_hijo2.setVisibility(View.GONE);*/
                                    try {
                                        Toast.makeText(LoginActivity.this, "" + response.getString("Message"), Toast.LENGTH_SHORT).show();
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
    public static void setLevelSesion(Context c, String level){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putString(PREFERENCE_LEVEL_SESSION, level).apply();
    }
    public static String getLevelSesion(Context c){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getString(PREFERENCE_LEVEL_SESSION, "");
    }
    public static void chageEstadoButton(Context c, boolean b){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTATE_BUTTON_SESION, b).apply();
    }
    public static void chageEstadoId(Context c, int id){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putInt(PREFERENCE_ESTATE_ID_SESION, id).apply();
    }
    public static void chageEstadoNombre(Context c, String nombre){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putString(PREFERENCE_ESTATE_NOMBRE_SESION, nombre).apply();
    }

    public void guardarEstadoButton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTATE_BUTTON_SESION, RBButton.isChecked()).apply();
    }
    public void guardarIdSession(int id){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putInt(PREFERENCE_ESTATE_ID_SESION, id).apply();
    }
    public static int getIdSession(Context c){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getInt(PREFERENCE_ESTATE_ID_SESION, 0);
    }
    public void guardarNombreSession(String nombre){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putString(PREFERENCE_ESTATE_NOMBRE_SESION, nombre).apply();
    }
    public static String getNombreSession(Context c){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getString(PREFERENCE_ESTATE_NOMBRE_SESION, "");
    }
    public static boolean getEstadoButton(Context c){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCE_ESTATE_BUTTON_SESION, false);
    }
}
