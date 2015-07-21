package tpi.tpi_autoclave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Javier Aros on 21-07-2015.
 */
public class ListaGraficoAdapter extends BaseAdapter {

    private Context context;
    private List<Grafico> graficos;
    public ListaGraficoAdapter(Context context, List<Grafico> graficos) {
        this.context = context;
        this.graficos = graficos;
    }
    @Override
    public int getCount() {
        return this.graficos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.graficos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_grafico, parent, false);
        }

        // Set data into the view.


        TextView nombre = (TextView) rowView.findViewById((R.id.textViewNombreRuta));
        TextView puntos = (TextView) rowView.findViewById((R.id.textViewPuntos));
        TextView distancia = (TextView) rowView.findViewById((R.id.textViewDistanciaTotal));

        Grafico grafico = graficos.get(position);


        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");


        //_nombre rutaActivity
        nombre.setText(dateFormat.format(grafico.horaInicio));

        //puntos rutaActivity
        puntos.setText(dateFormat.format(grafico.horatermino));

        //distancia recorrida
        distancia.setText(grafico.temperatura+ "°C");

        return rowView;
    }

    public void setdatos(List<Grafico> graficos){
        this.graficos = graficos;
        notifyDataSetChanged();
    }




}
