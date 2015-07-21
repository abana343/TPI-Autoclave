package tpi.tpi_autoclave;

/**
 * Created by Javier Aros on 21-07-2015.
 */
public class Comunicador {
    private static Object objeto = null;


    public static Object getObjeto(){
        return objeto;
    }
    public static Object setObject(Object newObject){
        return objeto = newObject;
    }
}
