public class MazeGame {
    
    public static void main(String[] args)
    {
        int complexity = Maze.COMPLEXITY_DEFAULT;

        if (args.length > 0)
            complexity = Integer.parseInt(args[0], 10);

        System.out.println();    

        Maze maze = new Maze(complexity);

        if (complexity < 15)
        {
            System.out.println();
            maze.display();
            System.out.println();    
        }

        else
        {
            System.out.println("Maze too large to display on command line.");
        }

    }
}