public class GridPoint {
    
    public final int x;
    public final int y;

    public GridPoint(int x, int y)
    {
        this.x = Math.max(0, x);
        this.y = Math.max(0, y);
    }

    public String toString()
    {
        String result = "";
        result += "(" + x + ", " + y + ")";
        return result;
    }

    public boolean equals(Object ob)
    {
        if (ob instanceof GridPoint)
        {
            GridPoint g = (GridPoint)(ob);

            return (this.x == g.x && this.y == g.y);
        }

        return false;
    }
}