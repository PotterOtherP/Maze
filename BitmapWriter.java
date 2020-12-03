import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BitmapWriter {
    
    public static void main(String[] args) throws IOException
    {
        String filename = "maze";

        FileOutputStream output = null;

        try
        {
            output = new FileOutputStream(new File(filename));
        }

        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        if (output == null) return;

        output.write('B');
        output.write('M');

    }
}