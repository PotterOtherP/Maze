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
        }
        catch (InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }

    }
    public ColorRGB(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getRed() { return r; }
    public int getGreen() { return g; }
    public int getBlue() { return b; }


}