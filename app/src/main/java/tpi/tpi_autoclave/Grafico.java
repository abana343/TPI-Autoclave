package tpi.tpi_autoclave;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Javier Aros on 21-07-2015.
 */
public class Grafico {
    public int id;
    public int temperatura;
    public int tiempo;  //minutos
    public int periodo; //minutos
    public Date horaInicio;
    public Date horatermino;
    public ArrayList<Point> puntos;


    public Grafico(int temperatura, int tiempo,int periodo){
        this.temperatura = temperatura;
        this.tiempo = tiempo;
        this.periodo = periodo;
        puntos = new ArrayList<>();
    }

    public Grafico(int id, int temperatura, int tiempo, int periodo, Date horaInicio, Date horatermino){
        this.id = id;
        this.temperatura = temperatura;
        this.tiempo = tiempo;
        this.periodo = periodo;
        this.horaInicio = horaInicio;
        this.horatermino = horatermino;
        puntos = new ArrayList<>();
    }

    public String getHoraInicioFormatoDB()
    {
        return "2007-01-01 10:00:00";                    //Arreeglarrr
    }

    public String getHoraTerminoFormatoDB()
    {
        return "2007-01-01 10:00:00";                    //Arreglarrr
    }



    public void setPuntos(ArrayList<Point> puntos){
        this.puntos = puntos;
    }

    public void agregarPunto(Point punto){
        puntos.add(punto);
    }

    public ArrayList<Point> getPuntos(){
        return puntos;
    }
}
