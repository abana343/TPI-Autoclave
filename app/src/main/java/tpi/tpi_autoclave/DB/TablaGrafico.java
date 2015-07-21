package tpi.tpi_autoclave.DB;

/**
 * Created by Javier Aros on 21-07-2015.
 */
public class TablaGrafico {
    public static final String TABLE_NAME = "grafico";
    public static final String FIELD_ID = "id";
    public static final String FIELD_TEMPERATURA = "temperatura";
    public static final String FIELD_TIEMPO = "tiempo";      //en minutos
    public static final String FIELD_PERIODO = "periodo";   //en minutos
    public static final String FIELD_HORA_INICIO = "horainicio";
    public static final String FIELD_HORA_TERMINO = "horatermino";
    public static final String CREATE_DB_TABLE = "create table " + TABLE_NAME + "(" +
            FIELD_ID + " integer primary key AUTOINCREMENT not null ," +
            FIELD_TEMPERATURA + " integer not null,"+
            FIELD_TIEMPO + " integer not null,"+
            FIELD_PERIODO + " integer not null,"+
            FIELD_HORA_INICIO + " DATETIME not null,"+
            FIELD_HORA_TERMINO + " DATETIME not null);";
}
//          Formato DATETIME
//'2007-01-01 10:00:00'
//i.e. yyyy-MM-dd HH:mm:ss