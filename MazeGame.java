public class MazeGame {
    
    public static void main(String[] args)
    {
        int argComp = 0;
        argComp = Integer.parseInt(args[0], 10);

        int complexity = Math.max(argComp, 3);
        complexity = Math.min(argComp, 100);

        System.out.println("Rows: " + complexity * 3);    
        System.out.println("Columns: " + complexity * 4);    
        System.out.println();    

        Maze maze = new Maze(complexity);

        if (complexity < 15)
        {
            System.out.println();
            maze.display();
            System.out.println();    
        }
    }
}