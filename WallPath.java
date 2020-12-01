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

    public WallPath branch()
    {
        int index = new Random().nextInt(points.size() - 2) + 1;

        int x = points.get(index).x;
        int y = points.get(index).y;

        int dirNew = 1;
        
        GridPoint gUp = new GridPoint(x, y - 1);
        GridPoint gDown = new GridPoint(x, y + 1);
        GridPoint gLeft = new GridPoint(x - 1, y);
        GridPoint gRight = new GridPoint(x + 1, y);

        int nx = x;
        int ny = y;

        // Path is vertical
        if ( (points.get(index - 1).equals(gUp) && points.get(index + 1).equals(gDown)) ||
             (points.get(index + 1).equals(gUp) && points.get(index - 1).equals(gDown)) )
        {
            int roll = new Random().nextInt(2);

            nx = (roll == 0 ? x + 1 : x - 1);
            dirNew = (roll == 0 ? 4 : 3);
            return new WallPath(nx, ny, dirNew);
        }

        // Path is horizontal
        if ( (points.get(index - 1).equals(gLeft) && points.get(index + 1).equals(gRight)) ||
             (points.get(index + 1).equals(gLeft) && points.get(index - 1).equals(gRight)) )
        {
            int roll = new Random().nextInt(2);

            ny = (roll == 0 ? y + 1 : y - 1);
            dirNew = (roll == 0 ? 2 : 1);
            return new WallPath(nx, ny, dirNew);
        }

        // Corner, upper right
        if ( (points.get(index - 1).equals(gUp) && points.get(index + 1).equals(gRight)) ||
             (points.get(index + 1).equals(gRight) && points.get(index - 1).equals(gUp)) )
        {
            int roll = new Random().nextInt(2);

            nx = (roll == 0 ? x - 1 : x);
            ny = (roll == 0 ? y : y + 1);
            dirNew = (roll == 0 ? 3 : 2);
            return new WallPath(nx, ny, dirNew);
        }

        // Corner, upper left
        if ( (points.get(index - 1).equals(gUp) && points.get(index + 1).equals(gLeft)) ||
             (points.get(index + 1).equals(gLeft) && points.get(index - 1).equals(gUp)) )
        {
            int roll = new Random().nextInt(2);

            nx = (roll == 0 ? x + 1 : x);
            ny = (roll == 0 ? y : y + 1);
            dirNew = (roll == 0 ? 4 : 2);
            return new WallPath(nx, ny, dirNew);
        }

        // Corner, lower right
        if ( (points.get(index - 1).equals(gDown) && points.get(index + 1).equals(gRight)) ||
             (points.get(index + 1).equals(gRight) && points.get(index - 1).equals(gDown)) )
        {
            int roll = new Random().nextInt(2);

            nx = (roll == 0 ? x - 1 : x);
            ny = (roll == 0 ? y : y - 1);
            dirNew = (roll == 0 ? 3 : 1);
            return new WallPath(nx, ny, dirNew);
        }

        // Corner, lower left
        if ( (points.get(index - 1).equals(gDown) && points.get(index + 1).equals(gLeft)) ||
             (points.get(index + 1).equals(gLeft) && points.get(index - 1).equals(gDown)) )
        {
            int roll = new Random().nextInt(2);

            nx = (roll == 0 ? x + 1 : x);
            ny = (roll == 0 ? y : y - 1);
            dirNew = (roll == 0 ? 4 : 1);
            return new WallPath(nx, ny, dirNew);
        }

        return new WallPath(nx, ny, dirNew);
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

    public GridPoint getBranchCheckPoint()
    {
        int checkX = points.get(0).x;
        int checkY = points.get(0).y;

        switch (direction)
        {
            case 1:
            {
                return new GridPoint(checkX, checkY - 1);
            } // break;

            case 2:
            {
                return new GridPoint(checkX, checkY + 1);
            } // break;

            case 3:
            {
                return new GridPoint(checkX - 1, checkY);
            } // break;

            case 4:
            {
                return new GridPoint(checkX + 1, checkY);
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

        // active = true;

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

    public void rotateClockwise()
    {
        switch (direction)
        {
            case 1: { direction = 4; } break;
            case 2: { direction = 3; } break;
            case 3: { direction = 1; } break;
            case 4: { direction = 2; } break;
            default: { } break;
        }
    }

    public void rotateCounterclockwise()
    {
        switch (direction)
        {
            case 1: { direction = 3; } break;
            case 2: { direction = 4; } break;
            case 3: { direction = 2; } break;
            case 4: { direction = 1; } break;
            default: { } break;
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