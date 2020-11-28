import java.util.ArrayList;
import java.util.Random;

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
    public boolean active;

    public WallPath(int startX, int startY, int direction)
    {
        setDirection(direction);

        points = new ArrayList<GridPoint>();

        points.add(new GridPoint(startX, startY));

        active = true;
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

    public GridPoint getCheckPoint()
    {

        int checkX = points.get(points.size() - 1).x;
        int checkY = points.get(points.size() - 1).y;

        switch (direction)
        {
            case 1:
            {
                return new GridPoint(checkX, checkY - 2);
            } // break;

            case 2:
            {
                return new GridPoint(checkX, checkY + 2);
            } // break;

            case 3:
            {
                return new GridPoint(checkX - 2, checkY);
            } // break;

            case 4:
            {
                return new GridPoint(checkX + 2, checkY);
            } // break;

            default: {} break;
        }

        return null;

    }

    public void checkActive()
    {
        int dieRoll = new Random().nextInt(100) + 1;

        if (points.size() > 10 && dieRoll < points.size() * 2)
        {
            active = false;
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

    public void changeDirection()
    {
        int opt = new Random().nextInt(2);

        switch (direction)
        {
            case 1:
            case 2:
            {
                direction = (opt == 0 ? 3 : 4);
            } break;

            case 3:
            case 4:
            {
                direction = (opt == 0 ? 1 : 2);
            } break;

            default: {} break;
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