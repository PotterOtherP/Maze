import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MazeGame {
    
    public static void main(String[] args)
    {
        int complexity = Maze.COMPLEXITY_DEFAULT;

        if (args.length > 0)
            complexity = Integer.parseInt(args[0], 10);

        String fileFolderName = "maze_images";

        File fileFolder = new File(fileFolderName);
        fileFolder.mkdir();

        String mazeName = "";

        for (int i = 1; i <= 20; ++i)
        {
            mazeName = fileFolderName + "\\maze_" + i + ".bmp";

            complexity = 15;

            Maze maze = new Maze(complexity, mazeName);
        }

        // Maze maze = new Maze(complexity, "test_name.bmp");



        // if (complexity < 15)
        // {
        //     System.out.println();
        //     maze.display();
        //     System.out.println();    
        // }

        // // else
        // // {
        // //     System.out.println("Maze too large to display on command line.");
        // // }

    }
}