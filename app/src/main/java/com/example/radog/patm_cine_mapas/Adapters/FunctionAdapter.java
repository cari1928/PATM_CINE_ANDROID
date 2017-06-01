package com.example.radog.patm_cine_mapas.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.Activities.AsientosActivity;
import com.example.radog.patm_cine_mapas.Activities.Login;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.LongClickListener;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDAPelicula;
import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by radog on 21/05/2017.
 */

public class FunctionAdapter extends
        RecyclerView.Adapter<FunctionAdapter.FuncViewHolder>
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private List<TDAPelicula> lPeli;
    Context context;
    private String name;
    public int tipo;
    private int funcion_id;

    public FunctionAdapter(List<TDAPelicula> listComp, Context context, int tipo) {
        this.lPeli = listComp;
        this.context = context;
        this.tipo = tipo;
    }

    @Override
    public FuncViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_function, parent, false);
        return new FuncViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FuncViewHolder holder, int position) {
        Bitmap bitmap;
        try {
            bitmap = checkConnection(position); //para obtener la imagen
            holder.imvPoster.setImageBitmap(bitmap);
            holder.txtTitulo.setText(lPeli.get(position).getTitulo());
            holder.txtLenguaje.setText("Language: " + lPeli.get(position).getLenguaje());
            holder.txtDuracion.setText("Duration: " + String.valueOf(lPeli.get(position).getDuracion()));

            if (tipo != 2) {
                holder.txtSala.setVisibility(TextView.INVISIBLE);
                holder.txtFecha.setVisibility(TextView.INVISIBLE);
                holder.txtHora.setVisibility(TextView.INVISIBLE);
            } else {
                holder.txtSala.setText(lPeli.get(position).getNombre());
                holder.txtFecha.setText(lPeli.get(position).getFecha() + " - " + lPeli.get(position).getFecha_fin());
                holder.txtHora.setText(lPeli.get(position).getHora() + " - " + lPeli.get(position).getHora_fin());
            }

            holder.setLongClickListener(new LongClickListener() {
                @Override
                public void onItemLongClick(int pos) {
                    name = lPeli.get(pos).getTitulo();
                    funcion_id = lPeli.get(pos).getFuncion_id();

                    ((MyApplication) context.getApplicationContext()).setFuncion_id(lPeli.get(pos).getFuncion_id());
                    ((MyApplication) context.getApplicationContext()).setPelicula_id(lPeli.get(pos).getPelicula_id());
                    ((MyApplication) context.getApplicationContext()).setPelicula_titulo(lPeli.get(pos).getTitulo());
                    ((MyApplication) context.getApplicationContext()).setSala_id(lPeli.get(pos).getSala_id());
                    ((MyApplication) context.getApplicationContext()).setSala_nombre(lPeli.get(pos).getNombre());
                    ((MyApplication) context.getApplicationContext()).setHora(lPeli.get(pos).getHora());
                    ((MyApplication) context.getApplicationContext()).setHora_fin(lPeli.get(pos).getHora_fin());
                    ((MyApplication) context.getApplicationContext()).setFecha(lPeli.get(pos).getFecha());
                    ((MyApplication) context.getApplicationContext()).setFecha_fin(lPeli.get(pos).getFecha_fin());
                    //Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return lPeli.size();
    }

    public void getItemSelected(MenuItem item, String type) {
        //Toast.makeText(context, name + " : " + item.getItemId(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case 0:
                if (type.equals("Login")) {
                    Intent iLogin = new Intent(context.getApplicationContext(), Login.class);
                    context.startActivity(iLogin);
                } else if (type.equals("Sucursal")) {
                    Intent iAsientos = new Intent(context.getApplicationContext(), AsientosActivity.class);
                    context.startActivity(iAsientos);
                }
                break;
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    public static class FuncViewHolder extends RecyclerView.ViewHolder implements
            View.OnLongClickListener,
            View.OnCreateContextMenuListener {

        public ImageView imvPoster;
        public TextView txtTitulo;
        public TextView txtLenguaje;
        public TextView txtDuracion;

        public TextView txtSala;
        public TextView txtFecha;
        public TextView txtHora;

        LongClickListener longClickListener;

        public FuncViewHolder(View itemView) {
            super(itemView);

            imvPoster = (ImageView) itemView.findViewById(R.id.imvPoster);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtLenguaje = (TextView) itemView.findViewById(R.id.txtLenguaje);
            txtDuracion = (TextView) itemView.findViewById(R.id.txtDuracion);

            txtSala = (TextView) itemView.findViewById(R.id.txtSala);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
            txtHora = (TextView) itemView.findViewById(R.id.txtHora);

            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setLongClickListener(LongClickListener longClickListener) {
            this.longClickListener = longClickListener;
        }

        @Override
        public boolean onLongClick(View v) {
            this.longClickListener.onItemLongClick(getLayoutPosition());
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action:");
            menu.add(0, 0, 0, "Buy Tickets");
            //menu.add(0, 1, 1, "Extra Info");
        }
    }

    private Bitmap checkConnection(int position) throws ExecutionException, InterruptedException {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            return Ion.with(context).load(lPeli.get(position).getPoster()).withBitmap().asBitmap().get();
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.no_disponible);
        }
    }
}
