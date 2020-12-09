import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SVG {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;
    
    private String filename;
    private ArrayList<String> elements;

    public SVG(String str)
    {
        filename = str;
        elements = new ArrayList<String>();
    }

    public void writeFile()
    {
        PrintStream output = null;

        try
        {
            output = new PrintStream(new File(filename));
        }

        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        if (output == null) return;

        output.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        output.println();
        output.println("<svg version=\"1.1\" baseProfile=\"full\"");
        output.println("\txmlns=\"http://www.w3.org/2000/svg\"");
        output.println("\txmlns:xlink=\"http://www.w3.org/1999/xlink\"");
        output.println("\txmlns:ev=\"http://www.w3.org/2001/sml-events\"");
        output.println("\tviewBox = \"0 0 " + WIDTH + " " + HEIGHT + "\">");

        // paintBackground(new ColorRGB(220, 220, 220));

        // drawCircle(500, 500, 300, new ColorRGB(180, 10, 180));

        drawHorizontal(50, 50, 200, 15, new ColorRGB(180, 10, 180));
        drawVertical(50, 50, 200, 15, new ColorRGB(10, 180, 180));

        for (String element : elements)
            output.println(element);


        output.println("</svg>");

        output.close();
    }

    private void paintBackground(ColorRGB c)
    {
        elements.add("<rect x=\"0\" y=\"0\" width=\"" + WIDTH + "\" height=\"" + HEIGHT + "\" fill=\"" + c.getCode() + "\"/>");
    }

    private void drawCircle(int cx, int cy, int r, ColorRGB c)
    {
        elements.add("<circle cx=\"" + cx + "\" cy=\"" + cy + "\" r=\"" + r + "\" fill=\"" + c.getCode() + "\"/>");
    }

    private void drawHorizontal(int x, int y, int width, int height, ColorRGB c)
    {
        elements.add("<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height
            + "\"" + " fill=\"" + c.getCode() + "\" rx=\"" + 5 + "\" />");
    }

    private void drawVertical(int x, int y, int width, int height, ColorRGB c)
    {
        elements.add("<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + height + "\" height=\"" + width
            + "\"" + " fill=\"" + c.getCode() + "\" rx=\"" + 5 + "\" />");
    }

    public void paintMaze(Maze m)
    {
        paintBackground(m.getSpaceColor());


    }
}