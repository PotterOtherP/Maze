import java.util.Random;
import java.util.ArrayList;
import java.io.IOException;

public class Maze {

    public static final int COMPLEXITY_DEFAULT = 5;
    public static final int COMPLEXITY_MIN = 3;
    public static final int COMPLEXITY_MAX = 100;
    public static final int MAX_ITERATIONS = 500;
    public static final int MAZE_BITMAP_WIDTH = 1200;
    public static final int MAZE_BITMAP_HEIGHT = 900;


    public static final char CH_WALL = 'X';
    public static final char CH_SPACE = '-';

    private char[][] mazeGrid;
    private ArrayList<WallPath> paths;
    private ArrayList<WallPath> branches;
    private int complexity;
    private int rows;
    private int columns;
    private int wall_left;
    private int wall_top;
    private int wall_right;
    private int wall_bottom;
    private int while_control;
    private int branch_percent = 10;
    private GridPoint startPoint;
    private GridPoint exitPoint;

    private ColorRGB wallColor;
    private ColorRGB spaceColor;

    private String filename;
    public static final String FILENAME_DEFAULT = "maze_default.bmp";


    public Maze()
    {
        this(COMPLEXITY_DEFAULT, FILENAME_DEFAULT);
    }

    public Maze(int comp)
    {
        this(comp, FILENAME_DEFAULT);
    }


    public Maze(int comp, String name)
    {
        if (comp < COMPLEXITY_MIN || comp > COMPLEXITY_MAX)
        {
            throw new IllegalArgumentException("Maze complexity must be between " + COMPLEXITY_MIN
            + " and " + COMPLEXITY_MAX + ".");
        }

        this.complexity = comp;
        this.rows = comp * 3;
        this.columns = comp * 4;
        this.wall_left = 0;
        this.wall_top = 0;
        this.wall_right = this.columns - 1;
        this.wall_bottom = this.rows - 1;

        filename = name;

        mazeGrid = new char[rows][columns];
        paths = new ArrayList<WallPath>();
        branches = new ArrayList<WallPath>();

        // wallColor = new ColorRGB(10, 150, 10);
        // spaceColor = new ColorRGB(10, 10, 150);

        randomizeColors();

        this.init();
        this.generate();
        this.createBitmap();
    }

    private void addPath(int x, int y, int d)
    {
        if (mazeGrid[y][x] == CH_SPACE)
        {
            paths.add(new WallPath(x, y, d));
            mazeGrid[y][x] = CH_WALL;
        }

    }

    private void addTopWallPaths()
    {
        Random rando = new Random();

        int pathY = wall_top + 1;
        int[] xCoords = new int[complexity / 2];

        if (xCoords.length <= 0) return;

        if (startPoint.x >= columns / 2)
            xCoords[0] = rando.nextInt(startPoint.x - 4 ) + 2;
        else
            xCoords[0] = rando.nextInt(columns - startPoint.x - 2) + startPoint.x + 1;

        addPath(xCoords[0], pathY, 2);

        for (int i = 1; i < xCoords.length; ++i)
        {
            int nextX = xCoords[0];
            boolean condition = true;

            while(condition)
            {
                nextX = rando.nextInt(columns - 4) + 2;
                condition = false;

                for (int j = 0; j < i; ++j)
                {
                    if (Math.abs(nextX - xCoords[j]) < 2)
                        condition = true;
                }

                if (Math.abs(nextX - startPoint.x) < 3)
                    condition = true;
            }

            xCoords[i] = nextX;
            addPath(nextX, pathY, 2);
        }

    }

    private void addBottomWallPaths()
    {
        Random rando = new Random();

        int pathY = wall_bottom - 1;
        int[] xCoords = new int[complexity / 2];

        if (exitPoint.x >= columns / 2)
            xCoords[0] = rando.nextInt(exitPoint.x - 4 ) + 2;
        else
            xCoords[0] = rando.nextInt(columns - exitPoint.x - 2) + exitPoint.x + 1;

        addPath(xCoords[0], pathY, 1);

        for (int i = 1; i < xCoords.length; ++i)
        {
            int nextX = xCoords[0];
            boolean condition = true;

            while(condition)
            {
                nextX = rando.nextInt(columns - 4) + 2;
                condition = false;

                for (int j = 0; j < i; ++j)
                {
                    if (Math.abs(nextX - xCoords[j]) < 2)
                        condition = true;
                }

                if (Math.abs(nextX - exitPoint.x) < 3)
                    condition = true;
            }

            xCoords[i] = nextX;
            addPath(nextX, pathY, 1);
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

    private void createBitmap()
    {
        try
        {
            Bitmap bitmap = new Bitmap(MAZE_BITMAP_WIDTH, MAZE_BITMAP_HEIGHT, filename + ".bmp");

            int columnPixels = MAZE_BITMAP_WIDTH / columns;
            int rowPixels = MAZE_BITMAP_HEIGHT / rows;

            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < columns; ++j)
                {
                    ColorRGB rectColor = mazeGrid[i][j] == CH_WALL ? wallColor: spaceColor;

                    int rectX = j * columnPixels;
                    int rectY = i * rowPixels;

                    bitmap.fillRectangle(rectX, rectY, columnPixels, rowPixels, rectColor);
                }

            bitmap.writeFile();
        }

        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }

    private void createSVG()
    {

    }

    public void display()
    {
        // shows path origins
        // for (WallPath path : paths)
        // {
        //     mazeGrid[path.points.get(0).y][path.points.get(0).x] = 'O';
        // }

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
        // Create the seed WallPaths... Around the entrance and exit
        addPath(startPoint.x - 1, startPoint.y + 1, 2);
        addPath(startPoint.x + 1, startPoint.y + 1, 2);
        addPath(exitPoint.x - 1, exitPoint.y - 1, 1);
        addPath(exitPoint.x + 1, exitPoint.y - 1, 1);

        addLeftWallPaths();
        addRightWallPaths();
        addTopWallPaths();
        addBottomWallPaths();

        addRandomInteriorPaths(complexity);
        // System.out.println("Complexity: " + complexity);

        for (int iter = 0; iter <= MAX_ITERATIONS; ++iter)
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
                    // path.checkActive();
                }

                if (!path.active && dieRoll <= branch_percent)
                {
                    branches.add(path.branch());
                }

                // update the grid letters
                for (GridPoint pt : path.points)
                {
                    mazeGrid[pt.y][pt.x] = CH_WALL;
                }
            }

