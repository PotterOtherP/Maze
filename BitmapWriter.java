import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BitmapWriter {
    
    public static void main(String[] args) throws IOException
    {
        String filename = "maze.bmp";
        String filedataname = "mazedata";

        FileOutputStream output = null;
        FileOutputStream outputData = null;

        try
        {
            output = new FileOutputStream(new File(filename));
            outputData = new FileOutputStream(new File(filedataname));
        }

        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        if (output == null) return;

        Bitmap image = new Bitmap();


        output.write(image.getFileSignature());
        outputData.write(image.getFileSignature());
        
        int[] header = image.getBitmapHeader();
        int[] dHeader = image.getDIBHeader();
        ColorRGB[] pixelColors = image.getPixels();

        writeIntArray(header, output);
        writeIntArray(dHeader, output);

        writeIntArray(header, outputData);
        writeIntArray(dHeader, outputData);

        // We want to convert the pixel information into a byte array, which
        // allows for the fastest writing. Writing one byte at a time is slow.

        byte[] pixelBytes = new byte[pixelColors.length * 3];

        for (int p = 0; p < pixelColors.length; ++p)
        {
            pixelBytes[(p * 3)] = (byte)pixelColors[p].getBlue();
            pixelBytes[(p * 3) + 1] = (byte)pixelColors[p].getGreen();
            pixelBytes[(p * 3) + 2] = (byte)pixelColors[p].getRed();
        }

        output.write(pixelBytes);
        outputData.write(pixelBytes);

        output.close();
        outputData.close();

    }

    private static void writeIntArray(int[] bArray, FileOutputStream fout) throws IOException
    {
        for (int i = 0; i < bArray.length; ++i)
            writeIntToBytes(bArray[i], fout);
    }

    private static void writeIntToBytes(int x, FileOutputStream fout) throws IOException
    {
        fout.write((byte)(x % 0x100));
        fout.write((byte)((x / 0x100) % 0x100));
        fout.write((byte)((x / 0x10000) % 0x100));
        fout.write((byte)((x / 0x1000000) % 0x100));
    }

}