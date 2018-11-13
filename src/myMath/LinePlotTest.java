package myMath;

import de.erichseifert.gral.data.DataTable;

import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


/**
 * This class will show a graph and plot the polynom to the screen, mark and print the extreme points of the graph,
 * and print the X,Y data of these points on the graph.
 * This graphing tool is based on and uses GRAL library, as seen on https://github.com/eseifert/gral .
 */
public class LinePlotTest extends JFrame {
    public LinePlotTest(Polynom_able pol, double x0, double x1) {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //will add listener for confirmation, so we do nothing on close.
        setTitle("Liad & Timor Polynom graph");
        setSize(600, 400);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            {
                String ObjButtons[] = {"Yes","No"};
                int PromptResult = JOptionPane.showOptionDialog(null,"You are about to leave Liad & Timor Amazing graphing tool. \nAre you sure you want to exit? we will miss you.","Leaving Liad & Timor Amazing graphing tool?",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });
        //Creates DataTables of Dots.
        DataTable data = new DataTable(Double.class, Double.class);
        for (double x = x0; x <= x1; x += 0.01) {
            double y = pol.f(x);
            data.add(x, y);
        }
        ArrayList<String> kitzon = new ArrayList();
        DataTable data2 = new DataTable(Double.class, Double.class);
        Polynom_able pcopy = pol.copy();
        pcopy = pcopy.derivative();
        for (double x = x0; x <= x1; x += 0.01) {
            double y = pol.f(x);
            if (pcopy.f(x)<0.017 && pcopy.f(x)>-0.017) {
                data2.add(x, y);
                String number = String.format("%.2f", x);
                String results = String.format("%.2f", y);
                kitzon.add("("+number + "," + results + ")");
            }
       }
        Color blueColor = new Color(0.0f, 0.0f, 1.0f); //blue
        Color redColor = new Color(1.0f, 0.0f, 0.0f); //red
        Color darkGreyColor = new Color(36, 39, 49); //dark grey.
        Color orangeColor = new Color(158,116,39);
        Color whiteColor = new Color(255, 255, 255);
        XYPlot plot = new XYPlot(data,data2);
        String kitonzs = kitzon.toString();
        kitonzs = kitonzs.replaceAll(", ",",   ");
        plot.getAxisRenderer(XYPlot.AXIS_X).getLabel().setText(kitonzs);
        plot.getAxisRenderer(XYPlot.AXIS_X).getLabel().setColor(whiteColor);
        plot.getAxisRenderer(XYPlot.AXIS_X).setShapeColor(whiteColor); //coloring X axis
        plot.getAxisRenderer(XYPlot.AXIS_Y).setShapeColor(whiteColor);//coloring Y axis
        plot.getAxisRenderer(XYPlot.AXIS_X).setMinorTickColor(whiteColor); //coloring x axis ticks .
        plot.getAxisRenderer(XYPlot.AXIS_Y).setMinorTickColor(whiteColor);//coloring y axis ticks .
        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(data, lines);
        plot.getPointRenderers(data).get(0).setColor(orangeColor);
        plot.getLineRenderers(data).get(0).setColor(orangeColor);
        plot.getPointRenderers(data2).get(0).setColor(whiteColor);
        plot.getPlotArea().setBackground(darkGreyColor);
    }
}