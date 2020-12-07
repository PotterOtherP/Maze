import java.util.InputMismatchException;

public class ColorRGB {
    
    private int r;
    private int g;
    private int b;


    // code e.g.: #ffaa00
    public ColorRGB(String code)
    {
        if (!code.startsWith("#") || code.length() != 7)
        {
            throw new IllegalArgumentException("Invalid color hash code");
        }

        try
        {
            this.r = Integer.parseInt(code.substring(1, 3), 16);
            this.g = Integer.parseInt(code.substring(3, 5), 16);
            this.b = Integer.parseInt(code.substring(5, 7), 16);

            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)
            {
                throw new IllegalArgumentException("Colors must be between 0 and 255");
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public ColorRGB(int r, int g, int b)
    {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)
        {
            throw new IllegalArgumentException("Colors must be between 0 and 255");
        }

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getRed() { return r; }
    public int getGreen() { return g; }
    public int getBlue() { return b; }

    public String getCode()
    {
        String rStr = Integer.toHexString(r);
        String gStr = Integer.toHexString(g);
        String bStr = Integer.toHexString(b);

        if (r < 0x10) rStr = "0" + rStr;
        if (g < 0x10) gStr = "0" + gStr;
        if (b < 0x10) bStr = "0" + bStr;


        return "#" + rStr + gStr + bStr;
    }


}