            for (WallPath branch : branches)
            {
                if (pathIsClear(branch.getBranchCheckPoint(), branch.getDirection()))
                    paths.add(branch);
            }

            branches.clear();

        }

        while_control = 0;

        while (!this.isComplete())
        {
        
            ++while_control;
            if (while_control > 1000)
                break;    
        }

        return true;

    }

    private double getWallPercent()
    {
        int total = (rows - 1 ) * (columns - 1);
        int wallCount = 0;

        for (int a = 1; a <= rows - 1; ++a)
            for (int b = 1; b <= columns - 1; ++b)
            {
                if (mazeGrid[a][b] == CH_WALL)
                    ++wallCount;
            }

        return(wallCount * 100.0) / (total * 1.0);

    }

    public static int getRandom(int n)
    {
       Random rand = new Random();
       return rand.nextInt(n);

    }

    private void randomizeColors()
    {
        Random rand = new Random();

        int wallRed = rand.nextInt(180) + 30;
        int wallGreen = rand.nextInt(180) + 30;
        int wallBlue = rand.nextInt(180) + 30;

        int spaceRed = (255 - wallRed) / 2;
        int spaceGreen = (255 - wallGreen) / 2;
        int spaceBlue = (255 - wallBlue) / 2;

        wallColor = new ColorRGB(wallRed, wallGreen, wallBlue);
        spaceColor = new ColorRGB(spaceRed, spaceGreen, spaceBlue);

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
        boolean result = true;

        // Horizontal set of 2 x 3 points
        for (int i = 1; i < rows - 2; ++i)
        {
            for (int j = 1; j < columns - 2; ++j)
            {
                if (mazeGrid[i][j] == CH_SPACE &&
                    mazeGrid[i][j + 1] == CH_SPACE &&
                    mazeGrid[i][j - 1] == CH_SPACE &&
                    mazeGrid[i + 1][j] == CH_SPACE &&
                    mazeGrid[i + 1][j + 1] == CH_SPACE &&
                    mazeGrid[i + 1][j - 1] == CH_SPACE)
                {
                    int roll = new Random().nextInt(2);

                    if (roll == 0 && mazeGrid[i - 1][j] == CH_WALL)
                    {
                        addPath(j, i, 2);
                        // System.out.println("Path added at (" + j + ", " + i + ").");
                    }

                    else if (roll == 0 && mazeGrid[i + 2][j] == CH_WALL)
                    {
                        addPath(j, i + 1, 1);
                        // System.out.println("Path added at (" + j + ", " + i + ").");
                    }
                    
                    result = false;
                }
            }
        }

        for (int i = 1; i < rows - 2; ++i)
        {
            for (int j = 1; j < columns - 2; ++j)
            {

                // Vertical set of 2 x 3 points
                if (mazeGrid[i][j] == CH_SPACE &&
                    mazeGrid[i][j + 1] == CH_SPACE &&
                    mazeGrid[i - 1][j] == CH_SPACE &&
                    mazeGrid[i - 1][j + 1] == CH_SPACE &&
                    mazeGrid[i + 1][j] == CH_SPACE &&
                    mazeGrid[i + 1][j + 1] == CH_SPACE)
                {
                    int roll = new Random().nextInt(2);

                    if (roll == 0 && mazeGrid[i][j - 1] == CH_WALL)
                    {
                        addPath(j, i, 3);
                        // System.out.println("Path added at (" + j + ", " + i + ").");
                    }

                    else if (roll == 0 && mazeGrid[i][j + 2] == CH_WALL)
                    {
                        addPath(j + 1, i, 4);
                        // System.out.println("Path added at (" + j + ", " + i + ").");
                    }
                    
                    result = false;
                }
            }

        }

        return result;

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

    public int getComplexity() { return complexity; }

    public char[][] getGrid() {return mazeGrid; }

}