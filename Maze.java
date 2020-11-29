import java.util.Random;
import java.util.ArrayList;

public class Maze {

    public static final int COMPLEXITY_DEFAULT = 5;
    public static final int MAX_ITERATIONS = 1000;
    public static final char CH_WALL = 'X';
    public static final char CH_SPACE = '-';

    private char[][] mazeGrid;
    private ArrayList<WallPath> paths;
    private int complexity;
    private int rows;
    private int columns;
    private int wall_left;
    private int wall_top;
    private int wall_right;
    private int wall_bottom;
    private GridPoint startPoint;
    private GridPoint exitPoint;

    public Maze()
    {
        this(COMPLEXITY_DEFAULT);
    }


    public Maze(int comp)
    {
        if (comp < 3 || comp > 100)
        {
            throw new IllegalArgumentException("Maze complexity must be between 3 and 100.");
        }

        this.complexity = comp;
        this.rows = comp * 3;
        this.columns = comp * 4;
        this.wall_left = 0;
        this.wall_top = 0;
        this.wall_right = this.columns - 1;
        this.wall_bottom = this.rows - 1;

        mazeGrid = new char[rows][columns];
        paths = new ArrayList<WallPath>();

        this.init();
        this.generate();
    }

    private void addPath(int x, int y, int d)
    {
        if (mazeGrid[y][x] == CH_SPACE)
        {
            paths.add(new WallPath(x, y, d));
            mazeGrid[y][x] = CH_WALL;
        }

    }

    private void addLeftWallPaths()
    {
        Random rando = new Random();

        // Left wall
        int pathX = wall_left + 1;
        int[] yCoords = new int[complexity / 2];

        if (yCoords.length <= 0) return;
        yCoords[0] = rando.nextInt(rows - 4) + 2;
        addPath(pathX, yCoords[0], 4);

        for (int i = 1; i < yCoords.length; ++i)
        {
            int nextY = yCoords[0];

            boolean condition = true;

            while (condition)
            {
                nextY = rando.nextInt(rows - 4) + 2;
                condition = false;

                for (int j = 0; j < i; ++j)
                {
                    if (Math.abs(nextY - yCoords[j]) < 2)
                        condition = true;
                }

            }

            yCoords[i] = nextY;
            addPath(pathX, nextY, 4);
        }

    }

    private void addRightWallPaths()
    {
        Random rando = new Random();

        // Right wall
        int pathX = wall_right - 1;
        int[] yCoords = new int[complexity / 2];

        if (yCoords.length <= 0) return;
        yCoords[0] = rando.nextInt(rows - 4) + 2;
        addPath(pathX, yCoords[0], 3);

        for (int i = 1; i < yCoords.length; ++i)
        {
            int nextY = yCoords[0];

            boolean condition = true;

            while (condition)
            {
                nextY = rando.nextInt(rows - 4) + 2;
                condition = false;

                for (int j = 0; j < i; ++j)
                {
                    if (Math.abs(nextY - yCoords[j]) < 2)
                        condition = true;
                }

            }

            yCoords[i] = nextY;
            addPath(pathX, nextY, 3);
        }

    }

    private void addRandomInteriorPaths(int numPaths)
    {
        for (int i = 0; i < numPaths; ++i)
        {
            int randX = getRandom(columns - 4) + 2;
            int randY = getRandom(rows - 4) + 2;
            int randD = getRandom(4) + 1;
            boolean condition = true;

            for (int j = randY - 1; j <= randY + 1; ++j)
            {
                for (int k = randX - 1; k <= randX + 1; ++k)
                {
                    if (mazeGrid[j][k] == CH_WALL)
                    {
                        condition = false;
                    }                   
                }
             
            }

            if (condition)
            {
                addPath(randX, randY, randD);
                mazeGrid[randY][randX] = CH_WALL;
            }

            else
            {
                // System.out.println("Condition violated at (" + randX + ", " + randY + ").");
            }
        }

    }

    public void display()
    {
        for (WallPath path : paths)
        {
            mazeGrid[path.points.get(0).y][path.points.get(0).x] = 'O';
        }

        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < columns; ++j)
            {
                System.out.print(mazeGrid[i][j] + " ");
            }

