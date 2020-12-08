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

        paintBackground(new ColorRGB(220, 220, 220));

        drawCircle(500, 500, 300, new ColorRGB(180, 10, 180));

        for (String element : elements)
            output.println(element);


        output.println("</svg>");

        output.close();
    }

    private void drawCircle(int cx, int cy, int r, ColorRGB c)
    {
        elements.add("<circle cx=\"" + cx + "\" cy=\"" + cy + "\" r=\"" + r + "\" fill=\"" + c.getCode() + "\"/>");
    }

    private void drawPath(WallPath path, int complexity, ColorRGB c)
    {
        int columnPixels = WIDTH / (complexity * 4);
        int rowPixels = HEIGHT / (complexity * 3);
        
        String result = "<path d=\"";

        // create a new 2D array with the center pixels of the grid points
        int[][] pathPoints = new int[path.points.size()][2];

        for (int i = 0; i < path.points.size(); ++i)
        {
            pathPoints[i][0] = columnPixels * path.points.get(i).x + (columnPixels / 2);
            pathPoints[i][1] = rowPixels * path.points.get(i).y + (rowPixels / 2);
        }



        result += "\" />";
        elements.add(result);

    }

    private void paintBackground(ColorRGB c)
    {
        elements.add("<rect x=\"0\" y=\"0\" width=\"" + WIDTH + "\" height=\"" + HEIGHT + "\" fill=\"" + c.getCode() + "\"/>");
    }

    public void paintMaze(Maze m,)
    {
        paintBackground(m.getSpaceColor());

        for (WallPath p : m.getWallPaths())
        {
            drawPath(p, m.getComplexity(), m.getWallColor());
        }
    }
}