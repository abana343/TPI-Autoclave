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

    public LineGraph()
    {
        mDataset.addSeries(dataset);

        // Customization time for line 1!
        renderer.setColor(Color.RED);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);

        //Fondo
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.WHITE);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0, Color.BLACK);
        mRenderer.setGridColor(Color.GREEN);

        // Enable Zoom
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setZoomEnabled(false,false);

        //titulos
        mRenderer.setXTitle("\n \n Tiempo (Minutos)");
        mRenderer.setYTitle("Temperatura (grados Celcius)\n \n.");

        //Establece los Rangos
        double[] d = new double[4];
        d[0]=0.0; //minX
        d[1]=2700.0; //maxX
        d[2]=0.0; //minY
        d[3]=150.0; //maxY
        mRenderer.setRange(d);
        mRenderer.setPanLimits(d);

        mRenderer.setXLabels(0);
        mRenderer.setYLabels(0);

        for(int i = 0; i<=45 ;i++){
            mRenderer.addXTextLabel(i*60 ,""+ i);
        }
        for(int i = 0; i<=15 ;i++){
            mRenderer.addYTextLabel(i*10 ,""+ i*10);
        }

        //mRenderer.removeXTextLabel(1000);






        //switch (getResources().getDisplayMetrics().densityDpi) {
        //	case  DisplayMetrics.DENSITY_XHIGH:
        //		mRenderer.setMargins(new int[] { 40, 90, 25, 10 });
        mRenderer.setAxisTitleTextSize(12);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setXAxisColor(Color.BLACK);
        mRenderer.setYAxisColor(Color.BLACK);
        mRenderer.setChartTitleTextSize(14);

        mRenderer.setLabelsTextSize(12);
        mRenderer.setLegendTextSize(18);

        //		break;
        //	case DisplayMetrics.DENSITY_HIGH:
        //		mRenderer.setMargins(new int[] { 30, 50, 20, 10 });
        //		mRenderer.setAxisTitleTextSize(Constants.TEXT_SIZE_HDPI);
        //		mRenderer.setChartTitleTextSize(Constants.TEXT_SIZE_HDPI);
        //		mRenderer.setLabelsTextSize(Constants.TEXT_SIZE_HDPI);
        //		mRenderer.setLegendTextSize(Constants.TEXT_SIZE_HDPI);
        //		break;
        //	default:
        //		mRenderer.setMargins(new int[] { 30, 50, 20, 10 });
        //		mRenderer.setAxisTitleTextSize(Constants.TEXT_SIZE_LDPI);
        //		mRenderer.setChartTitleTextSize(Constants.TEXT_SIZE_LDPI);
        //		mRenderer.setLabelsTextSize(Constants.TEXT_SIZE_LDPI);
        //		mRenderer.setLegendTextSize(Constants.TEXT_SIZE_LDPI);
        //		break;
        //}



        // Add single renderer to multiple renderer
        mRenderer.addSeriesRenderer(renderer);
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
