import java.util.Random;
import java.util.ArrayList;

public class Maze {

    public static final int ROWS_DEFAULT = 15;
    public static final int COLUMNS_DEFAULT = 20;


    public static final int MAX_ITERATIONS = 1000;

    private char[][] mazeGrid;
    private ArrayList<WallPath> paths;
    private int rows;
    private int columns;
    private int wall_left;
    private int wall_top;
    private int wall_right;
    private int wall_bottom;

    public Maze()
    {
        this(ROWS_DEFAULT, COLUMNS_DEFAULT);
    }

    public Maze(int rows, int columns)
    {
        if (rows < 3 || columns < 3)
        {
            throw new IllegalArgumentException("Maze must have more than 2 rows and columns.");
        }

        this.rows = rows;
        this.columns = columns;
        this.wall_left = 0;
        this.wall_top = 0;
        this.wall_right = this.columns - 1;
        this.wall_bottom = this.rows - 1;

        mazeGrid = new char[rows][columns];
        paths = new ArrayList<WallPath>();

        this.init();
        this.generate();
    }


    private boolean generate()
    {
        int iterations = 0;

        // Create the seed WallPaths

        while (!this.isComplete())
        {
            
            
            ++iterations;

            if (iterations > MAX_ITERATIONS)
            {
                System.out.println("Could not construct maze in " + MAX_ITERATIONS + " iterations.");
                return false;
            }
        }

        System.out.println("Maze generated in " + iterations + " iterations.");
        return true;
    }


    private void init()
    {
        // Fill grid with letters and create border walls
        for (int i = 0; i < this.rows; ++i)
        {
            for (int j = 0; j < this.columns; ++j)
            {
                if (i == this.wall_top || i == this.wall_bottom || j == this.wall_left || j == this.wall_right)
                    mazeGrid[i][j] = 'w';
                else
                    mazeGrid[i][j] = 'o';
            }
        }

        // Select an entrance ("S") and exit ("E")

        int start = getRandom(this.columns - 2);
        int exit = getRandom(this.columns - 2);

        mazeGrid[this.wall_top][start + 1] = 'S';
        mazeGrid[this.wall_bottom][exit + 1] = 'E';
    }


    public void display()
    {
        for (int i = 0; i < this.rows; ++i)
        {
            for (int j = 0; j < this.columns; ++j)
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

    public boolean isComplete()
    {
        for (int i = 1; i < this.rows - 1; ++i)
        {
            for (int j = 1; j < this.columns -1; ++j)
            {
                if (mazeGrid[i][j] == 'o' &&
                    mazeGrid[i + 1][j] == 'o' &&
                    mazeGrid[i][j + 1] == 'o' &&
                    mazeGrid[i + 1][j + 1] == 'o')
                {
                    return false;
                }
            }
        }

        return true;
    }
    
}