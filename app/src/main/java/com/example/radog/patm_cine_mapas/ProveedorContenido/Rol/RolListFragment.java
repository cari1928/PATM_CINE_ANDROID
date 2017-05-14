package com.example.radog.patm_cine_mapas.ProveedorContenido.Rol;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.radog.patm_cine_mapas.ProveedorContenido.Proveedor.Contrato;
import com.example.radog.patm_cine_mapas.R;

public class RolListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //private static final String LOGTAG = "Tiburcio - RolListFragment";

    RolCursorAdapter mAdapter;
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    public static RolListFragment newInstance() {
        RolListFragment f = new RolListFragment();
        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.i(LOGTAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_rol_list, container, false);

        mAdapter = new RolCursorAdapter(getActivity());
        setListAdapter(mAdapter);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i(LOGTAG, "onActivityCreated");
        mCallbacks = this;
        getLoaderManager().initLoader(0, null, mCallbacks);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        String columns[] = new String[]{
                Contrato.Rol._ID,
                Contrato.Rol.ROL
        };

        Uri baseUri = Contrato.Rol.CONTENT_URI;

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

        String selection = null;

        return new CursorLoader(getActivity(), baseUri, columns, selection, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)

        Uri laUriBase = Uri.parse("content://" + Contrato.AUTHORITY + "/Rol");
        data.setNotificationUri(getActivity().getContentResolver(), laUriBase);

        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    public class RolCursorAdapter extends CursorAdapter {
        public RolCursorAdapter(Context context) {
            super(context, null, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int ID = cursor.getInt(cursor.getColumnIndex(Contrato.Rol._ID));
            String rol = cursor.getString(cursor.getColumnIndex(Contrato.Rol.ROL));

            TextView textviewNombre = (TextView) view.findViewById(R.id.textview_ciclo_list_item_nombre);
            textviewNombre.setText(rol);

            /*ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color = generator.getColor(rol); //Genera un color según el nombre
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(rol.substring(0, 1), color);

            ImageView image = (ImageView) view.findViewById(R.id.image_view);
            image.setImageDrawable(drawable);*/

            view.setTag(ID);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.rol_list_item, parent, false);
            bindView(v, context, cursor);
            return v;
        }
    }

}
