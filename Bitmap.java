public class Bitmap {
    

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

    public Bitmap()
    {
        width = 640;
        height = 480;

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
        DIB_imageSize = width * height * Integer.BYTES * 3;
        DIB_Xppm = 0;
        DIB_Yppm = 0;
        DIB_colorsInColorTable = 0;
        DIB_colorCount = 0;

        header_FileSize = Integer.BYTES * (2 + 3 + 10) + DIB_imageSize;
        header_PixelOffset = Integer.BYTES * (2 + 3 + 10);

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
        fillPixelsBlue();

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

    public byte[] getFileSignature() { return FileSignature; }
    public int[] getBitmapHeader() { return BitmapHeader; }
    public int[] getDIBHeader() { return DIBHeader; }
    public ColorRGB[] getPixels() { return PixelArray; }
}