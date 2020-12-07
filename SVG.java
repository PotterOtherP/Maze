import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SVG {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;
    
    private String filename;

    public SVG(String str)
    {
        filename = str;
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

        output.println("<circle cx=\"600\" cy=\"450\" r=\"200\" fill=\"#0a0\"/>");


        output.println("</svg>");

        output.close();
    }

    private void drawCircle(int cx, int cy, int r, ColorRGB c, PrintStream out)
    {
        
    }
}