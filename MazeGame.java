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
        if (!fileFolder.exists()) fileFolder.mkdir();

        String mazeName = "";

        for (int i = 1; i <= 10; ++i)
        {
            mazeName = fileFolderName + "/maze_" + i;

            complexity = 20;

            System.out.println("Generating maze " + i + " with complexity " + complexity);

            Maze maze = new Maze(complexity, mazeName);
        }

        // SVG testSVG = new SVG("test.svg");
        // testSVG.writeFile();

        // Maze maze = new Maze(10, "svgTestMaze");
    }
}