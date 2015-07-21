package tpi.tpi_autoclave;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;

import static android.webkit.WebSettings.TextSize.NORMAL;



public class LineGraph {

    private GraphicalView view;
    private TimeSeries dataset = new TimeSeries("Sensor");
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph

    public LineGraph(int tiempoMax, int temperaturaMax)
    {
        mDataset.addSeries(dataset);
        //crea la linea
        setLinea();
        //crea el grafico y lo pinta
        setGrafico();
        //Establece los Rangos
        setRangoGrafico(tiempoMax,temperaturaMax);
        // Add single renderer to multiple renderer
        mRenderer.addSeriesRenderer(renderer);
    }

    private void setLinea()
    {
        // Customization time for line 1!
        renderer.setColor(Color.RED);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);
    }

    private void setRangoGrafico(int tiempoMax, int temperaturaMax)
    {
        double[] d = new double[4];
        d[0]=0.0; //minX
        d[1]=(double) 60*tiempoMax; //maxX segundos
        d[2]=0.0; //minY
        d[3]=(double) temperaturaMax + temperaturaMax* 0.2; //maxY
        mRenderer.setRange(d);
        mRenderer.setPanLimits(d);

        mRenderer.setXLabels(0);
        mRenderer.setYLabels(0);

        for(int i = 0; i<=tiempoMax ;i++){
            mRenderer.addXTextLabel(i*60 ,""+ i);
        }
        for(int i = 0; i<= (temperaturaMax* 0.2/10) ;i++){
            mRenderer.addYTextLabel(i*10 ,""+ i*10);
        }

    }

    public void setGrafico()
    {
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.WHITE);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0, Color.BLACK);
        mRenderer.setGridColor(Color.GREEN);

        // Enable Zoom
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setZoomEnabled(false, false);

        //titulos
        mRenderer.setXTitle("\n \n Tiempo (Minutos)");
        mRenderer.setYTitle("Temperatura (grados Celcius)\n \n.");


        mRenderer.setAxisTitleTextSize(12);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setXAxisColor(Color.BLACK);
        mRenderer.setYAxisColor(Color.BLACK);
        mRenderer.setChartTitleTextSize(14);

        mRenderer.setLabelsTextSize(12);
        mRenderer.setLegendTextSize(18);
    }

    public GraphicalView getView(Context context)
    {
        view =  ChartFactory.getLineChartView(context, mDataset, mRenderer);
        return view;
    }

    public void addNewPoints(Point p)
    {
        dataset.add(p.getX(), p.getY());
    }

}
