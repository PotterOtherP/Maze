import java.util.ArrayList;

public class WallPath {

    /**
     *  Direction codes:
     *  
     *  Up - 1
     *  Down - 2
     *  Left - 3
     *  Right  - 4
     */
    private int direction;

    public ArrayList<GridPoint> points;

    public WallPath(int startX, int startY, int direction)
    {
        setDirection(direction);

        points = new ArrayList<GridPoint>();

        points.add(new GridPoint(startX, startY));
    }

    public void grow()
    {
        int newX = points.get(points.size() - 1).x;
        int newY = points.get(points.size() - 1).y;

        switch (direction)
        {
            case 1:
            {
                --newY;
                points.add(new GridPoint(newX, newY));
            } break;

            case 2:
            {
                ++newY;
                points.add(new GridPoint(newX, newY));
            } break;

            case 3:
            {
                --newX;
                points.add(new GridPoint(newX, newY));
            } break;

            case 4:
            {
                ++newX;
                points.add(new GridPoint(newX, newY));
            } break;

            default: {} break;
        }
    }


    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int d)
    {
        switch (d)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            {
                direction = d;

            } break;

            default:
            {
                throw new IllegalArgumentException("Invalid direction.");
            } // break;
        }
    }

    public String toString()
    {
        String result = "";

        for (GridPoint p : points)
        {
            result += ("(" + p.x + ", " + p.y + ")\n");
        }

        return result;
    }
    
}