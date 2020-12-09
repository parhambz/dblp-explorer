
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class Util{
    static String filePath="C:\\Users\\bghpa\\Desktop\\New folder\\dblp-explorer\\src\\main\\java\\dblp_papers_v11.txt";
    public static Boolean isContain(String keyword,String article){
        return article.contains(keyword);
    }
}

public class Main {

    public static void main(String[] args) throws IOException{
        int n=5;
        String keyword="parham";

        List<String> tempId=new ArrayList<String>();
        Stream<String> articles = Files.lines(Path.of(Util.filePath));
        articles.filter(articleStr->Util.isContain(keyword,articleStr))
                .map(s->{ return new JSONObject(s); })
                .forEach(art->
                {
                    //System.out.println(art) ;
                    JSONArray tempRef=null;
                    try {
                        tempRef=(JSONArray) art.get("references");
                        tempRef
                                .toList()
                                .stream()
                                .forEach(
                                        tId->{tempId.add((String)tId);}
                                        );
                        }catch (JSONException e){}
                });
        Stream<Integer> otherTires;
    }
}
