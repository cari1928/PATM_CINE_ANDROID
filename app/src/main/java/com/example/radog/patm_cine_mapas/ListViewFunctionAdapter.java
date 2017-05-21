package com.example.radog.patm_cine_mapas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by radog on 20/05/2017.
 */

public class ListViewFunctionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<itemFunctionEvent> arrayListItem;
    private LayoutInflater layoutInflater;

    public ListViewFunctionAdapter(Context context, ArrayList<itemFunctionEvent> arrayListItem) {
        this.context = context;
        this.arrayListItem = arrayListItem;
    }

    @Override
    public int getCount() {
        return arrayListItem.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewItem = layoutInflater.inflate(R.layout.item_function, parent, false);

        ImageView ivPicture = (ImageView) viewItem.findViewById(R.id.ivPicture);
        TextView tvTitulo = (TextView) viewItem.findViewById(R.id.tvTitulo);
        TextView tvCategoria = (TextView) viewItem.findViewById(R.id.tvCategoria);
        TextView tvDuracion = (TextView) viewItem.findViewById(R.id.tvDuracion);

        ivPicture.setImageResource(arrayListItem.get(position).getPicture());
        tvTitulo.setText(arrayListItem.get(position).getTitulo());
        tvCategoria.setText(arrayListItem.get(position).getCategoria());
        tvDuracion.setText(arrayListItem.get(position).getDuracion());

        return viewItem;
    }
}
