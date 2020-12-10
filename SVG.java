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

        int columns = m.getComplexity() * 4;
        int rows = m.getComplexity() * 3;

        int columnPixels = WIDTH / columns;
        int rowPixels = HEIGHT / rows;

        char[][] grid = m.getGrid();

        // Horizontal wall sections
        for (int i = 0; i < rows; ++i)
        {
            int sectionLength = 0;
            int sectionX = 0;
            int sectionY = i * rowPixels;

            for (int j = 0; j < columns; ++j)
            {
                if (grid[i][j] == Maze.CH_WALL)
                {
                    if (sectionLength == 0)
                        sectionX = j * columnPixels;

                    ++sectionLength;
                }

                if (grid[i][j] != Maze.CH_WALL || j == columns - 1)
                {
                    if (sectionLength > 1)
                    {
                        drawHorizontal(sectionX, sectionY, sectionLength * columnPixels, rowPixels, m.getWallColor());
                    }

                    sectionLength = 0;
                }
            }
        }

        // Vertical wall sections
        for (int i = 0; i < columns; ++i)
        {
            int sectionLength = 0;
            int sectionX = i * columnPixels;
            int sectionY = 0;

            for (int j = 0; j < rows; ++j)
            {
                if (grid[j][i] == Maze.CH_WALL)
                {
                    if (sectionLength == 0)
                        sectionY = j * rowPixels;

                    ++sectionLength;
                }

                if (grid[j][i] != Maze.CH_WALL || j == rows - 1)
                {
                    if (sectionLength > 1)
                    {
                        drawVertical(sectionX, sectionY, sectionLength * rowPixels, columnPixels, m.getWallColor());
                    }

                    sectionLength = 0;
                }
            }
        }


    }
}