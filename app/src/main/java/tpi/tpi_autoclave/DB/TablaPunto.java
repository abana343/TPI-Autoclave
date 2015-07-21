package tpi.tpi_autoclave.DB;

/**
 * Created by Javier Aros on 21-07-2015.
 */
public class TablaPunto {
    public static final String TABLE_NAME = "punto";
    public static final String FIELD_ID_PUNTO  = "idPunto";
    public static final String FIELD_ID_GRAFICO = "idGrafico";
    public static final String FIELD_X = "X";
    public static final String FIELD_Y = "Y";
    public static final String CREATE_DB_TABLE = "create table " + TABLE_NAME + "(" +
            FIELD_ID_PUNTO + " integer primary key AUTOINCREMENT not null," +
            FIELD_ID_GRAFICO + " integer unsigned not null," +
            FIELD_X + " integer not null,"+
            FIELD_Y + " integer not null,"+
            "constraint fk_"+TABLE_NAME +" foreign key ("+FIELD_ID_GRAFICO+") references "+ TablaGrafico.TABLE_NAME+ "("+TablaGrafico.FIELD_ID + ")" +
            "on delete cascade on update cascade );";

}
