package tpi.tpi_autoclave.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

import tpi.tpi_autoclave.Grafico;
import tpi.tpi_autoclave.Point;

/**
 * Created by Javier Aros on 21-07-2015.
 */
public class BaseDatoGrafico extends SQLiteOpenHelper {

    private static final String DB_NAME = "TPI";
    private static  final int SCHEME_VERSION = 1;
    private SQLiteDatabase db;

    public BaseDatoGrafico(Context context) {
        super(context, DB_NAME, null, SCHEME_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TablaGrafico.CREATE_DB_TABLE);
        db.execSQL(TablaPunto.CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private ContentValues generaValoresGrafico(Grafico grafico){
        ContentValues valores = new ContentValues();
        valores.put(TablaGrafico.FIELD_PERIODO, grafico.periodo);
        valores.put(TablaGrafico.FIELD_TEMPERATURA, grafico.temperatura);
        valores.put(TablaGrafico.FIELD_TIEMPO, grafico.tiempo);
        valores.put(TablaGrafico.FIELD_HORA_INICIO, grafico.getHoraInicioFormatoDB());
        valores.put(TablaGrafico.FIELD_HORA_TERMINO, grafico.getHoraTerminoFormatoDB());
        return valores;
    }

    public void InsertarGrafico(Grafico grafico){
        db.insert(TablaGrafico.TABLE_NAME,null,generaValoresGrafico(grafico));
        int ultimoID = getUltimoIdTablaGrafico();
        insertarPuntoGrafico(grafico.getPuntos(), ultimoID);
    }


    private void insertarPuntoGrafico(ArrayList<Point> puntos , int id){
        for(Point point:puntos){
            db.insert(TablaPunto.TABLE_NAME, null, generarValoresPunto(point, id));
        }
    }

    public void eliminarGrafico(int id){
        db.delete(TablaGrafico.TABLE_NAME, TablaGrafico.FIELD_ID + " = " + id, null);
    }

    private void eliminarPunto(int id){
        db.delete(TablaPunto.TABLE_NAME, TablaPunto.FIELD_ID_GRAFICO + " = " + id, null);
    }



    public ContentValues generarValoresPunto(Point point,int id){
        ContentValues valores = new ContentValues();
        valores.put(TablaPunto.FIELD_ID_GRAFICO, id);
        valores.put(TablaPunto.FIELD_X, point.getX());
        valores.put(TablaPunto.FIELD_Y, point.getY());
        return valores;
    }


    public ArrayList<Grafico> getGraficos(){
        ArrayList<Grafico> graficos = new ArrayList<>();
        String columnas[] = {TablaGrafico.FIELD_ID ,
                TablaGrafico.FIELD_TEMPERATURA,
                TablaGrafico.FIELD_TIEMPO,
                TablaGrafico.FIELD_PERIODO,
                TablaGrafico.FIELD_HORA_INICIO,
                TablaGrafico.FIELD_HORA_TERMINO};

        Cursor c = db.query(TablaGrafico.TABLE_NAME,columnas,null,null,null,null,null);
        if(c.moveToFirst())
        {
            do {
                Grafico g = new Grafico(c.getInt(0),c.getInt(1),c.getInt(2),c.getInt(3),horaInicioFormatoDate(c.getString(4)),horaInicioFormatoDate(c.getString(5)));
                graficos.add(g);

            }while(c.moveToNext());
        }
        return graficos;
    }

    public Date horaInicioFormatoDate(String horaInicio){       //arreglar
        Date dato = new Date();
        return dato;
    }


    public ArrayList<Point> getPuntosGrafico(int IdGrafico){
        ArrayList<Point> puntos = new ArrayList<>();

        String columnas[] = {TablaPunto.FIELD_X,
                TablaPunto.FIELD_Y,
                TablaPunto.FIELD_ID_GRAFICO };


        String[] args = new String[]{""+ TablaPunto.FIELD_ID_GRAFICO};

        Cursor c = db.query(TablaPunto.TABLE_NAME,columnas,"`"+TablaPunto.FIELD_ID_GRAFICO+ "`=?",args,null,null,null);

        if(c.moveToFirst())
        {
            do {
                Point point = new Point(c.getInt(0), c.getInt(1));
                puntos.add(point);
            }while(c.moveToNext());
        }
        return puntos;
    }

    public Grafico cargarPuntosARuta(Grafico grafico){
        grafico.setPuntos(getPuntosGrafico(grafico.id));
        return grafico;
    }



    public int getUltimoIdTablaGrafico(){

        String query = "select MAX(`id`) as max_id from " + TablaGrafico.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int id = 0;
        if(cursor.moveToFirst())
        {
            do{
                id = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        return id;
    }


}


