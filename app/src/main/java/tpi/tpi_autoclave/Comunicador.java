package tpi.tpi_autoclave;

import android.hardware.usb.UsbManager;

import tpi.tpi_autoclave.DB.BaseDatoGrafico;

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
    private static UsbManager usbManager = null;

    public static UsbManager getUsbManager() {
        return usbManager;
    }

    public static void setUsbManager(UsbManager usbManager) {
        Comunicador.usbManager = usbManager;
    }

    public static BaseDatoGrafico getBASE_DATO_GRAFICO() {
        return BASE_DATO_GRAFICO;
    }

    public static void setBASE_DATO_GRAFICO(BaseDatoGrafico BASE_DATO_GRAFICO) {
        Comunicador.BASE_DATO_GRAFICO = BASE_DATO_GRAFICO;
    }

    private static BaseDatoGrafico BASE_DATO_GRAFICO = null;
}