            System.out.print("\n");
        }

    }

    private boolean generate()
    {
        int iterations = 0;

        // Create the seed WallPaths... Around the entrance and exit
        addPath(startPoint.x - 1, startPoint.y + 1, 2);
        addPath(startPoint.x + 1, startPoint.y + 1, 2);
        addPath(exitPoint.x - 1, exitPoint.y - 1, 1);
        addPath(exitPoint.x + 1, exitPoint.y - 1, 1);

        addLeftWallPaths();
        addRightWallPaths();

        addRandomInteriorPaths(complexity);
        System.out.println("Complexity: " + complexity);


        while (!this.isComplete())
        {
            // Main path updates
            for (WallPath path : paths)
            {
                int dieRoll = new Random().nextInt(100) + 1;

                if (path.active && pathIsClear(path.getCheckPoint(), path.getDirection()))
                {
                    if (dieRoll <= 70)
                    {
                        path.grow();
                        path.checkActive();
                    }
                    else
                        path.changeDirection();
                }
                else
                {
                    path.changeDirection();
                    path.checkActive();
                }

                // update the grid letters
                for (GridPoint pt : path.points)
                {
                    mazeGrid[pt.y][pt.x] = CH_WALL;
                }
            }


            ++iterations;

            if (iterations > MAX_ITERATIONS)
            {
                System.out.println("Could not complete maze in " + MAX_ITERATIONS + " iterations.");
                return false;
            }
        }

        System.out.println("Maze generated in " + iterations + " iterations.");
        return true;

    }

    public static int getRandom(int n)
    {
       Random rand = new Random();
       return rand.nextInt(n);

    }

    private void init()
    {
        // Fill grid with letters and create border walls
        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < columns; ++j)
            {
                if (i == wall_top || i == wall_bottom || j == wall_left || j == wall_right)
                    mazeGrid[i][j] = CH_WALL;
                else
                    mazeGrid[i][j] = CH_SPACE;
            }
        }

        // Select an entrance ("S") and exit ("E")

        int startX = getRandom(columns - 6) + 3;
        int exitX = getRandom(columns - 6) + 3;

        mazeGrid[wall_top][startX] = 'S';
        mazeGrid[wall_bottom][exitX] = 'E';

        startPoint = new GridPoint(startX, wall_top);
        exitPoint = new GridPoint(exitX, wall_bottom);

    }

    public boolean isComplete()
    {
        for (int i = 1; i < rows - 2; ++i)
        {
            for (int j = 1; j < columns - 2; ++j)
            {
                if (mazeGrid[i][j] == CH_SPACE &&
                    mazeGrid[i + 1][j] == CH_SPACE &&
                    mazeGrid[i][j + 1] == CH_SPACE &&
                    mazeGrid[i + 1][j + 1] == CH_SPACE)
                {
                    // int chance = new Random().nextInt(20);
                    // if (chance == 1)
                    // {
                    //     int spot = new Random().nextInt(4);
                    //     int dir = new Random().nextInt(4) + 1;

                    //     if (spot == 0) addPath(j, i, dir);
                    //     else if (spot == 1) addPath(j, i + 1, dir);
                    //     else if (spot == 2) addPath(j + 1, i, dir);
                    //     else addPath(j + 1, i + 1, dir);
                    // }
                    return false;
                }
            }
        }

        return true;

    }

    private boolean pathIsClear(GridPoint g, int d)
    {

        int checkX = Math.max(wall_left, g.x);
        int checkY = Math.max(wall_top, g.y);

        checkX = Math.min(wall_right, g.x);
        checkY = Math.min(wall_bottom, g.y);


        if (mazeGrid[checkY][checkX] == CH_WALL)
            return false;

        switch (d)
        {
            case 1:
            {
                if (mazeGrid[checkY][checkX + 1] == CH_WALL     ||
                    mazeGrid[checkY][checkX - 1] == CH_WALL     ||
                    mazeGrid[checkY + 1][checkX + 1] == CH_WALL ||
                    mazeGrid[checkY + 1][checkX] == CH_WALL     ||
                    mazeGrid[checkY + 1][checkX - 1] == CH_WALL)
                {
                    return false;
                }

            } break;
            
            case 2:
            {
                if (mazeGrid[checkY][checkX + 1] == CH_WALL     ||
                    mazeGrid[checkY][checkX - 1] == CH_WALL     ||
                    mazeGrid[checkY - 1][checkX + 1] == CH_WALL ||
                    mazeGrid[checkY - 1][checkX] == CH_WALL     ||
                    mazeGrid[checkY - 1][checkX - 1] == CH_WALL)
                {
                    return false;
                }

            } break;

            case 3:
            {
                if (mazeGrid[checkY + 1][checkX] == CH_WALL     ||
                    mazeGrid[checkY - 1][checkX] == CH_WALL     ||
                    mazeGrid[checkY + 1][checkX + 1] == CH_WALL ||
                    mazeGrid[checkY][checkX + 1] == CH_WALL     ||
                    mazeGrid[checkY - 1][checkX + 1] == CH_WALL)
                {   
                    return false;
                }

            } break;

            case 4:
            {
                if (mazeGrid[checkY + 1][checkX] == CH_WALL     ||
                    mazeGrid[checkY - 1][checkX] == CH_WALL     ||
                    mazeGrid[checkY + 1][checkX - 1] == CH_WALL ||
                    mazeGrid[checkY][checkX - 1] == CH_WALL     ||
                    mazeGrid[checkY - 1][checkX - 1] == CH_WALL)
                {   
                    return false;
                }

            } break;

            default: {} break;
        }
        
        return true;
    }

}