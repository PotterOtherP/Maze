public class MazeGame {
    
    public static void main(String[] args)
    {
        System.out.println("Rows: " + Maze.ROWS_DEFAULT);    
        System.out.println("Columns: " + Maze.COLUMNS_DEFAULT);    
        System.out.println();    

        Maze maze = new Maze(15, 20);

        System.out.println();    
        maze.display();

        System.out.println();    
        System.out.println("Maze complete: " + maze.isComplete());
    }
}