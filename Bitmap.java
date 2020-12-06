import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Bitmap {
    
    private String filename;

    private int width;
    private int height;

    private byte[] FileSignature;
    private final char fileSig_Char1 = 'B';
    private final char fileSig_Char2 = 'M';


    private int[] BitmapHeader;
    private int header_FileSize;
    private final int header_Reserved = 0;
    private int header_PixelOffset = 0;

    private int[] DIBHeader;
    private int DIB_headerSize;
    private int DIB_imageWidth;
    private int DIB_imageHeight;
    private int DIB_planesAndBPP;
    private int DIB_compression;
    private int DIB_imageSize;
    private int DIB_Xppm;
    private int DIB_Yppm;
    private int DIB_colorsInColorTable;
    private int DIB_colorCount;


    private ColorRGB[] PixelArray;

    public Bitmap(int w, int h, String str)
    {
        filename = str;

        width = w;
        height = h;

        FileSignature = new byte[2];

        FileSignature[0] = fileSig_Char1;
        FileSignature[1] = fileSig_Char2;


        BitmapHeader = new int[3];


        DIBHeader = new int[10];

        DIB_headerSize = Integer.BYTES * 10;
        DIB_imageWidth = width;
        DIB_imageHeight = height;
        DIB_planesAndBPP = 0x1 | 0x180000;
        DIB_compression = 0;
        DIB_imageSize = width * height * 3;
        DIB_Xppm = 0;
        DIB_Yppm = 0;
        DIB_colorsInColorTable = 0;
        DIB_colorCount = 0;

        header_FileSize = 2 + Integer.BYTES * (3 + 10) + DIB_imageSize;
        header_PixelOffset = 2 + Integer.BYTES * (3 + 10);

        BitmapHeader[0] = header_FileSize;
        BitmapHeader[1] = header_Reserved;
        BitmapHeader[2] = header_PixelOffset;

        DIBHeader[0] = DIB_headerSize;
        DIBHeader[1] = DIB_imageWidth;
        DIBHeader[2] = DIB_imageHeight;
        DIBHeader[3] = DIB_planesAndBPP;
        DIBHeader[4] = DIB_compression;
        DIBHeader[5] = DIB_imageSize;
        DIBHeader[6] = DIB_Xppm;
        DIBHeader[7] = DIB_Yppm;
        DIBHeader[8] = DIB_colorsInColorTable;
        DIBHeader[9] = DIB_colorCount;

        PixelArray = new ColorRGB[width * height];
        fillPixelsGreen();

    }

    private void fillPixelsBlack()
    {
        for (int i = 0; i < PixelArray.length; ++i)
                PixelArray[i] = new ColorRGB(0, 0, 0);
    }

    private void fillPixelsBlue()
    {
        for (int i = 0; i < PixelArray.length; ++i)
                PixelArray[i] = new ColorRGB(10, 10, 200);
    }

    private void fillPixelsGreen()
    {
        for (int i = 0; i < PixelArray.length; ++i)
                PixelArray[i] = new ColorRGB(10, 200, 10);
    }

    public void writeFile() throws IOException
    {
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

        output.write(FileSignature);
        writeIntArray(BitmapHeader, output);
        writeIntArray(DIBHeader, output);

        byte[] pixelBytes = new byte[PixelArray.length * 3];

        for (int p = 0; p < PixelArray.length; ++p)
        {
            pixelBytes[(p * 3)] = (byte)PixelArray[p].getBlue();
            pixelBytes[(p * 3) + 1] = (byte)PixelArray[p].getGreen();
            pixelBytes[(p * 3) + 2] = (byte)PixelArray[p].getRed();
        }

        output.write(pixelBytes);

        output.close();
    }

    private void writeIntArray(int[] bArray, FileOutputStream fout) throws IOException
    {
        for (int i = 0; i < bArray.length; ++i)
            writeIntToBytes(bArray[i], fout);
    }

    private void writeIntToBytes(int x, FileOutputStream fout) throws IOException
    {
        fout.write((byte)(x % 0x100));
        fout.write((byte)((x / 0x100) % 0x100));
        fout.write((byte)((x / 0x10000) % 0x100));
        fout.write((byte)((x / 0x1000000) % 0x100));
    }

    public void writePixel(int x, int y, ColorRGB c)
    {
        try
        {
            PixelArray[(y * width) + x] = c;
        }

        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Could not write pixel (out of bounds) at " + x + ", " + y);
        }

    }

    public void setFilename(String str) { filename = str; }
    public byte[] getFileSignature() { return FileSignature; }
    public int[] getBitmapHeader() { return BitmapHeader; }
    public int[] getDIBHeader() { return DIBHeader; }
    public ColorRGB[] getPixels() { return PixelArray; }
}