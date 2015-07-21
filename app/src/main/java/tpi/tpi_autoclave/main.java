package tpi.tpi_autoclave;

import android.app.Activity;

import org.achartengine.GraphicalView;


import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.os.PowerManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


import java.nio.ByteBuffer;


public class main extends Activity implements Runnable
{

    private static final int CMD_LED_OFF = 2;
    private static final int CMD_LED_ON = 1;

    ToggleButton buttonLed;
    TextView texto;
    String muestra="0";
    int temperatura=0;

    boolean rele= true;


    int tMAx=0;
    private static EditText tMaxText;

    /* variables algoritmo de control de temperatura;
     */
    public static final int GRADOS_MAXIMOS = 180;//grados maximos del servo = 220 volts.
    public static final int GRADOS_MINIMOS = 0;//grados minimos del servo = 0 volts.
    public static int delay= 30;//segundo de espera para continuar con el algoritmo
    public int grados_motor=180;// informacion que se le enviara al arduino para controlar el voltaje conforme a la medicion de temperatura.
    public int t_m_a_a=40;//temperatura maxima a alcanzar;
    public int grado_anterior=0;
    public int grado_ahora=180;





    // Crear un par de conjuntos de valores de y para trazar:

    private UsbManager usbManager;

    private UsbDevice deviceFound;
    private UsbDeviceConnection usbDeviceConnection;
    private UsbInterface usbInterfaceFound = null;
    private UsbEndpoint endpointOut = null;
    private UsbEndpoint endpointIn = null;

    //-------------------------------------------
    private static GraphicalView view;
    private static LinearLayout prueba;

    private LineGraph line;
    private static Thread thread;

    protected PowerManager.WakeLock wakelock;
    //-----------------
    Grafico grafico;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        grafico = (Grafico)Comunicador.getObjeto();
        line = new LineGraph(grafico.periodo,grafico.temperatura);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        buttonLed = (ToggleButton)findViewById(R.id.arduinoled);
        texto = (TextView) findViewById(R.id.texto);
        prueba = (LinearLayout) findViewById(R.id.grafico);

        tMaxText = (EditText) findViewById(R.id.tMax);


        view = line.getView(this);
        prueba.addView(view);
        this.onClickTexto();

        buttonLed.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    sendCommand(CMD_LED_ON);
                }else{
                    sendCommand(CMD_LED_OFF);
                }
            }});

        usbManager = (UsbManager)getSystemService(Context.USB_SERVICE);

        final PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        this.wakelock=pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "etiqueta");
        wakelock.acquire();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        this.wakelock.release();
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
            setDevice(device);
        } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
            if (deviceFound != null && deviceFound.equals(device)) {
                setDevice(null);
            }
        }
    }

    private void setDevice(UsbDevice device) {
        usbInterfaceFound = null;
        endpointOut = null;
        endpointIn = null;

        for (int i = 0; i < device.getInterfaceCount(); i++) {
            UsbInterface usbif = device.getInterface(i);

            UsbEndpoint tOut = null;
            UsbEndpoint tIn = null;

            int tEndpointCnt = usbif.getEndpointCount();
            if (tEndpointCnt >= 2) {
                for (int j = 0; j < tEndpointCnt; j++) {
                    if (usbif.getEndpoint(j).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                        if (usbif.getEndpoint(j).getDirection() == UsbConstants.USB_DIR_OUT) {
                            tOut = usbif.getEndpoint(j);
                        } else if (usbif.getEndpoint(j).getDirection() == UsbConstants.USB_DIR_IN) {
                            tIn = usbif.getEndpoint(j);
                        }
                    }
                }

                if (tOut != null && tIn != null) {
                    // This interface have both USB_DIR_OUT
                    // and USB_DIR_IN of USB_ENDPOINT_XFER_BULK
                    usbInterfaceFound = usbif;
                    endpointOut = tOut;
                    endpointIn = tIn;
                }
            }

        }

        if (usbInterfaceFound == null) {
            return;
        }

        deviceFound = device;

        if (device != null) {
            UsbDeviceConnection connection =
                    usbManager.openDevice(device);
            if (connection != null &&
                    connection.claimInterface(usbInterfaceFound, true)) {
                usbDeviceConnection = connection;
                Thread thread = new Thread(this);
                thread.start();

            } else {
                usbDeviceConnection = null;
            }
        }
    }

    private void sendCommand(int control) {
        synchronized (this) {

            if (usbDeviceConnection != null) {
                byte[] message = new byte[1];
                message[0] = (byte)control;
                usbDeviceConnection.bulkTransfer(endpointOut,
                        message, message.length, 0);
            }
        }
    }

    public void onClickTexto()
    {
        thread = new Thread()
        {
            @Override
            public void run()
            {
                int tiempo = 1;
                int grados=0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                        tMAx = Integer.parseInt(tMaxText.getText().toString());
                        texto.setText("Temperatura: " + muestra + " grados C" + " MAX: " + tMAx +" grados: " + grado_ahora);


                    } catch (Exception e) {
                    }

                    try {
                        if(temperatura>=0)
                        {
                            Point p = MockData.getDataFromReceiver(tiempo,temperatura); // We got new data!
                            line.addNewPoints(p); // Add it to our graph
                            view.repaint();
                        }

                    }catch (Exception e){}

                    //servo motor
                    /*funciona con esto asta el asd7.
                    if(tiempo%120==0)
                    {
                        grados+=20;
                        sendCommand(grados);
                    }
                     */

                    /*
                    algoritmo de control de temperatura.
                     */
                    int aux=0;
                    if(tiempo%delay==0 && tiempo>1)
                    {
                        if(grado_anterior>grado_ahora)
                        {
                            aux= grado_anterior-grado_ahora;

                        }
                        else
                        {
                            aux=grado_ahora-grado_anterior;
                        }
                        aux= aux/2;

                        if(temperatura < t_m_a_a)
                        {
                            aux=grado_ahora+aux;
                        }
                        else
                        {
                            if(temperatura>t_m_a_a)
                            {
                                aux=grado_ahora-aux;
                            }
                        }
                        grado_anterior=grado_ahora;
                        grado_ahora=aux;
                        sendCommand(grado_ahora);
                    }
                    else
                    {
                        if(tiempo==0)
                        {
                            sendCommand(grado_ahora);
                        }
                    }


                    tiempo++;
                }
            }
        };
        thread.start();

    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        UsbRequest request = new UsbRequest();
        request.initialize(usbDeviceConnection, endpointIn);

        while (true) {
            request.queue(buffer, 1);
            if (usbDeviceConnection.requestWait() == request) {
                byte rxCmd = buffer.get(0);

                if(rxCmd!=0)
                {
                    muestra =""+rxCmd;
                    temperatura = (int) rxCmd;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            } else {

                break;
            }
        }

    }








}
