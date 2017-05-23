package com.example.radog.patm_cine_mapas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.Activities.Login;
import com.example.radog.patm_cine_mapas.TDA.TDAPelicula;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by radog on 21/05/2017.
 */

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FuncViewHolder> {

    private List<TDAPelicula> lPeli;
    Context context;
    private String name;

    public FunctionAdapter(List<TDAPelicula> listComp, Context context) {
        this.lPeli = listComp;
        this.context = context;
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
            bitmap = Ion.with(context).load(lPeli.get(position).getPoster()).withBitmap().asBitmap().get();
            holder.imvPoster.setImageBitmap(bitmap);
            holder.txtTitulo.setText(lPeli.get(position).getTitulo());
            holder.txtLenguaje.setText("Language: " + lPeli.get(position).getLenguaje());
            holder.txtDuracion.setText("Duration: " + String.valueOf(lPeli.get(position).getDuracion()));

            holder.setLongClickListener(new LongClickListener() {
                @Override
                public void onItemLongClick(int pos) {
                    name = lPeli.get(pos).getTitulo();
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

    public void getItemSelected(MenuItem item, String tClass) {
        //Toast.makeText(context, name + " : " + item.getItemId(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case 0:
                if (tClass.equals("login")) {
                    Intent iLogin = new Intent(context.getApplicationContext(), Login.class);
                    context.startActivity(iLogin);
                }
                break;
        }
    }


    public static class FuncViewHolder extends RecyclerView.ViewHolder implements
            View.OnLongClickListener,
            View.OnCreateContextMenuListener {

        public ImageView imvPoster;
        public TextView txtTitulo;
        public TextView txtLenguaje;
        public TextView txtDuracion;
        LongClickListener longClickListener;

        public FuncViewHolder(View itemView) {
            super(itemView);

            imvPoster = (ImageView) itemView.findViewById(R.id.imvPoster);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtLenguaje = (TextView) itemView.findViewById(R.id.txtLenguaje);
            txtDuracion = (TextView) itemView.findViewById(R.id.txtDuracion);

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
        }
    }

}