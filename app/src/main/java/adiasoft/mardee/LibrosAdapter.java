package adiasoft.mardee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import adiasoft.mardee.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class LibrosAdapter extends BaseAdapter {

    String id_user;
    ArrayList<String> id;
    ArrayList<String> img;
    ArrayList<String> libro;
    ArrayList<String> autor;
    ArrayList<String> estado;
    Context contexto;
    private static LayoutInflater inflater= null;

    public LibrosAdapter(LibrosActivity contexto, ArrayList<String> id, ArrayList<String> img, ArrayList<String> libro, ArrayList<String> autor, String id_user, ArrayList<String> estado) {
        this.id_user = id_user;
        this.id = id;
        this.img = img;
        this.libro = libro;
        this.autor = autor;
        this.estado = estado;
        this.contexto = contexto;

        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return libro.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        ImageView img_libro;
        TextView tv_libro;
        TextView tv_autor;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View fila;
        fila = inflater.inflate(R.layout.lista_libros, null);
        fila.setBackgroundColor(Color.parseColor("#bf360c"));
        holder.img_libro = (ImageView) fila.findViewById(R.id.img_libro);
        holder.tv_libro = (TextView) fila.findViewById(R.id.tv_libro);
        holder.tv_autor = (TextView) fila.findViewById(R.id.tv_autor);

        new DownLoadImageTask(holder.img_libro).execute("http://adiasis.com/sis_lectura/system/controllers/uploads/"+img.get(position));

        holder.tv_libro.setText(libro.get(position));
        holder.tv_autor.setText(autor.get(position));

        fila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("Estadop " + estado.get(position));

                if(estado.get(position).equals("0")){
                    Intent intent = new Intent(contexto, CapitulosActivity.class);
                    intent.putExtra("id_libro", id.get(position));
                    intent.putExtra("id", id_user);
                    contexto.startActivity(intent);
                }else{
                    Intent intent = new Intent(contexto, LockActivity.class);
                    intent.putExtra("id_libro", id.get(position));
                    intent.putExtra("id", id_user);
                    contexto.startActivity(intent);
                }

            }
        });
        return fila;
    }
    public static Drawable return_image(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    private Drawable LoadImageFromWebOperations(String url)
    {
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}

