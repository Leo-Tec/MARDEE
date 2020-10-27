package adiasoft.mardee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import adiasoft.mardee.R;

import java.util.ArrayList;

public class AdapterComentarios extends BaseAdapter {
    ArrayList<String> usuario;
    ArrayList<String> comentario;
    Context contexto;
    //String id_libro;

    private static LayoutInflater inflater= null;
    public AdapterComentarios (ViewerActivity mainActivity, ArrayList<String>usuario, ArrayList<String> comentario) {

        contexto = mainActivity;
        this.comentario = comentario;
        this.usuario = usuario;
        //this.id_libro = id_libro;

        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount(){
        return comentario.size();
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
        TextView tv_usuario;
        TextView tv_comentario;
    }
    @Override
    public View getView(final int posicion, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View fila;
        fila = inflater.inflate(R.layout.lista_comentarios, null);
        //fila.setBackgroundColor(Color.parseColor("#bf360c"));
        holder.tv_usuario = (TextView) fila.findViewById(R.id.tv_usuario);
        holder.tv_comentario = (TextView) fila.findViewById(R.id.tv_comentario);

        holder.tv_usuario.setText(comentario.get(posicion));
        holder.tv_comentario.setText(usuario.get(posicion));

        /*fila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(contexto, ViewerActivity.class);
                intent.putExtra("id_libro", id_libro);
                contexto.startActivity(intent);
            }
        });*/
        return fila;
    }
}
