package adiasoft.mardee;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class ViewerActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    String url;

    ArrayList<String> usuario;
    ArrayList<String> comentario;
    ListView lv_comentarios;

    EditText edt_comentario;
    ImageButton btn_comentar;
    ImageButton btn_megusta, btn_nomegusta;

    int delay = 50;

    String propio = null;
    boolean flag = false;

    TextView tv;
    ScrollView scrollView;
    Button mas, menos;
    ImageButton start, pause, btn_configuracion;
    int vel_start = 1;
    final Handler handler = new Handler();
    Runnable r;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ed1b24"));
        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("MARDEE - MATTOS");
        get_detalles(getIntent().getStringExtra("id_capitulo"));


        tv = (TextView) this.findViewById(R.id.tv);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        mas = findViewById(R.id.mas);
        menos = findViewById(R.id.menos);
        btn_configuracion = findViewById(R.id.btn_configuracion);

        btn_configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(ViewerActivity.this);
                b.setTitle("Tamaño de letra");
                String[] types = {"Pequeño", "Mediano", "Grande"};
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                tv.setTextSize(20);
                                break;
                            case 1:
                                tv.setTextSize(25);
                                break;
                            case 2:
                                tv.setTextSize(30);
                                break;
                        }
                    }

                });

                b.show();
            }
        });

        scrollView = findViewById(R.id.sc);


        lv_comentarios = findViewById(R.id.id_lv_comentarios);
        edt_comentario = findViewById(R.id.edt_comentario);
        btn_comentar = findViewById(R.id.btn_comentar);
        btn_megusta = findViewById(R.id.btn_megusta);
        btn_nomegusta = findViewById(R.id.btn_nomegusta);

        btn_nomegusta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://adiasis.com//libros_veloz/set_megusta.php?estado=0&id_capitulo=" + getIntent().getStringExtra("id_capitulo") + "&id_usuario=" + getIntent().getStringExtra("id");
                requestQueue = Volley.newRequestQueue(ViewerActivity.this);
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
                                if (result.equals("OK")) {
                                    btn_nomegusta.setImageResource(R.drawable.th_nothi_no);
                                    btn_megusta.setImageResource(R.drawable.th_nothi);
                                    get_detalles(getIntent().getStringExtra("id_capitulo"));
                                } else {
                                    try {
                                        Toast.makeText(ViewerActivity.this, "" + response.getString("Message"), Toast.LENGTH_SHORT).show();
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
        btn_megusta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://adiasis.com//libros_veloz/set_megusta.php?estado=1&id_capitulo=" + getIntent().getStringExtra("id_capitulo") + "&id_usuario=" + getIntent().getStringExtra("id");
                requestQueue = Volley.newRequestQueue(ViewerActivity.this);
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
                                if (result.equals("OK")) {
                                    btn_megusta.setImageResource(R.drawable.th_like);
                                    btn_nomegusta.setImageResource(R.drawable.th_nothi_do);
                                    get_detalles(getIntent().getStringExtra("id_capitulo"));
                                } else {
                                    try {
                                        Toast.makeText(ViewerActivity.this, "" + response.getString("Message"), Toast.LENGTH_SHORT).show();
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

        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(ViewerActivity.this);
        alertDialog2.setTitle("Mensaje");
        String string1 = "Extrayendo el capítulo para lectura, puede cerrar esta alerta cuando desee.";
        alertDialog2.setMessage(string1);
        alertDialog2.setIcon(R.drawable.ic_action_notification);
        alertDialog2.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog2.show();

        get_texto();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {

                } else {
                    vel_start = 1;
                    ejecutar();
                    flag = true;
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(r);
                vel_start = 0;
                flag = false;
            }
        });

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vel_start += 4;
                delay -= 5;
            }
        });
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vel_start < 2) {
                    vel_start = 1;
                    delay = 50;
                } else {
                    vel_start -= 4;
                    delay += 5;
                }
            }
        });

        get_comentarios();

        btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = edt_comentario.getText().toString();
                if (comentario.isEmpty() || comentario.equals("")) {
                    Toast.makeText(ViewerActivity.this, "Comentario Vacio.", Toast.LENGTH_SHORT).show();
                } else {
                    url = "https://adiasis.com//libros_veloz/comentar.php?comentario=" + comentario + "&id_capitulo=" + getIntent().getStringExtra("id_capitulo") + "&id_usuario=" + getIntent().getStringExtra("id");
                    System.out.println("Pa comentar " + url);
                    requestQueue = Volley.newRequestQueue(ViewerActivity.this);
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
                                    if (result.equals("OK")) {
                                        edt_comentario.setText("");
                                        get_comentarios();
                                    } else {
                                        try {
                                            Toast.makeText(ViewerActivity.this, "" + response.getString("Message"), Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    private void ejecutar() {
        r = new Runnable() {
            public void run() {
                metodoEjecutar(vel_start);
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(r, 0);
    }

    private void metodoEjecutar(int vel) {
        System.out.println("VEL METODO " + vel);
        scrollView.scrollTo(0, scrollView.getScrollY() + vel);
    }

    public void get_texto() {
        url = "https://adiasis.com/libros_veloz/get_texto.php?id_capitulo=" + getIntent().getStringExtra("id_capitulo");
        requestQueue = Volley.newRequestQueue(ViewerActivity.this);
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
                        if (result.equals("OK")) {
                            try {
                                String values = response.getString("Texto");
                                tv.setText(values);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    tv.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Toast.makeText(ViewerActivity.this, "" + response.getString("Message"), Toast.LENGTH_SHORT).show();
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

    public void get_comentarios() {
        url = "https://adiasis.com//libros_veloz/get_comments.php?id=" + getIntent().getStringExtra("id_capitulo");
        usuario = new ArrayList<String>();
        comentario = new ArrayList<String>();

        lv_comentarios.setAdapter(null);
        requestQueue = Volley.newRequestQueue(ViewerActivity.this);
        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject colorObj = response.getJSONObject(i);
                        usuario.add(colorObj.getString("nombres"));
                        comentario.add(colorObj.getString("comentario"));
                    }
                    lv_comentarios.setAdapter(new AdapterComentarios(ViewerActivity.this, usuario, comentario));
                } catch (JSONException e) {
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

    public void get_detalles(String id_capitulo) {
        final TextView actionEvent = findViewById(R.id.actionEvent);
        final TextView tv_megusta = findViewById(R.id.tv_megusta);
        final TextView tv_nomegusta = findViewById(R.id.tv_nomegusta);

        url = "https://adiasis.com//libros_veloz/get_detalles.php?id_capitulo=" + getIntent().getStringExtra("id_capitulo") + "&id_usuario=" + getIntent().getStringExtra("id");
        requestQueue = Volley.newRequestQueue(ViewerActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            actionEvent.setText(response.getString("titulo"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tv_megusta.setText(response.getString("me_gusta"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tv_nomegusta.setText(response.getString("no_me_gusta"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            propio = response.getString("propio");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (propio.equals("1")) {
                            btn_megusta.setImageResource(R.drawable.th_like);
                            btn_nomegusta.setImageResource(R.drawable.th_nothi_do);
                        } else if (propio.equals("0")) {
                            btn_nomegusta.setImageResource(R.drawable.th_nothi_no);
                            btn_megusta.setImageResource(R.drawable.th_nothi);
                        } else {

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
}
