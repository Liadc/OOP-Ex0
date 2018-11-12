package myMath;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

import javax.swing.*;
import java.awt.*;

public class LinePlotTest extends JFrame {
    public LinePlotTest(Polynom_able pol, double x0, double x1) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        //Creates DataTables of Dots.
        DataTable data = new DataTable(Double.class, Double.class);
        for (double x = x0; x <= x1; x += 0.35) {
            double y = pol.f(x);
            data.add(x, y);
        }

        DataTable data2 = new DataTable(Double.class, Double.class);
        Polynom_able pcopy = pol.copy();
        pcopy.derivative();
        for (double x = x0; x <= x1; x += 0.001) {
            double y = pol.f(x);
            if (pcopy.f(x)<0.5 || pcopy.f(x)>-0.5) {
                data2.add(x, y);
            }
        }


        XYPlot plot = new XYPlot(data);
        XYPlot plot2 = new XYPlot(data2);
        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(data, lines);
        Color color1 = new Color(0.0f, 0.0f, 1.0f);
        Color color2 = new Color(1.0f, 0.0f, 0.0f); //red green blue
        plot.getPointRenderers(data).get(0).setColor(color1);
        plot.getLineRenderers(data).get(0).setColor(color1);
       plot2.getPointRenderers(data2).get(0).setColor(color2);
    }

}