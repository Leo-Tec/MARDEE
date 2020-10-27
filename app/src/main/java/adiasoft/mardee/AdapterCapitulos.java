package adiasoft.mardee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import adiasoft.mardee.R;

import java.util.ArrayList;

public class AdapterCapitulos extends BaseAdapter {

    String id_user;
    ArrayList<String> id;
    ArrayList<String> capitulo;
    ArrayList<String> n_hojas;
    Context contexto;
    String id_libro;

    private static LayoutInflater inflater= null;
    public AdapterCapitulos (CapitulosActivity mainActivity, ArrayList<String>id, ArrayList<String>capitulo, ArrayList<String> n_hojas, String id_libro, String id_user) {
        this.id = id;
        this.id_user = id_user;
        contexto = mainActivity;
        this.capitulo = capitulo;
        this.n_hojas = n_hojas;
        this.id_libro = id_libro;

        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount(){
        return capitulo.size();
    }
    @Override
    public  Object getItem(int posicion) {
        return posicion;
    }
    @Override
    public  long  getItemId(int posicion) {
        return posicion;
    }
    public class Holder
    {
        TextView tv_capitulo;
        TextView tv_num_hojas;
    }
    @Override
    public View getView(final int posicion, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View fila;
        fila = inflater.inflate(R.layout.lista_capitulos, null);
        fila.setBackgroundColor(Color.parseColor("#bf360c"));
        holder.tv_capitulo = (TextView) fila.findViewById(R.id.tv_capitulo);
        holder.tv_num_hojas = (TextView) fila.findViewById(R.id.tv_num_hojas);

        holder.tv_capitulo.setText(capitulo.get(posicion));
        holder.tv_num_hojas.setText(n_hojas.get(posicion));

        fila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(contexto, ViewerActivity.class);
                intent.putExtra("id_capitulo", id.get(posicion));
                intent.putExtra("id_libro", id_libro);
                intent.putExtra("id", id_user);
                contexto.startActivity(intent);
            }
        });
        return fila;
    }
}
