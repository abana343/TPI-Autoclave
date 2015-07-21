package tpi.tpi_autoclave;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tpi.tpi_autoclave.DB.BaseDatoGrafico;


public class Index extends ActionBarActivity {
    EditText editTextTiempo;
    EditText editTextTemperatura;
    EditText editTextPeriodo;
    UsbManager usbManager;
    ListView listView;

    List<Grafico> lista;
    BaseDatoGrafico baseDato;
    private ListaGraficoAdapter adapterGrafico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        editTextTiempo = (EditText) findViewById(R.id.Tiempo);
        editTextTemperatura = (EditText) findViewById(R.id.Temperatura);
        editTextPeriodo = (EditText) findViewById(R.id.Periodo);
        listView = (ListView) findViewById(R.id.listView);
        usbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
        Comunicador.setUsbManager(usbManager);
        baseDato = new BaseDatoGrafico(this);
        Comunicador.setBASE_DATO_GRAFICO(baseDato);



        lista = cargarGraficoInterna();
        lista = cargarPrueba();
        System.out.println(lista.size());




        adapterGrafico =new ListaGraficoAdapter(this, lista);
        this.listView.setAdapter(adapterGrafico);
        this.listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Grafico> cargarGraficoInterna(){
        return Comunicador.getBASE_DATO_GRAFICO().getGraficos();
    }

    public void onClickIniciarGraficaSensando(View view){
        int temperatura = Integer.parseInt( editTextTemperatura.getText().toString());
        int tiempo = Integer.parseInt( editTextTiempo.getText().toString());
        int periodo =  Integer.parseInt( editTextPeriodo.getText().toString());
        Grafico grafico = new Grafico(temperatura,  tiempo, periodo);
        Comunicador.setObject(grafico);
        Intent i = new Intent(this, main.class);
        startActivity(i);

    }

    //pruebas
    private List<Grafico> cargarPrueba(){
        ArrayList<Grafico> graficos = new ArrayList<>();
        Date dIni = new Date( );
        Date dFin = new Date( );
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "21-07-2015 10:20:56";
        String dateInString2 = "21-07-2015 15:20:56";
        try{
            dIni = sdf.parse(dateInString);
            dFin = sdf.parse(dateInString2);
        }
         catch (Exception e) {
            e.printStackTrace();
        }





        Grafico g = new Grafico(1,45,15,60,dIni,dFin);
        graficos.add(g);
        g = new Grafico(1,45,15,60,dIni,dFin);
        graficos.add(g);
        g = new Grafico(2,55,18,120,dIni,dFin);
        graficos.add(g);
        g = new Grafico(3,65,20,60,dIni,dFin);
        graficos.add(g);
        g = new Grafico(4,75,5,80,dIni,dFin);
        graficos.add(g);
        g = new Grafico(5,85,10,60,dIni,dFin);
        graficos.add(g);
        g = new Grafico(6,95,15,50,dIni,dFin);
        graficos.add(g);

        return graficos;
    }

}
