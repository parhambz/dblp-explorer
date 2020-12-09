import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class Main {
    static String filePath="C:\\Users\\bghpa\\Desktop\\New folder\\dblp-explorer\\src\\main\\java\\dblp_papers_v11.txt";
    public static Boolean isContain(String keyword,JSONObject j){
        return keyword.contains("");
    }
    public static void main(String[] args) {
        /*BufferedReader br = Files.newBufferedReader(Paths.get("dblp_papers_v11.txt"));
        Stream<String> lines = br.lines();
        lines.forEach(System.out::println);*/
        //Files.lines(Path.of(Main.filePath)).flatMap(line-> Stream.of(line.split("\n"))).forEach(System.out::println);

        Stream<String> lines = null;
        try {
            lines = Files.lines(Path.of(Main.filePath));
            lines.map(s->{
                return new JSONObject(s);
            }).map(j -> {return j.get("id");}).forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
