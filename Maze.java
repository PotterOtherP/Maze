import java.util.Random;

public class Maze {

    public static final int ROWS = 15;
    public static final int COLUMNS = 20;

    public static final int WALL_LEFT = 0;
    public static final int WALL_TOP = 0;
    public static final int WALL_RIGHT = COLUMNS - 1;
    public static final int WALL_BOTTOM = ROWS - 1;

    public static final int MAX_ITERATIONS = 1000;

    public static char[][] mazeGrid;

    public static void main(String[] args)
    {

        // System.out.println("Rows: " + ROWS);    
        // System.out.println("Columns: " + COLUMNS);    
        System.out.println();    

        mazeGrid = new char[ROWS][COLUMNS];

        initMaze();
        generateMaze();

        System.out.println();    
        displayMaze();

        System.out.println();    
        System.out.println("Maze complete: " + mazeComplete());


    }

    public static boolean generateMaze()
    {
        int iterations = 0;

        while (!mazeComplete())
        {
            ++iterations;

            if (iterations > MAX_ITERATIONS)
            {
                System.out.println("Could not construct maze in " + MAX_ITERATIONS + " iterations.");
                return false;
            }
        }

        return true;
    }


    public static void initMaze()
    {
        // Fill grid with letters and create border walls
        for (int i = 0; i < ROWS; ++i)
        {
            for (int j = 0; j < COLUMNS; ++j)
            {
                if (i == WALL_TOP || i == WALL_BOTTOM || j == WALL_LEFT || j == WALL_RIGHT)
                    mazeGrid[i][j] = 'W';
                else
                    mazeGrid[i][j] = 'O';
            }
        }

        // Select an entrance ("S") and exit ("E")

        int start = getRandom(COLUMNS);
        int exit = getRandom(COLUMNS);

        mazeGrid[WALL_TOP][start] = 'S';
        mazeGrid[WALL_BOTTOM][exit] = 'E';
    }


    public static void displayMaze()
    {
        for (int i = 0; i < ROWS; ++i)
        {
            for (int j = 0; j < COLUMNS; ++j)
            {
                System.out.print(mazeGrid[i][j] + " ");
            }

            System.out.print("\n");
        }
    }

    public static int getRandom(int n)
    {
       Random rand = new Random();
       return rand.nextInt(n);
    }

    public static boolean mazeComplete()
    {
        if (ROWS <= 2 || COLUMNS <= 2)
        {
            return true;
        }

        for (int i = 1; i < ROWS - 1; ++i)
        {
            for (int j = 1; j < COLUMNS -1; ++j)
            {
                if (mazeGrid[i][j] == 'O' &&
                    mazeGrid[i + 1][j] == 'O' &&
                    mazeGrid[i][j + 1] == 'O' &&
                    mazeGrid[i + 1][j + 1] == 'O')
                {
                    return false;
                }
            }
        }

        return true;
    }
    
}