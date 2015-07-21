package tpi.tpi_autoclave;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class Index extends ActionBarActivity {
    EditText editTextTiempo;
    EditText editTextTemperatura;
    EditText editTextPeriodo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        editTextTiempo = (EditText) findViewById(R.id.Tiempo);
        editTextTemperatura = (EditText) findViewById(R.id.Temperatura);
        editTextPeriodo = (EditText) findViewById(R.id.Periodo);
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

    public void onClickIniciarGraficaSensando(View view){
        int temperatura = Integer.parseInt( editTextTemperatura.getText().toString());
        int tiempo = Integer.parseInt( editTextTiempo.getText().toString());
        int periodo =  Integer.parseInt( editTextPeriodo.getText().toString());
        Grafico grafico = new Grafico(temperatura,  tiempo, periodo);
        Comunicador.setObject(grafico);
        Intent i = new Intent(this, main.class);
        startActivity(i);

    }

}
