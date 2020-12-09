import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class Main {
    static String filePath="/dblp_papers_v11.txt";
    public static void main(String[] args) {
        /*BufferedReader br = Files.newBufferedReader(Paths.get("dblp_papers_v11.txt"));
        Stream<String> lines = br.lines();
        lines.forEach(System.out::println);*/
        //Files.lines(Path.of(Main.filePath)).flatMap(line-> Stream.of(line.split("\n"))).forEach(System.out::println);

        Stream<String> lines = null;
        try {
            lines = Files.lines(Path.of(Main.filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.forEach(System.out::println);
    }
}
